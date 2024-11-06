package BaekJoon.my.P12100_2048;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P12100_2048 {
    private static int N;   // 보드의 크기
    private static int[][] firstStatus; // 초기 게임판 상태
    private static int[] dx = new int[]{-1, 1, 0, 0};
    private static int[] dy = new int[]{0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();

        // 초기 게임판 상태 입력받기
        firstStatus = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                firstStatus[i][j] = sc.nextInt();
            }
        }

        sc.close();

        int ans = findMaxBlock();    // 만들 수 있는 가장 큰 블록의 값 찾기
        System.out.println(ans);
    }

    private static int findMaxBlock() {
        int maxBlock = 0;   // 만들 수 있는 가장 큰 블록의 값
        Queue<Status> que = new LinkedList<>();
        que.add(new Status(0, firstStatus));

        while (!que.isEmpty()) {
            Status curStatus = que.poll();
            int curTurn = curStatus.getTurn();
            int[][] curBoard = curStatus.getBoard();

            // 보드판 이동.
            int nextTurn = curTurn + 1;
            // 현재 보드판 4방향으로 이동
            for (int i = 0; i < 4; i++) {

                int[][] nextBoard = deepCopyBoard(curBoard);  // 이동 후 보드 정보
                boolean[][] merged = new boolean[N][N]; // 해당 칸에 위치한 블럭이 현재 turn에 합쳐진 블럭인지 flag

                switch (i) {
                    case 0: {   // 위쪽으로 이동
                        int dir = 0;    // 움직이는 방향(상)

                        // 윗쪽 칸부터 이동 & 합치며 최대 블럭 값 찾기
                        for (int x = 0; x < N; x++) {
                            for (int y = 0; y < N; y++) {
                                if (curBoard[x][y] != 0) {
                                    // 블럭 이동 & 합친 후 블럭의 값 받아오기.
                                    int curBlock = moveBoard(dir, nextBoard, merged, x, y);

                                    maxBlock = Math.max(maxBlock, curBlock); // maxBlock 업데이트
                                }
                            }
                        }

                        // 이동된 보드를 que 에 넣기
                        if (nextTurn < 5) { // 5번째 turn인 경우는 넣지 않음.
                            que.add(new Status(nextTurn, nextBoard));
                        }

//                        // 출력
//                        My2DArrayClass myArray1 = new My2DArrayClass(curBoard);
//                        My2DArrayClass myArray2 = new My2DArrayClass(nextBoard);
//                        System.out.println("상");
//                        System.out.println("curBoard: ");
//                        System.out.println(myArray1.toString());
//                        System.out.println("nextBoard: ");
//                        System.out.println(myArray2.toString());
//                        System.out.println("-----------------");
                        break;
                    }
                    case 1: {   // 아래쪽으로 이동
                        int dir = 1;    // 움직이는 방향(하)

                        // 아래쪽 칸부터 이동 & 블록을 합치며 최대 블럭 값 찾기
                        for (int x = N - 1; x >= 0; x--) {
                            for (int y = 0; y < N; y++) {
                                int curBlock = moveBoard(dir, nextBoard, merged, x, y);

                                maxBlock = Math.max(maxBlock, curBlock); // maxBlock 업데이트
                            }
                        }

                        // 이동된 보드를 que 에 넣기
                        if (nextTurn < 5) { // 5번째 turn인 경우는 넣지 않음.
                            que.add(new Status(nextTurn, nextBoard));
                        }

//                        // 출력
//                        My2DArrayClass myArray1 = new My2DArrayClass(curBoard);
//                        My2DArrayClass myArray2 = new My2DArrayClass(nextBoard);
//                        System.out.println("하");
//                        System.out.println("curBoard: ");
//                        System.out.println(myArray1.toString());
//                        System.out.println("nextBoard: ");
//                        System.out.println(myArray2.toString());
//                        System.out.println("-----------------");
                        break;


                    }
                    case 2: {   // 왼쪽으로 이동
                        int dir = 2;    // 움직이는 방향(좌)

                        // 왼쪽 칸부터 이동 & 합치기
                        for (int y = 0; y < N; y++) {
                            for (int x = 0; x < N; x++) {
                                int curBlock = moveBoard(dir, nextBoard, merged, x, y);

                                maxBlock = Math.max(maxBlock, curBlock); // maxBlock 업데이트
                            }
                        }

                        // 이동된 보드를 que 에 넣기
                        if (nextTurn < 5) { // 5번째 turn인 경우는 넣지 않음.
                            que.add(new Status(nextTurn, nextBoard));
                        }

//                        // 출력
//                        My2DArrayClass myArray1 = new My2DArrayClass(curBoard);
//                        My2DArrayClass myArray2 = new My2DArrayClass(nextBoard);
//                        System.out.println("좌");
//                        System.out.println("curBoard: ");
//                        System.out.println(myArray1.toString());
//                        System.out.println("nextBoard: ");
//                        System.out.println(myArray2.toString());
//                        System.out.println("-----------------");
                        break;

                    }
                    case 3: {   // 오른쪽으로 이동
                        int dir = 3;    // 움직이는 방향(우)

                        // 오른쪽 칸부터 이동 & 합치기
                        for (int y = N - 1; y >= 0; y--) {
                            for (int x = 0; x < N; x++) {
                                int curBlock = moveBoard(dir, nextBoard, merged, x, y);

                                maxBlock = Math.max(maxBlock, curBlock); // maxBlock 업데이트
                            }
                        }

                        // 이동된 보드를 que 에 넣기
                        if (nextTurn < 5) { // 5번째 turn인 경우는 넣지 않음.
                            que.add(new Status(nextTurn, nextBoard));
                        }

//                        // 출력
//                        My2DArrayClass myArray1 = new My2DArrayClass(curBoard);
//                        My2DArrayClass myArray2 = new My2DArrayClass(nextBoard);
//                        System.out.println("우");
//                        System.out.println("curBoard: ");
//                        System.out.println(myArray1.toString());
//                        System.out.println("nextBoard: ");
//                        System.out.println(myArray2.toString());
//                        System.out.println("-----------------");
                        break;
                    }
                }
            }
        }
        return maxBlock;
    }

    // 배열 깊은 복사 메서드
    private static int[][] deepCopyBoard(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }

    // nextBoard에서 (x, y) 좌표의 블럭을 이동 & 인접한 블럭과 합치고, 블럭의 값 return
    private static int moveBoard(int dir, int[][] nextBoard, boolean[][] merged, int x, int y) {
        // 블럭이 이동할 칸의 좌표 찾기
        int value = nextBoard[x][y];    // 블럭의 값

        int nextX = x;  // (x, y)를 이동시킬 좌표
        int nextY = y;
        while (true) {
            // dir 방향으로 한 칸 이동 (조건 1: 좌표가 보드 내에 위치. 조건 2: 이동할 좌표가 빈칸이어야함.)
            nextX += dx[dir];
            nextY += dy[dir];

            // 이동할 수 없으면 break
            if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= N || nextBoard[nextX][nextY] != 0) {
                nextX -= dx[dir];
                nextY -= dy[dir];
                break;
            }
        }

        // 블럭 이동
        if (x != nextX || y != nextY) {
            nextBoard[x][y] = 0;
            nextBoard[nextX][nextY] = value;
        }

        // 블럭 합치기
        int nearX = nextX + dx[dir];    // dir 방향으로 인접한 칸의 좌표
        int nearY = nextY + dy[dir];
        // 합치는 조건 (1. near 좌표가 보드 내에 위치. 2. 현재 turn에 합쳐지지 않음. 3. 블럭의 값이 같음)
        if (nearX >= 0 && nearX < N && nearY >= 0 && nearY < N && !merged[nearX][nearY] && nextBoard[nextX][nextY] == nextBoard[nearX][nearY]) {
            nextBoard[nextX][nextY] = 0;
            nextBoard[nearX][nearY] = value * 2;
            merged[nearX][nearY] = true;
            value = nextBoard[nearX][nearY];
        }

        // 현재 블럭의 값 return
        return value;
    }

    private static class Status {
        private int turn;   // 이동 횟수
        private int[][] board;  // 현재 보드판의 상태

        public Status(int turn, int[][] board) {
            this.turn = turn;
            this.board = board;
        }

        public int getTurn() {
            return turn;
        }


        public int[][] getBoard() {
            return board;
        }
    }

    // My2DArrayClass를 static 클래스로 변경
    public static class My2DArrayClass {
        private int[][] arr;

        // 생성자
        public My2DArrayClass(int[][] arr) {
            this.arr = arr;
        }

        // toString() 메서드 오버라이드
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[\n");
            for (int i = 0; i < arr.length; i++) {
                sb.append("  [");
                for (int j = 0; j < arr[i].length; j++) {
                    sb.append(arr[i][j]);
                    if (j < arr[i].length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                if (i < arr.length - 1) {
                    sb.append(",\n");
                }
            }
            sb.append("\n]");
            return sb.toString();
        }
    }
}
