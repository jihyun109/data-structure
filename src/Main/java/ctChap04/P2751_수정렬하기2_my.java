package ctChap04;

import java.util.Scanner;

public class P2751_수정렬하기2_my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] a = new int[N];
		for (int i = 0; i < N; i++)
			a[i] = sc.nextInt();
		mergeSort(a);
	}

	public static void mergeSort(int[] a) {
		if (a.length > 1) {
			int m = a.length / 2;
			int[] left = new int [m];
			int[] right = new int [a.length - m];
			int i;
			for (i = 0; i < left.length; i++)
				left[i] = a[i];
			for (int j = 0; j < right.length; j++)
				right[j] = a[j + i + 1];
			if (left.length > 1)
				mergeSort(left);
			if (right.length >1)
				mergeSort(right);
		}
	}

}
