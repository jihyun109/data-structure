package ctChap04;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P13023_친구관계파악하기 {
<<<<<<< HEAD

=======
	static ArrayList<Node>[] arrList;
	static boolean[][] visited;
	static long[][] cost;
	static PriorityQueue<Node> pq;
	static int[] gasStation;
	static int N;
	static int M;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt(); // 도시 수
		M = sc.nextInt(); // 도로 수

		// 각 도시의 기름 가격 입력 받기
		gasStation = new int[N + 1]; // 기름 가격 저장 배열
		int maxOilPrice = 0; // 기름 가격의 최댓값
		for (int i = 1; i <= N; i++) {
			gasStation[i] = sc.nextInt();
			maxOilPrice = Math.max(maxOilPrice, gasStation[i]);
>>>>>>> 54c48bf (macPush)
		}

		arrList = new ArrayList[N + 1]; // 도로 정보 저장 배열
		visited = new boolean[N + 1][maxOilPrice + 1]; // 방문 처리 배열
		cost = new long[N + 1][maxOilPrice + 1]; // 최소 비용 저장 배열
		pq = new PriorityQueue<Node>();
		for (int i = 1; i <= N; i++) { // arrList 배열 초기화
			arrList[i] = new ArrayList<Node>();
		}

		// 도로 정보 입력 받기
		for (int i = 0; i < M; i++) {
<<<<<<< HEAD
			p = st.nextInt();
			q = st.nextInt();
			A[p].add(q);
			A[q].add(p);
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				visited[j] = false;

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
		if (count == 5)
			return;
		for (int next : A[vIdx]) {
			if(!visited[next] && count<5) {
//				System.out.println(vIdx+" -> " + next +" cnt:"+(count+1));
				DFS(next);
=======
			int s = sc.nextInt(); // 출발 도시
			int e = sc.nextInt(); // 도착 도시
			int d = sc.nextInt(); // 도로 길이
			Node eNode=new Node(e, d, gasStation[e], 0);
			Node sNode=new Node(s, d, gasStation[s], 0);
			arrList[s].add(eNode);
			arrList[e].add(sNode);
			Node dNode=sNode;
			System.out.println("dNode = " + dNode);
			
			System.out.println("sNode = " + sNode);
			System.out.println("eNode = " + eNode);

		}
		sc.close();

		// cost 배열 최댓값으로 설정
		for (int i = 0; i <= N; i++) {
			Arrays.fill(cost[i], Long.MAX_VALUE);
		}

		long answer = Dijkstra();
		System.out.println(answer);
	}

	private static long Dijkstra() {
		// 출발 노드 pq에 삽입
		pq.add(new Node(1, 0, gasStation[1], gasStation[1]));
		cost[1][gasStation[1]] = 0;

		while (!pq.isEmpty()) {
			Node cur = pq.poll();

			if (cur.num == N) { // 답 return
				return cost[N][cur.selectedOil];
			}

			if (visited[cur.num][cur.selectedOil]) { // 현재 노드를 방문했으면 continue
				continue;
			}

			visited[cur.num][cur.selectedOil] = true;	// 방문 처리

			for (Node next : arrList[cur.num]) {
				// next에 가져갈 selectedOil 가격 정하기
				System.out.println(next);
				next.selectedOil = Math.min(cur.selectedOil, next.oilPrice);
				long nextCost = cur.sumCost + cur.selectedOil * next.dist; // next까지의 비용

				// next까지의 최소 비용 업데이트
				if (cost[next.num][next.selectedOil] > nextCost) {
					cost[next.num][next.selectedOil] = nextCost;
					next.sumCost = nextCost;
					pq.add(next);
				}
			}
		}
		return 0;
	}

	static class Node implements Comparable<Node> {
		int num; // 도시 번호
		int dist; // 거리
		int oilPrice; // 기름 가격
		int selectedOil; // 지나온 도시 중 가장 가격이 싼 곳의 기름 가격
		long sumCost; // 도시1~num 총 비용

		private Node(int num, int dist, int oilPrice, int selectedOil) {
			this.num = num;
			this.dist = dist;
			this.oilPrice = oilPrice;
			this.selectedOil = selectedOil;
		}

		public Node(Node n) {
			this.num = n.num;
			this.dist = n.dist;
			this.oilPrice = n.oilPrice;
			this.selectedOil = n.selectedOil;
			this.sumCost = n.sumCost;
		}

		@Override
		public int compareTo(Node o) {
			if (this.sumCost > o.sumCost) {
				return 1;
			} else if (this.sumCost < o.sumCost) {
				return -1;
			} else {
				return Integer.compare(this.selectedOil, o.selectedOil);
>>>>>>> 54c48bf (macPush)
			}
		}
		
		if (count < 5)
			count--;
		visited[vIdx] = false;
		
	}
}
