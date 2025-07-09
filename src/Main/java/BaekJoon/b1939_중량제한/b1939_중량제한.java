package BaekJoon.b1939_중량제한;

import java.util.*;
import java.io.*;

public class b1939_중량제한 {
	// 중량의 최대, 최솟값
	private static long minWeight;
	private static long maxWeight;

	private static int islA;
	private static int islB;
	private static int islandN;
	private static List<List<BridgeInfo>> bridges;

	public static void main(String[] args) throws IOException {
		// 데이터 입력받기
		init();

		// 한 번에 옮길 수 있는 중량의 최댓값 구하기
		long maxWeight = findMaxWeight();

		// 답 출력
		System.out.println(maxWeight);
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		islandN = Integer.parseInt(st.nextToken()); // 섬의 수
		int bridgeN = Integer.parseInt(st.nextToken()); // 다리의 수

		// 다리 정보 입력받기
		bridges = new ArrayList<>(); // k: 다리의 출발섬 번호, v: 다리의 도착섬 번호, 중량 제한
		minWeight = Long.MAX_VALUE;
		maxWeight = Long.MIN_VALUE;
		
		for (int i = 0; i <= islandN; i++) {
			bridges.add(new LinkedList<>());
		}

		for (int i = 0; i < bridgeN; i++) {
			st = new StringTokenizer(br.readLine());

			int startIsl = Integer.parseInt(st.nextToken());
			int destIsl = Integer.parseInt(st.nextToken());
			long weightLimit = Long.parseLong(st.nextToken());

			if (weightLimit < minWeight) {
				minWeight = weightLimit;
			}

			if (weightLimit > maxWeight) {
				maxWeight = weightLimit;
			}

			bridges.get(startIsl).add(new BridgeInfo(destIsl, weightLimit));
			bridges.get(destIsl).add(new BridgeInfo(startIsl, weightLimit));
		}

		// 공장이 있는 섬 번호 입력받기
		st = new StringTokenizer(br.readLine());
		islA = Integer.parseInt(st.nextToken());
		islB = Integer.parseInt(st.nextToken());

		br.close();
	}

	private static long findMaxWeight() {
		// MIN_WEIGHT ~ MAX_WEIGHT 에서 최댓값이 가능한 값 이분탐색으로 찾기
		long lWeight = minWeight;
		long rWeight = maxWeight;

		while (lWeight <= rWeight) {
			long mWeight = lWeight + (rWeight - lWeight) / 2;

			// mWeight가 옮길 수 있는 최댓값이 될 수 있는지 확인
			boolean canBeMax = canBeMax(mWeight);

			if (canBeMax) {
				lWeight = mWeight + 1;
			} else {
				rWeight = mWeight - 1;
			}
		}

		return lWeight - 1;
	}

	private static boolean canBeMax(long mWeight) {
		boolean[] visited = new boolean[islandN + 1];
		Queue<Integer> que = new LinkedList<>();

		// isA에서 출발
		que.add(islA);

		while (!que.isEmpty()) {
			int cur = que.poll();
			visited[cur] = true;

			// 건너갈 수 있는 모든 다리 check
			List<BridgeInfo> curBridges = bridges.get(cur);
			for (BridgeInfo nextBridge : curBridges) {
				int next = nextBridge.getDestIsl();
				long weightLimit = nextBridge.getWeightLimit();

				// weightLimit 이 mWeight보다 작으면 continue
				if (weightLimit < mWeight) {
					continue;
				}

				// 방문한 경우 continue
				if (visited[next]) {
					continue;
				}

				visited[next] = true;
				// next 가 islB인 경우 max 가능
				if (next == islB) {
					return true;
				}

				que.add(next);
			}
		}

		return false;
	}
}

class BridgeInfo {
	private int destIsl;
	private long weightLimit;

	public BridgeInfo(int destIsl, long weightLimit) {
		this.destIsl = destIsl;
		this.weightLimit = weightLimit;
	}

	public int getDestIsl() {
		return this.destIsl;
	}

	public long getWeightLimit() {
		return this.weightLimit;
	}
}