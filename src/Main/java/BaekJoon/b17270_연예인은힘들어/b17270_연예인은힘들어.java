package BaekJoon.b17270_연예인은힘들어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b17270_연예인은힘들어 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());   // 약속 장소 후보 수
        int roadN = Integer.parseInt(st.nextToken());

        // 길 정보 저장
        int[][] minTimes = init(V);   // minTimes[a][b]: a->b 최소 비용
        for (int i = 0; i < roadN; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            minTimes[a][b] = Math.min(time, minTimes[a][b]);
            minTimes[b][a] = Math.min(time, minTimes[b][a]);
        }

        st = new StringTokenizer(br.readLine());
        int jLoc = Integer.parseInt(st.nextToken());
        int sLoc = Integer.parseInt(st.nextToken());
        br.close();

        // 모든 정점까지 최소 시간 구하기
        calcMinTimes(V, minTimes);

//        print(minTimes);

        // 조건을 만족하는 지점 찾기
        int answer = findAnswer(V, jLoc, sLoc, minTimes);
        System.out.println(answer);
    }

    private static int findAnswer(int V, int jLoc, int sLoc, int[][] minTimes) {
        int minTime = Integer.MAX_VALUE;
        int spot = Integer.MAX_VALUE;
        int minJT = Integer.MAX_VALUE;
        for (int i = 1; i <= V; i++) {
            // 조건 1
            if (i == jLoc || i == sLoc) {
                continue;
            }

            int jT = minTimes[jLoc][i];
            int sT = minTimes[sLoc][i];
            int totalT = jT + sT;

            // 조건 2
            if (totalT < minTime) {
                minTime = totalT;
                if (jT > sT) {
                    spot = Integer.MAX_VALUE;
                    minJT = Integer.MAX_VALUE;
                } else {
                    spot = i;
                    minJT = jT;
                }
            }

            // 최소 시간과 합이 같은 경우
            else if (totalT == minTime) {
                // 지헌이의 시간이 더 오래걸리는 경우
                if (jT > sT) {
                    continue;
                }

                // 지헌이 시간이 최소 지헌이의 시간보자 작으면
                if (jT < minJT) {
                    spot = i;
                    minJT = jT;
                }
                // 같으면
                else if (jT == minJT) {
                    spot = Math.min(spot, i);
                }
            }
        }

        if (spot == Integer.MAX_VALUE) {
            return -1;
        } else {
            return spot;
        }
    }

    private static void calcMinTimes(int V, int[][] minTimes) {
        for (int k = 1; k <= V; k++) {
            for (int s = 1; s <= V; s++) {

                for (int e = 1; e <= V; e++) {
                    int lTime = minTimes[s][k];
                    int rTime = minTimes[k][e];

                    if (lTime != Integer.MAX_VALUE && rTime != Integer.MAX_VALUE) {
                        minTimes[s][e] = Math.min(minTimes[s][e], lTime + rTime);
                    }
                }
            }
        }

    }

    private static int[][] init(int V) {
        int[][] ret = new int[V + 1][V + 1];
        for (int i = 0; i <= V; i++) {
            for (int j = 0; j <= V; j++) {
                ret[i][j] = Integer.MAX_VALUE;
            }
        }
        return ret;
    }

    private static void print(int[][] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[i].length; j++) {
                if (arr[i][j] == Integer.MAX_VALUE) {
                    System.out.print("0 ");
                } else {
                    System.out.print(arr[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
