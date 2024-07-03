package chap01;

import java.util.Scanner;

class DigitsNo {

	public static void main(String[] args) {
		Scanner stdIn = new Scanner(System.in);

		int n;
		do {
			System.out.print("정수값: ");
			n = stdIn.nextInt();
		} while (n <= 0);

		int no = 0; // 자릿수
		while (n > 0) {
			n /= 10;
			no++;
		}

		System.out.println("그 수는 " + no + "자리입니다.");
	}

}
