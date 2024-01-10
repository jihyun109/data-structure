package ctChap04;

import java.util.Arrays;
import java.util.Scanner;

public class P11004_K번쨰수_my {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();
		long[] A = new long[N+1];
		for (int i = 1; i <= N; i++)
			A[i] = sc.nextInt();
		int left = 1;
		int right = N;
		quickSort(A, left, right);
		int i;
		for (i = 1; i <= N; i++)
			if (i == K)
				break;
		System.out.print(A[i]);
	}

	public static void quickSort(long[] a, int left, int right) {
		int pl = left;
		int pr = right;
		int pivot = (pl + pr) / 2;
		while (pl <= pr) {
			while (a[pl] < a[pivot])
				pl++;
			while (a[pr] > a[pivot])
				pr--;
			if (pl <= pr)
				swap(a, pl++, pr--);
		}
		if (left < pr)
			quickSort(a, left, pr);
		if (right > pl)
			quickSort(a, pl, right);
	}

	public static void swap(long[] a, int pl, int pr) {
		long tmp = a[pl];
		a[pl] = a[pr];
		a[pr] = tmp;
	}
}
