package BaekJoon.b9663_NQueen;

import java.util.*;
import java.io.*;

public class b9663_NQueen {
    private static int queenN;
    private static int cnt;
    private static int N;
    private static int[] queenLoc;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        sc.close();

        findAnswer();

        System.out.println(cnt);
    }

    private static void findAnswer() {
        // row 별 방문할 수 있는 좌표 저장
        cnt = 0;
        queenN = 0;
        queenLoc = new int[N];

        DFS(0);
    }

    // r행에서부터 퀸을 놓는 모든 경우의 수 구하기
    private static void DFS(int r) {
        for (int c = 0; c < N; c++) {
            // 퀸을 놓을 수 있는 경우
            if (canVisit(r, c)) {
                // 퀸 놓기
                queenN++;
//				System.out.println("(" + r + ", " + c + ")");

                // 퀸 수가 다 채워진 경우
                if (queenN == N) {
                    cnt++;
                    queenN--;
                    return;
                }
                // 퀸 수가 다 채워지지 않은 경우
                else {
                    // 방문 처리
                    queenLoc[r] = c;

                    // 다음 행 탐색
                    DFS(r + 1);

//					System.out.println("out");

                    // 방문처리 롤백
                    queenLoc[r] = -1;
                    queenN--;
                }

            }
        }
    }

    private static boolean canVisit(int r, int c) {
        // 이전 행에 놓은 퀸의 위치를 통해 판별
        for (int qR = 0; qR < r; qR++) {
            int qC = queenLoc[qR];

            // 대각선에 위치하는 경우
            if (Math.abs(r - qR) == Math.abs(c - qC)) {
                return false;
            }
            // 같은 열에 위치하는 경우
            else if (qC == c) {
                return false;
            }
        }
        return true;
    }
}
