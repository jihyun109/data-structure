package ctChap06;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class P1931_회의실배정_my {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt(); // 회의의 개수
		int[][] schedule = new int[N][2]; // 회의 시간(시작, 끝)배열
		int count = 0; // 진행할 수 있는 회의 개수
		int end = 0; // 마지막으로 선택한 회의의 종료 시간

		for (int i = 0; i < N; i++) { // 스케쥴 입력 받기
			schedule[i][0] = sc.nextInt();
			schedule[i][1] = sc.nextInt();
		}

		// 종료시간 오름차순 & 종료시간이 같으면 시작시간 오름차순 정렬
		Arrays.sort(schedule, new Comparator<int[]>() {
			public int compare(int[] S, int[] E) {
				if (S[1] == E[1]) { // 종료 시간이 같을 때 시작시간 오름차순 정렬
					return S[0] - E[0];
				}
				return S[1] - E[1]; // 종료시간 오름차순 정렬
			}
		});

		// 회의를 훌다가 시작시간이 이전 종료시간 이상인 회의가 나오면 회의 추가.
		for (int i = 0; i < N; i++) {
			if (schedule[i][0] >= end) {
				end = schedule[i][1];
				count++;
			}
		}
		System.out.println(count);
	}
}
