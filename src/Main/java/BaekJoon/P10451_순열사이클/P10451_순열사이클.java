package BaekJoon.P10451_순열사이클;

import java.io.*;
import java.util.StringTokenizer;

public class P10451_순열사이클 {
    private static boolean[] visited;
    private static int[] arr;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());    // 테스트 케이스 수

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int t = 0; t < T; t++) {   // 모든 테스트케이스 수행
            int N = Integer.parseInt(br.readLine());    // 순열의 수

            // 순열 입력 받기
            arr = new int[N + 1]; // 순열 저장 배열
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            // 순열 사이클 수 구하기
            visited = new boolean[N + 1]; // 방문 처리 배열
            int cnt = 0;    // 순열 사이클 수
            for (int i = 1; i <= N; i++) {
                if (!visited[i]) {
                    DFS(i);
                    cnt++;
                }
            }
            bw.write(cnt + "\n");
        }
        // 답 출력
        bw.flush();
        bw.close();
        br.close();
    }

    private static void DFS(int i) {
        visited[i] = true;  // 현재 수 방문처리
        int next = arr[i];

        while (true) {  // 이미 방문한 수가 나올 때까지 반복
            if (visited[next]) {
                return;
            }

            visited[next] = true;
            next = arr[next];
        }
    }
}
