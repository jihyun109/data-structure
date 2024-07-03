package ctChap04;

import java.util.Scanner;

public class P2750_수정렬하기_my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = sc.nextInt();
		}
		for (int i = N - 1; i > 0; i--) {
			int cnt = 0;
			for (int j = 0; j < i; j++) {
				if (a[j] > a[j + 1])
					swap(a, j, j + 1);
				cnt++;
			}
			if (cnt == 0)
				break;
		}
		for (int i = 0; i < N; i++) {
			System.out.println(a[i]);
		}
	}

	static void swap(int[] a, int idx1, int idx2) {
		int tmp = a[idx1];
		a[idx1] = a[idx2];
		a[idx2] = tmp;
	}
}
