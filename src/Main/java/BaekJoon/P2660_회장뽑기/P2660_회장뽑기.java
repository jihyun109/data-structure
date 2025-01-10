package BaekJoon.P2660_회장뽑기;

import java.util.*;

public class P2660_회장뽑기 {
    private static int memberN;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        memberN = sc.nextInt(); // 회원의 수

        // 회원 정보 입력받기.
        int[][] relations = new int[memberN + 1][memberN + 1];   // relations[i][j]: 회원 i와 j의 관계 (1: 친구, 2: 친구의 친구..)

        for (int i = 1; i <= memberN; i++) {    // relations 배열 큰 수로 채우기.
            Arrays.fill(relations[i], 10000001);
            relations[i][i] = 0;
        }
        while (true) {
            int m1 = sc.nextInt();  // 회원1
            int m2 = sc.nextInt();  // 회원2

            if (m1 == -1) {
                break;
            }

            relations[m1][m2] = 1;
            relations[m2][m1] = 1;
        }

        sc.close();

        // 회원 관계 배열 채우기
        findRel(relations);

        // 회장 후보의 점수와 모든 회장 후보 구하기
        HashMap<Integer, Queue<Integer>> scores = new HashMap<>();  // key: 점수, value: 점수에 해당하는 회원 리스트
        int presidentScore = findScores(relations, scores); // 회원들의 점수를 구하고, 회장 후보의 점수 return
        Queue<Integer> presidents = scores.get(presidentScore);

        // 답 출력
        System.out.println(presidentScore + " " + presidents.size());
        while (!presidents.isEmpty()) {
            System.out.print(presidents.poll() + " ");
        }
    }

    private static void findRel(int[][] relations) {
        for (int k = 1; k <= memberN; k++) {
            for (int i = 1; i <= memberN; i++) {
                for (int j = i + 1; j <= memberN; j++) {
                    relations[i][j] = relations[j][i] = Math.min(relations[i][k] + relations[k][j], relations[i][j]);
                }
            }
        }
    }

    private static int findScores(int[][] relations, HashMap<Integer, Queue<Integer>> scores) {
        // 모든 회원의 점수 구하기
        int minScore = Integer.MAX_VALUE;   // 회원 점수의 최솟값 (회장 후보의 점수)
        for (int m1 = 1; m1 <= memberN; m1++) {
            int score = 0;  // m1의 점수

            for (int m2 = 1; m2 <= memberN; m2++) {
                int rel = relations[m1][m2];    // m1과 m2의 관계
                score = Math.max(rel, score);
            }

            scores.put(score, scores.getOrDefault(score, new LinkedList<>()));
            scores.get(score).add(m1);
            minScore = Math.min(score, minScore);
        }

        return minScore;
    }
}
