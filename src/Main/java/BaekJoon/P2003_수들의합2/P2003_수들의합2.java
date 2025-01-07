package BaekJoon.P2003_수들의합2;

import java.util.Scanner;

public class P2003_수들의합2 {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        int N = sc.nextInt();   // 수열의 길이
        int M = sc.nextInt();   // 부분수열의 합이 M이 되는 경우의 수를 찾아야 함.
        int[] seq = new int[N+1];   // 수열 저장 배열

        // 수열 입력받기
        for (int i = 1; i <= N; i++) {
            seq[i] = sc.nextInt();
        }
        sc.close();

        // 투포인터로 부분수열의 합이 M이되는 모든 경우의 수 찾기
        int cnt = 0;
        for (int p1 = 1; p1 <= N; p1++) {
            int sum = 0;
            for (int p2 = p1; p2 <= N; p2++) {
                sum += seq[p2];
                if (sum == M) {
                    cnt++;
                    break;
                }
            }
        }

        System.out.println(cnt);
    }
}
