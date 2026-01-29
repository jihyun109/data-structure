package BaekJoon.b3980_선발명단;

import java.io.*;
import java.util.*;

public class b3980_선발명단 {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcaseN = Integer.parseInt(br.readLine());

        // 모든 테스트 케이스 수행
        for (int t = 0; t < testcaseN; t++) {
            int[][] maxAbilities = new int[(1 << 11)][12];
            List<Integer> availablePos = new LinkedList<>();

            // 모든 선수의 포지션별 능력치 입력받기
            for (int player = 1; player <= 11; player++) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                // 능력치가 있는 포지션 찾기
                List<Integer> newAvailablePos = new LinkedList<>();
                for (int pos = 0; pos < 11; pos++) {
                    int ability = Integer.parseInt(st.nextToken());    // 능력치

                    // 능력치가 있는 경우
                    if (ability != 0) {
                        // 첫번째 선수인 경우
                        if (player == 1) {
                            int afterPos = putInPos(0, pos);
                            maxAbilities[afterPos][player] = ability;
                            availablePos.add(afterPos);
                            continue;
                        }

                        // 첫번째 선수가 아닌 경우
                        // 1. 이전에서 현재 포지션이 비어있고, 값이 있는 모든 경우에 max값 갱신
                        for (int beforePos : availablePos) {
                            // 현재 포지션이 비어있는 경우
                            if ((beforePos & (1 << pos)) == 0) {
                                int afterPos = putInPos(beforePos, pos);
                                maxAbilities[afterPos][player] = Math.max(maxAbilities[beforePos][player - 1] + ability, maxAbilities[afterPos][player]);
                                newAvailablePos.add(afterPos);
                            }
                        }
                    }
                }
                if (player != 1) {
                    availablePos = newAvailablePos;
                }
            }

            // 최댓값 찾기
            int max = 0;
            for (int pos : availablePos) {
                max = Math.max(max, maxAbilities[pos][11]);
            }
            bw.append(max + "\n");

        }

        br.close();
        bw.flush();
        bw.close();

    }

    // beforePos(이전 포지션 상태)에서 pos자리에 선수 추가
    private static int putInPos(int beforePos, int pos) {
        return (beforePos | (1 << pos));
    }
}
