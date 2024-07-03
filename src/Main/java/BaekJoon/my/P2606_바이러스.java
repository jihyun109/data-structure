package my;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P2606_바이러스 {
	static Queue<Integer> que;
	static ArrayList<Integer>[] arrList; // 연결된 컴퓨터 저장
	static boolean[] visited;
	static int count = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		que = new LinkedList<Integer>();
		int N = sc.nextInt(); // 컴퓨터의 수
		int E = sc.nextInt(); // 연결되어있는 컴퓨터 쌍의 수
		arrList = new ArrayList[N + 1];
		visited = new boolean[N + 1];

		for (int i = 0; i < N + 1; i++) { // ArrayList 초기화
			arrList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < E; i++) { // 연결되어있는 컴퓨터 입력 받기
			int c1 = sc.nextInt();
			int c2 = sc.nextInt();
			arrList[c1].add(c2);
			arrList[c2].add(c1);
		}
		visited[1] = true;

		for (int i : arrList[1]) { // 1번 컴퓨터와 연결되어 있는 컴퓨터 번호 que에 저장
			que.add(i);
			visited[i] = true;
			count++;
		}
		BFS();
		System.out.print(count);
	}

	static void BFS() {
		while (!que.isEmpty()) {
			int cur = que.poll(); // 현재 선택된 컴퓨터
			for (int next : arrList[cur]) {
				if (!visited[next]) {
					que.offer(next);
					visited[next] = true;
					count++;
				}
			}
		}
	}
}
