package ctChap05;

import java.util.Arrays;
import java.util.Scanner;

public class P1920_원하는정수찾기_my {
	static int[] arrN;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		arrN = new int[N];
		for (int i = 0; i < N; i++) {
			arrN[i] = sc.nextInt();
		}
		Arrays.sort(arrN);		// arrN 정렬

		int M = sc.nextInt();
		int[] arrM = new int[M];
		for (int i = 0; i < M; i++) {
			arrM[i] = sc.nextInt();
		}

		for (int i = 0; i < M; i++) {	// M개의 수들이 A 안에 존재하는지 알아보기
			int answer = binarySearch(0, N - 1, arrM[i]);
			System.out.println(answer);
		}
	}

	private static int binarySearch(int pl, int pr, int target) {
		boolean find = false;
		while (pl <= pr) {
			int m = (pl + pr) / 2;
			if (arrN[m] == target) {
				find = true;
				break;
			} else if (arrN[m] > target) {
				pr = m - 1;
			} else if (arrN[m] < target) {
				pl = m + 1;
			}
		}
		if (find == true)
			return 1;
		else
			return 0;
	}
}
