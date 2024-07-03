package my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P1753_최단경로 {
	static ArrayList<Node>[] list;
	static boolean visited[];
	static int dist[];
	static PriorityQueue<Node> pq;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int V = sc.nextInt(); // 정점의 수
		int E = sc.nextInt(); // 간선의 수
		int K = sc.nextInt(); // 시작 정점 번호
		list = new ArrayList[V + 1];
		visited = new boolean[V + 1];
		dist = new int[V + 1];
		pq = new PriorityQueue<>();

		for (int i = 1; i <= V; i++) { // list배열 선언
			list[i] = new ArrayList<>();
		}

		Arrays.fill(dist, Integer.MAX_VALUE); // dist 배열 최댓값으로 설정

		for (int i = 0; i < E; i++) { // 간선 정보 입력받기
			int u = sc.nextInt(); // 출발 노드
			int v = sc.nextInt(); // 도착 노드
			int w = sc.nextInt(); // 가중치

			list[u].add(new Node(v, w));
		}

		dijkstra(K);
		for (int i = 1; i <= V; i++) {
			if (visited[i]) {
				System.out.println(dist[i]);
			} else {
				System.out.println("INF");
			}
		}
	}

	private static void dijkstra(int k) {
		pq.add(new Node(k, 0));
		dist[k] = 0;

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int c_n = cur.node;
			if (visited[c_n]) {
				continue;
			}
			visited[c_n] = true;

			for (Node next : list[c_n]) {
				int n_n = next.node;
				int n_d = next.dist;
				if (dist[n_n] > dist[c_n] + n_d) {
					dist[n_n] = dist[c_n] + n_d;
					pq.add(new Node(n_n, dist[n_n]));
				}
			}
		}
	}

	static class Node implements Comparable<Node> {
		int node;
		int dist;

		Node(int node, int dist) {
			this.node = node;
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
