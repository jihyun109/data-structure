package BaekJoon.P9205_맥주마시면서걸어가기_BFS;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P9205_맥주마시면서걸어가기_BFS {
    private final static int maxBeerDist = 1000; // 한 박스의 맥주로 갈 수 있는 최대 거리
    private static Coordinate home;
    private static Coordinate festival;
    private static Coordinate[] stores;
    private static int storeN;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int testcaseN = Integer.parseInt(br.readLine());    // 테스트케이스 수

        for (int t = 0; t < testcaseN; t++) {
            // 정보 입력받기
            storeN = Integer.parseInt(br.readLine()); // 편의점 수

            // 집 좌표 입력받기
            StringTokenizer st = new StringTokenizer(br.readLine());
            int homeX = Integer.parseInt(st.nextToken());
            int homeY = Integer.parseInt(st.nextToken());
            home = new Coordinate(homeX, homeY);

            // 편의점 정보 입력받기
            stores = new Coordinate[storeN];   // 편의점 좌표 저장 배열
            for (int i = 0; i < storeN; i++) {
                st = new StringTokenizer(br.readLine());

                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                stores[i] = new Coordinate(x, y);
            }

            // 페스티벌 좌표 입력받기
            st = new StringTokenizer(br.readLine());
            int festivalX = Integer.parseInt(st.nextToken());
            int festivalY = Integer.parseInt(st.nextToken());
            festival = new Coordinate(festivalX, festivalY);

            // 행복할 수 있는지 알아보기
            boolean canHappy = bfs();

            // 결과 저장
            if (canHappy) {
                bw.write("happy" + '\n');
            } else {
                bw.write("sad" + '\n');
            }
        }

        // 답 출력
        bw.flush();
        bw.close();
        br.close();
    }

    private static boolean bfs() {
        Queue<Integer> statuses = new LinkedList<>();
        boolean[] visited = new boolean[storeN];

        // 집에서 페스티벌까지 바로 갈 수 있으면 return true
        if (calculateDist(home, festival) <= maxBeerDist) {
            return true;
        }

        // 초기값 넣기 (집에서 출발)
        for (int i = 0; i < storeN; i++) {
            if (calculateDist(home, stores[i]) <= maxBeerDist) {
                statuses.add(i);
                visited[i] = true;
            }
        }

        while (!statuses.isEmpty()) {
            int curStoreN = statuses.poll();   // 현재 편의점 번호
            Coordinate cur = stores[curStoreN]; // 현재 편의점 좌표

            // 현재 위치에서 페스티벌까지의 거리가 1,000 이하이면 바로 페스티벌로 이동
            int distToFestival = calculateDist(cur, festival);   // cur 에서 festival 까지의 거리
            if (distToFestival <= maxBeerDist) {
                return true;
            }

            // 다른 편의점 거치기
            for (int n = 0; n < storeN; n++) {
                Coordinate next = stores[n];    // 다음 방분할 편의점
                int dist = calculateDist(cur, next);    // cur 과 next 사이의 거리

                // next 편의점이 cur 과 같거나 이미 방문했거나 maxBeerDist 내로 cur 에서 next 로 이동할 수 없으면 다른 편의점 찾기.
                if (next == cur || visited[n] || dist > maxBeerDist) {
                    continue;
                }

                // maxBeerDist 내로 cur 에서 next 로 이동할 수 있는 경우
                visited[n] = true;
                statuses.add(n);
            }
        }

        return false;
    }

    private static int calculateDist(Coordinate c1, Coordinate c2) {

        return Math.abs(c1.getX() - c2.getX()) + Math.abs(c1.getY() - c2.getY());
    }
}

class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
