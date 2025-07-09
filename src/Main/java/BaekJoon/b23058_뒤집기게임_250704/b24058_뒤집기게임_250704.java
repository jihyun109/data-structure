package BaekJoon.b23058_뒤집기게임_250704;

import java.io.*;
import java.util.*;

public class b24058_뒤집기게임_250704 {
	private static int[] gridBitMask;
	private static int N;
	private static int rockN; // 총 돌의 개수

	private static int maxFlipN;

	public static void main(String[] args) throws IOException {
		init();

		int minTime = findMinTime();

		System.out.println(minTime);
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		rockN = N * N;
		maxFlipN = (N * N) / 2;
		gridBitMask = new int[N];

		// 격자 입력받기
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());

			for (int j = 0; j < N; j++) {
				int rock = Integer.parseInt(st.nextToken());

				if (rock == 1) {
					gridBitMask[i] |= 1 << N - j - 1;
				}
			}
		}
		br.close();
	}

	private static int findMinTime() {
		int minTime = Integer.MAX_VALUE;

		// 1. 열과 행을 뒤집는 모든 경우 수행
		int[] saveGrid = gridBitMask.clone(); // 처음 상태 그리드 저장
		int flip = (1 << N) - 1; // 뒤집는 경우를 비트로 표현 i 번째비트가 1이면 뒤집기 0이면 뒤집지 X

		for (int r = 0; r <= flip; r++) {
			for (int c = 0; c <= flip; c++) {
//				if (r == 2 && c == 2) {
//					System.out.println();
//				}
				// r, c 값에 따라 행과 열 뒤집기
				flip(r, c);

				// 뒤집은 횟수
				int count = Integer.bitCount(r) + Integer.bitCount(c);

				// 1의 개수 구하기
				int numOf1 = findLeftRockN();
				
				if (numOf1 <= rockN / 2) {
					count += numOf1;
				} else {
					int numOf0 = rockN - numOf1;
					count +=numOf0;
				}

				// minTime 업데이트
				minTime = Math.min(count, minTime);

				// 그리드 원복
				gridBitMask = saveGrid.clone();
				count = 0;
			}
		}

		return minTime;
	}

	private static void flip(int r, int c) {
		int mask = (1 << N) - 1;

		// 행 뒤집기
		for (int i = 0; i < N; i++) {
			// i 번째 행을 뒤집어야 하는 경우
			if ((r & (1 << i)) != 0) {
				gridBitMask[i] = ~gridBitMask[i] & mask;
			}
		}

		// 열 뒤집기
		for (int i = 0; i < N; i++) {
			// i 번쨰 열을 뒤집어야하는 경우
			if ((c & (1 << i)) != 0) {
				// 모든 행의 i 번쨰 요소 뒤집기
				for (int j = 0; j < N; j++) {
					gridBitMask[j] ^= (1 << N - i - 1);
				}
			}
		}
	}

	private static int findLeftRockN() {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			int rockN = Integer.bitCount(gridBitMask[i]);
			cnt += rockN;
		}

		return cnt;
	}
}
