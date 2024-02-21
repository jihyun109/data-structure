package ctChap08;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P1707_이분그래프_my {
	static int V; // 노드 개수
	static int E; // 엣지 개수
	static ArrayList<Integer>[] arrList;
	static int visited[]; // 방문, 그룹 확인 배열
	static Queue<Integer> que;
	static Queue<String> answerQue;
	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int K = sc.nextInt(); // 테스트 케이스 수
		boolean isBipartiteGraph;
		answerQue = new LinkedList();

		for (int test = 0; test < K; test++) { // 테스트 케이스만큼 반복
			V = sc.nextInt();
			E = sc.nextInt();
			arrList = new ArrayList[V + 1];
			visited = new int[V + 1];
			que = new LinkedList();
			for (int i = 1; i <= V; i++) { // arrayList 초기화
				arrList[i] = new ArrayList<>();
			}

			for (int i = 0; i < E; i++) { // 엣지 입력받기
				int node1 = sc.nextInt();
				int node2 = sc.nextInt();
				arrList[node1].add(node2);
			}

			isBipartiteGraph = BFS(); // 이분 그래프인지 판별);

			if (isBipartiteGraph == true) {	// 출력
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
		}
	}

	private static boolean BFS() {
		que.offer(1); // 첫번쨰 노드 que에 offer
		visited[1] = 1;

		while (!que.isEmpty()) {
			int now = que.poll(); // 현재 노드
			for (int next : arrList[now]) {
				if (visited[next] != 0) {	// 방문한 적이 있으면
					if (visited[now] == visited[next]) {	// now와 next의 그룹이 같으면
						return false;
					}
				}
				if (visited[now] == 1) {
					visited[next] = 2;
				} else if (visited[now] == 2) {
					visited[next] = 1;
				}
				que.offer(next);
			}
		}
		return true; // while문이 다 끝나면 이분리스트임
	}
}
