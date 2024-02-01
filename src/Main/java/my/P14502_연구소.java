package my;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P14502_연구소 {
	static int N;
	static int M;
	static int room[][]; // 연구실의 방 배열
	static int blank; // 비어있는 빙 개수
	static int first; // 처음부터 감염되어 있었던 방 수
	static Queue<int[]> que;
	static Queue<int[]> offeredXY; // 바이러스가 이동된 방들을 BFS가 끝나면 다시 0처리 해주기 위해
	static int[] firstInfected[]; // 처음부터 바이러스가 있던 방들의 좌표 저장
	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt(); // 연구실 세로
		M = sc.nextInt(); // 연구실 가로
		room = new int[N][M];
		que = new LinkedList<>();
		offeredXY = new LinkedList<>();
		firstInfected = new int[N * M][2];

		int max = 0;
		first = 0; // 처음부터 바이러스가 있던 방의 개수

		for (int i = 0; i < N; i++) { // 각 방의 상태 입력받기
			for (int j = 0; j < M; j++) {
				int status = sc.nextInt();
				room[i][j] = status;
				if (status == 0)
					blank++;
				else if (status == 2) {
					que.offer(new int[] { i, j }); // x좌표, y좌표, turn
					firstInfected[first++] = new int[] { i, j };
				}
			}
		}
//		System.out.println("blank: " + blank + " que.size(): " +que.size());
		int sumBlank = blank; // 처음 상태의 blank 개수

		for (int i1 = 0; i1 < N; i1++) {
			for (int j1 = 0; j1 < M; j1++) {
				if (room[i1][j1] == 0) {
					room[i1][j1] = 1;
					for (int i2 = 0; i2 < N; i2++) {
						for (int j2 = 0; j2 < M; j2++) {
							if (room[i2][j2] == 0) {
								room[i2][j2] = 1;
								for (int i3 = 0; i3 < N; i3++) {
									for (int j3 = 0; j3 < M; j3++) {
										if (room[i3][j3] == 0) {
//											System.out.println("(" +i1+","+ j1 + ")" + "(" + i2 +"," +j2+ ")" + "(" + i3 + "," + j3+ ")");
											room[i3][j3] = 1; // 벽을 세웠던 방 초기화
											int safeZone = BFS();
											if (max < safeZone)
												max = safeZone;
											room[i3][j3] = 0;
											blank = sumBlank; // blank를 다시 처음 상태의 값으로 초기화
										}
									}
								}
								room[i2][j2] = 0; // 벽을 세웠던 방 초기화
							}
						}
					}
					room[i1][j1] = 0;
				}
			}
		}
		System.out.print(max - 3); // 3개의 벽 세운 것 빈칸에서 빼주기.
	}

	private static int BFS() {
		while (!que.isEmpty()) {
			int now[] = que.poll();
			for (int i = 0; i < 4; i++) { // 상하좌우
				int x = now[0] + dx[i];
				int y = now[1] + dy[i];

				if (x >= 0 && x < N && y >= 0 && y < M && room[x][y] == 0) {
					que.offer(new int[] { x, y });
					offeredXY.offer(new int[] { x, y });
					room[x][y] = 2;
					blank--;
				}
			}
		}
//		for (int i = 0; i < N; i++) {// 출력
//			for (int j =0; j < M; j++) {
//				System.out.print(room[i][j] + " ");
//			}
//			System.out.println();
//		}
		while (!offeredXY.isEmpty()) { // 전염된 방들 초기화 (처음 전염되어있던 방들 빼고)
			int now[] = offeredXY.poll();
			int x = now[0];
			int y = now[1];
			room[x][y] = 0;
		}
		for (int i = 0; i < first; i++) {
			que.add(firstInfected[i]);
		}

		return blank;
	}
}
