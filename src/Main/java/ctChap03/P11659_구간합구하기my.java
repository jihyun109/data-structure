package ctChap03;

import java.util.Scanner;

public class P11659_구간합구하기my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int[] a = new int[N+1];
		int[] i = new int[M];
		int[] j = new int[M];
		a[1] = sc.nextInt();
		for (int k = 2; k < N+1; k++) {
			a[k] = sc.nextInt();
			a[k] = a[k - 1] + a[k];
		}
		for(int k = 0; k < M; k++) {
			i[k] = sc.nextInt();
			j[k] = sc.nextInt();
		}
		for(int k = 0; k < M; k++) {
			System.out.println(a[j[k]] - a[i[k]-1]);
		}
	}
}
