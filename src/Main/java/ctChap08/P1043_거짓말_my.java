package ctChap08;

import java.util.Scanner;

public class P1043_거짓말_my {
	static int[] parent;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(); // 사람 수
		int M = sc.nextInt(); // 파티 수
		int truthN = sc.nextInt(); // 진실을 아는 사람 수
		int[] truth = new int[truthN]; // 진실을 아는 사람 저장 배열
		for (int i = 0; i < truthN; i++) {
			truth[i] = sc.nextInt();
		}
		int[] party = new int[M]; // 각 파티 구성원 중 1명 저장 배열
		parent = new int[N + 1]; // 각 구성원의 parentNode 저장 배열

		for (int i = 1; i <= N; i++) { // 자기자신을 대표 노드로 초기화
			parent[i] = i;
		}

		for (int i = 0; i < M; i++) { // 파티 정보 입력받기
			int partyN = sc.nextInt(); // 파티 멤버 수
			int party1 = sc.nextInt(); // 첫번째 파티 멤버
			party[i] = party1;

			// 파티 멤버 입력받고 파티 멤버 union처리
			for (int j = 1; j < partyN; j++) {
				int partyMem = sc.nextInt();
				union(party1, partyMem);
			}
		}
		sc.close();

		// 각 파티에서 거짓말 할 수 있는지 판별
		int canLie = M;
		for (int i = 0; i < M; i++) {
			int leader = find(party[i]); // 파티 멤버의 parentNode 찾기
			for (int j = 0; j < truthN; j++) {
				// 진실을 알고있는 사람의 parentNode와 leader의 parentNode가 같으면 이 파티에서 거짓말 할 수 없음
				if (find(truth[j]) == leader) {
					canLie--; // 총 파티 수에서 빼기
					break;
				}
			}
		}

		System.out.println(canLie);
	}

	private static void union(int party1, int party2) {
		party1 = find(party1);
		party2 = find(party2);
		if (party1 != party2) {
			parent[party2] = parent[party1];
		}
	}

	private static int find(int i) {
		if (parent[i] == i) {
			return i;
		} else {
			return parent[i] = find(parent[i]);
		}
	}
}
