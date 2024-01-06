package chap03;

import java.util.Scanner;

public class BinSearchExMy {

	static int binSearch(int[] a, int key) {
		int pl = 0;
		int pr = a.length - 1;
		int pc;

		System.out.print("   |");
		for (int i = 0; i < a.length; i++)
			System.out.printf("%3d", i);
		System.out.println();
		System.out.print("---+");
		for (int i = 0; i < a.length * 3 + 2; i++)
			System.out.print("-");
		System.out.println();

		do {
			pc = (pl + pr) / 2;
			print(a, pl, pr, pc);
			if (a[pc] == key)
				return pc;
			else if (a[pc] < key) {
				pl = pc + 1;
			} else
				pr = pc - 1;

		} while (pl <= pr);
		return -1;
	}

	public static void print(int[] a, int pl, int pr, int pc) {
		System.out.printf(String.format("   |%%%ds%%%ds%%%ds\n", (pl + 1) * 2, (pc + 1) * 2, (pr + 1) * 2), "<-", "+",
				"->");
		System.out.printf("%3d|", pc);
		for (int i = 0; i < a.length; i++)
			System.out.printf("%3d", a[i]);
		System.out.println();
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.print("요솟수: ");
		int n = sc.nextInt();
		int[] a = new int[n];

		System.out.println("오름차순으로 입력하세요.");
		for (int i = 0; i < n; i++) {
			System.out.print("a[" + i + "]: ");
			a[i] = sc.nextInt();
		}

		System.out.print("검색할 값: ");
		int key = sc.nextInt();

		int idx = binSearch(a, key);

		System.out.println(key + "는 x[" + idx + "]에 있습니다.");
	}
}
