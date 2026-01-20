package BaekJoon.b5721_사탕줍기대회;

import java.util.*;
import java.io.*;

public class b5721_사탕줍기대회 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 모든 테스트 케이스 수행
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int rowN = Integer.parseInt(st.nextToken());
            int colN = Integer.parseInt(st.nextToken());

            if (rowN == 0) {
                break;
            }

            // 박스 정보 입력받기 & 각 행별 최대로 선택할 수 있는 사탕 개수 저장
            int[] rowCandies = new int[rowN + 1];    // 각 행 별 최대 사탕 선택 개수 저장
            int[][] dpCol = new int[2][colN + 1];    // dp[0/1][i]: 현재 행에서 0~i번째 사탕이 대상일 때 i번째 사탕을 집지않을/집을 떄 최댓값
            int[][] dpRow = new int[2][rowN + 1];
            int NOT_PICK = 0;
            int PICK = 1;

            for (int r = 1; r <= rowN; r++) {
                st = new StringTokenizer(br.readLine());

                for (int c = 1; c <= colN; c++) {
                    int candyN = Integer.parseInt(st.nextToken());

                    // 현재 사탕을 집지 않을 때 최댓값
                    dpCol[NOT_PICK][c] = Math.max(dpCol[NOT_PICK][c - 1], dpCol[PICK][c - 1]);
                    // 현재 사탕을 집을 때 최댓값
                    dpCol[PICK][c] = dpCol[NOT_PICK][c - 1] + candyN;
                }

                // 현재 행의 최대 사탕 선택 개수
                int maxCandyRow = Math.max(dpCol[NOT_PICK][colN], dpCol[PICK][colN]);

                // 현재 행을 선택하지 않을 때 최댓값
                dpRow[NOT_PICK][r] = Math.max(dpRow[NOT_PICK][r - 1], dpRow[PICK][r - 1]);
                // 현재 행을 선택할 때 최댓값
                dpRow[PICK][r] = dpRow[NOT_PICK][r - 1] + maxCandyRow;
            }

            // 최종 최댓값
            bw.append(Math.max(dpRow[NOT_PICK][rowN], dpRow[PICK][rowN]) + "\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }
}
