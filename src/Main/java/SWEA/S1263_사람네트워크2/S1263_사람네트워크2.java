package SWEA.S1263_사람네트워크2;

import java.util.*;

public class S1263_사람네트워크2 {
    static int N;
    static List<Integer>[] arrList;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCaseN = sc.nextInt();

        for (int t = 1; t <= testCaseN; t++) {
            N = sc.nextInt();   // 사람 수
            arrList = new ArrayList[N]; // 사람 네트워크 저장 인접 리스트
            int[][] dist = new int[N][N];   // 사용자 간 최단 거리 저장 배열

            // 네트워크 입력받기
            for (int n = 0; n < N; n++) {
                arrList[n] = new ArrayList<>(); // 인접 리스트의 각 ArrayList 초기화

                for (int c = 0; c < N; c++) {
                    int input = sc.nextInt();
                    if (input == 1) {
                        arrList[n].add(c);
                    }
                }
            }

            int minCC = Integer.MAX_VALUE;  // 최소 CC 값
            // 모든 사용자의 CC값을 구해 최솟값 구하기
            for (int i = 0; i < N; i++) {
                // 사용자 i의 CC 값 구하기
                int CC = 0; // 사용자 i의 CC
                for (int j = 0; j < N; j++) {
                    if (i >= j) {
                        CC += dist[i][j];
                    } else {
                        int d = BFS(i, j);  // 사용자 i와 j의 최단거리 구하기
                        dist[i][j] = d;
                        dist[j][i] = d;
                        CC += d;
                    }
                }

                // CC 최솟값 업데이트
                minCC = Math.min(minCC, CC);
            }
            System.out.println("#" + t + " " + minCC);
        }
        sc.close();
    }

    // u1과 u2 사이의 최단거리 구하는 메서드
    private static int BFS(int u1, int u2) {
        Queue<Node> que = new LinkedList<>();
        boolean[] visited = new boolean[N];
        que.add(new Node(u1, 0));
        visited[u1] = true;

        while (!que.isEmpty()) {
            Node cur = que.poll();
            visited[cur.node] = true;

            for (int next: arrList[cur.node]) {
                if (next == u2) {
                    return cur.dist + 1;
                }

                if (!visited[next]) {
                    que.add(new Node(next, cur.dist + 1));
                }
            }
        }

        return 0;
    }

    private static class Node {
        int node;
        int dist;

        private Node(int node, int dist) {
            this.node = node;
            this.dist = dist;
        }
    }
}
