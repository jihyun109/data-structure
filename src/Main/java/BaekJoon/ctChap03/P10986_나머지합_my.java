package ctChap03;

import java.util.Scanner;

public class P10986_나머지합_my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int answer = 0;
		int n = sc.nextInt();
		int m = sc.nextInt();
		int[] a = new int[n+1];
		long[] s = new long[n+1];
		int[] c = new int[m];
		
		for (int i = 1; i <= n; i++) {
			a[i] = sc.nextInt();
		}
		for (int i = 1; i <= n; i++) {
			s[i] = s[i-1] + a[i];
		}
		for(int i = 1; i <= n; i++) {
			int remainder = (int) (s[i] % m);
			if (remainder == 0)
				answer++;
			c[remainder]++;
		}
		for(int i = 0; i < m; i++) {
			answer += c[i] * (c[i] - 1) / 2;
		}
		System.out.println(answer);
	}
}
