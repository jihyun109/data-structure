package BaekJoon.P1969_DNA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class P1969_DNA {
    private static int M;
    private static char[][] dna;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // DNA 수
        M = Integer.parseInt(st.nextToken());   // 문자열의 길이

        // DNA 입력받기
        dna = new char[N][M]; // dna 저장 배열 (dna[i][j]: i번째 dna 의 j번째 값
        for (int i = 0; i < N; i++) {
            dna[i] = br.readLine().toCharArray();
        }
        br.close();

        // HD의 합이 최소인 DNA 와 그 합 구하기
        char[] minHdDna = new char[M];  // HD의 합이 최소인 DNA
        int hd = 0;

        for (int i = 0; i < M; i++) {
            // 모든 dna 에서 i 번쨰에 오는 뉴클레오티드의 개수 구하기
            HashMap<Character, Integer> atgcN = new HashMap<>(4);   // 모든 DNA에서 i번째 자리의 ATGC 개수 저장 맵
            for (int dnaN = 0; dnaN < N; dnaN++) {
                char kind = dna[dnaN][i];   // dnaN 번째 dna 의 i번째 뉴클레오 티드
                atgcN.put(kind, atgcN.getOrDefault(kind, 0) + 1);
            }

            // 개수가 제일 많은 뉴클레오티드 구하기.
            char maxNucl = Character.MAX_SURROGATE;   // 개수가 제일 많은 뉴클레오티드
            int maxN = 0;    // 개수가 제일 많은 뉴클레오티드의 개수
            for (Map.Entry entry : atgcN.entrySet()) {
                char curNucl = (char) entry.getKey();
                int nuclN = (int) entry.getValue();

                if (nuclN > maxN) {
                    maxNucl = curNucl;
                    maxN = nuclN;
                } else if (nuclN == maxN) {
                    if (curNucl < maxNucl) {
                        maxNucl = curNucl;
                    }
                }
            }

            minHdDna[i] = maxNucl;
            // hd 구하기
            for (Map.Entry entry : atgcN.entrySet()) {
                char curNucl = (char) entry.getKey();
                if (maxNucl != curNucl) {
                    hd += atgcN.getOrDefault(curNucl, 0);
                }
            }
        }

        System.out.println(new String(minHdDna));
        System.out.println(hd);
    }
}
