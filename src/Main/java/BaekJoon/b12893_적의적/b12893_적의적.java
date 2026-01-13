package BaekJoon.b12893_적의적;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b12893_적의적 {
    private static int[] enemy;
    private static int[] friend;

    public static void main(String[] args) throws IOException {
        // 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int peopleN = Integer.parseInt(st.nextToken());
        int relationN = Integer.parseInt(st.nextToken());

        boolean isPoss = true;
        enemy = new int[peopleN + 1];
        friend = initFriend(peopleN);

        for (int r = 0; r < relationN; r++) {
            st = new StringTokenizer(br.readLine());

            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            // A와 B가 친구사이인지 확인
            int friendA = find(A);
            int friendB = find(B);

            // 친구사이인 경우
            if (friendA == friendB) {
                isPoss = false;
                break;
            }

            // A와 B가 친구가 아닌 경우
            int enemyA = enemy[A];
            int enemyB = enemy[B];

            if (enemyA != 0) {
                union(enemyA, B);
            } else if (enemyB != 0) {
                union(enemyB, A);
            }

            enemy[A] = B;
            enemy[B] = A;
        }

        br.close();

        // 답 출력
        System.out.println(isPoss ? 1 : 0);
    }

    private static int[] initFriend(int peopleN) {
        int[] ret = new int[peopleN + 1];

        for (int i = 1; i <= peopleN; i++) {
            ret[i] = i;
        }
        return ret;
    }

    private static int find(int child) {
        int parent = friend[child];

        if (parent == child) {
            return  parent;
        }

        return friend[child] = find(parent);
    }

    private static void union(int A, int B) {
        int parentA = find(A);
        int parentB = find(B);

        friend[parentA] = friend[parentB] = Math.min(parentA, parentB);
    }
}
