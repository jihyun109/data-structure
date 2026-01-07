package BaekJoon.b10942_팰린드롬;

import java.io.*;
import java.util.StringTokenizer;

public class b10942_팰린드롬 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 수열 입력받기
        int N = Integer.parseInt(br.readLine());
        int[] sequence = new int[N + 1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());
        }

        // 모든 팰린드롬 찾기
        boolean[][] ispalindrome = findPalindrome(N, sequence);

        // 질문 처리
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int queryN = Integer.parseInt(br.readLine());
        for (int q = 0; q < queryN; q++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());

            if (ispalindrome[s][e]) {
                bw.append("1" + '\n');
            } else {
                bw.append("0" + '\n');
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }

    static boolean[][] findPalindrome(int N, int[] sequence) {
        boolean[][] res = new boolean[N + 1][N + 1];
        init(res, N);

        for (int size = 2; size <= N; size++) {
            for (int s = 1; s <= N - size + 1; s++) {
                int e = s + size - 1;

                if (s + 1 > e - 1 && sequence[s] == sequence[e]) {
                    res[s][e] = true;
                    continue;
                }

                if (res[s + 1][e - 1] && sequence[s] == sequence[e]) {
                    res[s][e] = true;
                }
            }
        }
        return res;
    }

    static void init(boolean[][] res, int N) {
        for (int i = 1; i <= N; i++) {
            res[i][i] = true;
        }
    }
}

