package BaekJoon.b2812_크게만들기;

import java.io.*;
import java.util.*;

public class b2812_크게만들기 {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int K = sc.nextInt();

		String numStr = sc.next();

		Deque<Integer> que = new LinkedList<>();
		int removedN = 0;
		String answer = "";

		for (int i = 0; i < numStr.length(); i++) {
			char numChar = numStr.charAt(i);
			int curN = numChar - '0';

			// before 이 curN보다 클 떄까지 반복
			while (!que.isEmpty()) {
				int before = que.pollLast();

				if (before < curN) {
					removedN++;

					// k 만큼 수를 지운 경우 답 구하기
					if (removedN == K) {
						answer = makeAnswer(i, que, numStr);
						break;
					}
				} else {
					que.add(before);
					break;
				}
			}

			// 답이 구해진 경우
			if (answer != "") {
				break;
			}

			que.add(curN);
		}

		if (removedN < K) {
			int remainedN = K - removedN; // 더 삭제해야하는 개수

			for (int i = 0; i < remainedN; i++) {
				que.pollLast();
			}
			answer = makeAnswer(-1, que, null);
		}

		System.out.println(answer);
	}

	private static String makeAnswer(int idx, Deque<Integer> que, String numStr) {
		StringBuilder sb = new StringBuilder();

		while (!que.isEmpty()) {
			int n = que.pollFirst();
			sb.append(n);
		}

		if (numStr != null) {
			for (int i = idx; i < numStr.length(); i++) {
				char numChar = numStr.charAt(i);
				int num = numChar - '0';
				sb.append(num);
			}
		}

		String ret = sb.toString();

		return ret;

	}
}
