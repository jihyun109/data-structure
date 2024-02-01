package ctChap04;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P1167_트리의지름_my3 {
	static ArrayList<Edge>[] A;
	static boolean visited[];
	static int diameter; // 지름 (답)

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int V = sc.nextInt();		// 노드의 개수
		A = new ArrayList[V + 1]; // 그래프의 데이터를 저장하는 리스트
		visited = new boolean[V + 1];

		for (int i = 0; i < V + 1; i++) // 리스트A배열의 각 ArrayList 초기화.
			A[i] = new ArrayList<Edge>();

		for (int i = 0; i < V; i++) { // A[i]에 Edge데이터 삽입
			int node = sc.nextInt();
			while (true) {
				int relatedNode = sc.nextInt();
				if (relatedNode == -1)
					break;
				int dist = sc.nextInt();
				A[node].add(new Edge(node, relatedNode, dist));
			}
		}
		for (int i = 1; i <= V; i++) {
			if (A[i].size() == 1)
				BFS(i);
		}
		System.out.println(diameter);
	}

	static void BFS(int v) {
		Queue<Edge> que = new LinkedList<>();
		
		visited[v] = true;		
		for (Edge edge : A[1]) {	// 첫번째 노드의 edge들 que에 add하고, visted, sumDist 업데이트
			que.add(edge);
			visited[edge.relatedNode] = true;
			edge.sumDist = edge.dist;
			if (diameter < edge.sumDist)// 지름에 최대 거리 업데이트
				diameter = edge.sumDist;
		}
		
		while (!que.isEmpty()) {		// que에서 poll한 edge의 relatedNode(연결된 노드)의 edge들을 que에 add
			Edge popedEdge = que.poll();
			for (Edge edge : A[popedEdge.relatedNode]) {
				if (edge.relatedNode != popedEdge.frontNode && !visited[edge.relatedNode]) {
					visited[edge.relatedNode] = true;
					que.add(edge);
					edge.sumDist = edge.dist + popedEdge.sumDist;
					if (diameter < edge.sumDist)	// 지름에 최대 거리 업데이트
						diameter = edge.sumDist;
				}
			} visited[popedEdge.relatedNode] = false;
		}
	}

	static class Edge {
		int frontNode;
		int relatedNode;
		int dist;
		int sumDist;

		public Edge(int frontNode, int relatedNode, int dist) {
			this.frontNode = frontNode;
			this.relatedNode = relatedNode;
			this.dist = dist;
		}
	}
}