package ctChap08.P1197;

import java.util.PriorityQueue;
import java.util.Scanner;

public class P1197_최소스패닝트리 {

    static int[] parent;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // init start
        int V = sc.nextInt();   // 노드 개수
        int E = sc.nextInt();   // 엣지 개수
        PriorityQueue<Edge> pq = new PriorityQueue<>();   // edge를 weight를 기준으로 정렬해 저장하는 pq
        parent = new int[V + 1];   // 대표 노드 저장 배열

        // parent 배열 초기화
        for (int i = 1; i <= V; i++) {
            parent[i] = i;
        }

        // 엣지 입력받아 edgeList에 정보 저장
        for (int i = 0; i < E; i++) {
            int n1 = sc.nextInt();  // 노드1
            int n2 = sc.nextInt();  // 노드2
            int w = sc.nextInt();   // 가중치

            pq.add(new Edge(n1, n2, w));
        }

        // init end
        sc.close();

        // 최소 신장 트리 만들기
        int edge = 0;   // 연결된 엣지 개수
        int minSpanningTreeWeight = 0;  // 최소 신장 트리의 가중치 저장 변수(답)
        while (edge != V - 1) { // 연결된 엣지의 개수가 V-1일 때 까지
            Edge curEdge = pq.poll();   // 현재 edge
            int curNode1 = curEdge.node1;   // 현재 edge의 node1
            int curNode2 = curEdge.node2;   // 현재 edge의 node2
            int curWeight = curEdge.weight; // 현재 edge의 가중치

            // node1과 node2를 연결했을 때 사이클이 만들어지면 continue
            if (find(curNode1) == find(curNode2)) {   // node1과 node2의 대표 노드가 같으면
                continue;
            }

            // curNode1과 curNode2 연결하기
            union(curNode1, curNode2);

            // 연결한 edge 개수 업데이트
            edge++;

            // 최소 신장 트리 가중치에 현재 연결한 edge의 가중치 더하기
            minSpanningTreeWeight += curWeight;
        }

        // 답 출력
        System.out.println(minSpanningTreeWeight);
    }

    // find 메서드
    private static int find (int node) {
        if (node == parent[node]) {
            return node;
        } else {
            return parent[node] = find(parent[node]);
        }
    }

    // union 메서드
    private static void union(int node1, int node2) {
        node1 = find(node1);    // node1에 node1의 대표 노드 저장
        node2 = find(node2);    // node2에 node2의 대표 노드 저장

        parent[node2] = node1;  // union
    }

    // Edge 클래스
    static class Edge implements Comparable<Edge>{
        int node1;
        int node2;
        int weight;
        public Edge(int node1, int node2, int weight) {
            this.node1 = node1;     // 노드1
            this.node2 = node2;     // 노드2
            this.weight = weight;   // 가중치
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }
}
