package my;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P2589_보물섬2 {
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static char[][] map;
	static int row;
	static int column;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		row = sc.nextInt();
		column = sc.nextInt();
		map = new char[row][column];
		int max = 0; // 보물 사이의 거리 이동 시간

		for (int i = 0; i < row; i++) { // map 입력받기
			String str = sc.next();
			for (int j = 0; j < column; j++) {
				map[i][j] = str.charAt(j);
			}
		}

		for (int i = 0; i < row; i++) { // 모든 L에서 BFS 수행 후 보물 사이의 이동 시간 구하기
			for (int j = 0; j < column; j++) {
				if (map[i][j] == 'L') {
					int h = BFS(i, j);
					if (h > max) {
						max = h;
					}
				}
			}
		}
		System.out.println(max);
	}

	private static int BFS(int x, int y) { // (x, y)로부터 가장 멀리 떨어진 땅의 좌표 구하는 메서드
		boolean[][] visited = new boolean[row][column];
		Queue<int[]> que = new LinkedList<int[]>();

		que.offer(new int[] { x, y, 0 });
		visited[x][y] = true;
		int[] now = null;
		while (!que.isEmpty()) {
			now = que.poll();
			for (int i = 0; i < 4; i++) { // 현재 위치의 상하좌우 검색
				int nextX = now[0] + dx[i];
				int nextY = now[1] + dy[i];
				if (nextX >= 0 && nextX < row && nextY >= 0 && nextY < column) { // next좌표가 범위 내인지 확인
					if (!visited[nextX][nextY] && map[nextX][nextY] == 'L') { // next좌표가 땅인지 확인
						que.offer(new int[] { nextX, nextY, now[2] + 1 });
						visited[nextX][nextY] = true;
					}
				}
			}
		}
		return now[2];
	}
}
