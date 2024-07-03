package ctChap08;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class P18352_특정거리도시찾기 {
	static ArrayList<Integer>[] city;
	static PriorityQueue<Integer> pq; // 거리가 K인 도시 번호 저장 큐
	static int N;
	static int K;
	static int X;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt(); // 도시의 개수
		int M = sc.nextInt(); // 도로 개수
		K = sc.nextInt(); // 거리 정보
		X = sc.nextInt(); // 출발 도시 번호
		city = new ArrayList[N + 1];
		pq = new PriorityQueue<>();

		for (int i = 1; i <= N; i++) { // num 배열 초기화
			city[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < M; i++) { // arrayList에 도로 정보 저장
			int s = sc.nextInt(); // 출발 도시
			int e = sc.nextInt(); // 도착 도시
			city[s].add(e);
		}

		BFS(X); // X로 부터거리가 K인 도시를 pq에 저장

		if (pq.isEmpty()) { // 거리가 K인 도시가 하나도 없으면
			System.out.println(-1);
		} else {	// 거리가 K인 도시가 있으면
			while (!pq.isEmpty()) { 
				System.out.println(pq.poll());
			}
		}
	}

	private static void BFS(int x) {
		Queue<Node> que = new LinkedList<Node>();
		boolean[] visited = new boolean[N + 1];
		que.offer(new Node(x, 0));	// 출발 도시 que에 offer
		visited[x] = true; // 출발 도시 visited 처리

		while (!que.isEmpty()) {
			Node now = que.poll();
			for (int next : city[now.n]) {
				// next에 방문하지 않았으면
				if (!visited[next]) {
					int nextDist = now.dist + 1;
					que.offer(new Node(next, nextDist));
					visited[next] = true;
					if (nextDist == K) { // K를 만족하면 pq에 넣기
						pq.offer(next);
					}
				}
			}
		}
	}

	static class Node {
		int n = 0; // 도시번호
		int dist = 0; // 이동 거리

		public Node(int n, int dist) {
			this.n = n;
			this.dist = dist;
		}
	}
}
