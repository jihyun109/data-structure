package BaekJoon.어른상어;

import java.util.*;
import java.io.*;

public class Main {
	private static int size; // 격자 크기
	private static int initialSharkN; // 초기 상어의 수
	private static int smellTime; // 상어의 냄새가 남아있는 시간

	private static Map<Point, SharkInfo> sharks; // 상어 정보 저장(k: 위치 좌표, v: 상어의 번호)
	private static Map<Point, Smell> smells; // 냄새 저장 (k: 위치, v: 냄새 정보)
	private static int[][][] priorityDirs; // 상어들의 위치에 따른 우선순위 방향([상어번호][상어방향][우선순위] : 해당되는 방향)

	private static int time;

	private static int[] dx = new int[] { 0, -1, 1, 0, 0 };
	private static int[] dy = new int[] { 0, 0, 0, -1, 1 };

	private static final int TIME_LIMIT = 1000; // TIME_LIMIT 초가 넘어도 다른 상어가 격자에 남아있으면 -1출력

	public static void main(String[] args) throws IOException {
		init();

		findTime(); // 1번 상어만 격자에 남기까지 걸리는 시간 구하기

		if (time > TIME_LIMIT) {
			System.out.println(-1);
		} else {
			System.out.println(time);
		}
	}

	private static void findTime() {
		time = 0;

		while (time <= TIME_LIMIT) {
			if (sharks.size() == 1) {
				return;
			}

			time++;

			Map<Point, SharkInfo> newSharks = new HashMap<>(); // 상어들이 이동한 후의 위치 저장

			for (Map.Entry<Point, SharkInfo> entry : sharks.entrySet()) {
				Point point = entry.getKey(); // 상어의 위치
				SharkInfo shark = entry.getValue(); // 상어의 번호

				// 상어의 이동 방향 찾기
				int direction = findDirection(point, shark);

				// 상어 이동
				moveShark(direction, point, shark, newSharks);

//				System.out.println("new sharks: " + newSharks.toString());
//				System.out.println(("smells: " + smells.toString()));
//				System.out.println();
			}

			sharks = newSharks;
			
			// 냄새 남기기
			leaveSmells();
		}
	}

	private static int findDirection(Point point, SharkInfo sharkInfo) {
		int shark = sharkInfo.getShark(); // 상어 번호
		int sharkDir = sharkInfo.getDir(); // 상어의 현재 방향
		int x = point.getX();
		int y = point.getY();
//		if (x == 2 && y == 5) {
//			System.out.print(false);
//		}

		int tmpDir = 0; // 자신의 냄새가 있는 우선순위 방향
		for (int i = 0; i < 4; i++) {
			int nextDir = priorityDirs[shark][sharkDir][i];

			int nextX = x + dx[nextDir];
			int nextY = y + dy[nextDir];
			Point next = new Point(nextX, nextY);

			// next 가 격자 범위 내에 위치하는지 확인
			if (nextX > 0 && nextX <= size && nextY > 0 && nextY <= size) {
				// next 에 냄새가 있는 경우
				if (smells.containsKey(next) && (time - smells.get(next).getTime()) - 1< smellTime) {
					// 냄새가 자신의 냄새이면 tmpDir 에 저장
					if (smells.get(next).getShark() == shark && tmpDir == 0) {
						tmpDir = nextDir;
					}
				}

				// next에 냄새가 없는 경우
				else {
					return nextDir;
				}
			}
		}

		return tmpDir;
	}

	private static void moveShark(int direction, Point originPoint, SharkInfo sharkInfo,
			Map<Point, SharkInfo> newSharks) {
		int nextX = originPoint.getX() + dx[direction];
		int nextY = originPoint.getY() + dy[direction];
		Point movedPoint = new Point(nextX, nextY);
		int shark = sharkInfo.getShark();

		// 이동할 위치에 다른 상어가 있는 경우
		if (newSharks.containsKey(movedPoint)) {
			int savedShark = newSharks.get(movedPoint).getShark();

			// savedShark 보다 현재 상어 숫자가 더 낮은 경우 savedShark out
			if (sharkInfo.getShark() < savedShark) {
				newSharks.put(movedPoint, new SharkInfo(shark, direction));

//				// 냄새 남기기
//				
			}
//			System.out.print(smells.get(movedPoint));
		} else {
			newSharks.put(movedPoint, new SharkInfo(sharkInfo.getShark(), direction));

//			// 냄새 남기기
//			smells.put(movedPoint, new Smell(shark, time));
//			System.out.println(smells.get(movedPoint));
		}
	}
	
	private static void leaveSmells() {
		for(Map.Entry<Point, SharkInfo> entry : sharks.entrySet()) {
			Point point = entry.getKey();
			int shark = entry.getValue().getShark();
			smells.put(point, new Smell(shark, time));
		}
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		size = Integer.parseInt(st.nextToken());
		initialSharkN = Integer.parseInt(st.nextToken());
		smellTime = Integer.parseInt(st.nextToken());

		// 상어의 위치 정보 입력받기 & 냄새 뿌리기
		sharks = new HashMap<>();
		smells = new HashMap<>();
		Map<Integer, Point> sharkPoints = new HashMap<>(); // 상어의 위치 저장 (k: 상어 번호, v: 위치)

		for (int x = 1; x <= size; x++) {
			st = new StringTokenizer(br.readLine());
			for (int y = 1; y <= size; y++) {
				int sharkN = Integer.parseInt(st.nextToken()); // 상어의 번호

				// 현재 위치에 상어가 존재하는 경우
				if (sharkN != 0) {
					Point point = new Point(x, y);

					sharkPoints.put(sharkN, point);

					smells.put(point, new Smell(sharkN, 0));
				}
			}
		}

		// 상어의 방향 입력받기
		st = new StringTokenizer(br.readLine());
		for (int s = 1; s <= initialSharkN; s++) {
			int dir = Integer.parseInt(st.nextToken());
			Point point = sharkPoints.get(s);

			sharks.put(point, new SharkInfo(s, dir));
		}

		// 상어의 방향 우선순위 입력받기
		priorityDirs = new int[initialSharkN + 1][5][4];
		for (int s = 1; s <= initialSharkN; s++) {
			for (int d = 1; d <= 4; d++) {
				st = new StringTokenizer(br.readLine());
				for (int i = 0; i < 4; i++) {
					int dir = Integer.parseInt(st.nextToken()); // 방향
					priorityDirs[s][d][i] = dir;
				}
			}
		}

		br.close();
	}

}

class Smell {
	private int shark; // 어떤 상어의 냄새인지
	private int time; // 냄새가 발생한 시간

	public Smell(int shark, int time) {
		this.shark = shark;
		this.time = time;
	}

	public int getShark() {
		return this.shark;
	}

	public int getTime() {
		return this.time;
	}
	
	@Override
	public String toString() {
		return "shark: " + shark + ", time: " + time;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			Point that = (Point) o;
			return this.x == that.x && this.y == that.y;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

class SharkInfo {
	private int shark; // 상어의 번호
	private int dir; // 상어의 방향

	public SharkInfo(int shark, int dir) {
		this.shark = shark;
		this.dir = dir;
	}

	public int getShark() {
		return this.shark;
	}

	public int getDir() {
		return this.dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
	
	@Override
	public String toString() {
		return "shark: " + shark + ", dir: " + dir;
	}
}
