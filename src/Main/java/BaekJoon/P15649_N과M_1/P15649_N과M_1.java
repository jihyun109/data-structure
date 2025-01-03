package BaekJoon.P15649_N과M_1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P15649_N과M_1 {
    static int N;
    static int M;
    static Queue<int[]> seqs;   // 출력할 수열 저장 큐
    static boolean[] visited;   // 방문 처리 배열

    public static void main(String[] args) {
        seqs = new LinkedList<>();

        // N과 M 입력받기
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        sc.close();
        visited = new boolean[N + 1];

        DFS(new int[N], 0);

        // 수열 출력
        while (!seqs.isEmpty()) {
            int[] seq = seqs.poll();
            for (int i = 0; i < M; i++) {
                System.out.print(seq[i] + " ");
            }
            System.out.println();
        }
    }

    static void DFS(int[] sequence, int cnt) {
        if (cnt == M) { // 수열이 완성된 경우
            seqs.add(sequence.clone());
            return;
        }

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {  // 수 중복 방지
                sequence[cnt++] = i;    // 수열에 수 넣기
                visited[i] = true;
                DFS(sequence, cnt);

                visited[i] = false;
                sequence[--cnt] = 0;
            }
        }
    }
}
