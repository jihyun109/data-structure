package SWEA.S1206_View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class S1206_View {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int tc = 1; tc <= 10; tc++) {
            // 입력받기
            int buildingN = Integer.parseInt(br.readLine());
            int[] heights = new int[buildingN];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < buildingN; i++) {
                heights[i] = Integer.parseInt(st.nextToken());
            }

            // max값 찾기
            int answer = 0;
            for (int cur = 2; cur < buildingN - 2; cur++) {
                int lH1 = heights[cur - 2];
                int lH2 = heights[cur - 1];
                int rH1 = heights[cur + 1];
                int rH2 = heights[cur + 2];
                int curH = heights[cur];

                int max = Math.max(lH1, lH2);
                max = Math.max(rH1, max);
                max = Math.max(max, rH2);

                if (max < curH) {
                    answer += curH - max;
                }
            }

            bw.append("#" + tc + " " + answer + '\n');

        }

        br.close();
        bw.close();

    }

}
