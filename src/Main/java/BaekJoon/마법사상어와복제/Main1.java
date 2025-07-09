package BaekJoon.마법사상어와복제;

import java.io.*;
import java.util.*;

public class Main1 {
	private static final int MAX_X = 4;
	private static final int MAX_Y = 4;
	private static final int MIN_X = 1;
	private static final int MIN_Y = 1;
	private static final int NUM_OF_FISH_DIR = 8;
	private static final int NUM_OF_SUM_SHARK_MOVE = 3;	// 상어가 이동하는 총 횟수
	
	private static Map<Position, Set<Integer>> fishPosition;	// 물고기가 존재하는 좌표, 방향 저장
	private static int[][][] mapFishN;		// mapFishN[x][y][dir] : (x, y)에 위치한 dir 방향인 물고기의 수
	private static List<Set<Position>> removedFish;	// removedFish.get(0) : 두 번 전 연습에서 제거된 물고기 좌표
	
	// 상어의 좌표
	private static int sharkX;
	private static int sharkY;
	
	private static int[] fishDX = new int[] {0, 0, -1, -1, -1, 0, 1, 1, 1};
	private static int[] fishDY = new int[] {0, -1, -1, 0, 1, 1, 1, 0, -1};
	private static int[] sharkDX = new int[] {-1, 0, 1, 0};
	private static int[] sharkDY = new int[] {0, 1, 0, -1};
	

	public static void main(String[] args) throws IOException {
		// 데이터 입력받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int fishN = Integer.parseInt(st.nextToken());
		int practiceN = Integer.parseInt(st.nextToken());
		
		fishPosition = new HashMap<>();	
		mapFishN = new int[MAX_X + 1][MAX_Y + 1][NUM_OF_FISH_DIR];	
		
		for (int i = 0; i < fishN; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int dir = Integer.parseInt(st.nextToken());
			
//			fishPosition.put(new Position(x, y), dir);
			mapFishN[x][y][dir] += 1;
		}
		
		st = new StringTokenizer(br.readLine());
		sharkX = Integer.parseInt(st.nextToken());
		sharkY = Integer.parseInt(st.nextToken());
		
		br.close();
		
		removedFish = new LinkedList<>();
		practice(practiceN);
		
		System.out.println(fishN);
	}
	
	private static void practice(int practiceN) {
		for (int i = 0; i < practiceN; i++) {
			// 1. 복제 마법 2. 모든 물고기 한 칸 이동
			Map<Fish, Integer> copiedFish = new HashMap<>();	// k: 복제된 물고기 정보, v: 물고기 수
			copyMagicAndMoveAllFish(copiedFish);
			
			// 3. 상어 연속 3칸 이동
			moveShark();
			
			// 4. 두 번 전 연습에서 생긴 물고기 냄새 제거
			removeFishSmell();
			
			// 5. 복제 마법 완료
			finishCopyMagic(copiedFish);
		}
	}
	
	private static void copyMagicAndMoveAllFish(Map<Fish, Integer> copiedFish) {
//		for (Fish fish: fishPosition) {
//			int x = fish.getX();
//			int y = fish.getY();
//			int dir = fish.getDir();
//			int fishN = mapFishN[x][y][dir];
//			Fish newFish = new Fish(fish);
//			
//			copiedFish.put(newFish, fishN);
//			
//			Set<Fish> movedFishPosition = new HashSet<>();
//			int[][][] movedFishN = new int[MAX_X + 1][MAX_Y + 1][NUM_OF_FISH_DIR];
//			moveFish(fish, movedFishPosition, movedFishN);
//		}
	}
	
//	private static void moveAllFish() {
//		Set<Fish> movedFishPosition = new HashSet<>();
//		int[][][] movedFishN = new int[MAX_X + 1][MAX_Y + 1][NUM_OF_FISH_DIR];
//		
//		for (Fish fish: fishPosition) {
//			int x = fish.getX();
//			int y = fish.getY();
//			int dir = fish.getDir();
//			int fishN = mapFishN[x][y][dir];
//			
//			moveFish(fish, movedFishPosition, movedFishN);
//		}
//	}
	
	private static void moveFish(Fish fish, Set<Fish> movedFishPosition, int[][][] movedFishN) {
		// 이동할 수 있는 방향 찾기
		int x = fish.getX();
		int y = fish.getY();
		int dir = fish.getDir();
		int canMoveDir = 0;	// 이동할 수 있는 방향
		int movedX = 0;
		int movedY = 0;
		
		// 현재 방향으로부터 (<- 방향까지) 반시계 회전
		for (int d = dir; d > 0; d--) {
			movedX = x + fishDX[d];
			movedY = y + fishDY[d];

			boolean canMove = canMove(movedX, movedY);	// d 방향으로 이동할 수 있는지
			if (canMove) {
				canMoveDir = d;
			}
		}
		
		// 이동할 수 있는 방향을 찾지 못했다면 <- 반시계 45도 방향부터 현재 방향까지 반시계 회전해 찾기
		if (canMoveDir == 0) {
			for (int d = NUM_OF_FISH_DIR; d > dir; d--) {
				movedX = x + fishDX[d];
				movedY = y + fishDY[d];
				
				boolean canMove = canMove(movedX, movedY);	// d 방향으로 이동할 수 있는지
				if (canMove) {
					canMoveDir = d;
				}
			}
		}
		
		// 이동 후 물고기 위치 저장
		int fishN = mapFishN[x][y][dir];
		if (canMoveDir != 0) {	// 이동가능한 방향이 있는 경우
			movedFishN[movedX][movedY][canMoveDir] = fishN;
			movedFishPosition.add(new Fish(movedX, movedY, canMoveDir));
		} else {	// 없는 경우
			movedFishN[x][y][dir] = fishN;
			movedFishPosition.add(new Fish(x, y, dir));
		}
	}
	
	private static boolean canMove(int movedX, int movedY) {
		// 이동한 좌표가 격자 내인지 확인
		if (movedX >= MIN_X && movedY >= MIN_Y && movedX <= MAX_X && movedY <= MAX_Y) {
			// 상어가 있는 좌표가 아닌지 확인
			if (movedX != sharkX && movedY != sharkY) {
				// 물고기의 냄새가 있는 칸이 아닌지
				if (!removedFish.get(0).contains(new Position(movedX, movedY)) && !removedFish.get(1).contains(new Position(movedX, movedY))) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static void moveShark() {
		PriorityQueue<Status> pq = new PriorityQueue<>();
		boolean[][] visited = new boolean[MAX_X][MAX_Y];
//		pq.add(new Status(new Position(sharkX, sharkY), 0, 0, 0, new LinkedList<>()));
		
		while (!pq.isEmpty()) {
			Status cur = pq.poll();
			
			// 상어가 3 칸 이동한 경우
			if (cur.getMovedN() == NUM_OF_SUM_SHARK_MOVE) {
				// 물고기 냄새 남기기 & 물고기 제거
				Set<Position> removedPoses = new HashSet<>();	// 제거된 물고기들의 좌표 저장
				for (Position removedPos : cur.getRemovedFishPos()) {
					removedPoses.add(removedPos);
					
					int x = removedPos.getX();
					int y = removedPos.getY();
//					int dir = 
//					mapFishN[x][y]
//							
//							fishPosition.remove(removedPos);
				}
				removedFish.add(removedPoses);
				
				
				
			}
		}
	}
	
	private static void removeFishSmell() {
		
	}
	
	private static void finishCopyMagic(Map<Fish, Integer> copiedFish) {
		
	}
}



class Fish {
	private int x;
	private int y;
	private int dir; // 방향

	public Fish (int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Fish(Fish fish) {
		this.x = fish.getX();
		this.y = fish.getY();
		this.dir = fish.getDir();
		
	}

	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getDir() {
		return this.dir;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		Fish that = (Fish) o;
		return this.x == that.x && this.y == that.y && this.dir == that.dir;

	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, dir);
	}
}

//class Status implements Comparable<Status>{
//	private Position pos;
//	private int fishN;
//	private int priorityNum;	// 우선순위 계산에 사용하는 수
//	private int movedN;	// 이동 횟수
//	private Queue<Position> removedFishPos;	// 제거된 물고기의 위치 좌표 
//	
//	public Status(Position pos, int fishN, int priorityNum, int movedN, Queue<Position> removedFishPos) {
//		this.pos = pos;
//		this.fishN = fishN;
//		this.priorityNum = priorityNum;
//		this.movedN = movedN;
//		this.removedFishPos = removedFishPos;
//	}
//	
//	public int getMovedN() {
//		return this.movedN;
//	}
//	
//	public Queue<Position> getRemovedFishPos() {
//		return this.removedFishPos;
//	}
//	
//	@Override
//	public int compareTo(Status o) {
//		if (this.fishN == o.fishN) {
//			return this.priorityNum - o.priorityNum;
//		}
//		return this.fishN - o.fishN;
//	}
//}
