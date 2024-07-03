package ctChap05;

import java.util.Scanner;

public class P2343_블루레이_my {
	static int N;
	static int M;
	static int[] lesson;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		lesson = new int[N];	// 레슨 길이 배열
		int longLesson = 0;		// 가장 긴 레슨 길이
		int sumLesson = 0;		// 모든 레슨의 길이 합한 값
		
		for (int i = 0; i < N; i++) {	// 레슨 배열 입력받음
			int m = sc.nextInt();
			lesson[i] = m;
			if (longLesson < m) {	// 가장 긴 레슨 길이 업데이트
				longLesson = m;
			}
			sumLesson += m;		// 모든 레슨의 길이 더함
		}
		
		// 블루레이 크기 최솟값 구하는 메서드
		int min = findMinBlue(longLesson, sumLesson);
		System.out.println(min);
	}

	private static int findMinBlue(int longLesson, int sumLesson) {
		int start = longLesson;
		int end = sumLesson;
		int mid = 0;
		while (start <= end) {
			mid = (start + end) / 2;
			// mid 크기의 블루레이 M개로 모든 레슨을 저장할 수 있는지 확인하는 메서드
			boolean canSave = canSave(mid);
			
			if (canSave) {
				end = mid - 1;
			} else {
				start = mid + 1;
			}
		}
		return start;
	}

	// mid 크기의 블루레이 M개로 모든 레슨을 저장할 수 있는지 확인하는 메서드
	private static boolean canSave(int mid) {
		int cnt = 1;	// 사용한 블루레이 개수
		int sum = 0;	// 하나의 블루레이에 들어간 레슨의 총 길이
		
		// 레슨들을 직접 넣어보며 mid 크기의 블루레이 M개로 모든 레슨을 담을 수 있는지 확인
		for (int i = 0; i < N; i++) {
			sum += lesson[i];
			if (sum > mid) {	// 블루레이 용량을 초과하면 다음 블루레이에 저장
				cnt++;
				sum = lesson[i];
			}
			if (cnt > M) {	// 주어진 블루레이 개수를 초과하면
				return false;
			}
		}
		return true;
	}
}
