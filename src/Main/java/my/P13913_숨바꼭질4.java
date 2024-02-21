package my;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class P13913_숨바꼭질4 {
	static Node loc[];
	static Queue<Node> pq;
	static Node cur;
	static int N;
	static int M;
	static Scanner sc;
	static boolean visited[];
	static int next;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		N = sc.nextInt(); // 수빈이의 위치
		M = sc.nextInt(); // 동생의 위치
		loc = new Node[100001]; // 최소 시간 배열
		pq = new LinkedList<>();
		sc.close();
		visited = new boolean[100001];
		Stack<Integer> stack = new Stack<>(); // 경로 저장 스택

		for (int i = 0; i < 100001; i++) {
			loc[i] = new Node(i, 0, 0);
		}

		dihkstra(N, M);
		Node answer = loc[M];
		Node r = loc[M];

		System.out.println(loc[answer.node].sec); // 답 출력

		stack.add(answer.node); // 경로 출력
		for (int i = 0; i < loc[answer.node].sec; i++) {
			stack.add(r.forward);
			r = loc[r.forward];
		}

		while (!stack.isEmpty()) {
			System.out.print(stack.pop() + " ");
		}
	}

	private static void dihkstra(int n, int m) {
		pq.add(new Node(n, 0, 0)); // 수빈이의 처음 위치 pq에 삽입
		loc[n].sec = 0;
		visited[n] = true;

		while (!pq.isEmpty()) {
			cur = pq.poll();
			
			if (cur.node == M) {
				return;
			}

			for (int i = 0; i < 3; i++) { // +1, -1, *2의 위치로 이동
				next = move(cur.node, i);
				if (next >= 0 && next <= 100000) { // next가 범위 내이면
					if (!visited[next]) {
						visited[next] = true;
						loc[next].sec = loc[cur.node].sec + 1;
						loc[next].forward = cur.node;
						pq.add(new Node(next, loc[next].sec, cur.node));
					}
				}
			}
		}
		return;
	}

	private static int move(int node, int i) {
		switch (i) {
		case 0:
			return node - 1;
		case 1:
			return node + 1;
		case 2:
			return node * 2;
		}
		return 0;
	}

	static class Node {
		int node; // 위치
		int forward; // 이전 노드의 번호
		int sec;

		Node(int node, int sec, int forward) {
			this.node = node;
			this.sec = sec;
			this.forward = forward;
		}
	}
}
