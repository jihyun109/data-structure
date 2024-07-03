package my.P1654_랜선자르기;

import java.util.Scanner;

public class P1654_랜선자르기 {
    static int N;
    static int[] lan;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int K = sc.nextInt();   // 이미 가지고 있는 랜선의 개수
        N = sc.nextInt();   // 필요한 랜선의 개수
        lan = new int[K]; // 가지고 있는 랜선의 길이 저장 배열

        // 랜선 길이 입력받기
        long maxLan = 0;
        for (int i = 0; i < K; i++) {
            lan[i] = sc.nextInt();
            if (maxLan < lan[i]) {
                maxLan = lan[i];
            }
        }
        sc.close();

        long answer = binarySearch(maxLan);
        System.out.println(answer);
    }

    private static long binarySearch(long maxLan) {
        long min = 1;
        long max = maxLan;

        while (min <= max) {
            long mid = (min + max) / 2;
            long lanN = cutLan(mid);

            if (lanN >= N) {
                min = mid + 1;
            } else {
                max = mid - 1;
            }
        }
        return min - 1;
    }

    // length 로 모든 랜선을 자르면 나오는 랜선의 개수를 return
    private static long cutLan(long length) {
        long lanN = 0;
        for (int i = 0; i < lan.length; i++) {
            lanN += lan[i] / length;
        }

        return lanN;
    }
}