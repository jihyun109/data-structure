package BaekJoon.b2293_동전1;

import java.util.*;

public class b2293_동전1 {
	private static int coinTypeN;
	private static int k;
	private static int[] coins;

	public static void main(String[] args) {
		init();

		int caseN = findCaseN();

		System.out.println(caseN);
	}

	private static void init() {
		Scanner sc = new Scanner(System.in);
		coinTypeN = sc.nextInt();
		k = sc.nextInt();

		coins = new int[coinTypeN + 1];
		for (int i = 1; i <= coinTypeN; i++) {
			coins[i] = sc.nextInt();
		}

		sc.close();
	}

	private static int findCaseN() {
		int[][] caseNs = new int[coinTypeN + 1][k + 1]; // caseNs[i][j]: 0~i 번쨰 코인을 모두 사용했을 때 j 를 만드는 경우의 수
		for (int c = 1; c <= coinTypeN; c++) {
			caseNs[c][0] = 1;
		}

		for (int c = 1; c <= coinTypeN; c++) {
			int coinValue = coins[c];
			for (int value = 1; value <= k; value++) {
				int t = value - coinValue;

				if (t < 0) {
					caseNs[c][value] = caseNs[c - 1][value];
					continue;
				}

				caseNs[c][value] = caseNs[c - 1][value] + caseNs[c][t];
			}
		}

		return caseNs[coinTypeN][k];
	}

}
