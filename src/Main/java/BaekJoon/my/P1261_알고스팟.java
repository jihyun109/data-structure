package my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.StringTokenizer;

public class P1261_알고스팟 {
	static int[][] miro;
	static boolean[][] visited;
	static int[][] brokenWall;
	static PriorityQueue<Node> pq;
	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N;
	static int M;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);

		M = sc.nextInt();
		N = sc.nextInt();
		miro = new int[N + 1][M + 1];
		visited = new boolean[N + 1][M + 1];
		brokenWall = new int[N + 1][M + 1]; // 부숴야하는 벽의 최솟값 저장 배열
		pq = new PriorityQueue<Node>();

		for (int i = 1; i <= N; i++) { // 미로 정보 입력받기
			String str = sc.next();
			for (int j = 1; j <= M; j++) {
				miro[i][j] = str.charAt(j - 1) - '0';
			}
		}

		sc.close();

		for (int i = 0; i <= N; i++) { // brokenWall 배열 최댓값으로 설정
			Arrays.fill(brokenWall[i], Integer.MAX_VALUE);
		}

		int answer = dijkstra();

		System.out.println(answer);
	}

	private static int dijkstra() {
		pq.add(new Node(1, 1, 0));
		brokenWall[1][1] = 0;

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int c_x = cur.x;
			int c_y = cur.y;

			if (visited[c_x][c_y]) {
				continue;
			}
			visited[c_x][c_y] = true;

			for (int i = 0; i < 4; i++) {
				int n_x = c_x + dx[i];
				int n_y = c_y + dy[i];
				if (n_x > 0 && n_x <= N && n_y > 0 && n_y <= M) {
					if (brokenWall[n_x][n_y] > brokenWall[c_x][c_y] + miro[n_x][n_y]) {
						brokenWall[n_x][n_y] = brokenWall[c_x][c_y] + miro[n_x][n_y];
						pq.add(new Node(n_x, n_y, brokenWall[n_x][n_y]));
					}
				}
			}
		}
		return brokenWall[N][M];
	}

	private static class Node implements Comparable<Node> {
		int x;
		int y;
		int wall;

		Node(int x, int y, int wall) {
			this.x = x;
			this.y = y;
			this.wall = wall;
		}

		@Override
		public int compareTo(Node o) {
			if (this.wall > o.wall) {
				return 1;
			} else if (this.wall < o.wall) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
