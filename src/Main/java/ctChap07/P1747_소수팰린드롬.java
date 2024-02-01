package ctChap07;

import java.util.Scanner;

public class P1747_소수팰린드롬 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int[] arr = new int[10000001];
		for (int i = 2; i < arr.length; i++) { // 배열 채우기
			arr[i] = i;
		}
		for (int i = 2; i < Math.sqrt(arr.length); i++) { // 소수만 남기고 다 0으로
			if (arr[i] == 0) {
				continue;
			}
			for (int j = i + i; j < arr.length; j = j + i) { // 배수 지우기
				arr[j] = 0;
			}
		}

		// N부터 펠린드롬 찾기
		int i = N;
		while (true) {
			if (arr[i] != 0) {
				if (isPalindrome(arr[i])) {
					System.out.println(arr[i]);
					break;
				}
			}
			i++;
		}
	}

	private static boolean isPalindrome(int k) {
		char[] num = String.valueOf(k).toCharArray();
		int pl = 0;
		int pr = num.length - 1;
		while (pl < pr) {
			if (num[pl] != num[pr]) {
				return false;
			}
			pl++;
			pr--;
		}
		return true;
	}
}
