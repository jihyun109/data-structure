package SWEA.S1244_최대상금;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class S1244_최대상금 {

    private static Set<String> visited;
    private static int exchangeLimit;
    private static int max;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCaseN = sc.nextInt();

        // test case 입력받기
        for (int t = 1; t <= testCaseN; t++) {
            String numberString = sc.next();
            char[] numArray = numberString.toCharArray();

            exchangeLimit = sc.nextInt();
            max = 0;
            visited = new HashSet<>();

            exchange(numArray, 0);
            System.out.println("#" + t + " " + max);
        }

        sc.close();
    }

    private static void exchange(char[] numArray, int exchangeN) {
        String curString = new String(numArray);
        if (exchangeN == exchangeLimit) {
            String numberString = new String(numArray);
            int number = Integer.parseInt(numberString);

            max = Math.max(max, number);
            return;
        }

        if (visited.contains(curString + exchangeN)) {
            return;
        }

        visited.add(curString + exchangeN);

        // numArray 의 숫자 두 개를 교환하는 모든 경우
        for (int i = 0; i < numArray.length; i++) {
            for (int j = i + 1; j < numArray.length; j++) {
                char[] exchangedArray = Arrays.copyOf(numArray, numArray.length);   // 교환된 숫자 배열
                exchangedArray[i] = numArray[j];
                exchangedArray[j] = numArray[i];

//                System.out.println(exchangedArray);
                exchange(exchangedArray, exchangeN + 1);
            }
        }
    }
}
