package BaekJoon.어항정리;
import java.io.*;
import java.util.*;

public class Main {
	private static int bowlN;
	private static int[][] bowls;
	private static int minFishN;
	private static Queue<Integer>  minBowls;
	

	public static void main(String[] args) throws IOException  {
		// 데이터 입력받기
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		bowlN = Integer.parseInt(st.nextToken()); // 어항 개수
		int K = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		bowls = new int[bowlN][bowlN];	// 어항 정보 저장
		minFishN = Integer.MAX_VALUE;	// 모든 어항에서 최소 물고기 수
		minBowls = new LinkedList<>();	// 물고기 수가 최소인 어항들의 index 저장.
		
		for (int i = 0; i < bowlN; i++) {
			int fishN = Integer.parseInt(st.nextToken());	// i 번쨰 어항의 물고기
			bowls[0][i] = fishN;
			
			// 물고기 수가 최소인 어항 구하기
			if (fishN < minFishN) {	// 여태까지의 최소값보다 작은 경우
				minFishN = fishN;
				minBowls.clear();
				minBowls.add(i);
			} else if(fishN == minFishN) {
				minBowls.add(i);
			}
		}
		
		br.close();
		
		int organizeN = findOrganizeN();
		System.out.println(organizeN);
		
	}
	
	private static int findOrganizeN() {
		// 1. 물고기 수가 가장 적은 어항에 물고기 1마리씩 넣기
		putFishIntoMinBowls();
		
		// 2. 2개 이상 쌓여있는 어항을 90도 회전시켜 옆의 어항 위에 올리기
		int leftIdx = rotateAndPutRight1();	// 회전시켜 올린 후 가장 왼쪽에 위치한 어항의 index
		
		// 3. 인접한 어항들의 물고기 수 조정 후 일렬로 만들기
		setFishN();
		
		// 4. N/2 개 90도 회전 & 어항 위에 올리기 (2번)
		rotateAndPutRight2();
		
		// 5. 인접한 어항들의 물고기 수 조정 후 일렬로 만들기
		int maxFishN = setFishN();
		
		return 0;
	}
	
	private static void putFishIntoMinBowls() {
		while (!minBowls.isEmpty()) {
			int bowlIdx = minBowls.poll();
			bowls[0][bowlIdx] += 1;
		}
	}
	
	private static int rotateAndPutRight1() {
		// 가장 왼쪽의 어항을 바로 옆 어항 위에 올리기
		putLeftBowlOnNextBowl();
		int leftIdx = 1;	// 가장 왼쪽에 있는 어항의 index
		int height = 2;	// 가장 왼쪽에 위치해 쌓여있는 어항들의 높이
		int width = 1;	// 가장 왼쪽에 위치해 쌓여있는 어항들의 폭
		
		// 2개 이상 쌓여있는 어항을 90도 회전시켜 옆의 어항 위에 올리기
		while (true) {
			// 회전시켜 올릴 수 없는 경우
			if (height > bowlN - width) {
				break;
			}
		}
		
		return 0;
	}
	
	private static int  rotateAndPutRight2() {
		
		return 0;
	}
	
	private static int setFishN() {
		return 0;
		
	}
	
	private static void putLeftBowlOnNextBowl() {
		int thisFishN = bowls[0][0];
		bowls[0][0] = 0;
		bowls[1][1] = thisFishN;
	}

}
