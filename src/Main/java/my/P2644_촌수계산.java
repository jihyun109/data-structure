package my;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P2644_촌수계산 {
	static ArrayList<Integer>[] arrList;
	static boolean[] visited;
	static int men1;
	static int men2;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(); // 전체 사람 수
		men1 = sc.nextInt(); // 촌수 계산 해야하는 두 사람의 번호
		men2 = sc.nextInt();
		int V = sc.nextInt(); // 관계 개수
		arrList = new ArrayList[n + 1];
		visited = new boolean[n + 1];
		for (int i = 0; i < n + 1; i++) { // arrList 초기화
			arrList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < V; i++) { // 관계 입력 받기
			int person1 = sc.nextInt();
			int person2 = sc.nextInt();
			arrList[person1].add(person2);
			arrList[person2].add(person1);
		}

		int chon = BFS();
		System.out.println(chon);
	}

	static int BFS() {
		Queue<int[]> que = new LinkedList<>();

		que.offer(new int[] { men1, 0 }); // men1의 번호와 촌수를 que에 저장
		visited[men1] = true;

		while (!que.isEmpty()) {
			int[] now = que.poll();
			for (int i : arrList[now[0]]) {
				if (!visited[i]) {
					int nextMen = i;
					int nextMenChon = now[1] + 1;
					que.offer(new int[] { nextMen, nextMenChon });
					visited[nextMen] = true;
					if (nextMen == men2) {
						return nextMenChon;
					}
				}

			}
		}
		return -1;
	}
}
