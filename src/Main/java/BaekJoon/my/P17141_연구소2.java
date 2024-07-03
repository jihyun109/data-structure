package my;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class P17141_연구소2 {
	static int N; // 연구소의 크기
	static int M; // 놓을 수 있는 바이러스의 개수
	static int lab[][]; // 연구소 상태 배열
	static boolean visited[][];
	static Queue<Node> que;
	static List<int[]> combinationList; // 조합 저장 리스트

	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		lab = new int[N][N];
		visited = new boolean[N][N];
		que = new LinkedList<Node>();

		for (int i = 0; i < N; i++) { // lab 상태 입력 받기.
			for (int j = 0; j < N; j++) {
				lab[i][j] = sc.nextInt();
			}
		}

		int answer = findMinTime();	
		System.out.println(answer);
	}

	private static int findMinTime() { // 최소 시간(답) 구하는 메서드
		int min = N * N;
		List<int[]> combinationList = findCombination(0, 0); // 바이러스를 놓을 M개의 좌표의 조합을 찾아 저장
		boolean canVirus = false; // 모든 칸에 바이러스를 퍼트릴 수 있는지

		// 바이러스를 놓을 좌표의 모든 조합의 바이러스를 퍼트리는 시간 구해 최솟값을 구함
		for (int c = 0; c < combinationList.size(); c++) { 
			int[] combination = combinationList.get(c);		// c번째 조합 불러옴
			
			for (int i = 0; i < M; i++) { // 바이러스를 놓을 자리 모두 que에 offer
				int x = combination[2 * i];
				int y = combination[2 * i + 1];
				que.offer(new Node(x, y, 0));
				visited[x][y] = true;
			}
			
			int h = BFS();	// 조합 c에서 바이러스를 모두 퍼트리는데 걸리는 시간

			for (int i = 0; i < N; i++) { // 바이러스가 모든 빈 칸에 퍼졌는지 확인
				for (int j = 0; j < N; j++) {
					// 빈 칸 중 방문하지 않은 곳이 있으면
					if ((lab[i][j] == 0 || lab[i][j] == 2) && !visited[i][j]) {
						h = -1;
						break;
					}
				}
			}

			if (h != -1 && min > h) { // 모든 빈 칸에 바이러스가 퍼졌으면 최솟값 업데이트
				min = h;
				canVirus = true;
			}

			for (int i = 0; i < N; i++) { // visited베열 초기화
				Arrays.fill(visited[i], false);
			}
		}

		if (!canVirus) { // 모든 조합에서 모든 빈 칸에 바이러스를 퍼트릴 수 없는 경우
			return -1;
		}
		return min;
	}

	// 바이러스를 놓을 M개의 칸을 정하는 모든 조합을 리스트에 저장하는 메서드
	private static List<int[]> findCombination(int start, int depth) {
		List<int[]> combinationList = new LinkedList<>();
		if (depth == M) {	// M개의 조합을 모두 모았으면 방문한 좌표를 conbinationArray에 순서대로 저장
			int[] combinationArray = new int[M * 2];
			int idx = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (visited[i][j]) {
						combinationArray[idx++] = i;
						combinationArray[idx++] = j;
					}
				}
			}
			combinationList.add(combinationArray);	// 저장한 하나의 조합을 list에 add
		}

		for (int i = start; i < N * N; i++) {
			int x = i / N;
			int y = i % N;
			if (!visited[x][y] && lab[x][y] == 2) {	// 바이러스를 놓을 수 있고 방문하지 않았으면(조합에 포함되지 않았으면) 
				visited[x][y] = true;	// visited 처리
				combinationList.addAll(findCombination(i + 1, depth + 1));
				visited[x][y] = false;
			}
		}
		return combinationList;
	}

	private static int BFS() {
		Node now = null;
		while (!que.isEmpty()) {
			now = que.poll();
			for (int i = 0; i < 4; i++) {
				int nextX = now.x + dx[i];
				int nextY = now.y + dy[i];
				if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) { // next가 연구실 범위 내 이고
					if (!visited[nextX][nextY] && lab[nextX][nextY] != 1) {
						que.offer(new Node(nextX, nextY, now.h + 1));
						visited[nextX][nextY] = true;
					}
				}
			}
		}
		return now.h; // 마지막으로 poll된 좌표의 이동 시간 return
	}

	static class Node {
		int x;
		int y;
		int h;
		int[] arr;

		Node(int x, int y, int h) {
			this.x = x;
			this.y = y;
			this.h = h;
		}
	}
}
