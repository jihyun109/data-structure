package my.P1309_동물원;

import java.util.Scanner;

public class P1309_동물원 {
    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        int row = sc.nextInt(); // 우리의 행 개수

        // init end
        sc.close();

        int[][] lionVariation = new int[row + 1][3];    // 2 * i 크기의 우리에 사자를 넣는 경우의 수 저장 배열

        // base case
        // 우리가 2 * 1 크기 일 떄
        lionVariation[1][0] = 1;    // 1번째 행의 사자를 넣지 않는 경우의 수
        lionVariation[1][1] = 1;    // 1번째 행의 왼쪽 우리에 사자를 넣는 경우의 수
        lionVariation[1][2] = 1;    // 1번쨰 행의 오른쪽 우리에 사자를 넣는 경우의 수

        // lionVariation 배열 채우기
        int moduloN = 9901; // 나머지 연산을 수행하는 숫자
        for (int i = 2; i <= row; i++) {
            lionVariation[i][0] = (lionVariation[i - 1][0] + lionVariation[i - 1][1] + lionVariation[i - 1][2]) % moduloN;
            lionVariation[i][1] = (lionVariation[i - 1][0] + lionVariation[i - 1][2]) % moduloN;
            lionVariation[i][2] = (lionVariation[i - 1][0] + lionVariation[i - 1][1]) % moduloN;
        }

        int answer = (lionVariation[row][0] + lionVariation[row][1] + lionVariation[row][2]) % moduloN;
        System.out.println(answer);
    }
}
