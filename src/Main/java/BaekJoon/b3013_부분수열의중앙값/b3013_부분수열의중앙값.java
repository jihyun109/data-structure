package BaekJoon.b3013_부분수열의중앙값;

import java.util.*;
import java.io.*;

public class b3013_부분수열의중앙값 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        // 수열 입력받기
        st = new StringTokenizer(br.readLine());
        int[] sequence = new int[N];
        int keyB = -1; // B의 위치
        for (int i = 0; i < N; i++) {
            int n = Integer.parseInt(st.nextToken());

            // n 이 B인 경우
            if (n == B) {
                keyB = i;
            }
            // n 값이 B보다 큰 경우
            else if (n > B) {
                sequence[i] = 1;
            }
            // n 값이 B보다 작은 경우
            else {
                sequence[i] = -1;
            }
        }

        br.close();

        // keyB - 1 -> 0까지 누적합 구하기
        Map<Integer, Integer> left = new HashMap<>();
        int sum = 0;
        for (int i = keyB - 1; i >= 0; i--) {
            sum += sequence[i];

            left.merge(sum, 1, Integer::sum);
        }

        // keyB - 1 -> 0까지 누적합 구하기
        Map<Integer, Integer> right = new HashMap<>();
        sum = 0;
        for (int i = keyB + 1; i < N; i++) {
            sum += sequence[i];

            right.merge(sum, 1, Integer::sum);
        }

        // Map을 돌면서 key의 합이 0이 되는 것의 개수 더하기
        int answer = 0;
        for (Map.Entry<Integer, Integer> entry : left.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();

            if (right.containsKey(-key)) {
                int rValue = right.get(-key);
                int t = value * rValue;
                answer += t;
            }
        }

        answer += left.getOrDefault(0, 0) + right.getOrDefault(0, 0) + 1;

        System.out.println(answer);
    }
}
