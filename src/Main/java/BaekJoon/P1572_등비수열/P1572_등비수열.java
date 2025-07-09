package BaekJoon.P1572_등비수열;

import java.util.Scanner;

public class P1572_등비수열 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();   // 초항
        int r = sc.nextInt();   // 등비
        int n = sc.nextInt();   // 항의 수
        int mod = sc.nextInt(); // 나눌 수

        sc.close();

        int answer = findAnswer(1, n);   // 1항부터 n 항까지의 합의 mod 구하는 메서드
        System.out.println(answer);
    }

    private static int findAnswer(int s, int e) {
        if (s == e) {
//            return
        }

        return 0;
    }
}
