package BaekJoon.b1194_달이차오른다가자;

import java.util.*;
import java.io.*;

public class b1194_달이차오른다가자 {
	private static final char MINSIC = '0';
	private static final char WALL = '#';
	private static final char EXIT = '1';
	private static final char BLANK = '.';
	private static Map<Character, Integer> keys;
	private static Set<Character> doors;

	private static Point start;
	private static int N;
	private static int M;
	private static char[][] miro;

	public static void main(String[] args) throws IOException {
		init();

		int answer = findMinRoute();
		System.out.println(answer);

	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		miro = new char[N][M];
		for (int i = 0; i < N; i++) {
			String row = br.readLine();

			for (int j = 0; j < M; j++) {
				char c = row.charAt(j);

				miro[i][j] = c;

				// 처음 민식이의 위치 저장
				if (c == MINSIC) {
					start = new Point(i, j);
					miro[i][j] = BLANK;
				}

			}
		}
		br.close();

		keys = new HashMap<>();
		addKeys(keys);
		doors = new HashSet<>();
		addDoors(doors);
	}

	private static void addKeys(Map<Character, Integer> keys) {
		keys.put('a', 0);
		keys.put('b', 1);
		keys.put('c', 2);
		keys.put('d', 3);
		keys.put('e', 4);
		keys.put('f', 5);
	}

	private static void addDoors(Set<Character> ddors) {
		doors.add('A');
		doors.add('B');
		doors.add('C');
		doors.add('D');
		doors.add('E');
		doors.add('F');
	}

	private static int findMinRoute() {
		Queue<Status> que = new LinkedList<>();
		int[] dx = new int[] { -1, 1, 0, 0 };
		int[] dy = new int[] { 0, 0, -1, 1 };
		boolean[][][] visited = new boolean[64][N][M];

		que.add(new Status(start, 0, 0));
		visited[0][start.getR()][start.getC()] = true;

		while (!que.isEmpty()) {
			Status curStatus = que.poll();
			Point cur = curStatus.getCur();

			for (int i = 0; i < 4; i++) {
				int nextX = cur.getR() + dx[i];
				int nextY = cur.getC() + dy[i];
				int nextMoveN = curStatus.getMoveN() + 1;
				int nextKeys = curStatus.getKeys();

				// nextX, nextY가 범위 밖이면 continue
				if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= M) {
					continue;
				}

				if (visited[nextKeys][nextX][nextY]) {
					continue;
				}

				char next = miro[nextX][nextY];

				// 출구이면 이동 횟수 return
				if (next == EXIT) {
					return nextMoveN;
				}
				// 벽이면 continue
				else if (next == WALL) {
					continue;
				}
				// 빈 칸이면 que 에 poll
				else if (next == BLANK) {
					que.add(new Status(new Point(nextX, nextY), nextMoveN, nextKeys));
					visited[nextKeys][nextX][nextY] = true;
				}
				// 키가 있는 경우
				else if (keys.containsKey(next)) {
					// nextKey에 추가
					nextKeys = addKey(nextKeys, next);
					que.add(new Status(new Point(nextX, nextY), nextMoveN, nextKeys));
					visited[nextKeys][nextX][nextY] = true;
				}
				// 문이 있는 경우
				else {
					boolean hasKey = findHasKey(nextKeys, next);

					// 키가 있는 경우
					if (hasKey) {
						que.add(new Status(new Point(nextX, nextY), nextMoveN, nextKeys));
						visited[nextKeys][nextX][nextY] = true;
					}
				}

			}
		}

		return -1;
	}

	private static int addKey(int keys, char key) {
		switch (key) {
		case 'a':
			keys = keys | 1 << 0;
			break;
		case 'b':
			keys = keys | 1 << 1;
			break;
		case 'c':
			keys = keys | 1 << 2;
			break;
		case 'd':
			keys = keys | 1 << 3;
			break;
		case 'e':
			keys = keys | 1 << 4;
			break;
		case 'f':
			keys = keys | 1 << 5;
			break;
		}

		return keys;
	}

	private static boolean findHasKey(int nextKeys, char next) {
		String nextStr = String.valueOf(next);
		nextStr = nextStr.toLowerCase();
		next = nextStr.charAt(0);

		int tmp = 1 << keys.get(next);

		if ((nextKeys & tmp) == 0) {
			return false;
		} else {
			return true;
		}
	}

}

class Point {
	private int r;
	private int c;

	public Point(int r, int c) {
		this.r = r;
		this.c = c;
	}

	public int getR() {
		return r;
	}

	public int getC() {
		return c;
	}
}

class Status {
	private Point cur; // 현재 위치
	private int moveN; // 이동 횟수
	private int keys; // 얻은 키의 정보

	public Status(Point cur, int moveN, int keys) {
		this.cur = cur;
		this.moveN = moveN;
		this.keys = keys;
	}

	public Point getCur() {
		return cur;
	}

	public int getMoveN() {
		return moveN;
	}

	public int getKeys() {
		return keys;
	}
}
