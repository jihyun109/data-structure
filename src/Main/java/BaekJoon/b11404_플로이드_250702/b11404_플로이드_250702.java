package BaekJoon.b11404_플로이드_250702;

import java.util.*;
import java.io.*;

public class b11404_플로이드_250702 {
	private static int cityN;

	private static final int MAX_VALUE = 10000001;

	public static void main(String[] args) throws IOException {
		int[][] minCosts = init();

		findMinCosts(minCosts);

		print(minCosts);
	}

	private static int[][] init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		cityN = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		int busN = Integer.parseInt(st.nextToken());

		// minCost 초기화
		int[][] minCosts = new int[cityN + 1][cityN + 1];
		init(minCosts);

		// 버스 정보 입력받기
		for (int b = 0; b < busN; b++) {
			st = new StringTokenizer(br.readLine());

			int startCity = Integer.parseInt(st.nextToken());
			int endCity = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

			minCosts[startCity][endCity] = Math.min(minCosts[startCity][endCity], cost);
		}
		br.close();

		return minCosts;
	}

	private static int[][] findMinCosts(int[][] minCosts) {
		// 모든 도시에서 출발해 최소 비용 구하기
		for (int mCity = 1; mCity <= cityN; mCity++) {
			for (int sCity = 1; sCity <= cityN; sCity++) {
				for (int eCity = 1; eCity <= cityN; eCity++) {
					if (minCosts[sCity][eCity] > minCosts[sCity][mCity] + minCosts[mCity][eCity]) {
						minCosts[sCity][eCity] = minCosts[sCity][mCity] + minCosts[mCity][eCity];
					}
				}
			}
		}

		// 값이 MAX_VALUE인 것들 0으로 바꿔주기
		trim(minCosts);

		return minCosts;
	}

	private static void init(int[][] arr) {
		for (int i = 1; i <= cityN; i++) {
			for (int j = 1; j <= cityN; j++) {
				if (i == j) {
					arr[i][j] = 0;
					continue;
				}

				arr[i][j] = MAX_VALUE;
			}
		}
	}

	private static void trim(int[][] arr) {
		for (int i = 1; i <= cityN; i++) {
			for (int j = 1; j <= cityN; j++) {
				if (arr[i][j] == MAX_VALUE) {
					arr[i][j] = 0;
				}

			}
		}
	}

	private static void print(int[][] arr) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		for (int i = 1; i <= cityN; i++) {
			for (int j = 1; j <= cityN; j++) {
				bw.append(arr[i][j] + " ");
			}
			bw.append('\n');
		}

		bw.flush();
		bw.close();
	}
}
