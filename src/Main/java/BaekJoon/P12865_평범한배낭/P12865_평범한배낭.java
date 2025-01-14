package BaekJoon.P12865_평범한배낭;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P12865_평범한배낭 {
    private static int N;
    private static int K;
    private static int[][] maxSums;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 물품의 수
        K = Integer.parseInt(st.nextToken());   // 준서가 버틸 수 있는 무게
        maxSums = new int[N + 1][K + 1];

        // 물품 정보 입력받기
        Stuff[] stuffs = new Stuff[N + 1];  // 물품 정보 저장 배열
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int weight = Integer.parseInt(st.nextToken());
            int value = Integer.parseInt(st.nextToken());
            stuffs[i] = new Stuff(weight, value);
            if (weight <= K) {
                maxSums[i][weight] = value;
            }
        }
        br.close();

        // 가치 합의 최댓값 구하기
        int maxSumOfValue = findMaxSumOfValue(stuffs);

        // 답 출력
        System.out.println(maxSumOfValue);
    }

    private static int findMaxSumOfValue(Stuff[] stuffs) {
        maxSums = new int[N + 1][K + 1];    // maxSums[i][j]: i번째 물품을 포함하고 따졌을 때 j무게 까지의 최대 가치의 합

        for (int s = 1; s <= N; s++) {
            for (int w = 0; w <= K; w++) {
                int sWeight = stuffs[s].getWeight(); // s번째 물품의 무게
                int sValue = stuffs[s].getValue();  // s번째 물품의 가치
                int spareWeight = w - sWeight;  // 현재 무게에서 sWeight 를 제외한 무게
                if (spareWeight > K) {
                    continue;
                }

                if (spareWeight >= 0) {
                    maxSums[s][w] = Math.max(maxSums[s - 1][w], maxSums[s - 1][spareWeight] + sValue);
                } else {
                    maxSums[s][w] = maxSums[s - 1][w];
                }
            }
        }

        return maxSums[N][K];
    }
}

class Stuff {
    private int weight;
    private int value;

    public Stuff(int weight, int value) {
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }
}
