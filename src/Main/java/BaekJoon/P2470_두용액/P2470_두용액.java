package BaekJoon.P2470_두용액;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P2470_두용액 {
    private static int s1;
    private static int s2;
    private static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());    // 전체 용액의 수

        // 용액 정보 입력받기
        int[] solutions = new int[N];   // 용액 저장 배열.
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            solutions[i] = Integer.parseInt(st.nextToken());
        }
        br.close();

        // 용액 오릌차순으로 정렬
        Arrays.sort(solutions);

        // 특성값의 합이 0과 가장 가까운 용액 찾기
        findSolutions(solutions);

        System.out.println(solutions[s1] + " " + solutions[s2]);
    }

    private static void findSolutions(int[] solutions) {
        int minAbs = Integer.MAX_VALUE;    // 두 용액의 특성값 합의 절댓값의 최솟값 저장 변수
        int p1 = 0; // 두 값을 비교할 때 사용할 포인터
        int p2 = N - 1;

        while (p1 < p2) {
            int sum = solutions[p1] + solutions[p2];    // 두 특성값의 합

            if (Math.abs(sum) < minAbs) {   // 두 특성값 합의 절댓값이 minAbs 보다 작으면 업데이트
                minAbs = Math.abs(sum);
                s1 = p1;
                s2 = p2;
            }

            // sum 의 부호에 따라 포인터 이동
            if (sum > 0) {
                p2--;
            } else if (sum < 0) {
                p1++;
            } else {    // sum 이 0인 경우 더 찾아볼 필요가 없으므로 return
                return;
            }
        }
    }
}
