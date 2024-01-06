package ctChap03;

import java.util.Scanner;

public class P1546_평균_my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		double[] score = new double[n];
		double max = 0;
		double sum = 0;

		for (int i = 0; i < n; i++) {
			score[i] = sc.nextInt();
			if (max < score[i])
				max = score[i];
		}

		for (int i = 0; i < n; i++) {
			score[i] = (score[i] / max) * 100;
			sum += score[i];
		}

		System.out.print(sum / n);
	}
}
