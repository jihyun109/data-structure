package SWEA.통행료할인;

import java.util.*;

class UserSolution {
	private Map<Integer, Map<Integer, Integer>> roads; // 도로 정보 저장.(k: 출발 도시, v: 도착도시 & cost)
	private Map<Integer, Road> roadsById; // 도로 Id 에 따라 도로 정보 저장
	private int cityN;	// 총 도시의 수

	public void init(int N, int K, int mId[], int sCity[], int eCity[], int mToll[]) {
		cityN = N;
		roads = new HashMap<>();
		roadsById = new HashMap<>();

		for (int i = 0; i < N; i++) {
			roads.put(i, new HashMap<>());
		}

		// 도로 정보 입력받기
		for (int i = 0; i < K; i++) {
			int s = sCity[i];
			int e = eCity[i];
			int cost = mToll[i];
			int roadId = mId[i];

			roads.get(s).put(e, cost);
			roadsById.put(roadId, new Road(s, e));
		}
	}

	public void add(int mId, int sCity, int eCity, int mToll) {
		roads.get(sCity).put(eCity, mToll);
		roadsById.put(mId, new Road(sCity, eCity));
	}

	public void remove(int mId) {
		Road road = roadsById.get(mId);
		int sCity = road.getSCity();
		int eCity = road.getECity();

		roads.get(sCity).remove(eCity);
		roadsById.remove(mId);
	}

	public int cost(int M, int sCity, int eCity) {
		PriorityQueue<Status> pq = new PriorityQueue<>();
		int[][] costs = new int[M + 1][cityN]; // costs[i][j]: i 번 상품권을 사용해 sCity 에서 j 까지의 최소 비용
		for (int i = 0; i <= M; i++) {
			for (int j = 0; j < cityN; j++) {
				costs[i][j] = Integer.MAX_VALUE;
				if (j == sCity) {
					costs[i][j] = 0;
				}
			}
		}

		pq.add(new Status(0, sCity, 0));

		while (!pq.isEmpty()) {
			Status cur = pq.poll();
			int curCity = cur.getCity();
			int curCost = cur.getCost();
			int discountN = cur.getDistcountN();

			// 도착 도시에 다다른 경우
			if (curCity == eCity) {
				return curCost;
			}
			
			if (costs[discountN][curCity] < curCost) {
				continue;
			}

			for (Map.Entry<Integer, Integer> next : roads.get(curCity).entrySet()) {
				int nextCity = next.getKey();
				int cost = next.getValue();

				// discount
				if (discountN < M) {
					int nextCost = curCost + (cost / 2);
					int nextDiscountN = discountN + 1;
					if (nextCost < costs[nextDiscountN][nextCity]) {
						pq.add(new Status(nextCost, nextCity, nextDiscountN));
						costs[nextDiscountN][nextCity] = nextCost;
					}
				}
				
				// not discount
				int nextCost = curCost + cost;
				if (nextCost < costs[discountN][nextCity]) {
					pq.add(new Status(nextCost, nextCity, discountN));
					costs[discountN][nextCity] = nextCost;
				}
			}
		}

		return -1;
	}
}

class Road {
	private int sCity;
	private int eCity;

	public Road(int startCity, int endCity) {
		this.sCity = startCity;
		this.eCity = endCity;
	}

	public int getSCity() {
		return this.sCity;
	}

	public int getECity() {
		return this.eCity;
	}
}

class Status implements Comparable<Status> {
	private int cost;
	private int city;
	private int discountN; // 할인권 사용 횟수

	public Status(int cost, int city, int discountN) {
		this.cost = cost;
		this.city = city;
		this.discountN = discountN;
	}

	public int getCost() {
		return this.cost;
	}

	public int getCity() {
		return this.city;
	}

	public int getDistcountN() {
		return this.discountN;
	}

	@Override
	public int compareTo(Status o) {
		return this.cost - o.cost;
	}
} 