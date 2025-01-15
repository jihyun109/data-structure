package BaekJoon.P9205_맥주마시면서걸어가기;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class P9205_맥주마시면서걸어가기_DFS_시간초과 {
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
            boolean canHappy = canHappy();

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

    // 행복하게 다녀올 수 있는지 알아보는 메서드
    private static boolean canHappy() {

        boolean[] visited = new boolean[storeN];    // visited[i]: i번째 편의점을 방문했는지

//        return DFS(home, visited);
        return BFS();
    }

    private static boolean BFS() {
        Queue<Status> statuses = new LinkedList<>();
        // 초기값 넣기
        for (int i = 0; i < storeN; i++) {
            if (calculateDist(home, stores[i]) <= maxBeerDist) {
                statuses.add(new Status(i, new boolean[storeN]));
            }
        }

        while (!statuses.isEmpty()) {
            Status curStatus = statuses.poll();
            int curStoreN = curStatus.getCurStoreN();   // 현재 편의점 번호
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
                if (next == cur || curStatus.getVisited()[n] || dist > maxBeerDist) {
                    continue;
                }

                // maxBeerDist 내로 cur 에서 next 로 이동할 수 있는 경우
                curStatus.getVisited()[n] = true;
                statuses.add(new Status(n, curStatus.getVisited().clone()));
            }
        }

        return false;
    }

    private static boolean DFS(Coordinate cur, boolean[] visited) {
        // 현재 위치에서 페스티벌까지의 거리가 1,000 이하이면 바로 페스티벌로 이동
        int distToFestival = calculateDist(cur, festival);   // cur 에서 festival 까지의 거리
        if (distToFestival <= maxBeerDist) {
            return true;
        }

        // 다른 편의점 방문
        for (int n = 0; n < storeN; n++) {
            Coordinate next = stores[n];    // 다음 방분할 편의점
            int dist = calculateDist(cur, next);    // cur 과 next 사이의 거리


            // next 편의점이 cur 과 같거나 이미 방문했거나 maxBeerDist 내로 cur 에서 next 로 이동할 수 없으면 다른 편의점 찾기.
            if (next == cur || visited[n] || dist > maxBeerDist) {
                continue;
            }

            // maxBeerDist 내로 cur 에서 next 로 이동할 수 있는 경우
            visited[n] = true;

            if (DFS(next, visited)) {   // DFS 를 해한 후 true 가 return 되었다면 return true
                return true;
            }
            visited[n] = false;
        }

        return false;
    }

    // 두 좌표 사이의 거리 계산 메서드
    private static int calculateDist(Coordinate c1, Coordinate c2) {
        return Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
    }
}


class Coordinate {
    int x;
    int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Status {
    private int curStoreN;  // 현재 편의점 번호
    private boolean[] visited;  // 방문 처리 배열

    public int getCurStoreN() {
        return curStoreN;
    }

    public boolean[] getVisited() {
        return visited;
    }

    public void setCurStoreN(int curStoreN) {
        this.curStoreN = curStoreN;
    }

    public void setVisited(boolean[] visited) {
        this.visited = visited;
    }

    public Status(int curStoreN, boolean[] visited) {
        this.curStoreN = curStoreN;
        this.visited = visited;
    }
}
