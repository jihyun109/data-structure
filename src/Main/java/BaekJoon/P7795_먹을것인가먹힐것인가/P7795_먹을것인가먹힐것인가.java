package BaekJoon.P7795_먹을것인가먹힐것인가;

import java.io.*;
import java.util.*;

public class P7795_먹을것인가먹힐것인가 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcaseN = Integer.parseInt(br.readLine());    // 테스트케이스 수

        for (int t = 0; t < testcaseN; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken()); // A의 개수
            int M = Integer.parseInt(st.nextToken());   // B의 개수
            int[] A = new int[N];   // A 저장 배열
            List<Integer> B = new ArrayList<>(M);   // B 저장 배열

            // A 입력받기
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                A[i] = Integer.parseInt(st.nextToken());
            }

            // B 입력받기
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                B.add(Integer.parseInt(st.nextToken()));
            }

            // B 정렬
            Collections.sort(B);

            // A를 돌면서 A가 B보다 큰 쌍의 개수 구하기
            int cnt = 0;    // A가 B보다 큰 쌍의 개수
            for (int i = 0; i < N; i++) {
                int a = A[i];
                int largestBelowA = findLargeestBelowA(a, B);  // B에서 a보다 작은 수 중 가장 큰 수의 index
                cnt += largestBelowA + 1;
            }
            bw.write(cnt + "\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    private static int findLargeestBelowA(int a, List<Integer> B) {
        int s = 0;
        int e = B.size() - 1;

        while (s <= e) {
            int m = (s + e) / 2;
            if (B.get(m) > a) {
                e = m - 1;
            } else if (B.get(m) < a) {
                s = m + 1;
            } else {
                e = m - 1;
            }
        }

        return e;
    }
}
