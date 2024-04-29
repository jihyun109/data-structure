package my.P16954_움직이는미로탈출;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P16954_움직이는미로탈출 {
    static Queue<UJStatus> que;
    static char[][] chessBoard;
    static Queue<Wall> wallLocation;
    static int wallN;
    static int[] dx = new int[]{0, -1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dy = new int[]{0, 0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        // 체스판 상태 입력받기
        chessBoard = new char[8][8]; // 체스판 상태 저장 배열
        wallLocation = new LinkedList<>();  // 벽의 위치 좌표 저장 큐
        wallN = 0;  // 벽의 개수

        for (int i = 0; i < 8; i++) {
            String str = sc.next(); // 입력받은 문자열

            // 입력받은 문자열 str 을 문자로 나누어 chessBoard 배열에 저장
            for (int j = 0; j < 8; j++) {
                char status = str.charAt(j);    // (i, j) 칸의 상태
                chessBoard[i][j] = status;  // chessBoard 배열에 상태 저장

                // 입력받은 값이 벽이면 wallLocation 큐에 벽의 위치 좌표 저장
                if (status == '#') {
                    wallLocation.offer(new Wall(i, j));
                    wallN++;
                }
            }
        }

        // init end
        sc.close();

        que = new LinkedList<>();   //  BFS 에 사용할 큐
        int answer = BFS();  // 완전 탐색 수행

        // 답 출력
        System.out.println(answer);
    }

    private static int BFS() {
        que.offer(new UJStatus(7, 0, 0));    // 처음 UJ의 위치 que 에 offer
        int curTime = 0;  // 출발할 때 부터 현재까지 지난 시간
        boolean visited[][] = new boolean[8][8];    // 동시간대에 방문한 위치 표시하는 배열

        while (!que.isEmpty()) {
            UJStatus cur = que.poll();  // 현재 UJ의 상태 que 에 offer

            // 같은 시간대의 UJ의 상태를 모두 처리했으면 벽 이동
            if (cur.second > curTime) {

                // 모든 벽 이동
                int deletedWall = 0;    // 삭제된 벽의 개수
                for (int i = 0; i < wallN; i++) {
                    Wall curWall = wallLocation.poll();

                    // 벽을 아래로 한 칸 이동 가능한지 확인 후 가능하면 이동, 불가능하면 벽 소멸
                    int nextWallX = curWall.x + 1;  // 벽을 아래로 한 칸 이동시킨 경우의 x 좌표

                    if (nextWallX < 8) {    // 벽을 아래로 한 칸 이동할 수 있으면 이동
                        // chessBoard 배열에서 이동
                        chessBoard[nextWallX][curWall.y] = '#'; // 벽을 한 칸 아래로 이동.
                        chessBoard[curWall.x][curWall.y] = '.'; // 현재 벽의 위치는 빈 칸으로 변경

                        // wallLocation 큐에 이동한 벽의 위치 offer
                        wallLocation.offer(new Wall(nextWallX, curWall.y));
                    } else {    // 벽을 아래로 한 칸 이동할 수 없으면 벽 소멸
                        // chessBoard 배열에서 벽 삭제
                        chessBoard[curWall.x][curWall.y] = '.';
                        deletedWall++;
                    }
                }
                wallN -= deletedWall;   // 벽을 이동시키고 남은 벽의 개수 업데이트
                curTime++;  // 현재 시간 업데이트

                // visited 배열 초기화
                for (int i = 0; i < 8; i++) {
                    Arrays.fill(visited[i], false);
                }
            }

            // cur 의 위치에 벽이 있다면 UJ는 이동 불가(삭제)
            if (chessBoard[cur.x][cur.y] == '#') {
                continue;
            }

            // UJ 이동
            for (int i = 0; i < 9; i++) {
                int nextX = cur.x + dx[i];  // UJ의 다음 이동할 x 좌표
                int nextY = cur.y + dy[i];  // UJ의 다음 이동할 y 좌표

                // 다음 이동할 좌표가 체스판 범위 내이고, 동시간대에 방문한 적이 없고, 벽이 아니면 이동(que 에 삽입)
                if (nextX >= 0 && nextY >= 0 && nextX < 8 && nextY < 8 && !visited[nextX][nextY]) {
                    if (chessBoard[nextX][nextY] != '#') {

                        // UJ가 가장 오른쪽 윗 칸으로 이동했다면 return 1
                        if (nextX == 0 && nextY == 7) {
                            return 1;
                        }

                        que.offer(new UJStatus(nextX, nextY, curTime + 1));
                        visited[nextX][nextY] = true;
                    }
                }
            }
        }

        // UJ가 가장 오른쪽 윗 칸으로 이동하지 못했다면 return 0
        return 0;
    }

    // UJ의 상태(위치, 시간)를 나타내는 클래스
    static class UJStatus {
        int x;  // 욱제 위치 x좌표
        int y;  // 욱제 위치 y좌표
        int second; // 출발할 때 부터 현재까지 걸린 시간

        UJStatus(int x, int y, int second) {
            this.x = x;
            this.y = y;
            this.second = second;
        }
    }

    // 벽의 좌표를 나타내는 클래스
    static class Wall {
        int x;  // 벽의 x좌표
        int y;  // 벽의 y좌표

        Wall(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
