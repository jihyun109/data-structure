package my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P13308_주유소2 {
	static ArrayList<Edge>[] Road;
	static boolean[][] visited;
	static long[][] cost;
	static PriorityQueue<City> pq;
	static int[] gasStation; 
	static int N;
	static int M;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt(); // 도시 수
		M = sc.nextInt(); // 도로 수

		// 각 도시의 oilPrice 입력 받기
		gasStation = new int[N + 1]; // 기름 가격 저장 배열
		int maxOilPrice = 0; // 기름 가격의 최댓값
		for (int i = 1; i <= N; i++) {
			gasStation[i] = sc.nextInt();
			maxOilPrice = Math.max(maxOilPrice, gasStation[i]);
		}

		Road = new ArrayList[N + 1]; // 도로 정보 저장 배열
		visited = new boolean[N + 1][maxOilPrice + 1]; // 방문 처리 배열
		cost = new long[N + 1][maxOilPrice + 1]; // 최소 비용 저장 배열
		pq = new PriorityQueue<City>();
		for (int i = 1; i <= N; i++) { // City 배열 초기화
			Road[i] = new ArrayList<Edge>();
		}

		// 도로 정보 입력 받기
		for (int i = 0; i < M; i++) {
			int s = sc.nextInt(); // 출발 도시
			int e = sc.nextInt(); // 도착 도시
			int d = sc.nextInt(); // 도로 길이

			Road[s].add(new Edge(e, d));
			Road[e].add(new Edge(s, d));
		}
		sc.close();

		// cost 배열 최댓값으로 설정
		for (int i = 0; i <= N; i++) {
			Arrays.fill(cost[i], Long.MAX_VALUE);
		}

		long answer = Dijkstra();
		System.out.println(answer);
	}

	private static long Dijkstra() {
		// 출발 노드 pq에 삽입
		pq.add(new City(1, gasStation[1], 0));
		cost[1][gasStation[1]] = 0;

		while (!pq.isEmpty()) {
			City curCity = pq.poll();

			if (curCity.num == N) { // 답 return
				return cost[N][curCity.selectedOil];
			}

			if (visited[curCity.num][curCity.selectedOil]) { // 현재 노드를 방문했으면 continue
				continue;
			}

			visited[curCity.num][curCity.selectedOil] = true; // 방문 처리

			for (Edge nextRoad : Road[curCity.num]) {
				// next에 가져갈 selectedOil 가격 정하기
				int nextOil = Math.min(curCity.selectedOil, gasStation[nextRoad.destination]);
				long nextCost = curCity.sumCost + (long)curCity.selectedOil * (long)nextRoad.dist; // next까지의 비용

				// next까지의 최소 비용 업데이트
				if (cost[nextRoad.destination][nextOil] > nextCost) {
					cost[nextRoad.destination][nextOil] = nextCost;
					long nextSumCost = nextCost;
					pq.add(new City(nextRoad.destination, nextOil, nextSumCost));
				}
			}
		}
		return 0;
	}
	
	static class Edge  {
		int destination;	// 도착지
		int dist;	// 도로 길이
		
		public Edge(int destination, int dist) {
			this.destination = destination;
			this.dist = dist;
		}
	}

	static class City implements Comparable<City> {
		int num; // 도시 번호
		int selectedOil; // 지나온 도시 중 가장 가격이 싼 곳의 기름 가격
		long sumCost; // 도시1부터 num까지 총 비용

		public City(int num, int selectedOil, long sumCost) {
			this.num = num;
			this.selectedOil = selectedOil;
			this.sumCost = sumCost;
		}

		@Override
		public int compareTo(City o) {
			if (this.sumCost > o.sumCost) {
				return 1;
			} else if (this.sumCost < o.sumCost) {
				return -1;
			} else {
				return Integer.compare(this.selectedOil, o.selectedOil);
			}
		}
	}
}
