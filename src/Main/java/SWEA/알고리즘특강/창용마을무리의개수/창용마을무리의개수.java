package SWEA.알고리즘특강.창용마을무리의개수;

import java.util.Scanner;

class Solution {
    static int[] parent;

    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            int N = sc.nextInt();   // 사람 수
            int M = sc.nextInt();   // 관계 수
            parent = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                parent[i] = i;
            }

            // 관계 입력
            for (int i = 0; i < M; i++) {
                int n1 = sc.nextInt();
                int n2 = sc.nextInt();

                union(n1, n2);
            }

            // 무리의 수 세기
            int groupN = 0;
            boolean[] group = new boolean[N + 1];   // 어느 무리의 대표이면 true 로 표시
            for (int i = 1; i <= N; i++) {
                int p = find(i);
                if (!group[p]) {
                    group[p] = true;
                    groupN++;
                }
            }

            System.out.println("#" + test_case + " " + groupN);
        }
    }

    static private void union(int n1, int n2) {
        int p1 = find(n1);
        int p2 = find(n2);

        if (p1 < p2) {
            parent[p2] = p1;
        } else if (p1 > p2) {
            parent[p1] = p2;
        }
    }

    static private int find(int p) {
        if (parent[p] == p) {
            return p;
        }
        return parent[p] = find(parent[p]);
    }
}