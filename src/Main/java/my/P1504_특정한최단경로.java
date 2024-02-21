package my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P1504_특정한최단경로 {
	static ArrayList<Node>[] list;
	static boolean[] visited;
	static PriorityQueue<Node> pq;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(); // 정점 개수
		int E = sc.nextInt(); // 엣지 개수
		list = new ArrayList[N + 1];
		visited = new boolean[N + 1];
		pq = new PriorityQueue<Node>();
		int D1[] = new int[N + 1]; // 1에서 다른 노드들까지의 최소거리 저장 배열
		int D2[] = new int[N + 1]; // V1에서 다른 노드들까지의 최소거리 저장 배열
		int D3[] = new int[N + 1]; // V2에서 다른 노드들까지의 최소거리 저장 배열
		Arrays.fill(D1, Integer.MAX_VALUE); // 모든 최단거리 배열값 최댓값을 설정
		Arrays.fill(D2, Integer.MAX_VALUE);
		Arrays.fill(D3, Integer.MAX_VALUE);

		for (int i = 1; i <= N; i++) { // list배열 선언
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < E; i++) { // 엣지 정보 입력받기
			int s = sc.nextInt(); // 출발 정점
			int e = sc.nextInt(); // 도착 정점
			int d = sc.nextInt(); // 두 정점 사이의 거리
			list[s].add(new Node(e, d)); // 양방향 그래프이므로
			list[e].add(new Node(s, d));
		}

		int V1 = sc.nextInt(); // 꼭 통과해야하는 정점1
		int V2 = sc.nextInt(); // 꼭 통과해야하는 정점2

		sc.close();

		Dijkstra(1, D1);
		Dijkstra(V1, D2);
		Dijkstra(V2, D3);

		if ((D1[V1] == Integer.MAX_VALUE || D2[V2] == Integer.MAX_VALUE || D3[N] == Integer.MAX_VALUE)
				&& (D1[V2] == Integer.MAX_VALUE || D3[V1] == Integer.MAX_VALUE || D2[N] == Integer.MAX_VALUE)) {
			System.out.println(-1);
		} else {
			System.out.println(Math.min(D1[V1] + D2[V2] + D3[N], D1[V2] + D3[V1] + D2[N]));
		}
	}

	private static void Dijkstra(int i, int[] D) {
		pq.add(new Node(i, 0));
		D[i] = 0;

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int c_n = cur.num;

			if (visited[c_n]) {
				continue;
			}
			visited[c_n] = true;

			for (Node next : list[c_n]) {
				int n_n = next.num;
				int n_d = next.dist;
				if (D[n_n] > D[c_n] + n_d) {
					D[n_n] = D[c_n] + n_d;
					pq.add(new Node(n_n, D[n_n]));
				}
			}
		}
		Arrays.fill(visited, false); // visited배열 초기화
	}

	static class Node implements Comparable<Node> {
		int num; // 노드 번호
		int dist; // 거리

		Node(int num, int dist) {
			this.num = num;
			this.dist = dist;
		}

		@Override
		public int compareTo(Node o) {
			if (this.dist > o.dist) {
				return 1;
			} else if (this.dist < o.dist) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
