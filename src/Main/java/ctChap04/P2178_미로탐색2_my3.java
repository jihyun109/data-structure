package ctChap04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P2178_미로탐색2_my3 {
	static int N;
	static int M;
	static int[][] arr;
	static boolean[][] visited;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };
	static int count = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new int[N][M];
		visited = new boolean[N][M];

		for (int i = 0; i < N; i++) {
			String line = br.readLine();
			for (byte j = 0; j < M; j++) {
				arr[i][j] = (byte) (line.charAt(j) - '0');
			}
		}
		br.close();
		BFS(0, 0);
		System.out.println(count);
	}

	static void BFS(int curX, int curY) {
		Queue<Node> que = new LinkedList<>();
		que.add(new Node(curX, curY, 1)); // (0, 0)을 스택에 add
		int preX = 0;
		int preY = 0;
		// q2.add(0)
		visited[curX][curY] = true;
		// q2 가 cnt 를 담는다면
		while (!que.isEmpty()) {
			Node cur = que.poll();
			// q2 poll
			int cx = cur.x;
			int cy = cur.y;
			int cd = cur.dist;
			count = cd;
			visited[cx][cy] = true;
			if (cx == N - 1 && cy == M - 1) {
				break;
			}
			if (cx != N - 1 || cy != M - 1) {
				for (int i = 0; i < 4; i++) {
					int nextX = cx + dx[i];
					int nextY = cy + dy[i];
					if (nextX >= 0 && nextY >= 0 && nextX < N && nextY < M)
						if (arr[nextX][nextY] != 0 && !visited[nextX][nextY]) {
							que.add(new Node(nextX, nextY, (cd + 1)));
							visited[nextX][nextY] = true;

							// q2 add (cur
//							System.out.println(cx + "," +cy + "->" +nextX+ "," +nextY);
						}
				}
			}
		}
	}

	static class Node {
		int y;
		int x;
		int dist;

		public Node(int curX, int curY, int dist) {
			this.x = curX;
			this.y = curY;
			this.dist = dist;
		}
	}
}
