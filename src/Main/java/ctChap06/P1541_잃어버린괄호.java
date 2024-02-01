package ctChap06;

import java.util.Scanner;

public class P1541_잃어버린괄호 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String form = sc.next(); // 식 입력받음
		int answer = 0; // 구하는 답

		String[] str = form.split("-"); // -로 나눠서 string배열에 넣음
		for (int i = 0; i < str.length; i++) {
			if (i == 0) {
				answer += sum(str[i]); // -가 있기 전 모든 수는 다 더함
			} else {
				answer -= sum(str[i]);
			}
		}
		System.out.println(answer);
	}

	private static int sum(String string) {
		String tmp[] = string.split("[+]"); // +로 나눠 배열을 만듬
		int sum = 0;
		for (int i = 0; i < tmp.length; i++) {
			sum += Integer.parseInt(tmp[i]);
		}
		return sum;
	}
}
