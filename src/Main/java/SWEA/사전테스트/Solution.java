package SWEA.사전테스트;

import java.util.Scanner;

class Solution {

    private static final int UP		= 0;
    private static final int RIGHT	= 1;
    private static final int DOWN	= 2;
    private static final int LEFT	= 3;

    private static final UserSolution usersolution = new UserSolution();

    private static int[][] orgBoard = new int[5][5];
    private static int[][] orgPattern = new int[3][3];
    private static int emptyY, emptyX;
    private static int callCnt;

    public static boolean swap(int dir) {

        int dy[] = { -1, 0, 1, 0 };
        int dx[] = { 0, 1, 0, -1 };

        ++callCnt;

        if (dir < UP || dir > LEFT) {
            return false;
        }

        int newY = emptyY + dy[dir];
        int newX = emptyX + dx[dir];

        if (newY < 0 || newY >= 5 || newX < 0 || newX >= 5) {
            return false;
        }

        orgBoard[emptyY][emptyX] = orgBoard[newY][newX];
        orgBoard[newY][newX] = 0;
        emptyY = newY;
        emptyX = newX;

        return true;
    }

    private static boolean run(Scanner sc) {
        int[][] board = new int[5][5];
        int[][] pattern = new int[3][3];
        int callCntLimit;

        callCntLimit = sc.nextInt();

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                orgBoard[i][j] = board[i][j] = sc.nextInt();

                if (board[i][j] == 0) {
                    emptyY = i;
                    emptyX = j;
                }
            }
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                orgPattern[i][j] = pattern[i][j] = sc.nextInt();
            }
        }

        callCnt = 0;

        usersolution.solve(board, pattern, callCntLimit);

        boolean isCorrect = (callCnt <= callCntLimit);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (orgBoard[i + 1][j + 1] != orgPattern[i][j]) {
                    isCorrect = false;
                }
            }
        }

        return isCorrect;
    }

    public static void main(String[] args) throws Exception {
        int test, T;

        // System.setIn(new java.io.FileInputStream("sample_input.txt"));

        Scanner sc = new Scanner(System.in);

        T = sc.nextInt();

        for (test = 1 ; test <= T ; ++test) {
            if (run(sc)) {
                System.out.println("#" + test + " 100");
            } else {
                System.out.println("#" + test + " 0");
            }
        }
    }

}