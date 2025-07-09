package BaekJoon.b10830_행렬제곱;

import java.io.*;
import java.util.*;

public class b10830_행렬제곱 {
	private static final int MOD_N = 1000;
	private static long B;
	private static int size;

	public static void main(String[] args) throws IOException {
		int[][] arr = init();

		int[][] answer = arrPow(arr, B);

		print(answer);
	}

	private static int[][] init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		size = Integer.parseInt(st.nextToken());
		B = Long.parseLong(st.nextToken());

		// 행렬 입력받기
		int[][] arr = new int[size][size];

		for (int i = 0; i < size; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < size; j++) {
				arr[i][j] = (Integer.parseInt(st.nextToken())) % MOD_N;
			}
		}

		br.close();

		return arr;
	}

	// arr 를 p 제곱
	private static int[][] arrPow(int[][] arr, long p) throws IOException {
		if (p == 1) {
			return arr;
		}

		// arr 의 p/2 제곱구하기
		int[][] tmp = arrPow(arr, p / 2);

		// tmp * tmp
		int[][] ret = multiply(tmp, tmp);

		// 홀수인 경우 arr 곱하기
		if (isOdd(p)) {
			ret = multiply(ret, arr);
		}

		return ret;
	}

	// 행렬 곱
	private static int[][] multiply(int[][] arr1, int[][] arr2) {
		int[][] ret = new int[size][size];

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				int value = 0; // ret[i][j]에 들어갈 값

				for (int t = 0; t < size; t++) {
					value += arr1[r][t] * arr2[t][c];
					value %= MOD_N;
				}
				ret[r][c] = value;
			}
		}

		return ret;
	}

	// n이 홀수인지 확인
	private static boolean isOdd(long n) {
		if (n % 2 == 1) {
			return true;
		}
		return false;
	}

	private static void print(int[][] arr) throws IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				bw.append(arr[r][c] + " ");
			}
			bw.append('\n');
		}

		bw.flush();
		bw.close();
	}
}
