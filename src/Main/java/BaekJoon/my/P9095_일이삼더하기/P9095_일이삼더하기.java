package my.P9095_일이삼더하기;

import java.util.Arrays;
import java.util.Scanner;

public class P9095_일이삼더하기 {
    static int[] D;
    public static void main(String[] args) {

        // 숫자 i(i <= 11)를 1, 2, 3의 합으로 나타내는 방법의 수 구하기
        D = new int[12];    // 1, 2, 3의 합으로 나타내는 방밥의 수 저장 배열

        Arrays.fill(D, Integer.MAX_VALUE);

        // base case
        D[0] = 0;
        D[1] = 1;
        D[2] = 2;
        D[3] = 4;

        dp(11);

        // test case 입력받아 답 출력
        Scanner sc = new Scanner(System.in);
        int testCaseN = sc.nextInt();   // testcase 개수

        for(int i = 0; i < testCaseN; i++) {

            // 정수 n 입력받기
            int n = sc.nextInt();

            System.out.println(D[n]);
        }

        // init end
        sc.close();
    }

    private static int dp(int i) {
        if (D[i] != Integer.MAX_VALUE) {
            return D[i];
        }

        return D[i] = dp(i-1) + dp(i-2) + dp(i-3);
    }
}
