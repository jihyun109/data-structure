package ctChap03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P11660_구간합구하기2_my {

	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
		int n = Integer.parseInt(stringTokenizer.nextToken());
		int m = Integer.parseInt(stringTokenizer.nextToken());
		
		long[][] a = new long [n+1][n+1];
		for(int i = 1; i < n + 1; i++) {			// 배열 입력받고 부분합 만들기
			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			for (int j = 1; j < n + 1; j++) {
				a[i][j] = Integer.parseInt(stringTokenizer.nextToken());
				a[i][j] += a[i][j-1];
			}
		}
		
		for(int i = 0; i < m; i++) {
			stringTokenizer = new StringTokenizer(bufferedReader.readLine());
			int x1 = Integer.parseInt(stringTokenizer.nextToken());
			int y1 = Integer.parseInt(stringTokenizer.nextToken());
			int x2 = Integer.parseInt(stringTokenizer.nextToken());
			int y2 = Integer.parseInt(stringTokenizer.nextToken());
			long sum = 0;
			
			for (int j = x1; j < x2 + 1; j++)
				sum += a[j][y2] - a[j][y1-1];
			System.out.println(sum);
		}
	}
}
