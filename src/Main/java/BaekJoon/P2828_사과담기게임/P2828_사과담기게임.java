package BaekJoon.P2828_사과담기게임;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P2828_사과담기게임 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   // 스크린 칸 수
        int M = sc.nextInt();   // 바구니 칸 수
        int j = sc.nextInt();   // 떨어지는 사과의 수
        Queue<Integer> que = new LinkedList<>();    // 사과가 떨어지는 위치 저장 배열

        // 사과가 떨어지는 위치 입력받기
        for (int i = 0; i < j; i++) {
            que.add(sc.nextInt());
        }
        sc.close();

        // 바구니가 이동해야하는 최소 거리 구하기
        int minDist = 0;    // 바구니가 이동해야하는 최소 거리 (구하는 값)
        int l = 1;  // 바구니 왼쪽끝의 위치
        int r = M;  // 바구니 오른쪽 끝의 위치

        while (!que.isEmpty()) {
            int apple = que.poll(); // 사과가 떨어지는 위치

            if (apple >= l && apple <= r) { // 사과가 바구니의 범위 안에 위치한 경우
                continue;
            } else if (apple < l) {     // 사과가 바구니보다 왼쪽에 위치한 경우
                int dist = l - apple;   // 바구니에 사과를 담기 위해 바구니가 이동해야할 거리
                minDist += dist;
                l -= dist;
                r -= dist;
            } else {    // 사과가 바구니보다 오른쪽에 위치한 경우
                int dist = apple - r;
                minDist += dist;
                l += dist;
                r += dist;
            }
        }

        // 답 출력
        System.out.println(minDist);
    }
}
