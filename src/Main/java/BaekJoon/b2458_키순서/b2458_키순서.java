package BaekJoon.b2458_키순서;

import java.util.*;
import java.io.*;

public class b2458_키순서 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int studentN = Integer.parseInt(st.nextToken());
        int queryN = Integer.parseInt(st.nextToken());

        boolean[][] relations = new boolean[studentN + 1][studentN + 1];
        for (int i = 0; i < queryN; i++) {
            st = new StringTokenizer(br.readLine());
            int smallStudent = Integer.parseInt(st.nextToken());
            int bigStudent = Integer.parseInt(st.nextToken());

            relations[smallStudent][bigStudent] = true;
        }

        br.close();

        floydWarshall(relations, studentN);

//		print(relations, studentN);

        int ret = findReturn(relations, studentN);

        System.out.println(ret);
    }

    private static void floydWarshall(boolean[][] arr, int N) {
        for (int k = 1; k <= N; k++) {
            for (int s = 1; s <= N; s++) {
                for (int e = 1; e <= N; e++) {
                    if (arr[s][k] == true && arr[k][e] == true) {
                        arr[s][e] = true;
                    }
                }
            }
        }
    }

    private static int findReturn(boolean[][] arr, int N) {
        int cnt = 0;

        for (int i = 1; i <= N; i++) {
            boolean canAware = true;

            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    continue;
                }

                if (arr[i][j] == false && arr[j][i] == false) {
                    canAware = false;
                    break;
                }
            }

            if (canAware) {
                cnt++;
            }
        }

        return cnt;
    }

    private static void print(boolean[][] arr, int N) {
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                System.out.print(arr[i][j] ? "t " : "f ");
            }
            System.out.println();
        }
    }
}
