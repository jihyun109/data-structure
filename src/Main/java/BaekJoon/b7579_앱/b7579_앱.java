package BaekJoon.b7579_앱;

import java.util.*;
import java.io.*;

public class b7579_앱 {
    private static int[] memories;   // i번째 앱의 메모리 바이트 수
    private static int[] costs;      // i번째 앱의 비용
    private static int appN;    // 앱의 수 
    private static int M;            // 확보해야하는 메모리 바이트 수

    private static final int MAX_COST = 10000;

    public static void main(String[] args) throws IOException{
        init();

        int minCost = findMinCost();

        System.out.print(minCost);
    }

    private static void init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        appN = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 각 앱의 메모리 바이트 수 입력받기
        st = new StringTokenizer(br.readLine());
        memories = new int[appN + 1];
        for (int i = 1; i <= appN; i++) {
            memories[i] = Integer.parseInt(st.nextToken());
        }

        // 각 앱의 비용 입력받기
        st = new StringTokenizer(br.readLine());
        costs = new int[appN + 1];
        for (int i = 1; i <= appN; i++) {
            costs[i] = Integer.parseInt(st.nextToken());
        }

        br.close();
    }

    private static int findMinCost() {
        int[][] maxMemories = new int[appN + 1][MAX_COST + 1]; // minCost[i][j]: 1~i번째 앱을 포함할 때 j비용으로 얻을 수 있는 최대 메모리

        // minCosts 채우기
        int minCost = MAX_COST + 1;
        for (int a = 1; a <= appN; a++) {
            for (int c = 0; c <= MAX_COST; c++) {
                int maxMemoryA = maxMemories[a - 1][c];

                int aCost = costs[a];   // a번째 앱의 비용
                int maxMemoryB = 0;

                int t = c - aCost;
                if (t < 0) {
                    maxMemoryB = 0;
                } else {
                    int aMemory = memories[a];
                    maxMemoryB = maxMemories[a - 1][t] + aMemory;
                }

                maxMemories[a][c] = Math.max(maxMemoryA, maxMemoryB);
                if (maxMemories[a][c] >= M) {
                    minCost = Math.min(c, minCost);
                }
            }
        }

        return minCost;
    }
}