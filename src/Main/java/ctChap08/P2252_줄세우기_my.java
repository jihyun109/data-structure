package ctChap08;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P2252_줄세우기_my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();	// 학생 수
		int M = sc.nextInt();	// 키 비교 횟수
		ArrayList<Integer>[] arrList = new ArrayList[N + 1];
		int[] D = new int[N + 1];	// 진입 차수 배열
		boolean visited[] = new boolean[N + 1];
		Queue<Integer> que = new LinkedList<>();	// 정렬 큐
		for (int i = 1; i <= N; i++) {
			arrList[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < M; i++) {	// 키 비교 입력 받기
			int p1 = sc.nextInt();
			int p2 = sc.nextInt();
			arrList[p1].add(p2);
			D[p2]++;
		}
		
		while (que.size() != N) {
			for (int i = 1; i <= N; i++) {
				if (!visited[i] && D[i] == 0) {
					que.offer(i);
					visited[i] = true;
					
					for (int next : arrList[i]) {
						D[next]--;
					}
					break;
				}
			}
		}
		
		while (!que.isEmpty()) {
			System.out.print(que.poll() + " ");
		}
	}
}
