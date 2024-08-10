package SWEA.S9660_번호붙이기;

import java.util.Scanner;

public class S9660_번호붙이기 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCaseN = sc.nextInt();

        for (int tc = 1; tc <= testCaseN; tc++) {
            int N = sc.nextInt();   // 사람 수
            int[] hateNs = new int[N + 1];  // 싫어하는 숫자 저장 배열

            for (int i = 1; i <= N; i++) {
                hateNs[i] = sc.nextInt();
            }
        }

        sc.close();
    }
}
