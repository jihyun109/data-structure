package ctChap06;

import java.util.Scanner;

public class P11047_동전개수최솟값 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(); // 동전의 종류
		int k = sc.nextInt(); // 동전으로 만들 금액
		int[] coin = new int[N]; // 동전 종류를 담은 배열
		int coinN = 0; // k금액을 맞추는데 필요한 동전 개수(답)
		for (int i = 0; i < N; i++) { // 동전 종류 입력받기
			coin[i] = sc.nextInt();
		}

		for (int i = N - 1; i >= 0; i--) {	// 단위가 큰 동전부터 확인
			if (coin[i] <= k) {	// k보다 작거나 같으면
				coinN += k / coin[i];	// k금액을 맞추는데 필요한 동전 개수에 더함.
				k = k % coin[i];	// 남은 금액
			}
			if (k == 0)
				break;
		}

		System.out.println(coinN);
	}
}
