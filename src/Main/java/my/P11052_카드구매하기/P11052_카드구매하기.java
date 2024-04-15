package my.P11052_카드구매하기;

import java.util.Scanner;

public class P11052_카드구매하기 {
    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        int cardN = sc.nextInt();   // 구매하려는 카드 개수
        double[] cardCost = new double[cardN + 1];    // 카드팩 가격 저장 배열

        // 카드팩 가격 입력받아 카드 한 장 가격 저장하기.
        for (int i = 1; i <= cardN; i++) {
            cardCost[i] = sc.nextDouble(); // 카드 i장이 들어있는 카드팩 가격 입력받기
            cardCost[i] = cardCost[i] / i;  // 카드 한 장 가격 저장.

            // 카드 1장이 들어있는 카드팩부터 i장 들어있는 카드팩까지 중 카드 한 장 가격의 최댓값 저장
            if (cardCost[i-1] > cardCost[i]) {
                cardCost[i] = cardCost[i-1];
            }
        }

        // init end
        sc.close();

        int answer = (int)(cardCost[cardN] * cardN);
        System.out.println(answer);
    }
}
