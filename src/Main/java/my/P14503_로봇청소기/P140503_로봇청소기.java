package my.P14503_로봇청소기;

import java.util.Scanner;

public class P140503_로봇청소기 {
    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        int row = sc.nextInt(); // 방의 행
        int column = sc.nextInt();  // 방의 열
        int curX = sc.nextInt();    // 로봇청소기의 x좌표
        int curY = sc.nextInt();    // 로봇청소기의 y좌표
        int curDir = sc.nextInt();  // 로봇청소기가 바라보는 방향
        int[][] room = new int[row][column];   // 방의 각 칸의 정보
        int[] dx = new int[]{-1, 0, 1, 0}; // 현재 위치의 북, 동, 남, 서를 순서대로 x, y 좌표로 나타냄.
        int[] dy = new int[]{0, 1, 0, -1};

        // 방의 정보 입력받아 room 배열에 저장
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                room[i][j] = sc.nextInt();
            }
        }

        // init end
        sc.close();

        // 로봇청소기 작동 시키기
        int cleanedN = 0;   // 청소한 칸의 수 저장 배열
        while (true) {   // 로봇 청소기가 작동을 멈출 떄 까지 반복

            // 현재 칸이 청소되지 않았으면 청소
            if (room[curX][curY] == 0) {
                room[curX][curY] = 2;   // 현재 칸 청소
                cleanedN++; // 청소한 칸 수 업데이트
            }

            // 현재 위치의 주변 4칸이 모두 청소되었는지 확인
            boolean allCleaned = true; // 현재 위치의 주변 4칸이 모두 청소되었는지 판단하는 변수

            for (int i = 0; i < 4; i++) {
                int nextX = curX + dx[i];   // 현재 칸의 주변 칸의 x좌표
                int nextY = curY + dy[i];   // 현재 칸의 주변 칸의 y좌표

                if (room[nextX][nextY] == 0) {  // 칸이 청소되어있지 않은 빈칸이면
                    allCleaned = false;
                    break;
                }
            }

            // 현재 칸의 주변 4칸이 모두 청소되어있는 경우
            if (allCleaned) {
                int backX = curX - dx[curDir];  // 현제 위치에서 후진할 때의 x좌표
                int backY = curY - dy[curDir];  // 현제 위치에서 후진할 때의 y좌표

                // 현재 칸에서 후진할 수 있다면 후진하기
                if (backX >= 0 && backY >= 0 && backX < row && backY < column) {    // 후진했을 떄 위치가 범위 안인지 확인
                    // 후진했을 떄 위치의 상태가 벽이 아닌지 확인
                    if (room[backX][backY] != 1) {  // 벽이 아니면 후진
                        curX = backX;
                        curY = backY;
                    } else {    // 벽이면 작동 멈춤
                        break;
                    }
                } else {    // 범위 밖이라면 작동 멈춤
                    break;
                }
            }

            // 현재 칸의 주변 4칸에 청소되지 않은 칸이 있는 경우
            else if (!allCleaned) {

                // 반시계 방향으로 90도 회전
                if (curDir == 0) {
                    curDir = 3;
                } else {
                    curDir -= 1;
                }

                // 바라보는 방향이 청소되지 않았으면 한 칸 전진

                // 현재 바라보는 방향으로 앞쪽 칸의 x, y 좌표
                int frontX = curX + dx[curDir];
                int frontY = curY + dy[curDir];

                if (room[frontX][frontY] == 0) {
                    // 전진
                    curX = frontX;
                    curY = frontY;
                }
            }
        }

        // 답 출력
        System.out.println(cleanedN);
    }
}
