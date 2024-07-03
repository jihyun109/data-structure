package my.P10815_숫자카드;

import java.util.Arrays;
import java.util.Scanner;

public class P10815_숫자카드 {
    private static int[] card;
    private static int N;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();   // 상근이가 갖고 있는 숫자 카드 개수
        card = new int[N];    // 숫자 카드 저장 배열

        // 숫자 카드 입력받기
        for (int i = 0; i < N; i++) {
            card[i] = sc.nextInt();
        }

        // 카드 정렬
        Arrays.sort(card);

        int M = sc.nextInt(); // 정수 개수
        int[] numbers = new int[M]; // 정수 저장 배열

        // 정수 입력받기
        for (int i = 0; i < M; i++) {
            numbers[i] = sc.nextInt();
        }

        sc.close();

        // 숫자 카드에서 정수 찾기
        for (int i = 0; i < M; i++) {
            int num = numbers[i];   // 찾는 정수
            int result = binarySearch(num);  // 이분 탐색
            System.out.print(result + " ");
        }
    }

    // 이분탐색으로 숫자 카드에 num 이 있으면 1, 없으면 0 반환
    private static int binarySearch(int num) {
        int start = 0;
        int middle = (N - 1) / 2;
        int end = N - 1;

        while (start <= end) {
            if (num == card[middle]) {
                return 1;
            } else if (num < card[middle]) {
                end = middle - 1;
                middle = (start + end) / 2;
            } else {
                start = middle + 1;
                middle = (start + end) / 2;
            }
        }
        return 0;
    }
}
