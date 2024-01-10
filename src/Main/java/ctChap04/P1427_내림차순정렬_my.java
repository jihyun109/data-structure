package ctChap04;

import java.util.Scanner;

public class P1427_내림차순정렬_my {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String str = sc.next();
		int[] A = new int[str.length()];
		for (int i = 0; i < str.length(); i++) {
			A[i] = Integer.parseInt(str.substring(i, i + 1));
		}
		for (int i = 0; i < A.length - 1; i++) {
			int max = i;
			for (int j = i + 1; j < A.length; j++) {
				if (A[max] < A[j])
					max = j;
			}
			swap(A, i, max);
		}
		for (int i = 0; i < A.length; i++)
			System.out.print(A[i]);
	}

	static void swap(int[] a, int i, int max) {
		int tmp = a[i];
		a[i] = a[max];
		a[max] = tmp;
	}
}
