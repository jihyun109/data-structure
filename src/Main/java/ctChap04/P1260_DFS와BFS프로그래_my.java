package ctChap04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P1260_DFS와BFS프로그래_my {
	static ArrayList<Integer>[] edge; 
	static boolean[] visited;
	static Queue<Integer> que;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int V = sc.nextInt();
		edge = new ArrayList[N+1];
		visited = new boolean[N+1];
		que = new LinkedList<>();
		for (int i = 0; i <= N; i++) {
			edge[i] = new ArrayList();
		}
		for (int i = 0; i < M; i++) {
			int p = sc.nextInt();
			int q = sc.nextInt();
			edge[p].add(q);
			edge[q].add(p);
		}
		for (int i = 1; i <= N; i++) {
			Collections.sort(edge[i]);
		}
		DFS(V);
		System.out.println();
		for (int i = 0; i < N+1; i++) {
			visited[i] = false;
		}
		BFS(V);
	}
	
	static void DFS(int curr) {
		visited[curr] = true;
		System.out.print(curr + " ");
		for (int i : edge[curr]) {
			if (!visited[i]) {
				DFS(i);
			}
		}
	}
	
	static void BFS(int curr) {
		que.add(curr);
		visited[curr] = true;
		System.out.print(curr+ " ");
		while (!que.isEmpty()) {
			int front = que.poll();
			for (int next : edge[front]) {
				if (!visited[next]) {
					que.add(next);
					visited[next] = true;
					System.out.print(next+ " ");
				}
			}
		}
	}

}
