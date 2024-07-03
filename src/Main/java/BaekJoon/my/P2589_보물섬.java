package my;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P2589_보물섬 {
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	static char[][] map;
	static boolean[][] visited;
	static Queue<int[]> que;
	static int row;
	static int column;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		row = sc.nextInt();
		column = sc.nextInt();
		map = new char[row][column];
		visited = new boolean[row][column];
		que = new LinkedList<int[]>();
		int max = 0; // 보물 사이의 거리 이동 시간

		for (int i = 0; i < row; i++) { // map 입력받기
			String str = sc.next();
			for (int j = 0; j < column; j++) {
				map[i][j] = str.charAt(j);
			}
		}
		

		for (int i = 0; i < row; i++) { // 방문하지 않은 땅 좌표 검색
			for (int j = 0; j < column; j++) {
				if (!visited[i][j] && map[i][j] == 'L') {
					// 그 좌표를 지나가는 경로 중 가장 긴 경로의 양 끝 좌표(보물) 사이의 이동시간 return
					int h = findHour(i, j);
//					System.out.println(i + "," + j);
					if (h > max) {
						max = h;
					}
				}
			}
		}
		System.out.println(max);
	}

	static int findHour(int x, int y) {
		int[] farLand = BFS(x, y); // (x, y)로부터 가장 멀리 떨어진 땅의 좌표
//		for (int i = 0; i < 2; i++)
//			System.out.print(farLand[i] + " ");
		int h = BFS(farLand); // farLand에서 가장 멀리 떨어져있는 땅까지의 이동 시간
//		System.out.println(h);
		return h;
	}
	
	private static int[] BFS(int x, int y) { // (x, y)로부터 가장 멀리 떨어진 땅의 좌표 구하는 메서드
		boolean[][] visited2 = new boolean[row][column];
		que.offer(new int[] { x, y });
		visited2[x][y] = true;
		int[] now = null;
		while (!que.isEmpty()) {
			now = que.poll();
			for (int i = 0; i < 4; i++) { // 현재 위치의 상하좌우 검색
				int nextX = now[0] + dx[i];
				int nextY = now[1] + dy[i];
				if (nextX >= 0 && nextX < row && nextY >= 0 && nextY < column) { // next좌표가 범위 내인지 확인
					if (!visited2[nextX][nextY] && map[nextX][nextY] == 'L') { // next좌표가 땅인지 확인
						que.offer(new int[] { nextX, nextY });
						visited2[nextX][nextY] = true;
					}
				}
			}
		}
		for (int i = 0; i < row; i++) { // visited2 배열 초기화
			for (int j = 0; j < column; j++) {
				visited2[i][j] = false;
			}
		}

		return now;
	}

	private static int BFS(int[] farLand) {		// farLand에서 가장 멀리 떨어져있는 땅까지의 이동 시간
		que.offer(new int[] { farLand[0], farLand[1], 0 });
		visited[farLand[0]][farLand[1]] = true;
		int[] now = null;
		while (!que.isEmpty()) {
			now = que.poll();
			for (int i = 0; i < 4; i++) { // 현재 위치의 상하좌우 검색
				int nextX = now[0] + dx[i];
				int nextY = now[1] + dy[i];
				if (nextX >= 0 && nextX < row && nextY >= 0 && nextY < column) {
					if (!visited[nextX][nextY] && map[nextX][nextY] == 'L') {
						que.offer(new int[] { nextX, nextY, now[2] + 1 });
						visited[nextX][nextY] = true;
					}
				}
			}
		}
		return now[2]; // 마지막으로 방문한 땅의 이동 시간 return
	}



}
