package SWEA.알고리즘특강.촛불이벤트;

import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/촛불이벤트/sample_input.txt"));

        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            long N = sc.nextLong();   // 양초의 수
            long left = 1;
            long right = (long) Math.sqrt(2 * N);

            long ans = -1;
            while (left <= right) {
                long dan = left + (right- left) / 2;
//                long dan =( left + right) / 2;
                long neededCandleN = (dan * (dan + 1)) / 2;

                if (N > neededCandleN) {
                    left =  (dan + 1);
                } else if (N < neededCandleN) {
                    right = dan - 1;
                } else {
                    ans = dan;
                    break;
                }
            }
            System.out.println("#" + test_case + " " + ans);
        }
    }
}