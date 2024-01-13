package ctChap04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class P13023_친구관계파악하기 {
	static boolean[] visited;
	static ArrayList<Integer>[] A=new ArrayList[1000];
	static int count = 0;
	public static void main(String[] args) throws IOException {
		Scanner st = new Scanner(System.in);
		int N = st.nextInt();
		int M = st.nextInt();

		int p = 0;
		int q = 0;
//		A = new ArrayList<>(N + 1);

		visited = new boolean [N + 1];
		for (int i = 1; i < N + 1; i++) {
			A[i]=new ArrayList<Integer>();
		}

		for (int i = 0; i < M; i++) {
			p = st.nextInt();
			q = st.nextInt();
			A[p].add(q);
		}

		for (int i = 1; i < N + 1; i++) {
			if (!visited[i]) {
				count = 0;
				DFS(i);
			}
			if (count == 5) {
				System.out.print("1");
				break;
			}
		}
		if (count < 5)
			System.out.println("0");
	}

	static void DFS(int vIdx) {
		if (visited[vIdx])
			return;
		visited[vIdx] = true;
		count++;
		for (Integer i : A[vIdx]) {
			DFS(vIdx);
			if (count == 5)
				return;
		}
	}
}
