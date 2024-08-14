package SWEA.알고리즘특강.그래도수명이절반이되어서는;

import java.io.FileInputStream;
import java.util.Scanner;

class Solution {
    private static int[] massSize;
    private static int[] wearLevel;
    static int N;
    static int K;

    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/그래도수명이절반이되어서는/sample_input.txt"));
        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            N = sc.nextInt();   // 플래시 메모리의 블록 수
            K = sc.nextInt();   // 초기 데이터 덩어리 수
            wearLevel = new int[N];   // 각 블록의 Wear Level 저장 배열
            int minWL = Integer.MAX_VALUE;
            int maxWL = 0;
            for (int i = 0; i < N; i++) {
                int input = sc.nextInt();
                wearLevel[i] = input;
                minWL = Math.min(minWL, input);
                maxWL = Math.max(maxWL, input);
            }

            massSize = new int[K]; // 초기 데이터 덩어리의 블록 수
            for (int i = 0; i < K; i++) {
                massSize[i] = sc.nextInt();
            }

            int ans = 0;   // 최대 WL의 최소값
            while (minWL <= maxWL) {
                if (minWL == maxWL) {
                    ans = maxWL;
                    break;
                }
                ans = (minWL + maxWL) / 2;
                boolean isPossible = isPossible(ans);   // 최대 WL 이 ans 가 가능한지 판단

                if (isPossible) {
                    maxWL = ans;
                } else {
                    minWL = ans + 1;
                }
            }
            System.out.println("#" + test_case + " " + ans);
        }
    }

    private static boolean isPossible(int maxWL) {
        int sIdx = 0;   // 덩어리를 배치할 수 있는 첫번쨰 위치
        // 덩어리 배치
        for (int i = 0; i < K; i++) {
            int curMassSize = massSize[i]; // 현재 덩어리 크기
            boolean allocated = false;  // 덩어리가 배치되었는지
            for (int j = sIdx; j <= N - curMassSize; j++) {
                // 덩어리가 연속으로 배치되는지 확인
                for (int x = 0; x < curMassSize; x++) {
                    if (wearLevel[j + x] > maxWL) {
                        allocated = false;
                        break;
                    } else {
                        allocated = true;
                    }
                }

                if (allocated) {
                    sIdx = j + curMassSize;
                    break;
                }
            }
            if (!allocated) {
                return false;
            }
        }

        return true;
    }
}