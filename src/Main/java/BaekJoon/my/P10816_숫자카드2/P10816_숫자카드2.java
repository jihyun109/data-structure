package BaekJoon.my.P10816_숫자카드2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class P10816_숫자카드2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   // 상근이가 갖고 있는 숫자 카드의 개수
        HashMap<Integer, Integer> cardCount = new HashMap<>();  // 카드의 숫자와 개수를 저장하는 해시맵

        // 숫자 카드 입력받기
        for (int i = 0; i < N; i++) {
            int card = sc.nextInt();
            cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
        }

        int M = sc.nextInt();   // 정수 개수

        // 정수를 입력 받고 숫자 카드에 해당 정수가 몇 개 있는지 확인
        // 입력 받고 확인하는게 성능이 좋은지 받으면서 동시에 하는게 성능이 좋은지 체크
        for (int i = 0; i < M; i++) {
            int key = sc.nextInt();
            System.out.print(cardCount.getOrDefault(key, 0) + " ");
        }
        sc.close();
    }
}
