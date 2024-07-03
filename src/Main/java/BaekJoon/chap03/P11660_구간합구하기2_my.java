package chap03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P11660_구간합구하기2_my {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] A = new int [N + 1][N + 1];		// 2차원 배열 데이터
		long sum[][] = new long [N + 1][N + 1];	// 배열 A의 합배열
		long answer; 	// 구하는 구간합
		Coordinate[] q = new Coordinate[M];	// M개의 입력 받은 좌표 저장 배열
		
		for (int i = 1; i <= N; i++) {		// 배열 A에 데이터 저장
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int i = 0; i < M; i++) {		// M개의 question 좌표를 q배열에 저장
			q[i] = new Coordinate(0, 0, 0, 0);
			st = new StringTokenizer(br.readLine());
			q[i].x1 = Integer.parseInt(st.nextToken());
			q[i].y1 = Integer.parseInt(st.nextToken());
			q[i].x2 = Integer.parseInt(st.nextToken());
			q[i].y2 = Integer.parseInt(st.nextToken());
		}
		br.close();
		
		// 배열 A의 합배열 만들기
		for (int i = 1; i <= N; i++) 
			for (int j = 1; j <= N; j++) 
				sum[i][j] = A[i][j] + sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1];
		
		// M개의 question 실행
		for (int i = 0; i < M; i++) {
			answer = sum[q[i].x2][q[i].y2] - sum[q[i].x1-1][q[i].y2] - sum[q[i].x2][q[i].y1-1] + sum[q[i].x1-1][q[i].y1-1];
			System.out.println(answer);
		}
	}

	static class Coordinate {
		int x1;
		int y1;
		int x2;
		int y2;
		public Coordinate (int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}
}
