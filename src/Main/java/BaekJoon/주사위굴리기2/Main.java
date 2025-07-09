package BaekJoon.주사위굴리기2;

import java.util.*;
import java.io.*;

public class Main {

	private static int height; // 지도의 세로 크기
	private static int width; // 지도의 가로 크기
	private static int moveN; // 이동 횟수
	private static int[][] map; // 지도 정보 저장

	private static Map<Character, Integer> directions; // k: E(동), W(서), S(남), N(북) / v: id
	private static int[] dirX = new int[] { 0, 0, 1, -1 }; // 동 서 남 북
	private static int[] dirY = new int[] { 1, -1, 0, 0 };

	private static Point curPoint; // 현재 위치
	private static char curDir; // 현재 이동 방향
	private static Map<String, Integer> curDice; // 현재 주사위 상태 (k: front, back, top.., v: 숫자)

	public static void main(String[] args) throws IOException {
		init();

		int sumScore = findSumScore(); // 각 이동에서 획득하는 점수의 합 구하기

		System.out.println(sumScore);
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		// 데이터 입력받기
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());
		moveN = Integer.parseInt(st.nextToken());

		map = new int[height + 1][width + 1];
		for (int r = 1; r <= height; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 1; c <= width; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		br.close();

		// 동서남북 설정
		directions = new HashMap<>();
		directions.put('E', 0);
		directions.put('W', 1);
		directions.put('S', 2);
		directions.put('N', 3);

		// 현재 위치와 이동방향 설정
		curPoint = new Point(1, 1);
		curDir = 'E';

		// 현재 주사위 상태 설정
		curDice = new HashMap<>();
		curDice.put("TOP", 1);
		curDice.put("BOTTOM", 6);
		curDice.put("FRONT", 5);
		curDice.put("BACK", 2);
		curDice.put("LEFT", 4);
		curDice.put("RIGHT", 3);
	}

	private static int findSumScore() {
		int sumScore = 0;

		// moveN 번 이동
		for (int m = 0; m < moveN; m++) {
			int score = move(); // 이동에서 얻은 점수
			sumScore += score;
		}

		return sumScore;
	}

	private static int move() {
		int score = 0; // 이번 이동에서 얻은 점수

		// 1. 이동 방향으로 한 칸 이동. return 이동 후 좌표
		Point point = moveOneBlock();

		// 2. 점수 획득. return: 점수
		score = addScore(point);

		// 3. 이동 방향 결정
		calcDir();

		return score;
	}

	private static Point moveOneBlock() {
		Point next = findNextPoint(); // 한 칸 이동했을 때 좌표
		curPoint = next;

		// 굴린 후 주사위 정보 수정
		rollDice();

		return next;
	}

	private static int addScore(Point point) {
		int curLocNum = map[point.getX()][point.getY()]; // 지도 상 현재 위치의 수 (B)

		// 현재 위치부터 연속해 이동할 수 있는 칸 수 구하기
		int blockNCanMove = findBlockN(point);

		return curLocNum * blockNCanMove;
	}

	private static void calcDir() {
		int bottomN = curDice.get("BOTTOM"); // 주사위의 바닥면 수 (A)
		int curLocN = map[curPoint.getX()][curPoint.getY()]; // 지도 상 현재 위치의 수 (B)

		if (bottomN > curLocN) { // A > B
			// 이동 방향을 90도 시계방향 회전
			rotateDir(true);
		} else if (bottomN < curLocN) { // A < B
			// 이동 방향을 90도 반시계방향 회전
			rotateDir(false);
		}
	}

	private static void rotateDir(boolean isClockDir) {
		if (isClockDir) { // 시계방향 회전
			switch (curDir) {
			case 'E':
				curDir = 'S';
				break;
			case 'W':
				curDir = 'N';
				break;
			case 'S':
				curDir = 'W';
				break;
			case 'N':
				curDir = 'E';
				break;
			}

		} else { // 반시계방향 회전
			switch (curDir) {
			case 'E':
				curDir = 'N';
				break;
			case 'W':
				curDir = 'S';
				break;
			case 'S':
				curDir = 'E';
				break;
			case 'N':
				curDir = 'W';
				break;
			}
		}
	}

	private static int findBlockN(Point point) {
		int blockN = 0;
		int curLocNum = map[point.getX()][point.getY()]; // 지도 상 현재 위치의 수(B)

		Queue<Point> que = new LinkedList<>();
		boolean[][] visited = new boolean[height + 1][width + 1];

		que.add(point);

		while (!que.isEmpty()) {
			Point cur = que.poll();
			int curX = cur.getX();
			int curY = cur.getY();

			// 이미 방문한 경우
			if (visited[curX][curY]) {
				continue;
			}
			visited[curX][curY] = true;
			blockN++;

			// 4방향 이동
			for (int i = 0; i < dirX.length; i++) {
				int nextX = curX + dirX[i];
				int nextY = curY + dirY[i];

				// next 가 지도 범위 내에 위치하는지 확인
				if (nextX > 0 && nextX <= height && nextY > 0 && nextY <= width) {
					// 아직 방문하지 않았고 B와 같은 수인 경우
					if (!visited[nextX][nextY] && map[nextX][nextY] == curLocNum) {
						que.add(new Point(nextX, nextY));
					}
				}
			}
		}

		return blockN;
	}

	private static Point findNextPoint() {
		int dir = directions.get(curDir);
		int curX = curPoint.getX();
		int curY = curPoint.getY();

		int nextX = curX + dirX[dir]; // 이동 후 좌표
		int nextY = curY + dirY[dir];

		// 이동할 수 없는 경우(지도 범위를 벗어난 경우)
		if (nextX <= 0 || nextX > height || nextY <= 0 || nextY > width) {
			// 이동방향을 반대로 돌리기
			switchDir();

			// 이동 전 좌표에서 돌린 방향으로 이동
			dir = directions.get(curDir);
			nextX = curX + dirX[dir];
			nextY = curY + dirY[dir];
		}

		return curPoint = new Point(nextX, nextY);
	}

	private static void rollDice() {
		Map<String, Integer> rolledDice = new HashMap<>(); // 굴려진 주사위 상태

		switch (curDir) {

		case 'E':
			rolledDice.put("FRONT", curDice.get("FRONT"));
			rolledDice.put("BACK", curDice.get("BACK"));
			rolledDice.put("TOP", curDice.get("LEFT"));
			rolledDice.put("BOTTOM", curDice.get("RIGHT"));
			rolledDice.put("LEFT", curDice.get("BOTTOM"));
			rolledDice.put("RIGHT", curDice.get("TOP"));
			break;
		case 'W':
			rolledDice.put("FRONT", curDice.get("FRONT"));
			rolledDice.put("BACK", curDice.get("BACK"));
			rolledDice.put("TOP", curDice.get("RIGHT"));
			rolledDice.put("BOTTOM", curDice.get("LEFT"));
			rolledDice.put("LEFT", curDice.get("TOP"));
			rolledDice.put("RIGHT", curDice.get("BOTTOM"));

			break;
		case 'S':
			rolledDice.put("FRONT", curDice.get("TOP"));
			rolledDice.put("BACK", curDice.get("BOTTOM"));
			rolledDice.put("TOP", curDice.get("BACK"));
			rolledDice.put("BOTTOM", curDice.get("FRONT"));
			rolledDice.put("LEFT", curDice.get("LEFT"));
			rolledDice.put("RIGHT", curDice.get("RIGHT"));

			break;
		case 'N':
			rolledDice.put("FRONT", curDice.get("BOTTOM"));
			rolledDice.put("BACK", curDice.get("TOP"));
			rolledDice.put("TOP", curDice.get("FRONT"));
			rolledDice.put("BOTTOM", curDice.get("BACK"));
			rolledDice.put("LEFT", curDice.get("LEFT"));
			rolledDice.put("RIGHT", curDice.get("RIGHT"));

			break;
		}

		curDice = rolledDice;
	}

	private static void switchDir() {
		switch (curDir) {

		case 'E':
			curDir = 'W';
			break;
		case 'W':
			curDir = 'E';
			break;
		case 'S':
			curDir = 'N';
			break;
		case 'N':
			curDir = 'S';
			break;
		}
	}

}

class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
