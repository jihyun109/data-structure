package 블록쌓기게임;

import java.util.*;

class UserSolution {
	private SegmentTree segmentTree;
	private int C;
	private long blockSum;
	private final int NUM = 1000000;

	void init(int C) {
		segmentTree = new SegmentTree(C);
		this.C = C;
		blockSum = 0;
	}

	Solution1.Result dropBlocks(int mCol, int mHeight, int mLength) {
		Solution1.Result ret = new Solution1.Result();

		// 블록이 떨어지는 가장 왼쪽 인덱스와 가장 오른쪽 인덱스
		int blockSIdx = mCol;
		int blockEIdx = mCol + mLength - 1;
		int blockN = mHeight * mLength;
		blockSum += blockN;

		segmentTree.update(0, C - 1, 1, blockSIdx, blockEIdx, mHeight);

		Node value = segmentTree.getValue();
		int max = value.getMax();
		int min = value.getMin();
		long leftBlockN = blockSum - (min * C);

		ret.top = max-min;
		ret.count = (int) (leftBlockN % NUM);

		return ret;
	}
}

class SegmentTree {
	Node[] tree;
	int[] lazy;
	boolean[] over;

	public SegmentTree(int n) {
		tree = new Node[n * 4];
		lazy = new int[n * 4];
		over = new boolean[n * 4];
		over[1] = true;
	}

	// l~r 의 값을 value 만큼 더하기
	public void update(int s, int e, int node, int l, int r, int value) {
		lazyDown(s, e, node);

		if (e < l || s > r) {
			return;
		}

		// lazy
		// s~e 가 l~r 에 완전히 포함되는 경우
		if (s >= l && e <= r) {
			lazy[node] += value;
			lazyDown(s, e, node);
			return;
		}

		int m = s + (e - s) / 2;
		int lChild = node * 2;
		int rChild = node * 2 + 1;

		update(s, m, lChild, l, r, value);
		update(m + 1, e, rChild, l, r, value);

		int lMax = tree[lChild].getMax();
		int lMin = tree[lChild].getMin();
		int rMax = tree[rChild].getMax();
		int rMin = tree[rChild].getMin();

		tree[node].setMax(Math.max(lMax, rMax));
		tree[node].setMin(Math.min(lMin, rMin));
	}

	public Node getValue() {
		return tree[1];
	}

	private void lazyDown(int s, int e, int node) {
		if (over[node]) {
			tree[node] = new Node(0, 0);
			
			if (s != e) {
				over[getLeftChild(node)] = true;
				over[getRightChild(node)] = true;
			}
			
			over[node] = false;
		}
		
		
		if (lazy[node] != 0) {
			// 리프노드가 아닌 경우 자식에게 lazy 물려줌
			if (s != e) {
				lazy[getLeftChild(node)] += lazy[node];
				lazy[getRightChild(node)] += lazy[node];
			}

			int originMax = tree[node].getMax();
			int originMin = tree[node].getMin();
			tree[node].setMax(originMax + lazy[node]);
			tree[node].setMin(originMin + lazy[node]);
			
			lazy[node] = 0;
		}
	}

	private int getLeftChild(int n) {
		return 2 * n;
	}

	private int getRightChild(int n) {
		return 2 * n + 1;
	}
}

class Node {
	private int minValue;
	private int maxValue;

	public Node(int min, int max) {
		this.minValue = min;
		this.maxValue = max;
	}

	public int getMin() {
		return this.minValue;
	}

	public int getMax() {
		return this.maxValue;
	}

	public void setMin(int n) {
		this.minValue = n;
	}

	public void setMax(int n) {
		this.maxValue = n;
	}
}
