package SWEA.알고리즘특강.하나로;

import java.util.PriorityQueue;
import java.util.Scanner;
import java.io.FileInputStream;

class Solution2
{
    private static int[] parent;
    public static void main(String args[]) throws Exception
    {
//        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/하나로/re_sample_input.txt"));
        Scanner sc = new Scanner(System.in);
        int T;
        T=sc.nextInt();

        for(int test_case = 1; test_case <= T; test_case++)
        {
            int N = sc.nextInt();
            Location islands[] = new Location[N];
            parent = new int[N];

            // 섬 위치 정보 입력 받기
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                islands[i] = new Location(sc.nextInt(), 0);
            }
            for (int i = 0; i < N; i++) {
                islands[i].y = sc.nextInt();
            }

            double E = sc.nextDouble(); // 환경 부담 세율

            // 모든 섬과 섬 사이의 거리를 구해 pq에 저장
            PriorityQueue<Edge> pq = new PriorityQueue<>();
            for (int i = 0; i < N; i++) {
                for (int j = i + 1; j < N; j++) {
                    double dist = (Math.pow(islands[i].x - islands[j].x, 2) + Math.pow(islands[i].y - islands[j].y, 2)); // 거리의 제곱
                    pq.add(new Edge(i, j, dist));
                }
            }

            // 연결되지 않은 섬 연결
            double fee = 0;
            while (!pq.isEmpty()) {
                Edge e = pq.poll();
                int p1 = find(e.i1);
                int p2 = find(e.i2);

                if (p1 != p2) {
                    union(e.i1, e.i2);
                    fee += e.dist;
                }
            }

            System.out.println("#" + test_case + " " + Math.round(fee * E));
        }
    }

    private static void union(int i1, int i2) {
        int p1 = find(i1);
        int p2 = find(i2);

        if (p1 < p2) {
            parent[p2] = p1;
        } else if (p1 > p2) {
            parent[p1] = p2;
        }
    }

    private static int find(int i) {
        if (parent[i] == i) {
            return i;
        }

        return parent[i] = find(parent[i]);
    }

    private static class Edge implements Comparable<Edge>{
        int i1;
        int i2;
        double dist;
        private Edge(int i1, int i2, double dist) {
            this.i1 = i1;
            this.i2 = i2;
            this.dist = dist;
        }

        @Override
        public int compareTo(Edge o) {
            if (this.dist - o.dist > 0) {
                return 1;
            } else if (this.dist - o.dist < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private static class Location {
        int x;
        int y;
        private Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}