package BaekJoon.b20056_마법사상어와파이어볼_250708;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class b20056_마법사상어와파이어볼 {
	private static int moveN;
	private static Map<Point, List<Fireball>> fireballs;

	private static final int[] dx = new int[] { -1, -1, 0, 1, 1, 1, 0, -1 };
	private static final int[] dy = new int[] { 0, 1, 1, 1, 0, -1, -1, -1 };

	private static final int minGrid = 1; // 좌표의 최솟값
	private static int maxGrid;

	public static void main(String[] args) throws IOException {
		init();

		int answer = findAnswer(); // 남아있는 파이어볼의 질량의 합 구하기

		System.out.println(answer);
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		maxGrid = Integer.parseInt(st.nextToken());
		int initialFireBallN = Integer.parseInt(st.nextToken()); // 초기 파이어볼 개수
		moveN = Integer.parseInt(st.nextToken()); // 이동 횟수

		// 파이어볼 정보 입력받기
		fireballs = new HashMap<>();
		for (int i = 0; i < initialFireBallN; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());

			Point p = new Point(r, c);
			fireballs.computeIfAbsent(p, k -> new LinkedList<>());
			fireballs.get(p).add(new Fireball(m, s, d));
		}

		br.close();
	}

	private static int findAnswer() {
		// k 번 이동 수행
		for (int i = 0; i < moveN; i++) {
			move();
		}

		// 남아있는 파이어볼의 질량의 합 구하기
		int remainedM = findRemainedM();

		return remainedM;
	}

	private static void move() {
		// 1. 모든 파이어볼 방향 d 로 s 칸 이동
		Set<Point> pointsOverTwoFireballs = moveAllFireballs(); // 2개 이상의 파이어볼을 가진 좌표 저장

		// 2. 2개 이상의 파이어볼이 있는 칸의 파이어볼들을 합치고 나누기
		dealPointsOverTwoFireballs(pointsOverTwoFireballs);
	}

	private static Set<Point> moveAllFireballs() {
		Set<Point> pointsOverTwoFireballs = new HashSet<>(); // 2개 이상의 파이어볼을 가진 좌표 저장
		Map<Point, List<Fireball>> movedFireballs = new HashMap<>(); // 이동한 파이어볼 저장

		for (Entry<Point, List<Fireball>> fireballList : fireballs.entrySet()) {
			Point point = fireballList.getKey();

			for (Fireball fireball : fireballList.getValue()) {
				// 현재 파이어볼의 이동 후 좌표 구하기
				Point movedPoint = move(fireball, point);

				// movedFireballs 에 저장
				movedFireballs.computeIfAbsent(movedPoint, k -> new LinkedList<>());
				movedFireballs.get(movedPoint).add(fireball);

				// 이동된 좌표에 파이어볼이 2개 이상일 경우 pointsOverTwoFireballs 에 저장
				if (movedFireballs.get(movedPoint).size() >= 2) {
					pointsOverTwoFireballs.add(movedPoint);
				}
			}
		}

		// fireballs 를 이동시킨 후의 정보로 바꿈.
		fireballs = movedFireballs;

		return pointsOverTwoFireballs;
	}

	private static Point move(Fireball fireball, Point point) {
		int r = point.getR();
		int c = point.getC();

		int d = fireball.getD(); // 방향
		int s = fireball.getS(); // 속도

		int movedR = r + (dx[d] * s);
		int movedC = c + (dy[d] * s);

		if (movedR < minGrid) {
			movedR = maxGrid - (Math.abs(movedR) % maxGrid);
		} else if (movedR > maxGrid) {
			movedR = movedR % maxGrid;
			if (movedR == 0) {
				movedR = maxGrid;
			}
		}

		if (movedC < minGrid) {
			movedC = maxGrid - (Math.abs(movedC) % maxGrid);
		} else if (movedC > maxGrid) {
			movedC = movedC % maxGrid;
			if (movedC == 0) {
				movedC = maxGrid;
			}
		}

		return new Point(movedR, movedC);
	}

	private static void dealPointsOverTwoFireballs(Set<Point> pointsOverTwoFireballs) {
		// 한 칸에 2개 이상인 파이어볼들을 합친 후 나누기
		for (Point point : pointsOverTwoFireballs) {
			int fireballN = fireballs.get(point).size();
			int sumM = 0; // 질량의 합
			int sumS = 0; // 속력의 합
			boolean hasOddD = false; // 파이어볼들 중 방향이 홀수인 것들이 있는지
			boolean hasEvenD = false; // 파이어볼들 중 방향이 짝수인 것들이 있는지

			for (Fireball fireball : fireballs.get(point)) {
				int m = fireball.getM();
				int s = fireball.getS();
				int d = fireball.getD();

				sumM += m;
				sumS += s;

				// 방향이 홀수인 경우
				if (d % 2 == 1) {
					hasOddD = true;
				} else {
					hasEvenD = true;
				}
			}

			int dividedM = (int) sumM / 5;
			fireballs.remove(point);

			// 질량이 0인 경우 소멸
			if (dividedM == 0) {
				continue;
			}

			int dividedS = (int) sumS / fireballN;

			// 파이어볼 나누어서 저장
			saveDividedFireball(hasOddD, hasEvenD, point, dividedM, dividedS);
		}

	}

	private static void saveDividedFireball(boolean hasOddD, boolean hasEvenD, Point point, int dividedM,
			int dividedS) {
		// 방향이 홀수인 파이어볼과 짝수인 파이어볼 모두 존재하는 경우
		fireballs.computeIfAbsent(point, k -> new LinkedList<>());
		if (hasOddD & hasEvenD) {

			for (int d = 0; d < 4; d++) {
				int dividedD = (2 * d) + 1;
				fireballs.get(point).add(new Fireball(dividedM, dividedS, dividedD));
			}
		}
		// 방향이 모두 홀수이거나 짝수인 경우
		else {
			for (int d = 0; d < 4; d++) {
				int dividedD = 2 * d;
				fireballs.get(point).add(new Fireball(dividedM, dividedS, dividedD));
			}
		}
	}

	private static int findRemainedM() {
		int remainedM = 0;
		for (List<Fireball> fireballList : fireballs.values()) {
			for (Fireball fireball : fireballList) {
				int m = fireball.getM();

				remainedM += m;
			}
		}
		return remainedM;
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
		return this.r;
	}

	public int getC() {
		return this.c;
	}

	@Override
	public int hashCode() {
		return Objects.hash(r, c);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		Point that = (Point) o;
		return this.r == that.r && this.c == that.c;
	}
}

class Fireball {
	private int m;
	private int s;
	private int d;

	public Fireball(int m, int s, int d) {
		this.m = m;
		this.s = s;
		this.d = d;
	}

	public int getM() {
		return this.m;
	}

	public int getS() {
		return this.s;
	}

	public int getD() {
		return this.d;
	}
}
