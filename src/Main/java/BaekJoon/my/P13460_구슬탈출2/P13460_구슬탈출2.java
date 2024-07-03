package my.P13460_구슬탈출2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class P13460_구슬탈출2 {
    private static Queue<Status> que;
    private static boolean[][][][] visited;
    private static String[][] board;
    private static int[] dx = new int[] {-1, 1, 0, 0};
    private static int[] dy = new int[] {0, 0, -1, 1};
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        String[] parts = input.split(" ");
        int N = Integer.parseInt(parts[0]);
        int M = Integer.parseInt(parts[1]);
        board = new String[N][M];    // 보드 상태 저장 배열
        int originRX = 0;
        int originRY = 0;
        int originBX = 0;
        int originBY = 0;
        que = new LinkedList<>();
        visited = new boolean[N][M][N][M];

        // 보드 상태 입력받기
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                String status = st.nextToken();

                if (status.equals("R")) {
                    originRX = i;
                    originRY = j;
                    board[i][j] = ".";
                } else if (status.equals("B")) {
                    originBX = i;
                    originRY = j;
                    board[i][j] = ".";
                }

                board[i][j] = status;
            }
        }

        // game start
        que.add(new Status(originRX, originRY, originBX, originBY, 0));
        visited[originRX][originRY][originBX][originBY] = true;
        int answer = gameStart();

        System.out.println(answer);
    }

    private static int gameStart() {
        while (!que.isEmpty()) {
            Status cur = que.poll();

            if (cur.cnt > 10) {
                return -1;
            }

            for (int i = 0; i < 4; i++) {
                int nextRX = cur.rX + dx[i];
                int nextRY = cur.rY + dy[i];
                int nextBX = cur.bX + dx[i];
                int nextBY = cur.bY + dy[i];
                int nextCnt = cur.cnt + 1;

                // 처음부터 두 구슬 모두 움직일 수 없으면
                if (!board[nextRX][nextRY].equals(".") && !board[nextBX][nextBY].equals(".")) {
                    continue;
                }

                int result = moveMarble(dx[i], dy[i], cur.rX, cur.rY, cur.bX, cur.bY, nextCnt);
                if (result >= 0) {
                    return result;
                }
            }
        }
        return -1;
    }

    // 두 구슬을 선택한 방향으로 움직이고 R이 구멍에 빠지면 cnt return, B이 구멍에 빠지거나 두 구슬 모두 빠지지 않으면 -1 return
    private static int moveMarble(int dx, int dy, int rX, int rY, int bX, int bY, int cnt) {
        // 움직이는 방향으로 두 구슬이 나란히 붙어있으면
        if ((rX + dx == bX && rY + dy == bY) || (bX + dx == rX && bY + dy == rY)) {
            while(true) {
                // R이 B보다 진행방향으로 앞에 있으면
                if (bX + dx == rX && bY + dy == rY) {
                    // r 앞에 벽이면 (움직일 수 없으면)
                    if (board[rX + dx][rY + dy].equals("#") && !visited[rX][rY][bX][bY]) {
                        que.add(new Status(rX, rY, bX, bY, cnt));
                        visited[rX][rY][bX][bY] = true;
                        return -1;
                    }

                    // 움직일 수 있으면
                    // R, B 구슬 이동
                    rX += dx;
                    rY += dy;
                    bX += dx;
                    bY += dy;

                    // R이 구멍에 빠지면
                    if (board[rX][rY].equals("0")) {
                        return cnt;
                    }
                }
                // B이 R보다 진행방향으로 앞에 있으면
                else {
                    // B 앞에 벽이면 (움직일 수 없으면)
                    if (board[bX + dx][bY + dy].equals("#") && !visited[rX][rY][bX][bY]) {
                        que.add(new Status(rX, rY, bX, bY, cnt));
                        visited[rX][rY][bX][bY] = true;
                        return -1;
                    }

                    // 움직일 수 있으면
                    // R, B 구슬 이동
                    rX += dx;
                    rY += dy;
                    bX += dx;
                    bY += dy;

                    // B 가 구멍에 빠지면
                    if (board[bX][bY].equals("0")) {
                        return -1;
                    }
                }
            }
        } else {
            while (!board[bX + dx][bY + dy].equals("#") || !board[rX + dx][rY + dy].equals("#")) {
                // 선택한 방향으로 B를 움직일 수 있으면
                if (board[bX + dx][bY + dy].equals("0") || board[bX + dx][bY + dy].equals(".")) {
                    bX += dx;
                    bY += dy;

                    if (board[bX][bY].equals("0")) {
                        return -1;
                    }
                }
                // 선택한 방향으로 R을 움직일 수 있으면
                if (board[rX + dx][rY + dy].equals("#") || board[rX + dx][rY + dy].equals(".")) {
                    rX += dx;
                    rY += dy;

                    if (board[rX][rY].equals("0")) {
                        return cnt;
                    }
                }
            }
        }



        return 0;
    }

    private static class Status {
        int rX;
        int rY;
        int bX;
        int bY;
        int cnt;

        private Status (int rX, int rY, int bX, int bY, int cnt) {
            this.rX = rX;
            this.rY = rY;
            this.bX = bX;
            this.bY = bY;
            this.cnt = cnt;
        }
    }
}
