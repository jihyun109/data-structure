package my;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P4485_녹색옷을입은애가젤다지 {
	static int[][] cave;
	static boolean visited[][];
	static int rupee[][];
	static PriorityQueue<Node> pq;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int problem = 1; // case 번호

		while (true) {
			N = sc.nextInt();
			if (N == 0) { // N == 0이면 종료
				break;
			}
			cave = new int[N][N]; // 도둑루피 크기 저장 배열
			visited = new boolean[N][N]; // 방문 배열
			rupee = new int[N][N]; // 지나온 경로의 도둑루피 합 최솟값 저장 배열
			pq = new PriorityQueue<>();

			for (int i = 0; i < N; i++) { // 동굴 각 간의 도둑루피 크기 입력받기
				for (int j = 0; j < N; j++) {
					cave[i][j] = sc.nextInt();
				}
			}
			// rupee배열의 각 칸 최댓값으로 설정
			for (int i = 0; i < N; i++) {
				Arrays.fill(rupee[i], Integer.MAX_VALUE);
			}

			int answer = Dijkstra();

			System.out.println("Problem " + problem + ": " + answer);
			problem++;
		}
	}

	private static int Dijkstra() {
		pq.add(new Node(0, 0, cave[0][0]));
		rupee[0][0] = cave[0][0];

		while (!pq.isEmpty()) {
			Node cur = pq.poll();
			int c_x = cur.x;
			int c_y = cur.y;
			if (visited[c_x][c_y]) {
				continue;
			}
			visited[c_x][c_y] = true;

			for (int i = 0; i < 4; i++) {
				int nextX = cur.x + dx[i];
				int nextY = cur.y + dy[i];
				// 다음 노드의 좌표가 범위 안이면
				if (nextX >= 0 && nextY >= 0 && nextX < N && nextY < N) {
					if (rupee[nextX][nextY] > rupee[c_x][c_y] + cave[nextX][nextY]) {
						rupee[nextX][nextY] = rupee[c_x][c_y] + cave[nextX][nextY];
						pq.add(new Node(nextX, nextY, rupee[nextX][nextY]));
					}
				}
			}
		}
		return rupee[N - 1][N - 1];
	}

	static class Node implements Comparable<Node> {
		int x; // x좌표
		int y; // y좌표
		int theifR; // 도둑 루피의 크기

		Node(int x, int y, int theifR) {
			this.x = x;
			this.y = y;
			this.theifR = theifR;
		}

		@Override
		public int compareTo(Node o) {
			if (this.theifR > o.theifR) {
				return 1;
			} else if (this.theifR < o.theifR) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}
