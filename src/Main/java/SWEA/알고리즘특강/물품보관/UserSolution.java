package SWEA.알고리즘특강.물품보관;

class UserSolution {
    private static int[] storage;   // 각 칸에 들어있는 물품 수 저장 배열
    private static Node[] segTree;   // 최댓값 세그먼트 트리
    private static int storageN;    // 물품저장소 칸 수
    private static int tentSize;    // 필요한 천막의 크기

    public void init(int N) {
        storageN = N;
        storage = new int[N + 1];
        segTree = new Node[N * 4];
        tentSize = 0;
        for (int i = 1; i < segTree.length; i++) {
            segTree[i] = new Node(0, 0, storageN);
        }
        return;
    }

    public int stock(int mLoc, int mBox) {
        storage[mLoc] += mBox;
        updateTree(1, 1, storageN, mLoc, storage[mLoc]);    // 세그먼트 트리 업데이트

        // mLoc 의 물품 수가 mLoc 의 왼쪽, 오른쪽 구간의 최댓값보다 작으면 update 필요 X
        int leftE = mLoc > 1 ? mLoc - 1 : 1;
        int rightS = mLoc < storageN ? mLoc + 1 : storageN;
        Node leftMax = findMax(1, 1, storageN, 1, leftE);
        Node rightMax = findMax(1, 1, storageN, rightS, storageN);
        if (Math.min(leftMax.value, rightMax.value) > storage[mLoc]) {
            return tentSize;
        }

        // 아닌 경우 다시 계산
        calTentSize();
        return tentSize;
    }

    public int ship(int mLoc, int mBox) {
        // mLoc 의 물품 수가 mLoc 의 왼쪽, 오른쪽 구간의 최댓값보다 작으면 update 필요 X
        int leftE = mLoc > 1 ? mLoc - 1 : 1;
        int rightS = mLoc < storageN ? mLoc + 1 : storageN;

        storage[mLoc] -= mBox;
        updateTree(1, 1, storageN, mLoc, storage[mLoc]);    // 트리 업데이트

        calTentSize();
        return tentSize;
    }

    public int getHeight(int mLoc) {
        return storage[mLoc];
    }


    private static void updateTree(int node, int s, int e, int i, int value) {
        if (s == e) {
            segTree[node] = new Node(value, i, i);
            return;
        }

        // 자식 노드 update
        int m = (s + e) / 2;
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;
        if (i <= m) {
            updateTree(leftChild, s, m, i, value);
        } else {
            updateTree(rightChild, m + 1, e, i, value);
        }

        // 현재 노드 update
        segTree[node] = calParent(segTree[leftChild], segTree[rightChild]);
    }

    private static Node findMax(int node, int ts, int te, int qs, int qe) {
        // 트리 구간이 질의 구간에 완전히 포함되면 값 return
        if (ts >= qs && qe >= te) return segTree[node];

        // 트리구간과 질의 구간이 전혀 겹치지 않으면
        if (te < qs || qe < ts) {
            return new Node(0, 0, 0);
        }

        // 일부 겹침
        int m = (ts + te) / 2;
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;
        Node maxL = findMax(leftChild, ts, m, qs, qe);
        Node maxR = findMax(rightChild, m + 1, te, qs, qe);
        return calParent(maxL, maxR);
    }

    private static Node calParent(Node leftC, Node rightC) {
        if (leftC.value > rightC.value) {
            return leftC;
        } else if (leftC.value < rightC.value) {
            return rightC;
        } else {
            return new Node(leftC.value, leftC.lIdx, rightC.rIdx);
        }
    }

    private static void calTentSize() {
        // 가장 최댓값에 해당되는 천막 면적 계산
        Node max = findMax(1, 1, storageN, 1, storageN);    // 최댓값
        tentSize = max.value * (max.rIdx - max.lIdx + 1);

        // 모든 천막 면적이 계산될 때까지 계산된 부분의 왼쪽, 오른쪽 최댓값 구해서 tentSize 에 더하기
        int left = max.lIdx - 1;    // 계산된 천막의 왼쪽 칸
        int right = max.rIdx + 1;   // 계산된 천막의 오른쪽 칸

        // 왼쪽 면적 계산
        while (left >= 1) {
            Node lMax = findMax(1, 1, storageN, 1, left);
            tentSize += (left - lMax.lIdx + 1) * lMax.value;
            left = lMax.lIdx - 1;
        }

        // 오른쪽 면적 계산
        while (right <= storageN) {
            Node rMax = findMax(1, 1, storageN, right, storageN);
            tentSize += (rMax.rIdx - right + 1) * rMax.value;
            right = rMax.rIdx + 1;
        }
    }

    private static class Node {
        int value;  // 최댓값
        int lIdx;    // 현재 구간 최댓값의 가장 왼쪽 idx
        int rIdx;    // 현재 구간 최댓값의 가장 오른쪽 idx

        private Node(int value, int lIdx, int rIdx) {
            this.value = value;
            this.lIdx = lIdx;
            this.rIdx = rIdx;
        }
    }
}