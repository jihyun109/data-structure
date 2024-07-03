package ctChap08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P1325_효율적인해킹_my {
	static ArrayList<Integer>[] arrList;
	static int N;
	static int M;
	static int[] trust;
	static boolean[] visited;
	static BufferedReader br;
	private static StringTokenizer st;
	static int max;
	static Queue<Integer> que;

	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken()); // 컴퓨터 개수
		M = Integer.parseInt(st.nextToken()); // 신뢰 관계 개수
		arrList = new ArrayList[N + 1];
		trust = new int[N + 1]; // 신뢰도 배열
		for (int i = 1; i <= N; i++) { // ArrayList배열 선언
			arrList[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) { // 신뢰관계 입력 받기
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			arrList[A].add(B);
		}

		for (int i = 1; i <= N; i++) { // 모든 컴퓨터에서 BFS 시작
			visited = new boolean[N + 1]; // 방문 상태 배열
			BFS(i);
		}

		max = 0;
		for (int i = 1; i <= N; i++) { // 신뢰도 최댓값 구하기
			max = Math.max(max, trust[i]);
		}
		for (int i = 1; i <= N; i++) {
			if (trust[i] == max) {
				System.out.print(i + " ");
			}
		}
	}

	private static void BFS(int s) {
		que = new LinkedList<Integer>();

		que.add(s); // 처음 해킹한 컴퓨터 번호 que에 add
		visited[s] = true;

		while (!que.isEmpty()) {
			int now = que.poll();
			for (int next : arrList[now]) {
				if (!visited[next]) {
					que.add(next);
					visited[next] = true;
					trust[next]++;
				}
			}
		}
	}
}
