package ctChap08;

import java.util.Scanner;

public class P1717_집합표현하기 {
	public static int[] parent;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		parent = new int[N + 1];
		for (int i = 0; i <= N; i++) {
			parent[i] = i;	// 대표 노드를 자기 자신으로 초기화하기
		}
		for (int i = 0; i < M; i++) {
			int question = sc.nextInt();
			int a = sc.nextInt();
			int b = sc.nextInt();
			if (question == 0) {	// 집합 합치기
				union(a, b);
			} else {
				if (checkSame(a, b)) {
					System.out.println("YES");
				} else {
					System.out.println("NO");
				}
			}
		}
	}
	
	public static void union(int a, int b) {	// union 연산: 대표 노드끼리 연결하기
		a = find(a);
		b = find(b);
		if (a != b) {
			parent[b] = a;
		}
	}
	
	public static int find(int a) {	// find 연산
		if (a == parent[a]) {
			return a;
		} else {
			return parent[a] = find(parent[a]);	// 재귀 함수의 형태로 구현 -> 경로 압축 부분
		}
	}
	
	public static boolean checkSame(int a, int b) {	// 두 원소가 같은 집합인지 확인하기
		a = find(a);
		b = find(b);
		if (a == b) {
			return true;
		}
		return false;
	}
}
