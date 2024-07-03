package ctChap04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

public class P2178_미로탐색_my {
	static int N;
	static int M;
	static int[][] arr;
	static boolean[][] visited;
	static int count = 0;
	static int min = 0;
	static int num = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new int[N+1][M+1];
		visited = new boolean[N+1][M+1];
		
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			String line = st.nextToken();
			for (int j = 1; j <= M; j++) {
				arr[i][j] = Integer.parseInt(line.substring(j-1, j));
			}
		}
		DFS(1, 1);
		System.out.println(min);
		System.out.println(num);

	}
	static void DFS(int curX, int curY) {
		count++;
		num++;
		visited[curX][curY] = true;
		if (curX == N && curY == M) {
			if (min == 0)
				min = count;
			if (min > count)
				min = count;
		}
		if (curX != N || curY != M) {
			if (arr[curX-1][curY] != 0 && !visited[curX-1][curY])
				DFS(curX-1, curY);
			if (curY+1 <= M && arr[curX][curY+1] != 0 && !visited[curX][curY+1])
				DFS(curX, curY+1);
			if (curX+1 <= N &&arr[curX+1][curY] != 0 && !visited[curX+1][curY])
				DFS(curX+1, curY);
			if (arr[curX][curY-1] != 0 && !visited[curX][curY-1])
				DFS(curX, curY-1);
		}
		count--;
		visited[curX][curY] = false;
		return;
	}
}
