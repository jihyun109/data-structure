package BaekJoon.P2447_별찍기10;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class P2448_별찍기10 {
    static char[][] star;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        sc.close();

        // 전체 구역을 9등분해 그 가운데를 빈칸으로 채우고 나머지는 별로 채운다음 별로 채운 구역들을 또 9로 나누는 과정 반복
        star = new char[N][N]; // 패턴을 채울 배열
        makePattern(0, 0, N, false);

        // 답 출력
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                bw.append(star[i][j]);
            }
            bw.append('\n');
        }
        bw.flush();
        bw.close();
    }

    private static void makePattern(int x, int y, int N, boolean isBlank) throws IOException {
        if (isBlank) {  // 현재 구역이 빈칸으로 채워져야 하는 경우
            for (int i = x; i < x + N; i++) {   // x, y 좌표로부터 크기 N의 구역을 빈칸으로 채우기
                for (int j = y; j < y + N; j++) {
                    star[i][j] = ' ';
                }
            }
            return;
        }

        // 현재 구역의 크기가 1인 경우 *로 채우고 return
        if (N == 1) {
            star[x][y] = '*';
            return;
        }

        // 9구역으로 나눈 후 패턴 만들기
        int size = N / 3;   // 패턴의 사이즈
        int cnt = 1;    // 몇번쨰 구역인지 count
        for (int i = x; i < x + N; i += size) {
            for (int j = y; j < y + N; j += size) {
                if (cnt == 5) { // 5번째 구역이면 빈칸으로 채우기
                    makePattern(i, j, size, true);
                } else {
                    makePattern(i, j, size, false);
                }
                cnt++;
            }
        }
    }
}
