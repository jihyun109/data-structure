package BaekJoon.my.P2294_동전2;

import java.util.Arrays;
import java.util.Scanner;

public class P2294_동전2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();   // 동전 종류 개수
        int k = sc.nextInt();   // 동전 합
        int[] coinType = new int[n];    // 동전 종류 저장 배열
        int[] minCoinN = new int[k + 1];   // minCoinN[i]: 동전의 합이 i가 되게하는 최소 동전의 수

        // minCoinN 배열 최댓값으로 초기화
        for (int i = 0; i <= k; i++) {
            minCoinN[i] = Integer.MAX_VALUE;
        }
        minCoinN[0] = 0;

        for (int i = 0; i < n; i++) {
            int value = sc.nextInt();
            coinType[i] = value;
            if (value <= k) {    // 동전의 가치가 k보다 크면 minCoinN 에 저장하지 않음.
                minCoinN[value] = 1;
            }
        }
        sc.close();

        // minCoinN 배열 채우기
        for (int i = 1; i <= k; i++) {
            if (minCoinN[i] == 1) {
                continue;
            }

            // 코인을 하나 더하면 i가 되는 값들 중 minCoinN의 값이 가장 작은 값 구하기
            int preMin = Integer.MAX_VALUE; // 구하는 값
            for (int j = 0; j < coinType.length; j++) {
                int coin = coinType[j];
                if (coin > i) {
                    continue;
                }
                int preSum = i - coin;   // 코인을 하나 더하면 i가 되는 값
                if (preSum < 0) {
                    continue;
                }
                preMin = Math.min(preMin, minCoinN[preSum]);
            }

            if (preMin > 10000) {   // 불가능한 경우
                continue;
            }

            // i를 만드는데 필요한 최소 동전 수 저장
            minCoinN[i] = preMin + 1;
        }

        System.out.println(Arrays.toString(minCoinN));

        if (minCoinN[k] > 10000) {
            System.out.println(-1);
        } else {
            System.out.println(minCoinN[k]);
        }
    }
}