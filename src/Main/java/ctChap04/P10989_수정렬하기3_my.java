package ctChap04;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

public class P10989_수정렬하기3_my {
	public static Queue<Integer>[] myque = new LinkedList[9];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int N = Integer.parseInt(br.readLine());
		int[] A = new int [N];
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(br.readLine());
		}
		radixSort(A, N);
		for (int i = 0; i < N; i++) {
			bw.write(A[i] + "\n");
		}
		bw.flush();
		bw.close();
	}

	private static void radixSort(int[] A, int N) {
		for (int i = 1; i <= 5; i++) {		// 각 자릿수 정렬
			for (int j = 0; j < N; j++) {	// 모든 수를 stack에 저장
				switch(A[j] % 10^(i+1)) {
				case 0: 
					(myque[0]).add(A[j]);
					break;
				case 1: 
					(myque[1]).add(A[j]);
					break;
				case 2: 
					(myque[2]).add(A[j]);
					break;
				case 3: 
					(myque[3]).add(A[j]);
					break;
				case 4: 
					(myque[4]).add(A[j]);
					break;
				case 5: 
					(myque[5]).add(A[j]);
					break;
				case 6: 
					(myque[6]).add(A[j]);
					break;
				case 7: 
					(myque[7]).add(A[j]);
					break;
				case 8: 
					(myque[8]).add(A[j]);
					break;
				case 9: 
					(myque[9]).add(A[j]);
					break;
				}
				int idx = 0;
				for (int k = 0; i < 10; k++) {
					while (myque[k].size() != 0) {
						A[idx++] = myque[k].poll();
					}
				}
			}
		}
	}

}
