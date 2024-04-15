package my.P11052_카드구매하기;

import java.util.Scanner;

public class P11052_카드구매하기_2 {

    static int[] cardPackCost;
    static int[] maxCost;
    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        int sumCardN = sc.nextInt();    // 민규가 구매하려는 카드 개수
        cardPackCost = new int[sumCardN + 1]; // i개 카드가 들어있는 카드팩의 가격 저장 배열

        // 각 카드팩의 가격 입력받기
        for(int i = 1; i <= sumCardN; i++) {
            cardPackCost[i] = sc.nextInt();
        }

        // init end
        sc.close();

        maxCost = new int[sumCardN + 1];    // i 개의 카드를 구매할 떄 지불할 수 있는 최대 금액 저장 배열

        // base case
        maxCost[1] = cardPackCost[1];

       int answer = findMaxCost(sumCardN);
       System.out.println(answer);

    }

    // cardN 개의 카드를 구매할 때 지불할 수 있는 최대 금액 구하는 메서드
    private static int findMaxCost(int cardN) {
        if (maxCost[cardN] != 0) {
            return maxCost[cardN];
        }

        int max = 0;    // 카드 cardN 장을 구매할 때 지불할 최대 금액 저장 변수
        for (int i = 1; i <= cardN; i++) {
            max = Math.max(cardPackCost[i] + findMaxCost(cardN - i), max);
        }

        return maxCost[cardN] = max;
    }
}
