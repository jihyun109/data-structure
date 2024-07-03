package my;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P2468_안전영역 {
	static int N;
	static boolean visited[][];
	static int[][] area;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		area = new int[N][N];
		visited = new boolean[N][N];
		int max = 0;

		for (int i = 0; i < N; i++) { // area 상태 입력받기
			for (int j = 0; j < N; j++) {
				area[i][j]= sc.nextInt();
			}
		}
		sc.close();

		for (int i = 1; i < 101; i++) { // 입력된 높이 이하의 영역이 잠길만큼 비가 왔을 때 안전영역의 개수 최댓값 구하기
			int safeZoneN = findSafeZone(i); // 높이가 i이하의 지점이 모두 잠기는 경우 안전영역 개수 찾기
			if (max < safeZoneN) { // max값 업데이트
				max = safeZoneN;
			}
		}
		System.out.println(max);
	}

	private static int findSafeZone(int water) {
		int safezone = 0; // water이하의 영역이 잠길 때 안전 영역의 개수
		for (int i = 0; i < N; i++) { // 모든 안전영역 검색
			for (int j = 0; j < N; j++) {
				if (!visited[i][j] && area[i][j] >= water) {
					BFS(i, j, water);
					safezone++;
//					System.out.println(i + ", " + j);
				}
			}
		}
//		System.out.println(water);
//		System.out.println();

		for (int i = 0; i < N; i++) { // visited 배열 초기화
			Arrays.fill(visited[i], false);
		}
		return safezone;
	}

	private static void BFS(int x, int y, int water) {
		Queue<int[]> que = new LinkedList<>();
		que.offer(new int[] { x, y });
		visited[x][y] = true;
		while (!que.isEmpty()) {
			int[] now = que.poll();
			for (int i = 0; i < 4; i++) { // now의 4방향 탐색
				int nextX = now[0] + dx[i];
				int nextY = now[1] + dy[i];
				if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) { // next가 영역 안 인지
					if (!visited[nextX][nextY] && area[nextX][nextY] >= water) { // next가 안전영역인지
						que.offer(new int[] { nextX, nextY });
						visited[nextX][nextY] = true;
					}
				}
			}
		}
	}
}
