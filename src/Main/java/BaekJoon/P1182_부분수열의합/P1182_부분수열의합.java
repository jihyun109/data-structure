package BaekJoon.P1182_부분수열의합;

import java.util.Scanner;

public class P1182_부분수열의합 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   // 수열을 이루는 정수의 개수
        int S = sc.nextInt();   // 부분 수열의 합으로 S를 만들어야 함.
        int[] seq = new int[N];     // 수열 저장 배열

        // 수열 입력받기
        for (int i = 0; i < N; i++) {
            seq[i] = sc.nextInt();
        }

        // bit masking 을 사용해 모든 부분 수열 완전 탐색
        int maxBit = (int) Math.pow(2, N) - 1;
        int seqN = 0;   // 합이 S가 되는 부분수열의 수 (구하는 값)
        for (int subSeq = 1; subSeq <= maxBit; subSeq++) {
            int sum = 0;    // 부분 수열에 포함된 정수들의 합
            for (int i = 0; i < N; i++) {   // bit 를 순회하면서 부분수열에 포함된 정수들의 합 구하기.
                boolean isContain = (subSeq & (1 << i)) != 0; // i번째 정수가 부분수열에 포함되어 있는지 나타내는 변수

                if (isContain) {    // 포함되어있으면 부분수열의 합에 더하기
                    sum += seq[i];
                }
            }

            if (sum == S) { // 현재 부분수열의 합이 S와 같으면
                seqN++;
            }
        }

        System.out.println(seqN);
    }
}
