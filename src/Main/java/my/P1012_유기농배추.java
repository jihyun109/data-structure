package my;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P1012_유기농배추 {
	static int[][] land;
	static boolean[][] visited;
	static int M;
	static int N;


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt(); // test case 개수

		for (int t = 0; t < T; t++) { // T만큼 test 수행
			M = sc.nextInt(); // 밭의 가로
			N = sc.nextInt(); // 밭의 세로
			int K = sc.nextInt(); // 배추가 심어져 있는 위치의 개수
			land = new int[N][M];
			visited = new boolean[N][M];
			for (int i = 0; i < K; i++) { // 배추의 위치 입력받기
				int x = sc.nextInt();
				int y = sc.nextInt();
				land[y][x] = 1;
			}

			int answer = findWormN(); // 해당 케이스의 필요한 지렁이 갯수 구하기
			System.out.println(answer);

			// 초기화
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					visited[i][j] = false;
				}
			}
		}
	}

	static int findWormN() {
		int wormN = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (!visited[i][j] && land[i][j] == 1) {
					BFS(i, j);
					wormN++;
				}
			}
		}
		return wormN;
	}

	static void BFS(int x, int y) {
		int[] dx = new int[] { -1, 1, 0, 0 };
		int[] dy = new int[] { 0, 0, -1, 1 };

		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] {x, y});
		visited[x][y] = true;
		
		while (!que.isEmpty()) {
			int[] now = que.poll();
			for (int i = 0; i < 4; i++) {	// now의 상하좌우 검색
				int nextX = now[0] + dx[i];
				int nextY = now[1] + dy[i];
				
				if (nextX >=0 && nextX < N && nextY >= 0 && nextY < M) {
					if(!visited[nextX][nextY] && land[nextX][nextY] == 1) {
						que.offer(new int[] {nextX, nextY});
						visited[nextX][nextY] = true;
					}
				}
			}
		}
	}

}
