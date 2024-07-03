package ctChap08;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P1753_최소비용구하기 {
	static int N;
	static ArrayList<Edge>[] list;
	static boolean visited[];
	static int cost[];
	static PriorityQueue<Edge> pq;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();	// 도시 수
		int M = sc.nextInt();	// 버스 수
		list = new ArrayList[N + 1];
		visited = new boolean[N + 1];
		cost = new int[N + 1];
		pq = new PriorityQueue<>();
		
		for (int i = 1; i <= N; i++) {	// list배열 초기화
			list[i] = new ArrayList<>();
		}
		for (int i = 1; i <= N; i++) {	// cost배열 값 최댓값을 설정
			cost[i] = Integer.MAX_VALUE;
		}
		
		for (int i = 0; i < M; i++) {	// 버스 정보 입력받기
			int s = sc.nextInt();	// 출발 도시
			int e = sc.nextInt();	// 도착 도시
			int c = sc.nextInt();	// 버스 비용
			list[s].add(new Edge(e, c));
		}
		
		int start = sc.nextInt();	// 출발점
		int end = sc.nextInt();		// 도착점
		sc.close();

		int leastcost = dijkstra(start, end);
		
		System.out.println(leastcost);
		
	}
	
	// 다익스트라
	private static int dijkstra(int start, int end) {
		pq.add(new Edge(start, 0));	// 출발점 pq에 삽입
		cost[start] = 0;
		
		while (!pq.isEmpty()) {
			Edge now = pq.poll();
			int n_c = now.city;
			if (visited[n_c]) {
				continue;
			}
			visited[n_c] = true;
			
			for (Edge next : list[n_c]) {
				int next_city = next.city;
				int buscost = next.cost;
				if (cost[next_city] > cost[n_c] + buscost) {
					cost[next_city] = cost[n_c] + buscost;
					pq.add(new Edge(next_city, cost[next_city]));
				}
			}
		}
		return cost[end];
	}

	static class Edge implements Comparable<Edge>{
		int city;
		int cost;
		
		Edge(int city, int cost) {
			this.city = city;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Edge e) {
			if (this.cost > e.cost)
				return 1;
			else if (this.cost < e.cost)
				return -1;
			else return 0;
		}
	}
}
