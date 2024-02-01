package ctChap04;

import java.util.ArrayList;
import java.util.Scanner;

public class P1167_트리의지름_my {
	static ArrayList<Edge>[] A;
	static boolean visited[];
	static int maxDist; // 1~V까지 각각 시작점을 설정해 DFS 실행 후 나오는 노드 사이 거리의 최댓값
	static int dist = 0; // v노드와 다른 노드 사이의 최대 거리

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int V = sc.nextInt();
		A = new ArrayList[V + 1]; // 그래프의 데이터를 저장하는 리스트
		visited = new boolean[V + 1];
		int diameter = 0; // 트리의 지름 (답)

		for (int i = 0; i < V + 1; i++) // A 리스트의 각 ArrayList 초기화.
			A[i] = new ArrayList<Edge>();

		for (int i = 0; i < V; i++) { // A[i]에 Edge데이터 삽입
			int node = sc.nextInt();
			while (true) {
				int relatedNode = sc.nextInt();
				if (relatedNode == -1)
					break;
				int dist = sc.nextInt();
				A[node].add(new Edge(relatedNode, dist));
			}
		}

		// DFS 완전탐색을 해 트리의 지름 구하기
		for (int i = 1; i <= V; i++) {
			dist = 0; // 시작 노드를 바꿀 때 마다 dist 초기화
			maxDist = DFS(i); // i노드부터 시작해 DFS를 실행했을 때 i노드와 다른 노드 사이의 최대 거리
			if (diameter < maxDist) // 최대 거리와 현재까지의 최대 거리를 비교해 지름 구하기
				diameter = maxDist;
		}
		System.out.println(diameter);
	}

	static int DFS(int v) {
		visited[v] = true;
		for (Edge edge : A[v]) { //
			if (!visited[edge.relatedNode]) {
				dist += edge.dist;
				DFS(edge.relatedNode);
				dist -= edge.dist;
				visited[edge.relatedNode] = false;
			}
		}
		if (maxDist < dist)
			maxDist = dist;
		return maxDist;
	}

	static class Edge {
		int relatedNode;
		int dist;

		public Edge(int relatedNode, int dist) {
			this.relatedNode = relatedNode;
			this.dist = dist;
		}
	}
}