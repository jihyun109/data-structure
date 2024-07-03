package chap01;

import java.util.Scanner;
import static java.lang.Math.*;

public class DigitsNoMy {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		int n;
		int i;
		boolean include = false;

		do {
			System.out.print("양의 정수: ");
			n = s.nextInt();
		} while (n < 0);

		for (i = 0; include == false; i++) {
			int left = (int) pow(10, i);
			int right = 0;
			for (int j = i; j >= 0; j--)
				right += pow(10, j) * 9;
			System.out.println(left + " " + right);

			if (left <= n && n <= right)
				include = true;
		}

		System.out.println("그 수는 " + i + "의 자리입니다.");
	}
}
