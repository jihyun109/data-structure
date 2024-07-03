package ctChap04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P2178_미로탐색_my2 {
	static byte N;
	static byte M;
	static byte[][] arr;
	static boolean[][] visited;
	static byte[] dx = { -1, 0, 1, 0 };
	static byte[] dy = { 0, 1, 0, -1 };
	static int count = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());		

		N = Byte.parseByte(st.nextToken());
		M = Byte.parseByte(st.nextToken());

		arr = new byte[N][M];
		visited = new boolean[N][M];

		for (byte i = 0; i < N; i++) {
			String line = br.readLine();
			for (byte j = 0; j < M; j++) {
				arr[i][j] = (byte) (line.charAt(j) - '0');
			}
		}
		br.close();
		BFS((byte) 0, (byte) 0);
		System.out.println(count);
	}

	static void BFS(byte curX, byte curY) {
		Queue<Node> que = new LinkedList<>();
		que.add(new Node(curX, curY, 1));	// (0, 0)을 스택에 add
		byte preX = 0;		// 이전에 pull 노드의 좌표값 저장
		byte preY = 0;
		// q2.add(0)
		visited[curX][curY] = true;
		// q2 가 cnt 를 담는다면
		while (!que.isEmpty()) {
			Node cur = que.poll();
			// poll한 노드가 이전에 poll한 노드와 좌표값이 같다면 버림.
			if (cur.x != 0 && cur.y != 0 && cur.x == preX && cur.y == preY)
				cur = que.poll();
			preX = cur.x;
			preY = cur.y;
			// q2 poll
			byte cx = cur.x;
			byte cy = cur.y;
			int cd = cur.dist;
			count = cd;
			visited[cx][cy] = true;
			if (cx == N - 1 && cy == M - 1) {
				break;
			}
			if (cx != N - 1 || cy != M - 1) {
				for (byte i = 0; i < 4; i++) {
					byte nextX = (byte) (cx + dx[i]);
					byte nextY = (byte) (cy + dy[i]);
					if (nextX >= 0 && nextY >= 0 && nextX < N && nextY < M)
						if (arr[nextX][nextY] != 0 && !visited[nextX][nextY]) {
							que.add(new Node(nextX, nextY, (cd + 1)));
							// q2 add (cur
//							System.out.println(cx + "," +cy + "->" +nextX+ "," +nextY);
						}
				}
			}
		}
	}

	static class Node {
		byte y;
		byte x;
		int dist;

		public Node(byte curX, byte curY, int dist) {
			this.x = curX;
			this.y = curY;
			this.dist = dist;
		}
	}
}
