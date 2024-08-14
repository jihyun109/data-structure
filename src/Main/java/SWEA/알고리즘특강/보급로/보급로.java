package SWEA.알고리즘특강.보급로;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/보급로/input.txt"));

        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            int N = sc.nextInt();   // 지도의 크기
            int[][] map = new int[N][N];
            for (int i = 0; i < N; i++) {
                String input = sc.next();
                for (int j = 0; j < N; j++) {
                    map[i][j] = input.charAt(j) - '0';
                }
            }

            // BFS
            PriorityQueue<Node> pq = new PriorityQueue<>();
            boolean[][] visited = new boolean[N][N];
            int[] dx = new int[]{-1, 1, 0, 0};
            int[] dy = new int[]{0, 0, -1, 1};
            pq.add(new Node(0, 0, 0));

            while (!pq.isEmpty()) {
                Node cur = pq.poll();
                int curX = cur.x;
                int curY = cur.y;
                visited[curX][curY] = true;

                if (curX == N - 1 && curY == N - 1) {
                    System.out.println("#" + test_case + " " + cur.accT);
                    break;
                }

                for (int i = 0; i < 4; i++) {
                    int nextX = cur.x + dx[i];
                    int nextY = cur.y + dy[i];

                    if (nextX >= 0 && nextX < N && nextY >= 0 && nextY < N) {
                        if (!visited[nextX][nextY]) {
                            pq.add(new Node(nextX, nextY, cur.accT + map[nextX][nextY]));
                        }
                    }
                }
            }
        }
    }

    private static class Node implements Comparable<Node> {
        int x;
        int y;
        int accT;   // 누적 시간

        private Node(int x, int y, int accT) {
            this.x = x;
            this.y = y;
            this.accT = accT;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.accT, o.accT);
        }
    }
}