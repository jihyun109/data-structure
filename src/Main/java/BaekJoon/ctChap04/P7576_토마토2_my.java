package ctChap04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P7576_토마토2_my {
	static int M;
	static int N;
	static int H;
	static Space box[][][];
	static int ripeTomato = 0; // 익은 토마토 개수
	static int blank = 0; // 토마토가 들어있지 않은 칸 개수
	static Queue<Space> filledTomato;
	static int[] dx = { -1, 0, 1, 0, 0, 0 };
	static int[] dy = { 0, 1, 0, -1, 0, 0 };
	static int[] dz = { 0, 0, 0, 0, 1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		M = Integer.parseInt(st.nextToken()); // box의 가로
		N = Integer.parseInt(st.nextToken()); // box의 세로
		H = Integer.parseInt(st.nextToken()); // box의 높이

		box = new Space[N][M][H];
		filledTomato = new LinkedList<Space>(); // 처음 채워져있는 space

		for (int h = 0; h < H; h++) {	// box배열에 입력된 상태 업데이트
			for (int i = 0; i < N; i++) { 
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					int status = Integer.parseInt(st.nextToken());
					box[i][j][h] = new Space(i, j, h, status);
					if (status == -1) // 토마토가 채워져있지 않으면
						blank++;
					else if (status == 1) { // 토마토가 채워져있으면
						filledTomato.add(box[i][j][h]);
						ripeTomato++;
					}
				}
			}
		}
		br.close();

		int answer = BFS();
		System.out.print(answer);
	}

	static int BFS() {
		int maxDate = 0;
		if ((N * M * H) - ripeTomato - blank == 0) // 저장될 때부터 모든 토마토가 익어있는 상태
			return 0;
		Queue<Space> que = filledTomato;

		while (!que.isEmpty()) {
			Space polledSpace = que.poll();
			maxDate = polledSpace.date;
			for (int i = 0; i < 6; i++) { // 상하좌우의 칸들 que에 add
				int nextX = polledSpace.x + dx[i];
				int nextY = polledSpace.y + dy[i];
				int nextZ = polledSpace.z + dz[i];
				if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < M && nextZ >= 0 && nextZ < H && box[nextX][nextY][nextZ].status == 0) {
					que.add(box[nextX][nextY][nextZ]);
					ripeTomato++;
					box[nextX][nextY][nextZ].status = 1;
					box[nextX][nextY][nextZ].date = polledSpace.date + 1;
				}
			}
		}
		if (N * M * H - ripeTomato - blank == 0)
			return maxDate;
		else if (N * M * H - ripeTomato - blank > 0)
			return -1;
		return 0;
	}

	static class Space {
		int x;
		int y;
		int z;
		int status;
		int date = 0;

		public Space(int x, int y, int z, int status) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.status = status;
		}
	}
}
