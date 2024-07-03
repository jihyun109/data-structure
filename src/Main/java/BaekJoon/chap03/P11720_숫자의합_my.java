package chap03;

import java.util.Scanner;

public class P11720_숫자의합_my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		String num = sc.next();
		int sum = 0;
		for (int i = 0; i < N; i++) {
			int n = num.charAt(i) - '0';
			sum += n;
		}
		System.out.print(sum);
	}
}
