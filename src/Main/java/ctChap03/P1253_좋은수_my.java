package ctChap03;

import java.util.Arrays;
import java.util.Scanner;

public class P1253_좋은수_my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int cnt = 0;
		int N = sc.nextInt();
		long[] a = new long[N];
		long[] good = new long[N];
		for (int i = 0; i < N; i++) {
			a[i] = sc.nextLong();
		}
		Arrays.sort(a);
		for (int i = 0; i < N - 1; i++) {
			int j = i + 1;
			int pnt = 0;
			while (pnt < a.length && j < a.length) {
				if (a[i] + a[j] < a[pnt])
					j++;
				else if (a[i] + a[j] == a[pnt]) {
					if (i != pnt && j != pnt) {
						good[pnt] = 1;
						pnt++;
						j++;
					} else if (i == pnt)
						i++;
					else if (j == pnt)
						j++;
				} else
					pnt++;
			}
		}
		for (int i = 0; i < N; i++) {
			if (good[i] == 1)
				cnt++;
		}
		System.out.print(cnt);
	}
}
