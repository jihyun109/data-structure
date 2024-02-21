package ctChap08;

import java.util.ArrayList;
import java.util.Scanner;

public class P1197_최소스패닝트리 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // init start
        int V = sc.nextInt();   // 노드 개수
        int E = sc.nextInt();   // 엣지 개수

        ArrayList edgeList<Edge> = new ArrayList[E + 1];
        // 엣지 입력받아 edgeList에 정보 저장
        for (int i = 0; i < E; i++) {

        }

        // init end
        sc.close();
    }

    static void Edge {
        int node1;
        int node2;
        int weight;
        public Edge(int node1, int node2, int weight) {
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
        }
    }
}
