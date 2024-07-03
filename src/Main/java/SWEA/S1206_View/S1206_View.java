package SWEA.S1206_View;

import java.util.Scanner;

public class S1206_View {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] dx = new int[]{-2, -1, 1, 2};

        // test case 입력받기
        for (int t = 1; t <= 10; t++) {

            int n = sc.nextInt();   // 건물 개수

            // 건물 높이 입력받기
            int[] buildings = new int[n];   // 건물 높이 저장 배열
            for (int i = 0; i < n; i++) {
                buildings[i] = sc.nextInt();
            }

            // 모든 건물의 조망권이 확보된 세대 수 구하기
            int totalHasView = 0;
            boolean[] hasNoView = new boolean[n];   // 조망권이 없는 건물 표시
            for (int b = 2; b < n - 2; b++) {
                if (hasNoView[b]) {
                    continue;
                }

                // 주위 4 건물과 비교해 해당 건물에 조망권이 있는 세대 수 구하기
                int curHeight = buildings[b];   // 현재 빌딩의 높이
                int hasView = Integer.MAX_VALUE;    // 현재 빌딩의 조망권을 확보한 세대 수
                for (int d = 0; d < 4; d++) {
                    int compareB = b + dx[d];   // 비교할 건물
                    int diff = curHeight - buildings[compareB];

                    if (diff > 0) {
                        hasView = Math.min(hasView, diff);  // 조망권을 확보한 세대 수 업데이트
                        hasNoView[compareB] = true;  // 조망권이 없는 건물 표시
                    } else {
                        hasView = 0;
                    }
                }

                // 해당 건물에 조망권을 확보한 세대가 있는 경우
                if (hasView != Integer.MAX_VALUE) {
                    totalHasView += hasView;
                }
            }

            System.out.println("#" + t + " " + totalHasView);
        }
        sc.close();
    }
}
