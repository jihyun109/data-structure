package ctChap04;

import java.util.Arrays;
import java.util.Scanner;

public class P11399_ATM_my {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] P = new int[N];
		for (int i = 0; i < N; i++)
			P[i] = sc.nextInt();
		Arrays.sort(P);
		int answer = P[0];
		for (int i = 1; i < N; i++) {
			P[i] += P[i - 1];
			answer += P[i];
		}
		System.out.print(answer);
	}
}
