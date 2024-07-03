package my;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P16236_아기상어 {
	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N; // 공간의 크기
	static Room space[][]; // 공간 2차원 배열
	static boolean[][] visited;
	static Queue<Room> que;
	static Queue<Room> smallFish;
	static int sharksize = 2; // 처음 아기 상어 크기
	static int eatenFish; // 먹힌 물고기 수
	static int total = 0; // 총 몇 초가 걸리는지
	static int sharkX = 0; // 상어의 위치
	static int sharkY = 0;
	static int nextX = 0;
	static int nextY = 0;
	static int sumEatenFish = 0;


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		space = new Room[N][N];
		visited = new boolean[N][N];
		que = new LinkedList<>();
		smallFish = new LinkedList<Room>();

		for (int i = 0; i < N; i++) { // 공간의 상태 입력받고 아기상어의 위치를 확인해 que에 offer
			for (int j = 0; j < N; j++) {
				int input = sc.nextInt();
				space[i][j] = new Room(i, j, input);
				if (input == 9) {
					sharkX = i;
					sharkY = j;
					
					que.offer(space[i][j]);
					visited[sharkX][sharkY] = true;
				}
			}
		}

		BFS(sharkX, sharkY); // 처음 아기상어의 위치부터 시작
		System.out.println(total);

	}

	static void BFS(int x, int y) {
		while (!que.isEmpty()) {
			Room now = que.poll(); // 현재 아기 상어의 위치
//			System.out.println("polled: " + now.x + ", " + now.y);
//			System.out.println("shark size: " + sharksize);

			for (int i = 0; i < 4; i++) {
				nextX = now.x + dx[i];
				nextY = now.y + dy[i];

				if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) { // 이동할 곳이 범위 내인지 확인

					// 아기 상어의 크기보다 작거나 같으면 이동 가능
					if (space[nextX][nextY].size <= sharksize && !visited[nextX][nextY]) {
						space[nextX][nextY].sec = now.sec + 1; // 걸린 시간 업데이트
						que.offer(space[nextX][nextY]);
						visited[nextX][nextY] = true;
//						System.out.println(nextX + ", " + nextY + ", " + space[nextX][nextY].sec +", "+ space[nextX][nextY].size);

						// 아기 상어의 크기보다 작아 먹을 수 있으면 smallfish에 저장 후 같은 시간 내에 먹을 수 있는 물고기들 중 먹을 물고기 정한다.
						if (space[nextX][nextY].size < sharksize && space[nextX][nextY].size != 0) {
							smallFish.add(space[nextX][nextY]);
							System.out.println("smallFish.add: " + nextX + ", " + nextY);
//							System.out.println("smallFish.size: " + smallFish.size());

						}
					}
				}
	
			}
			// 다음 que의 sec이 next와 같으면 next가 같은 턴에 마지막이므로 먹을 고기를 정한다.
			if (que.size() == 0 || (smallFish.size() > 0 && que.peek().sec > now.sec)) {
				SelectFishToEat();
			}
		}
	}

	static void SelectFishToEat() {
		int fishToEatX = 20;
		int fishToEatY = 20;

		if (smallFish.size() > 0) { // 먹을 고기 정하기
			while (!smallFish.isEmpty()) {
				Room fish = smallFish.poll();
				if (fish.x < fishToEatX) {
					fishToEatX = fish.x;
					fishToEatY = fish.y;
				} else if (fish.x == fishToEatX)
					if (fish.y < fishToEatY) {
						fishToEatX = fish.x;
						fishToEatY = fish.y;
					}
			}
			sumEatenFish++;
			space[fishToEatX][fishToEatY].size = 9; // 물고기 먹음 처리
			total = space[fishToEatX][fishToEatY].sec; // 걸린 시간 업데이트
			space[sharkX][sharkY].size = 0;
			sharkX = fishToEatX;
			sharkY = fishToEatY;
			eatenFish++;
			if (sharksize == eatenFish) {
				sharksize++;
				eatenFish = 0;
			}
			System.out.println("selectedFish: " + fishToEatX + ", " + fishToEatY);
			System.out.println("total:" + total + " eatenfish: " + sumEatenFish + "sharksize: " + sharksize);
			que.clear();
			for (int k = 0; k < N; k++) { // visited = false 처리
				for (int j = 0; j < N; j++) {
					visited[k][j] = false;
				}
			}

			visited[sharkX][sharkY] = true;
			que.offer(space[sharkX][sharkY]);
//			System.out.println("que.size: " + que.size());
		}
	}

	static class Room {
		int x;
		int y;
		int sec = 0;
		int size;

		public Room(int x, int y, int status) {
			this.x = x;
			this.y = y;
			this.size = status;
		}
	}
}
