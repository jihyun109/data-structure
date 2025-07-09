package BaekJoon.마법사상어와복제;

import java.io.*;
import java.util.*;

public class Main {
	private static final int MAX_X = 4;
	private static final int MAX_Y = 4;
	private static final int MIN_X = 1;
	private static final int MIN_Y = 1;
	private static final int NUM_OF_FISH_DIR = 8;
	private static final int NUM_OF_SUM_SHARK_MOVE = 3; // 상어가 이동하는 총 횟수

	private static Map<Position, Map<Integer, Integer>> fishs; // k: 물고기들의 좌표, v: 방향, 해당되는 물고기 수
	private static Map<Position, Integer> fishNs; // k: 물고기들의 좌표, v: 해당 위치에 존재하는 물고기 수
	private static List<Set<Position>> removedFish; // removedFish.get(0) : 두 번 전 연습에서 제거된 물고기들의 좌표
	private static int sumFishN; // 총 물고기 수

	// 상어의 좌표
	private static int sharkX;
	private static int sharkY;

	private static int[] fishDX = new int[] { 0, 0, -1, -1, -1, 0, 1, 1, 1 };
	private static int[] fishDY = new int[] { 0, -1, -1, 0, 1, 1, 1, 0, -1 };
	private static int[] sharkDX = new int[] { 0, -1, 0, 1, 0 };
	private static int[] sharkDY = new int[] { 0, 0, -1, 0, 1 };

	public static void main(String[] args) throws IOException {
		// 데이터 입력받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int fishN = Integer.parseInt(st.nextToken());
		int practiceN = Integer.parseInt(st.nextToken());

		fishs = new HashMap<>();
		fishNs = new HashMap<>();
		sumFishN = fishN;

		for (int i = 0; i < fishN; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int dir = Integer.parseInt(st.nextToken());

			Position pos = new Position(x, y);
			fishs.computeIfAbsent(pos, k -> new HashMap<>());
			int blockFishN = fishs.get(pos).getOrDefault(dir, 0); // (x, y)에 dir 방향의 물고기 수

			fishs.get(pos).put(dir, blockFishN + 1);
			fishNs.put(pos, fishNs.getOrDefault(pos, 0) + 1);
		}

		st = new StringTokenizer(br.readLine());
		sharkX = Integer.parseInt(st.nextToken());
		sharkY = Integer.parseInt(st.nextToken());

		br.close();

		removedFish = new LinkedList<>();
		removedFish.add(new HashSet<>());
		removedFish.add(new HashSet<>());
		practice(practiceN);

		System.out.println(sumFishN);
	}

	private static void practice(int practiceN) {
		for (int i = 0; i < practiceN; i++) {
			print("FIRST");
			// 1. 복제 마법 2. 모든 물고기 한 칸 이동
			Map<Position, Map<Integer, Integer>> copiedFish = new HashMap<>(); // k: 복제된 물고기 정보, v: 물고기 수
			copyMagicAndMoveAllFish(copiedFish);
			print("copyAndMoveFish");

			// 3. 상어 연속 3칸 이동
			moveShark();
			print("moveShark");

			// 4. 두 번 전 연습에서 생긴 물고기 냄새 제거
			removeFishSmell();

			// 5. 복제 마법 완료
			finishCopyMagic(copiedFish);
			print("copy");
			System.out.println("Shark: " + sharkX + " , " + sharkY);
		}
	}

	private static void copyMagicAndMoveAllFish(Map<Position, Map<Integer, Integer>> copiedFish) {
		Map<Position, Map<Integer, Integer>> movedFish = new HashMap<>(); // 한 칸 이동시킨 후 물고기들의 위치
		Map<Position, Integer> movedFishNs = new HashMap<>();

		for (Map.Entry<Position, Map<Integer, Integer>> entry : fishs.entrySet()) {
			Position pos = entry.getKey();
			int x = entry.getKey().getX();
			int y = entry.getKey().getY();

			for (Map.Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
				int dir = entry2.getKey();
				int fishN = entry2.getValue();

				copiedFish.computeIfAbsent(pos, k -> new HashMap<>());
				copiedFish.get(pos).put(dir, fishN);

				moveFish(pos, dir, fishN, movedFish, movedFishNs);
			}
		}

		fishs = movedFish;
		fishNs = movedFishNs;
	}

	private static void moveFish(Position pos, int dir, int fishN, Map<Position, Map<Integer, Integer>> movedFish,
			Map<Position, Integer> movedFIshNs) {
		// 이동할 수 있는 방향 찾기
		int x = pos.getX();
		int y = pos.getY();
		int canMoveDir = 0; // 이동할 수 있는 방향
		int movedX = 0;
		int movedY = 0;
		if (x == 3 && y == 4 && dir == 4) {
			System.out.print("HI");
		}

		// 현재 방향으로부터 (<- 방향까지) 반시계 회전
		for (int d = dir; d > 0; d--) {
			movedX = x + fishDX[d];
			movedY = y + fishDY[d];

			boolean canMove = canMove(movedX, movedY); // d 방향으로 이동할 수 있는지
			if (canMove) {
				canMoveDir = d;
				break;
			}
		}

		// 이동할 수 있는 방향을 찾지 못했다면 <- 반시계 45도 방향부터 현재 방향까지 반시계 회전해 찾기
		if (canMoveDir == 0) {
			for (int d = NUM_OF_FISH_DIR; d > dir; d--) {
				movedX = x + fishDX[d];
				movedY = y + fishDY[d];

				boolean canMove = canMove(movedX, movedY); // d 방향으로 이동할 수 있는지
				if (canMove) {
					canMoveDir = d;
					break;
				}
			}
		}

		if (movedX == 0) {
			System.out.println();
		}

		// 이동 후 물고기 위치 저장
		if (canMoveDir != 0) { // 이동가능한 방향이 있는 경우
			Position moved = new Position(movedX, movedY);
			movedFish.computeIfAbsent(moved, k -> new HashMap<>());
			movedFish.get(moved).put(canMoveDir, movedFish.get(moved).getOrDefault(canMoveDir, 0) + fishN);
			movedFIshNs.put(moved, movedFIshNs.getOrDefault(moved, 0) + fishN);
		} else { // 없는 경우
			movedFish.computeIfAbsent(pos, k -> new HashMap<>());
			movedFish.get(pos).put(dir, movedFish.get(pos).getOrDefault(pos, 0) + fishN);
			movedFIshNs.put(pos, movedFIshNs.getOrDefault(pos, 0) + fishN);
		}
	}

	private static void moveShark() {
		Queue<Status> pq = new LinkedList<>();
		boolean[][] visited = new boolean[MAX_X + 1][MAX_Y + 1];
		pq.add(new Status(new Position(sharkX, sharkY), 0, new int[] { 5, 5, 5 }, 0, new LinkedList<>()));

		while (!pq.isEmpty()) {
			Status cur = pq.poll();
			int curX = cur.getPos().getX();
			int curY = cur.getPos().getY();

			// 상어가 3 칸 이동한 경우
			if (cur.getMovedN() == NUM_OF_SUM_SHARK_MOVE) {
				PriorityQueue<Status> q = new PriorityQueue<>();
				while (true) {
					if (cur == null || cur.getMovedN() > 3) {
						break;
					}
					q.add(cur);
					cur = pq.poll();
				}

				// 물고기 냄새 남기기 & 물고기 제거
				cur = q.poll();
				Set<Position> removedPoses = new HashSet<>(); // 제거된 물고기들의 좌표 저장
				int removedN = 0;
				for (Position removedPos : cur.getRemovedFishPos()) {
					removedPoses.add(removedPos);

					int x = removedPos.getX();
					int y = removedPos.getY();

					removedN += fishNs.get(removedPos);
					fishs.remove(removedPos);
				}

				removedFish.add(removedPoses);
				sumFishN -= removedN;
				sharkX = cur.getPos().getX();
				sharkY = cur.getPos().getY();
				return;
			}

			if (curX == sharkX && curY == sharkY) {
				visited[curX][curY] = false;
			} else {
				visited[curX][curY] = true;	
			}
			

			// 상어 이동시키기
			for (int i = 1; i <= 4; i++) {
				int nextX = curX + sharkDX[i];
				int nextY = curY + sharkDY[i];

				// next 가 격자 범위 내인지 확인
				if (nextX >= MIN_X && nextY >= MIN_Y && nextX <= MAX_X && nextY <= MAX_Y) {
					// next 를 방문하지 않았는지 확인
					if (!visited[nextX][nextY]) {
						Position next = new Position(nextX, nextY);
						int nextFishN = fishNs.getOrDefault(next, 0);

						Queue<Position> removedFishPos = new LinkedList<>(cur.getRemovedFishPos());
						if (nextFishN > 0) {
							removedFishPos.add(next);
						}

						nextFishN += cur.getFishN();
						int nextMovedN = cur.getMovedN() + 1;
						int[] priorityNum = cur.getPriorityNum().clone();
						priorityNum[nextMovedN - 1] = i;

						pq.add(new Status(next, nextFishN, priorityNum, nextMovedN, removedFishPos));
						System.out.println("x: " + nextX + " y: " + nextY);
					}
				}
			}
		}
	}

	private static void removeFishSmell() {
		removedFish.removeFirst();
	}

	private static void finishCopyMagic(Map<Position, Map<Integer, Integer>> copiedFish) {
		for (Map.Entry<Position, Map<Integer, Integer>> entry1 : copiedFish.entrySet()) {
			Position pos = entry1.getKey();

			for (Map.Entry<Integer, Integer> entry2 : entry1.getValue().entrySet()) {
				int dir = entry2.getKey();
				int fishN = entry2.getValue();

				fishs.computeIfAbsent(pos, k -> new HashMap<>());
				fishs.get(pos).put(dir, fishs.get(pos).getOrDefault(dir, 0) + fishN);
				fishNs.put(pos, fishNs.getOrDefault(pos, 0) + fishN);
				sumFishN += fishN;
			}
		}
	}

	private static boolean canMove(int movedX, int movedY) {
		// 이동한 좌표가 격자 내인지 확인
		if (movedX >= MIN_X && movedY >= MIN_Y && movedX <= MAX_X && movedY <= MAX_Y) {
			// 상어가 있는 좌표가 아닌지 확인
			if (movedX != sharkX || movedY != sharkY) {
				// 물고기의 냄새가 있는 칸이 아닌지
				if (removedFish.size() > 0) {
					if (!removedFish.get(0).contains(new Position(movedX, movedY))) {
						if (removedFish.size() > 1) {
							if (!removedFish.get(1).contains(new Position(movedX, movedY))) {
								return true;
							} else {
								return false;
							}
						} else {
							return true;
						}
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}

	private static void print(String msg) {
		System.out.println("-----" + msg + "-----");
		for (Map.Entry<Position, Map<Integer, Integer>> entry1 : fishs.entrySet()) {
			Position pos = entry1.getKey();

			for (Map.Entry<Integer, Integer> entry2 : entry1.getValue().entrySet()) {
				int dir = entry2.getKey();
				int fishN = entry2.getValue();

				System.out.println(String.format("(%d, %d) dir: %d, fishN: %d", pos.getX(), pos.getY(), dir, fishN));
			}
		}
		System.out.println(sumFishN);
		System.out.println();
	}
}

class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
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
		if (o == this) {
			return true;
		}
		Position that = (Position) o;
		return this.x == that.x && this.y == that.y;

	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}

class Status implements Comparable<Status> {
	private Position pos;
	private int fishN;
	private int[] priorityNum; // 우선순위 계산에 사용하는 수
	private int movedN; // 이동 횟수
	private Queue<Position> removedFishPos; // 제거된 물고기의 위치 좌표

	public Status(Position pos, int fishN, int[] priorityNum, int movedN, Queue<Position> removedFishPos) {
		this.pos = pos;
		this.fishN = fishN;
		this.priorityNum = priorityNum;
		this.movedN = movedN;
		this.removedFishPos = removedFishPos;
	}

	public Position getPos() {
		return this.pos;
	}

	public int getMovedN() {
		return this.movedN;
	}

	public Queue<Position> getRemovedFishPos() {
		return this.removedFishPos;
	}

	public int getFishN() {
		return this.fishN;
	}

	public int[] getPriorityNum() {
		return this.priorityNum;
	}

	@Override
	public int compareTo(Status o) {
		if (this.fishN == o.fishN) {
			for (int i = 0; i < 3; i++) {
				int thisN = this.priorityNum[i];
				int oN = o.priorityNum[i];

				if (thisN != oN) {
					return thisN - oN;
				}
			}
		}
		return o.fishN - this.fishN;
	}
}