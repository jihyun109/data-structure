package my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P1238_파티 {
	static ArrayList<Node>[] list;
	static boolean visited[];
	static int oneWayTime[];
	static int totalTime[];
	static PriorityQueue<Node> pq;
	static int N;
	static int X;


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt(); // 학생 수
		int M = sc.nextInt(); // 도로 수
		X = sc.nextInt(); // 파티 마을
		list = new ArrayList[N + 1];
		visited = new boolean[N + 1]; // 방문 배열
		oneWayTime = new int[N + 1]; // 편도 시간 임시 저장 배열
		totalTime = new int[N + 1]; // 왕복 시간 저장 배열

		pq = new PriorityQueue<>();

		for (int i = 1; i <= N; i++) { // list 배열 선언
			list[i] = new ArrayList<>();
		}
		
		Arrays.fill(oneWayTime, Integer.MAX_VALUE);

		for (int i = 0; i < M; i++) { // 도로 정보 입력 받기
			int s = sc.nextInt(); // 도로 시작점
			int e = sc.nextInt(); // 도로 도착점
			int t = sc.nextInt(); // 소요 시간
			list[s].add(new Node(e, t));
		}
		sc.close();

		// 파티에 가는데 걸리는 시간을 구해 totalTime배열에 업데이트
		for (int i = 1; i <= N; i++) {
			if (i != X) {
				dijkstra(i);
				totalTime[i] = oneWayTime[X];
				// 걸리는 시간 배열, 방문배열 초기화
				Arrays.fill(oneWayTime, Integer.MAX_VALUE);
				Arrays.fill(visited, false);
			}
		}
		
		dijkstra(X);	// X(파티 장소에서 각 학생들의 집까지 걸리는 시간 구하기
		// totalTime 배열에 집으로 오는 데 걸리는 시간 더하기.
		for (int i = 1; i <= N; i++) {
		    totalTime[i] += oneWayTime[i];
		}
		
		int maxTime = 0;
		for (int i = 1; i <= N; i++) {
		    maxTime = Math.max(maxTime, totalTime[i]);
		}
		System.out.println(maxTime);
	}

	private static void dijkstra(int start) {
		pq.add(new Node(start, 0));
		oneWayTime[start] = 0;
		
		while (!pq.isEmpty()) {
			Node cur = pq.poll();

			if (visited[cur.node]) {
				continue;
			}
			visited[cur.node] = true;
			
			for (Node next : list[cur.node]) {
				int n_n = next.node;
				int n_T = next.T;
				if (oneWayTime[n_n] > oneWayTime[cur.node] + n_T) {
					oneWayTime[n_n] = oneWayTime[cur.node] + n_T;
					pq.add(new Node(n_n, oneWayTime[n_n]));
				}
			}
		}
	}

	static class Node implements Comparable<Node> {
		int node;
		int T;	// 걸린 시간

		Node(int node, int T) {
			this.node = node;
			this.T = T;
		}

		@Override
		public int compareTo(Node o) {
			if (this.T > o.T) {
				return 1;
			} else if (this.T < o.T) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
