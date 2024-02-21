package ctChap08;

import java.util.ArrayList;
import java.util.Scanner;

public class P1043_거짓말 {
	public static int[] parent;
	public static int[] trueP;
	public static ArrayList<Integer>[] party;
	public static int result;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int T = sc.nextInt();
		result = 0;
		trueP = new int[T];
		for (int i = 0; i < T; i++) {	// 진실을 아는 사람 저장하기
			trueP[i] = sc.nextInt();
		}
		party = new ArrayList[M];
		for (int i = 0; i < M; i++) {	// 파티 데이터 저장하기
			party[i] = new ArrayList<Integer>();
			int party_size = sc.nextInt();
			for (int j = 0; j < party_size; j++) {
				party[i].add(sc.nextInt());
			}
		}
		parent = new int[N + 1];
		for (int i = 0; i <= N; i++) {	// 대표 노드를 자기 자신으로 초기화하기
			parent[i] = i;
		}
		for (int i = 0; i < M; i++) {	// 각 파티에 참여한 사람들을 1개의 그룹으로 만들기
			int firstPeaple = party[i].get(0);
			for (int j = 1; j < party[i].size(); j++) {
				union(firstPeaple, party[i].get(j));
			}
		}
		
		// 각 파티의 대표 노드와 진실을 아는 사람들의 대표 노드가 같다면 과장할 수 없음
		for (int i = 0; i < M; i++) {
			boolean isPossible = true;
			int cur = party[i].get(0);
			for (int j = 0; j < trueP.length; j++) {
				if (find(cur) == find(trueP[j])) {
					isPossible = false;
					break;
				}
			}
			if (isPossible) {	// 모두 다르면 결괏값 1 증가
				result++;
			}
		}
		System.out.println(result);
	}
	
	public static void union(int a, Integer b) {
		a = find(a);
		b = find(b);
		if (a != b) {
			parent[b] = a;
		}	
	}
	
	public static int find(int a) {
		if (a == parent[a]) {
			return a;
		} else {
			return parent[a] = find(parent[a]);
		}
	}	
}
