package ctChap04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class P13023_친구관계파악하기 {
	static boolean visited[];
	static ArrayList<Integer> A[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int p;
		int q;
		int count = 0;
		A = new ArrayList[N + 1];
		visited = new boolean [N + 1];
		for (int i = 1; i < N + 1; i++) {
			A[i] = new ArrayList<Integer>();
		}
		for (int i = 0; i < M; i++) {
			p = Integer.parseInt(st.nextToken());
			q = Integer.parseInt(st.nextToken());
			A[p].add(q);
		}
		for (int i = 1; i < N + 1; i++) {
			if ()
		}
	}

}
