package SWEA.국가행정;

import java.util.*;

class UserSolution {
	private SegmentTree times; 						// 이동 시간의 구간합 세그먼트 트리
	private int[] populations; 						// 인구 수 저장
	private int[] roadN; 							// 도로를 잇는 구간의 차선 수
	private TreeSet<Road> roadsOrderByTime; 		// 도시 사이의 도로 이동 시간 내림차순 저장
	private int cityN; 								// 도시의 수
	private final int MAX_POPULATION_OF_CITY = 1000; // 도시의 최대 인구 수

	void init(int N, int mPopulation[]) {
		cityN = N;
		populations = mPopulation;

		// 도시 간의 이동시간 저장
		times = new SegmentTree(N - 1);
		roadN = new int[N - 1];
		roadsOrderByTime = new TreeSet<>();

		for (int i = 0; i < N - 1; i++) {
			int city1Population = mPopulation[i];
			int city2Population = mPopulation[i + 1];

			int dueTime = city1Population + city2Population;
			times.update(i, dueTime);
			roadsOrderByTime.add(new Road(i, dueTime));

			// 차선 수 저장
			roadN[i] = 1;
		}
	}

	int expand(int M) {
		// 도로를 M번 확장
		int lastExpandedTime = 0; // 마지막으로 확장한 도로의 변경된 이동 시간
		for (int i = 1; i <= M; i++) {
			Road longestRoad = roadsOrderByTime.first(); // 이동시간이 가장 긴 도로
			roadsOrderByTime.remove(longestRoad);
			int roadId = longestRoad.getId();

			// 도로 확장
			int city1 = roadId;
			int city2 = city1 + 1;
			int expandedRoadN = ++this.roadN[roadId]; // 도로 확장 후 city1 과 city2 사이 도로 수
			int expandedTime = (populations[city1] + populations[city2]) / expandedRoadN;

			// 변경된 이동 시간 업데이트
			times.update(roadId, expandedTime);
			roadsOrderByTime.add(new Road(roadId, expandedTime));

			// 마지막 확장인 경우
			if (i == M) {
				lastExpandedTime = expandedTime;
			}
		}

		return lastExpandedTime;
	}

	int calculate(int mFrom, int mTo) {
		// mFrom ~ mTo 구간의 시작 & 끝 도로
		int sRoad = mFrom < mTo ? mFrom : mTo;
		int eRoad = mFrom < mTo ? mTo - 1 : mFrom - 1;

		int time = times.query(sRoad, eRoad);

		return time;
	}

	int divide(int mFrom, int mTo, int K) {
		int maxPopulation = findMaxPopulation(mFrom, mTo);

		int minMax = findMinMax(maxPopulation, K, mFrom, mTo);
		return minMax;
	}

	private int findMinMax(int maxPopulation, int K, int from, int to) {
		int s = maxPopulation;
		int e = MAX_POPULATION_OF_CITY * cityN;

		while (s <= e) {
			int m = s + (e - s) / 2;

			// m 이 가장 인구수가 많은 선거구가 될 수 있는지 확인
			boolean canBeMinMax = findCanBeMinMax(m, K, from, to);

			if (canBeMinMax) {
				e = m - 1;
			} else {
				s = m + 1;
			}
		}

		return e + 1;
	}

	private boolean findCanBeMinMax(int m, int K, int from, int to) {
		int sum = 0; // 현재 지역구의 인구 수
		int cnt = 1; // 지역구의 수

		for (int i = from; i <= to; i++) {
			int population = populations[i];

			// 지역구의 인구 수가 m을 넘어간 경우
			if (sum + population > m) {
				cnt++;
				sum = 0;

				// 더 많은 지역구가 필요한 경우
				if (cnt > K) {
					return false;
				}
			}

			sum += population;
		}
		return true;
	}

	private int findMaxPopulation(int from, int to) {
		int max = 0;
		for (int city = from; city <= to; city++) {
			max = Math.max(max, populations[city]);
		}

		return max;
	}
}

class SegmentTree {
	private int[] tree;
	private int firstLeafNode;
	private int n; // 원소의 개수

	public SegmentTree(int n) {
		tree = new int[n * 4];
		this.n = n;
		firstLeafNode = findFirstLeafNode();

	}

	public void update(int idx, int value) {
		int leafNode = firstLeafNode + idx;
		tree[leafNode] = value;

		while (leafNode > 1) {
			leafNode /= 2;

			int lChildNode = getLChild(leafNode);
			int rChildNode = getRChild(leafNode);
			tree[leafNode] = tree[lChildNode] + tree[rChildNode];
		}
	}

	public int query(int s, int e) {
		int sum = 0;
		int lNode = firstLeafNode + s;
		int rNode = firstLeafNode + e;

		while (lNode < rNode) {
			if (!isLChild(lNode)) { // lNode 가 오른쪽 자식인 경우
				sum += tree[lNode++];
			}

			if (isLChild(rNode)) { // rNode 가 왼쪽 자식인 경우
				sum += tree[rNode--];
			}

			// 부모 노드로 이동
			lNode /= 2;
			rNode /= 2;
		}

		if (lNode == rNode) {
			sum += tree[lNode];
		}

		return sum;
	}

	private int findFirstLeafNode() {
		int height = 0;
		while (true) {
			int siblingN = (int) Math.pow(2, height);
			if (siblingN >= n) {
				return siblingN;
			}
			height++;
		}
	}

	private int getLChild(int node) {
		return node * 2;
	}

	private int getRChild(int node) {
		return node * 2 + 1;
	}

	private boolean isLChild(int node) {
		if (node % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
}

class Road implements Comparable<Road> {
	private int id;
	private int time;

	public Road(int id, int time) {
		this.id = id;
		this.time = time;
	}

	public int getId() {
		return this.id;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public int compareTo(Road o) {
		if (this.time == o.time) {
			return this.id - o.id;
		} else {
			return o.time - this.time;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		Road that = (Road) o;
		return this.id == that.id && this.time == that.time;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, time);
	}
}