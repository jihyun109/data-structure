package SWEA.사전테스트;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class UserSolution {
    private static int[][] b;
    private static int patternX = 0;
    private static int patternY = 0;
    private static int blankX = 0;
    private static int blankY = 0;
    private static int blockType = 0;
    private static int selectedX = 0;
    private static int selectedY = 0;
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, 1, 0, -1};

    public void solve(int[][] board, int[][] pattern, int callCntLimit) {
        b = Arrays.copyOf(board, board.length);
        // 0 좌표 찾기
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (b[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                    break;
                }
            }
        }

        // 순차적으로 패턴 맞추기
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                blockType = pattern[x - 1][y - 1];  // (x, y)에 위치해야할 블록 타입
                patternX = x;
                patternY = y;

                if (b[x][y] == blockType) {
                    continue;
                }
                Stack<Coordinate> blankRoute = selectBlock();   // 패턴을 맞추기 위해 사용할 블럭 탐색 & 0이 위치해야하는 순서 구하기
                moveBlock(blankRoute);    // 선택된 블럭을 (x, y)로 이동시키기
            }
        }
    }

    private Stack<Coordinate> selectBlock() {
        Queue<Status> que = new LinkedList<>();
        boolean[][] visited = new boolean[5][5];
        Stack<Coordinate> curRoute = new Stack<>();
        curRoute.add(new Coordinate(patternX, patternY));
        que.add(new Status(patternX, patternY, curRoute));
        visited[patternX][patternY] = true;

        // 이미 정렬된 칸 방문 처리
        for (int i = 1; i <= 3; i++) {
            if (i < patternX) {
                for (int j = 1; j <= 3; j++) {
                    visited[i][j] = true;
                }
            } else if (i == patternX) {
                for (int j = 1; j < patternY; j++) {
                    visited[i][j] = true;
                }
            }
        }

        while (!que.isEmpty()) {
            Status cur = que.poll();
            visited[cur.x][cur.y] = true;

            if (b[cur.x][cur.y] == blockType) {
                selectedX = cur.x;
                selectedY = cur.y;
                cur.route.pop();
                return cur.route;
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int nextX = cur.x + dx[i];
                    int nextY = cur.y + dy[i];

                    if (nextX < 0 || nextX > 4 || nextY < 0 || nextY > 4 || visited[nextX][nextY]) {
                        continue;
                    }

                    Stack<Coordinate> nextRoute = (Stack<Coordinate>) cur.route.clone();
                    nextRoute.add(new Coordinate(nextX, nextY));
                    que.add(new Status(nextX, nextY, nextRoute));
                }
            }
        }
        return null;
    }

    private void moveBlock(Stack<Coordinate> blankRoute) {
        while (!blankRoute.isEmpty()) {
            // 0 이동
            Coordinate destination = blankRoute.pop();
            moveBlankTo(destination.x, destination.y);  // 0을 (destX, destY)로 이동시키기

            // 0과 블럭 위치 바꾸기
            for (int i = 0; i < 4; i++) {
                if (destination.x + dx[i] == selectedX && destination.y + dy[i] == selectedY) {
                    Solution.swap(i);
                    int tmpX = blankX;
                    int tmpY = blankY;
                    b[blankX][blankY] = blockType;
                    b[selectedX][selectedY] = 0;
                    blankX = selectedX;
                    blankY = selectedY;
                    selectedX = tmpX;
                    selectedY = tmpY;
                    break;
                }
            }
        }
    }

    private void moveBlankTo(int destX, int destY) {
        Queue<Integer> route = findBlankRoute(destX, destY);  // 0을 이동시키는 경로 탐색
        while (!route.isEmpty()) {
            int dir = route.poll();
            Solution.swap(dir);
            int exchangeX = blankX + dx[dir];
            int exchangeY = blankY + dy[dir];
            int exchangeBlock = b[exchangeX][exchangeY];
            b[blankX][blankY] = exchangeBlock;
            b[exchangeX][exchangeY] = 0;
            blankX = exchangeX;
            blankY = exchangeY;
        }
    }

    private Queue<Integer> findBlankRoute(int destX, int destY) {
        Queue<BlankStatus> que = new LinkedList<>();
        boolean[][] visited = new boolean[5][5];

        que.add(new BlankStatus(blankX, blankY, new LinkedList<>()));
        visited[blankX][blankY] = true;
        visited[selectedX][selectedY] = true;

        for (int i = 1; i <= 3; i++) {
            if (i < patternX) {
                for (int j = 1; j <= 3; j++) {
                    visited[i][j] = true;
                }
            } else if (i == patternX) {
                for (int j = 1; j < patternY; j++) {
                    visited[i][j] = true;
                }
            }
        }

        while (!que.isEmpty()) {
            BlankStatus cur = que.poll();
            visited[cur.x][cur.y] = true;

            if (cur.x == destX && cur.y == destY) {
                return cur.route;
            }

            for (int i = 0; i < 4; i++) {
                int nextX = cur.x + dx[i];
                int nextY = cur.y + dy[i];

                if (nextX < 0 || nextX > 4 || nextY < 0 || nextY > 4 || visited[nextX][nextY]) {
                    continue;
                }

                Queue<Integer> nextRoute = new LinkedList<>(cur.route);
                nextRoute.add(i);
                que.add(new BlankStatus(nextX, nextY, nextRoute));
            }
        }
        return null;
    }

    private static class Status {
        int x;
        int y;
        Stack<Coordinate> route;

        public Status(int x, int y, Stack<Coordinate> route) {
            this.x = x;
            this.y = y;
            this.route = route;
        }
    }

    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class BlankStatus {
        int x;
        int y;
        Queue<Integer> route;

        public BlankStatus(int x, int y, Queue<Integer> route) {
            this.x = x;
            this.y = y;
            this.route = route;
        }
    }
}
