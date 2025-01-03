package BaekJoon.P11723_집합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P11723_집합 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());   // 연산의 수
        int[] S = new int [4];  // 공집합 S를 4개의 비트로 표현

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            int x = 0;
            int row = 0;
            int col = 0;

            switch (command) {
                case "add":
                    x = Integer.parseInt(st.nextToken()) - 1;
                    row = x / 5;
                    col = x % 5;
                    S[row] = S[row] | (1 << col);
                    break;

                case "remove":
                    x = Integer.parseInt(st.nextToken()) -1;
                    row = x / 5;
                    col = x % 5;
                    S[row] = S[row] & ~(1 << col);
                    break;

                case "check":
                    x = Integer.parseInt(st.nextToken()) -1;
                    row = x / 5;
                    col = x % 5;
                    boolean isPresent = (S[row] & (1 << col)) != 0;
                    int present = isPresent ? 1 : 0;
                    System.out.println(present);
                    break;

                case "toggle":
                    x = Integer.parseInt(st.nextToken()) -1;
                    row = x / 5;
                    col = x % 5;
                    S[row] = S[row] ^ (1 << col);
                    break;

                case "all":
                    for (int j = 0; j < 4; j++) {
                        S[j] = 63;
                    }
                    break;

                case "empty":
                    for (int j = 0; j < 4; j++) {
                        S[j] = 0;
                    }
                    break;
            }
        }
    }
}
