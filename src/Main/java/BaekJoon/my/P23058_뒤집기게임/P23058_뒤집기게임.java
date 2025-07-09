package BaekJoon.my.P23058_뒤집기게임;

import java.util.*;

public class P23058_뒤집기게임 {
    private static int N;   // 격자 판 행의 수
    private static int totalStoneN; // 총 돌의 수

    private static Set<int[]> boardStructureSet;  // 만들어진 보드의 구조를 저장하는 set

    public static void main(String[] args) {
        boardStructureSet = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        totalStoneN = N * N;
        int initialWhiteN = 0;
        int initialBlackN = 0;

        // 격자판 입력받기
//        boolean[][] board = new boolean[N][N];  // 흰색(1)은 true, 검은색(0)은 false
        int[] board = new int[N];   // 보드의 한 줄을 하나의 수로 표현
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int color = sc.nextInt();
                if (color == 1) {   // 흰색인 경우
                    board[i] = board[i] | (1 << j);
                    initialWhiteN++;
                }
            }
        }
        initialBlackN = totalStoneN - initialWhiteN;

        if (initialWhiteN == 0 || initialWhiteN == totalStoneN) {
            System.out.println(0);
            return;
        }

        int flippedStoneN; // 뒤집힌 돌의 수
        boolean colorToFlip;    // 보드를 채울 돌의 색. 흰색(1)인 경우 true, 검은색(0)인 경우 false
        if (initialWhiteN >= initialBlackN) {
            colorToFlip = true;
            flippedStoneN = initialWhiteN;
        } else {
            colorToFlip = false;
            flippedStoneN = initialBlackN;
        }

        int minSec = BFS(board, colorToFlip, flippedStoneN);
        System.out.println(minSec);

        sc.close();
    }

    // colorToFlip 색 돌로 바꾸는데 걸리는 최소 시간 구하기
    private static int BFS(int[] board, boolean colorToFlip, int flippedStoneN) {
        Queue<State> que = new LinkedList<>();
        que.add(new State(board, flippedStoneN, 0));
        boardStructureSet.add(board);   // 가장 처음의 보드 상태 set 에 저장

        while (!que.isEmpty()) {
            State curState = que.poll();
            State nextState;

            // curState 에서 뒤집는 모든 경우의 수를 que 에 넣기. (각 열, 행, 칸)
            // 한 줄 뒤집는 모든 경우의 수 구하기
            for (int i = 0; i < N; i++) {
                nextState = flipLine(curState, colorToFlip, i, 0);    // curState 에서 r,c 줄을 colorToFlip 으로 뒤집기
                int[] nextBoard = nextState.board;
                int nextSec = nextState.sec;

                if (nextState.flippedStoneN == totalStoneN) {   // 모두 같은 색으로 뒤집혔으면 return
                    return nextSec;
                }

                if (!boardStructureSet.contains(nextBoard)) {   // 이전의 보드 상태와 겹치지 않으면
                    boardStructureSet.add(board);
                    que.add(nextState);
                }
            }

            // 한 개 뒤집는 모든 경우의 수 구하기
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    nextState = flipOne(curState, colorToFlip, i, j);
                    int[] nextBoard = nextState.board;
                    int nextSec = nextState.sec;

                    if (nextState.flippedStoneN == totalStoneN) {   // 모두 같은 색으로 뒤집혔으면 return
                        return nextSec;
                    }

                    if (!boardStructureSet.contains(nextBoard)) {   // 이전의 보드 상태와 겹치지 않으면
                        boardStructureSet.add(board);
                        que.add(nextState);
                    }
                }
            }
        }

        return 0;
    }

    // curState 에서 r,c 줄을 colorToFlip 으로 뒤집기
    private static State flipLine(State curState, boolean colorToFlip, int r, int c) {
        int[] nextBoard = new int[N];
        int nextFlippedStoneN = curState.flippedStoneN;

        // nextBoard 를 curBoard 로 복사
        nextBoard = curState.getBoard().clone();

        // 뒤집기
        if (r == 0) {
            for (int i = 0; i < N; i++) {   // c 열 뒤집기
                nextBoard[i] = nextBoard[i] ^ (1 << c);
//                nextBoard[i][c] = !nextBoard[i][c];
                boolean color = (nextBoard[i] & (1 << c)) == 1;  // 뒤집은 수가 1인지 check
                if (color == colorToFlip) {   // 뒤집어야 하는 색과 현재 뒤집은 색이 같으면
                    nextFlippedStoneN++;
                } else {
                    nextFlippedStoneN--;
                }
            }
        } else {    // r 행 뒤집기
            for (int i = 0; i < N; i++) {
                nextBoard[r] = nextBoard[r] ^ (1 << i);
                boolean color = (nextBoard[i] & (1 << c)) == 1;  // 뒤집은 수가 1인지 check
                if (color == colorToFlip) {
                    nextFlippedStoneN++;
                } else {
                    nextFlippedStoneN--;
                }
            }
        }

        return new State(nextBoard, nextFlippedStoneN, curState.sec + 1);
    }

    private static State flipOne(State curState, boolean colorToFlip, int r, int c) {
        int[] nextBoard = new int[N];
        int nextFlippedStoneN = curState.flippedStoneN;

        // nextBoard 를 curBoard 로 복사
        nextBoard = curState.getBoard().clone();

        // 뒤집기
        nextBoard[r] = nextBoard[r] ^ (1 << c);

        boolean color = (nextBoard[r] & (1 << c)) == 1;  // 뒤집은 수가 1인지 check
        if (color== colorToFlip) {
            nextFlippedStoneN++;
        } else {
            nextFlippedStoneN--;
        }

        return new State(nextBoard, nextFlippedStoneN, curState.sec + 1);
    }

    private static class State {
        int[] board;
        int flippedStoneN;
        int sec;

        public State(int[] board, int flippedStoneN, int sec) {
            this.board = board;
            this.flippedStoneN = flippedStoneN;
            this.sec = sec;
        }

        public int[] getBoard() {
            return board;
        }
    }
}
