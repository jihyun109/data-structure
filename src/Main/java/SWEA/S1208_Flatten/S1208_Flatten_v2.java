package S1208_Flatten;

import java.util.*;
import java.io.*;

public class S1208_Flatten_v2 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		for (int tc = 1; tc <= 10; tc++) {
			// 입력 받기
			int dumpN = Integer.parseInt(br.readLine());

			StringTokenizer st = new StringTokenizer(br.readLine());
			int[] arr = new int[101]; // arr[i]: i의 높이를 가진 col의 개수
			for (int i = 0; i < 100; i++) {
				int height = Integer.parseInt(st.nextToken());
				arr[height]++;
			}

			// dump 수행
			int maxH = 100;
			int minH = 1;
			int diff = 99;
			for (int d = 0; d < dumpN; d++) {

				while (arr[minH] == 0) {
					minH++;
				}

				while (arr[maxH] == 0) {
					maxH--;
				}

				diff = maxH - minH;
				if (diff == 0 || diff == 1) {
					break;
				}

				// dump 수행
				arr[maxH]--;
				arr[maxH - 1]++;
				arr[minH]--;
				arr[minH + 1]++;
			}

			while (arr[minH] == 0) {
				minH++;
			}

			while (arr[maxH] == 0) {
				maxH--;
			}
			bw.append("#" + tc + " " + (maxH - minH) + '\n');
		}

		bw.flush();

	}

}
