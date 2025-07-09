package BaekJoon.b2473_세용액;

import java.util.*;
import java.io.*;

public class b2473_세용액 {

	public static void main(String[] args) throws IOException {
		// init
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		List<Long> yongaks = new ArrayList<>(N); // 용액 저장 배열

		for (int i = 0; i < N; i++) {
			yongaks.add(Long.parseLong(st.nextToken()));
		}

		br.close();

		// 답 구하기
		Collections.sort(yongaks);
		int minLIdx = 0;
		int minRIdx = 0;
		int minMIdx = 0;
		long min = Long.MAX_VALUE; // 세 용액의 합 절댓값의 최솟값
		boolean found = false;

		for (int mIdx = 0; mIdx < N; mIdx++) {
			int lIdx = 0;
			int rIdx = N - 1;
			long mValue = yongaks.get(mIdx);

			while (lIdx < rIdx) {
				if (lIdx == mIdx) {
					lIdx++;
				} else if (rIdx == mIdx) {
					rIdx--;
				}

				if (lIdx >= rIdx) {
					break;
				}

				long lValue = yongaks.get(lIdx);
				long rValue = yongaks.get(rIdx);

				// 세 용액의 합
				long sum = lValue + rValue + mValue;

				// 현재까지 구한 세 용액의 합의 최솟값보다 sum 이 작은 경우
				long absSum = Math.abs(sum);
				if (absSum < min) {
					min = absSum;
					minLIdx = lIdx;
					minRIdx = rIdx;
					minMIdx = mIdx;
				}

				if (sum > 0) { // 세 용액의 합이 양수인 경우 rIdx를 한 칸 왼쪽으로
					rIdx--;

				} else if (sum < 0) { // 세 용액의 합이 음수인 경우 lIdx를 한 칸 오른쪽으로
					lIdx++;

				} else { // 세 용액의 합이 0인 경우 break
					minLIdx = lIdx;
					minRIdx = rIdx;
					minMIdx = mIdx;
					found = true;
					break;
				}

			}

			// 답을 구한 경우
			if (found == true) {
				break;
			}
		}

		// 답 출력
		List<Long> answerValues = new LinkedList<>();
		answerValues.add(yongaks.get(minLIdx));
		answerValues.add(yongaks.get(minRIdx));
		answerValues.add(yongaks.get(minMIdx));
		Collections.sort(answerValues);
		System.out.println(answerValues.get(0) + " " + answerValues.get(1) + " " + answerValues.get(2));
	}

}
