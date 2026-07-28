package SWEA.S1204_최빈수구하기;

import java.io.*;
import java.util.*;

public class S1204_최빈수구하기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcaseN = Integer.parseInt(br.readLine());

        for (int t = 1; t <= testcaseN; t++) {
            int tc = Integer.parseInt(br.readLine());

            int[] frequency = new int[101];

            // 각 점수의 빈도 수 구하기
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 1000; i++) {
                int score = Integer.parseInt(st.nextToken());
                frequency[score]++;
            }

            // 가장 빈도수가 높은 점수 구하기
            int maxFrequency = 0;
            int score = 0;
            for (int i = 0; i <= 100; i++) {
                if (frequency[i] >= maxFrequency) {
                    maxFrequency = frequency[i];
                    score = i;
                }
            }

            bw.append("#" + t + " " + score + '\n');
        }

        br.close();
        bw.flush();
        bw.close();

    }

}
