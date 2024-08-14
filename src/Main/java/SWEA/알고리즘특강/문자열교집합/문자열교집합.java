package SWEA.알고리즘특강.문자열교집합;

import java.util.HashMap;
import java.util.Scanner;
import java.io.FileInputStream;


class Solution {
    public static void main(String args[]) throws Exception {

        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/문자열교집합/sample_input.txt"));

        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();


        for (int test_case = 1; test_case <= T; test_case++) {
            int N = sc.nextInt();   // 첫번째 집합의 원소 수
            int M = sc.nextInt();   // 두번쨰 집합의 원소 수
            HashMap<String, String> hashMap = new HashMap<>();
            for (int i = 0; i < N; i++) {
                String input = sc.next();
                hashMap.put(input, null);
            }

            int cnt = 0;
            for (int i = 0; i < M; i++) {
                String input = sc.next();
                if (hashMap.containsKey(input)) {
                    cnt++;
                }
            }
            System.out.println("#" + test_case + " " + cnt);
        }
    }
}