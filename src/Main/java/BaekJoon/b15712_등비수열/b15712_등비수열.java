package BaekJoon.b15712_등비수열;

import java.util.*;
import java.io.*;

public class b15712_등비수열 {
    private static long mod;
    private static long r;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long a = Long.parseLong(st.nextToken());
        r = Long.parseLong(st.nextToken());
        long n = Long.parseLong(st.nextToken());
        mod = Long.parseLong(st.nextToken());

        long answer = findAnswer(a, n);

        System.out.println(answer);

        br.close();
    }

    private static long findAnswer(long a, long n) {
        long answer = a % mod * solve(n);

        return answer % mod;
    }

    // 등비가 r이고 수열의 길이가 n인 등비수열의 합 구하기
    private static long solve(long n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        long ret = 0;

        // 수열의 길이가 홀수인 경우
        if (n % 2 == 1) {
            ret += power(n - 1);
        }

        ret += solve(n / 2) * (power(n / 2) + 1);

        return ret % mod;
    }

    // r의 n제곱 구하기
    private static long power(long n) {
        long ret = 1;
        long tmp = r;

        if (n == 0) {
            return 1;
        } else if (n == 1) {
            return r;
        }

        while (n > 1) {
            if (n % 2 == 1) {
                ret = ret * tmp % mod;
            }
            tmp = tmp * tmp % mod;
            n /= 2;
        }

        return ret * tmp % mod;
    }
}