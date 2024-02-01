package ctChap07;

import java.util.Scanner;

public class P1456_거의소수 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long A = sc.nextLong();
		long B = sc.nextLong();
		long[] arr = new long[10000001];
		for (int i = 2; i < arr.length; i++) {
			arr[i] = i;
		}
		for (int i = 2; i <= Math.sqrt(arr.length); i++) {
			if (arr[i] == 0) {
				continue;
			}
			for (int j = i + i; j < arr.length; j = j + i) {
				arr[j] = 0;
			}
		}
		int cnt = 0;
		for (int i = 2; i < 10000001; i++) {
			if (arr[i] != 0) {
				long tmp = arr[i];
				while ((double)arr[i] <= (double)B / (double)tmp) {
					if ((double)arr[i] >= (double)A / (double)tmp) {
						cnt++;
					}
					tmp = tmp * arr[i];
				}
			}
		}
		System.out.println(cnt);
	}
}
