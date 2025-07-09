package BaekJoon.P2933_미네랄;

import java.io.*;
import java.security.cert.PolicyNode;
import java.util.*;

public class P2933_미네랄 {
    private static char[][] cave;
    private static int R;
    private static int C;
    private static int[] dx = new int[]{1, -1, 0, 0};
    private static int[] dy = new int[]{0, 0, 1, -1};
    private static boolean throwToRight;

    public static void main(String[] args) throws IOException {
        // 정보 입력받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        throwToRight = true;

        cave = new char[R + 1][C];    // 동굴 정보 입력받기
        for (int i = R; i > 0; i--) {
            String str = br.readLine();
            for (int j = 0; j < C; j++) {
                cave[i] = str.toCharArray();
            }
        }

        int N = Integer.parseInt(br.readLine());    // 막대를 던진 횟수
        Queue<Integer> stickHeights = new LinkedList<>();   // 막대를 던진 높이 저장
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            stickHeights.add(Integer.parseInt(st.nextToken()));
        }

        br.close();

        // cave 에서 stickHeights 의 막대를 다 던졌을 때 최종적으로 나오는 미네랄 모양 구하기
        findFinalMinerals(stickHeights);

        // 답 출력
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int r = R; r > 0; r--) {
            for (int c = 0; c < C; c++) {
                bw.write(cave[r][c]);
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
    }

    // 최종적으로 나오는 미네랄 모양 구하기
    private static void findFinalMinerals(Queue<Integer> stickHeights) {
        // 모든 막대 던지고 결과 cave 에 넣어서 전달
        throwToRight = true;  // 어디서 막대를 던지는지 나타냄
        while (!stickHeights.isEmpty()) {   // 모든 막대를 던질 떄까지 반복
            int height = stickHeights.poll();

            // 막대를 현재 던져야하는 방향으로 height 의 높이에서 던져 미네랄 파괴
            Point deletedPoint = removeMineral(height);   // 삭제된 미네랄의 좌표
            printCave("removeMineral");
            throwToRight = !throwToRight;


            // 미네랄을 파괴한 경우 클러스터가 분리되었는지 확인
            if (deletedPoint != null) {
                Set<Point> seperatedMinerals = findSeperatedCluster(deletedPoint);    // 분리된 미네랄들의 좌표

                // 분리된 클러스터가 없는 경우
                if (seperatedMinerals == null) {
                    continue;
                }

                // 분리된 클러스터 재위치시키기
                seperatedMinerals = new TreeSet<>(seperatedMinerals);
                relocateCluster(seperatedMinerals);
                printCave("relocate");


//                // 다음 turn 에 던질 막대의 방향 지정
//                throwToRight = !throwToRight;
            }
        }
    }

    // deletedPoint 가 제거되고 분리된 클러스터를 구성하는 좌표들 찾기
    private static Set<Point> findSeperatedCluster(Point deletedPoint) {
        // deletedPoint 의 네 방향에서 BFS 를 사용해 탐색. 탐색 중 바닥에 닿으면 분리된 클러스터가 아님.

        for (int d = 0; d < 4; d++) {
            int x = deletedPoint.getX() + dx[d];    // BFS 를 시작할 좌표
            int y = deletedPoint.getY() + dy[d];
            if (x > 0 && x <= R && y >= 0 && y < C) {
                if (cave[x][y] == 'x') {
                    Set<Point> seperatedCluster = BFS(x, y);
                    if (seperatedCluster != null) {
                        return seperatedCluster;
                    }
                }
            }
        }
        return null;
    }

    // (x, y)에서 BFS. BFS 도중 바닥에 닿으면 return
    private static Set<Point> BFS(int x, int y) {
        Queue<Point> que = new LinkedList<>();
        boolean[][] visited = new boolean[R + 1][C];
        que.add(new Point(x, y));
        visited[x][y] = true;
        Set<Point> constructionOfCluster = new HashSet<>();    // 현재 클러스터를 구성하는 미네랄 좌표 저장 배열
        constructionOfCluster.add(new Point(x, y));

        while (!que.isEmpty()) {
            Point cur = que.poll();

            for (int i = 0; i < 4; i++) {
                int nextX = cur.getX() + dx[i];
                int nextY = cur.getY() + dy[i];
                if (nextX > 0 && nextX <= R && nextY >= 0 && nextY < C) {   // next 좌표가 범위 안인지 확인
                    if (nextX == 1) {   // 현재 좌표가 동굴의 맨 밑에 위치하면 이 클러스터는 분리된 클러스터가 아님.
                        return null;
                    }
                    if (!visited[nextX][nextY] && cave[nextX][nextY] == 'x') {   // next 좌표에 방문하지 않았고 미네랄이 있으면 현재 좌표로 이동
                        Point next = new Point(nextX, nextY);
                        constructionOfCluster.add(next);
                        que.add(next);
                        visited[nextX][nextY] = true;
                    }
                }
            }
        }

        return constructionOfCluster;
    }

    // 분리된 클러스터 재위치시키기
    private static void relocateCluster(Set<Point> seperatedMinerals) {
        // 모든 미네랄에서 다른 클러스터와의 최소 높이 차이 찾기
        int min = findMinDist(seperatedMinerals);

        // 모든 미네랄 min 만큼 아래로 이동
        if (min >= 0) {
            for (Point p : seperatedMinerals) {
                int x = p.getX();
                int y = p.getY();
                cave[x][y] = '.';
                cave[x - min][y] = 'x';
            }
        }
    }

    private static int findMinDist(Set<Point> seperatedMinerals) {
        int min = Integer.MAX_VALUE;
        for (Point p : seperatedMinerals) {
            int x = p.getX();   // (x, y)에서 다른 클러스터와의 최소 높이 차이 구하기
            int y = p.getY();
            int dist = 0;

            // 현재 클러스터가 아닌 다른 미네랄(x)이 나온 경우 탐색 stop
            for (int nextX = x - 1; nextX > 0; nextX--) {
                if (cave[nextX][y] == 'x' && !seperatedMinerals.contains(new Point(nextX, y))) {
                    min = Math.min(min, dist);
                }
                dist++;
            }
            min = Math.min(min, dist);
        }


        return min;
    }

    // height 높이에서 throwToRight 방향으로 막대기를 던져 미네랄 파괴. return: 파괴한 미네랄의 위치 (파괴하지 않았으면 null)
    private static Point removeMineral(int height) {
        if (throwToRight) { // 왼쪽에서 오른쪽으로 던지는 차례인 경우 height 행의 가장 왼쪽에 위치한 미네랄 파괴
            for (int column = 0; column < C; column++) {
                if (cave[height][column] == 'x') {  // (height, column) 에 미네랄이 있는 경우 파괴
                    cave[height][column] = '.';
                    return new Point(height, column);
                }
            }
        } else {    // 오른쪽에서 왼쪽으로 던지기
            for (int column = C - 1; column >= 0; column--) {
                if (cave[height][column] == 'x') {  // (height, column) 에 미네랄이 있는 경우 파괴
                    cave[height][column] = '.';
                    return new Point(height, column);
                }
            }
        }
        return null;
    }

    private static void printCave(String msg) {
//        System.out.println("------------" + msg + "-------------");
//        for (int r = R; r > 0; r--) {
//            for (int c = 0; c < C; c++) {
//                System.out.print(cave[r][c]);
//            }
//            System.out.println();
//        }
//        System.out.println();
    }
}

class Status {
    private Point point;
    private Queue<Point> constructionOfCluster; // 현재 클러스터를 구성하는 미네랄의 좌표

    public Status(Point point, Queue<Point> constructionOfCluster) {
        this.point = point;
        this.constructionOfCluster = constructionOfCluster;
    }

    public Point getPoint() {
        return point;
    }

    public Queue<Point> getConstructionOfCluster() {
        return constructionOfCluster;
    }
}

class Point implements Comparable<Point> {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point o) {
        if (this.x != o.x) {
            return Integer.compare(this.x, o.x);
        } else {
            return Integer.compare(this.y, o.y);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
