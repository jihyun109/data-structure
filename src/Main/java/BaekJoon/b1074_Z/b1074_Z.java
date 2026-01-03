package BaekJoon.b1074_Z;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class b1074_Z {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int answer = findAnswer(N, r, c);

        System.out.println(answer);
        br.close();
    }

    private static int findAnswer(int N, int r, int c) {
        int sR = 0; int sC = 0;
        int eR = (int) (Math.pow(2, N) - 1); int eC = (int) (Math.pow(2, N) - 1);
        int cnt = 0;

        while (sR != eR || sC != eC) {
            int mR = ((sR + eR) / 2) + 1;
            int mC = ((sC + eC) / 2) + 1;
            int n = ((eR - sR + 1) * (eR - sR + 1)) / 4;

            if (r >= sR && r < mR && c >= sC && c < mC) {
                eR = mR - 1;
                eC = mC - 1;
            } else if (r >= sR && r < mR && c >= mC && c <= eC) {
                eR = mR - 1;
                sC = mC;
                cnt += n;
            } else if (r >= mR && r <= eR && c >= sC && c < mC) {
                sR = mR;
                eC = mC - 1;
                cnt += n * 2;
            } else {
                sR = mR;
                sC = mC;
                cnt += n * 3;
            }
        }

        return cnt;
    }
}
