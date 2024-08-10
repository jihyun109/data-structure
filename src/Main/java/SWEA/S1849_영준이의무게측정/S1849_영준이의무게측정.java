package SWEA.S1849_영준이의무게측정;

import java.util.*;

public class S1849_영준이의무게측정 {
    private static int[] parent;
    private static int[] weights;
    private static int[] ranks;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int testCaseN = sc.nextInt();

        for (int t = 1; t <= testCaseN; t++) {
            int N = sc.nextInt();   // 샘플 종류의 개수
            int M = sc.nextInt();   // 영준이가 해야 하는 일 개수
            parent = new int[N + 1];  // 대표 노드 저장 배열
            weights = new int[N + 1];  // 대표 노드와의 상대 무게 저장 배열
            ranks = new int[N + 1]; // 대표 노드일 때 트리의 높이 저장 배열

            for (int i = 1; i <= N; i++) {
                parent[i] = i;
                weights[i] = ranks[i] = 0;
            }

            System.out.print("#" + t + " ");

            // 일 수행
            for (int m = 0; m < M; m++) {
                String work = sc.next();    // 일의 종류
                int sample1 = sc.nextInt();
                int sample2 = sc.nextInt();

                if (work.equals("!")) {
                    int weight = sc.nextInt();
                    union(sample1, sample2, weight);
                } else {
                    if (find(sample1) == find(sample2)) {   // 무게 차를 구할 수 있으면
                        int diff = weights[sample2] - weights[sample1]; // 두 샘플의 무게 차
                        System.out.print(diff + " ");
                    } else {
                        System.out.print("UNKNOWN ");
                    }
                }
            }
            System.out.println();
        }

        sc.close();
    }

    private static void union(int a, int b, int weight) {
        int pa = find(a);
        int pb = find(b);

        if (pa != pb) {
            if (ranks[pa] < ranks[pb]) {
                parent[pa] = pb;
                weights[pa] = weights[b] - weight - weights[a];
            } else {
                parent[pb] = pa;
                weights[pb] = weights[a] + weight - weights[b];
                if (ranks[pa] == ranks[pb]) {
                    ranks[pa]++;
                }
            }
        }
    }

    private static int find(int s) {
        if (parent[s] == s) {
            return s;
        }

        int p = find(parent[s]);

        if (p != parent[s]) {
            weights[s] += weights[parent[s]];
        }
        return parent[s] = p;
    }
}
