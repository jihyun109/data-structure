package BaekJoon.P15651_N과M3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class P15651_N과M3 {
    private static BufferedWriter bw;
    private static int cnt; // 수열의 길이
    private static int[] seq;   // 수열 저장 배열
    private static int N;
    private static int M;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = sc.nextInt();
        M = sc.nextInt();
        sc.close();
        cnt = 0;
        seq = new int[M];

        DFS();

        bw.flush();
        bw.close();
    }

    private static void DFS() throws IOException {
        if (cnt == M) { // 수열의 길이가 M이 되었다면
            for (int i = 0; i < M; i++) {
                bw.write(seq[i] + " ");
            }
            bw.write("\n");
            return;
        }
        for (int i = 1; i <= N; i++) {
            seq[cnt] = i;
            cnt++;
            DFS();
            cnt--;
            seq[cnt] = 0;
        }
    }
}
