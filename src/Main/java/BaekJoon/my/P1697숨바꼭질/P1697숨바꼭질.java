package my.P1697숨바꼭질;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P1697숨바꼭질 {

    static int subin;
    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        subin = sc.nextInt();
        int sister = sc.nextInt();

        sc.close();

        int answer = BFS(sister);
        System.out.println(answer);
    }

    private static int BFS(int sister) {
        if (subin == sister) {
            return 0;
        }

        Queue<Subin> que = new LinkedList<>();
        boolean visited[] = new boolean[100001];

        que.add(new Subin(subin, 0));
        visited[subin] = true;

        while (!que.isEmpty()) {
            Subin now = que.poll();
            int nowSubin = now.x;
            int nowSecond = now.second;

            int nextSecond = nowSecond + 1;

            // 수빈 x-1 로 이동
            int next = nowSubin - 1;

            if (next == sister) {
                return nextSecond;
            }

            if (next >= 0 && next <= 100000 && visited[next] == false) {
                que.add(new Subin(next, nextSecond));
                visited[next] = true;
            }

            // 수빈 x+1 로 이동
            next = nowSubin + 1;

            if (next == sister) {
                return nextSecond;
            }

            if (next >= 0 && next <= 100000 && visited[next] == false) {
                que.add(new Subin(next, nextSecond));
                visited[next] = true;
            }

            // 수빈 x-1 로 이동
            next = nowSubin * 2;

            if (next == sister) {
                return nextSecond;
            }

            if (next >= 0 && next <= 100000 && visited[next] == false) {
                que.add(new Subin(next, nextSecond));
                visited[next] = true;
            }
        }

        return 0;
    }

    static class Subin {
        int x;
        int second;

        Subin(int x, int second) {
            this.x = x;
            this.second = second;
        }
    }
}
