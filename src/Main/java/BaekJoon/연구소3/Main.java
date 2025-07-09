package BaekJoon.연구소3;

import java.util.*;
import java.io.*;

public class Main {
	private static int labSize; // 연구소 크기.
	private static int enableVirusN; // 활성시킬 수 있는 바이러스 수
	private static int totalVirusN; // 총 바이러스의 수
	private static int wallN; // 벽의 수
	private static int blankN; // 빈칸 수

	private static int[][] lab; // 연구소 상태 저장
	private static Map<Integer, Point> pointsOfVirus; // 바이러스의 좌표 저장(k: id, v: 좌표)

	private static final int BLANK = 0; // 빈칸
	private static final int WALL = 1; // 벽
	private static final int VIRUS = 2; // 바이러스

	public static void main(String[] args) throws IOException {
		// 데이터 입력받기
		init();

		// 최소 시간 구하기
		int minTime = findMinTime();

		System.out.println(minTime);
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		labSize = Integer.parseInt(st.nextToken());
		enableVirusN = Integer.parseInt(st.nextToken());

		totalVirusN = 0;
		wallN = 0;
		blankN = 0;
		lab = new int[labSize][labSize];
		pointsOfVirus = new HashMap<>();
		for (int i = 0; i < labSize; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < labSize; j++) {
				int status = Integer.parseInt(st.nextToken());
				lab[i][j] = status;

				switch (status) {
				case WALL:
					wallN++;
					break;

				case VIRUS:
					pointsOfVirus.put(totalVirusN++, new Point(i, j));
					break;
				case BLANK:
					blankN++;
				}
			}
		}

		br.close();
	}

	private static int findMinTime() {
		int minTime = Integer.MAX_VALUE;

		// enableVirusN 개의 바이러스를 활성시킬 수 있는 모든 경우의 수에서 바이러스 활성시키기
		int total = 1 << totalVirusN; // 모든 경우의 수
		for (int i = 1; i <= total; i++) {
			int selectedVirusN = Integer.bitCount(i); // 선택된 바이러스의 개수

			// enableVirusN 개의 바이러스가 선택된 경우 바이러스 활성시켜 퍼트리기
			if (selectedVirusN == enableVirusN) {
				int time = spreadVirus(i, minTime); // 바이러스가 확산되는데 걸린 시간

				// 바이러스가 모든 빈칸에 확산된 경우 minTime 업데이트
				if (time != -1) {
					minTime = Math.min(minTime, time);
				}
			}
		}

		// 바이러스를 어떻게 놓아도 모든 빈칸에 퍼트릴 수 없는 경우 return -1
		if (minTime == Integer.MAX_VALUE) {
			return -1;
		}

		return minTime;
	}

	private static int spreadVirus(int n, int minTime) {
		int[] dx = new int[] { -1, 1, 0, 0 };
		int[] dy = new int[] { 0, 0, 1, -1 };

		// 활성시킬 바이러스의 위치를 추출해 que 에 넣기
		Queue<Status> que = new LinkedList<>();
		extractVirusPos(n, que);

		boolean[][] visited = new boolean[labSize][labSize];
		int spreadedBlankN = 0; // 바이러스가 확산된 빈칸 수

		while (!que.isEmpty()) {
			Status cur = que.poll();
			int curX = cur.getPoint().getX();
			int curY = cur.getPoint().getY();
			int curTime = cur.getTime();

			// 현재 시간이 현재까지의 최소시간보다 크거나 같은 경우 더 계산할 필요 없음.
			if (curTime >= minTime) {
				return curTime;
			}

			// 이미 방문한 경우 continue
			if (visited[curX][curY]) {
				continue;
			}

			// 현재 위치가 빈칸인 경우
			if (lab[curX][curY] == BLANK) {
				spreadedBlankN++;
			}
			visited[curX][curY] = true;

			// 바이러스가 모든 빈칸에 확산된 경우 return 시간 return
			if (spreadedBlankN == blankN) {
				return curTime;
			}

			// 4방향으로 확산
			for (int i = 0; i < dx.length; i++) {
				int nextX = curX + dx[i];
				int nextY = curY + dy[i];

				// next 가 연구실 범위 내인지 확인
				if (nextX >= 0 && nextX < labSize && nextY >= 0 && nextY < labSize) {
					if (!visited[nextX][nextY] && lab[nextX][nextY] != WALL) { // next 를 아직 방문하지 않았고 벽이 아닌지 확인
						que.add(new Status(new Point(nextX, nextY), curTime + 1));

					}
				}
			}
		}

		return -1;
	}

	private static void extractVirusPos(int n, Queue<Status> que) {
		// n 의 i 번째 자리가 1이면 해당되는 좌표 que 에 넣기
		for (int i = 0; i < totalVirusN; i++) {
			int tmp = 1 << i;
			if ((n & tmp) == tmp) {
				Point p = pointsOfVirus.get(i);
				que.add(new Status(p, 0));
			}
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

class Status {
	private Point point;
	private int time;

	public Status(Point point, int time) {
		this.point = point;
		this.time = time;
	}

	public Point getPoint() {
		return this.point;
	}

	public int getTime() {
		return this.time;
	}

}
