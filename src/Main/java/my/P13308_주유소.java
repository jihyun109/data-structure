package my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P13308_주유소 {
	static ArrayList<Edge>[] Road;
	static long[][] cost;
	static boolean[][] visited;
	static int[] gasStation;
	static PriorityQueue<City> pq;
	static int N;	
	static int M;

	public static void main(String[] args) {
		// init start
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt(); // 도시의 수
		M = sc.nextInt(); // 도로의 수
		Road = new ArrayList[N + 1]; // 도로 정보 리스트
		gasStation = new int[N + 1]; // 각 도시 주유소의 가격 저장 배열
		pq = new PriorityQueue<>();

		for (int i = 0; i <= N; i++) { // Road 배열 선언
			Road[i] = new ArrayList<>();
		}

		int maxPrice = 0; // 주유소 가격의 최댓값
		for (int i = 1; i <= N; i++) { // 각 도시 주유소의 가격 입력받기
			int price = sc.nextInt();
			gasStation[i] = price;
			
			if (price > maxPrice) { // maxPrice 업데이트
				maxPrice = price;
			}
		}

		for (int i = 0; i < M; i++) { // 도로 정보 입력받기
			int s = sc.nextInt(); // 출발 도시 번호
			int e = sc.nextInt(); // 도착 도시 번호
			int d = sc.nextInt(); // 도로의 길이
			Road[s].add(new Edge(e, d));
			Road[e].add(new Edge(s, d));
		}
		sc.close();
		// init end

		cost = new long[N + 1][maxPrice + 1]; // 도시1부터 각 도시까지의 최소 비용 저장 배열
		visited = new boolean[N + 1][maxPrice + 1]; // 방문 배열

		for (int i = 0; i <= N; i++) { // cost 배열 최댓값으로 설정
			Arrays.fill(cost[i], Long.MAX_VALUE);
		}


		long answer = Dijkstra();
		System.out.println(answer); // 답 출력
	}

	private static long Dijkstra() {
		pq.add(new City(1, gasStation[1], 0)); // 출발 도시 pq에 넣고 비용 입력
		cost[1][gasStation[1]] = 0;

		while (!pq.isEmpty()) {
			City cur = pq.poll();
			int curCity = cur.num;	// 현재 도시 번호
			int curSelectedOil = cur.selectedOil;	// 현제 선택된 기름 가격
			
			if (curCity == N) {
				return cost[curCity][curSelectedOil];
			}

			if (visited[curCity][curSelectedOil]) {	// 방문했으면 넘어감
				continue;
			}
			
			visited[curCity][curSelectedOil] = true;	// 방문 처리

			for (Edge nextRoad : Road[curCity]) {
				int nextCity = nextRoad.destination;	// 다음 도시
				long nextCost = cost[curCity][curSelectedOil] + (long)nextRoad.dist * (long)curSelectedOil;	// curCity를 거쳐간 다음 도시까지의 비용
				
				// 다음 도시까지의 최소 비용 업데이트
				if (cost[nextCity][curSelectedOil] > nextCost) {
					cost[nextCity][curSelectedOil] = nextCost;
					
					// 다음 도시에서 사용할 최소 기름 가격(selectdOil) 업데이트
					int nextOil = 0;
					if (curSelectedOil > gasStation[nextCity]) {	// 다음 도시의 기름가격이 더 싸면
						nextOil = gasStation[nextCity];
						cost[nextCity][nextOil] = nextCost;
					} else {	// 현제 선택한 기름 가격이 더 싸면
						nextOil = curSelectedOil;
					}

					pq.add(new City(nextCity, nextOil, nextCost));
				}
			}
		}
		return 0;
	}

	static class Edge {
		int destination;
		int dist;

		public Edge(int destination, int dist) {
			this.destination = destination;
			this.dist = dist;
		}
	}

	static class City implements Comparable<City> {
		int num; // 도로와 이어진 도시 번호
		int selectedOil; // 방문한 주유소 중 최소 기름 가격
		long sumCost; // 현재까지 요금

		City(int num, int selectedOil, long sumCost) {
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
