package SWEA.알고리즘특강.하나로;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Solution {

    static final int R = 1000001;
    static int[] parent;
    static double[] relatedTurnelDist;
    static int[][] map;
    static Island[] islands;
    static int N;

    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/하나로/re_sample_input.txt"));

        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            double fee = 0; // 환경부담금
            N = sc.nextInt();   // 섬의 개수
            islands = new Island[N + 1];   // 섬 정보 저장 배열
            parent = new int[N + 1];    // 연결된 섬의 대표 섬 번호 저장 배열
            relatedTurnelDist = new double[N + 1]; // 대표 섬에 연결된 터널의 제곱을 모두 더한 값 저장 배열
            map = new int[R][R];

            // 섬 위치 입력받기
            for (int i = 1; i <= N; i++) {
                islands[i] = new Island(sc.nextInt(), 0, 0);
            }
            for (int i = 1; i <= N; i++) {
                islands[i].y = sc.nextInt();
                map[islands[i].x][islands[i].y] = i;
            }

            final double E = sc.nextDouble();   // 환경 부담 세율

            // 모든 섬을 가장 가까운 섬과 연결
            boolean[] related = new boolean[N]; // 섬이 다른 섬과 적어도 하나 연결되었는지 check
            for (int i = 1; i <= N; i++) {
                if (!related[i]) {
                    // 가장 가까운 섬 찾아 연결
                    int nearestIsl = findNearestIsl(i, false); // 가장 가까운 섬 번호

                    union(i, nearestIsl);
                    related[i] = related[nearestIsl] = true;
                }
            }

            // 연결되지 않은 섬의 그룹을 찾아 그룹 간 최소거리로 연결
            for (int i = 1; i <= N; i++) {
                if (relatedTurnelDist[i] != 0) {    // 대표 섬이면
                    int nearestIsl = findNearestIsl(i, true);

                    union(i, nearestIsl);
                }
            }

            // 대표 노드 찾기
            double sumDist = 0;
            for (int i = 1; i <= N; i++) {
                if (relatedTurnelDist[i] != 0) {
                    sumDist = relatedTurnelDist[i];
                    break;
                }
            }

            fee = E * sumDist;

            System.out.println("#" + test_case + " " + fee);
        }
    }

    private static double findDist(int i1, int i2) {
        int i1X = islands[i1].x;
        int i1Y = islands[i1].y;
        int i2X = islands[i2].x;
        int i2Y = islands[i2].y;

        return Math.abs(Math.pow(i1X,2) - Math.pow(i2X, 2) + Math.pow(i1Y, 2) - Math.pow(i2Y, 2));
    }

    private static void union(int i1, int i2) {
        int p1 = find(i1);
        int p2 = find(i2);

        if (p1 > p2) {
            parent[p1] = p2;
            relatedTurnelDist[p1] += findDist(i1, i2) + relatedTurnelDist[p2];
            relatedTurnelDist[p2] = 0;
        } else if (p1 < p2) {
            parent[p2] = p1;
            relatedTurnelDist[p2] += findDist(i1, i2) + relatedTurnelDist[p1];
            relatedTurnelDist[p1] = 0;
        }
    }

    private static int find(int i) {
        if (parent[i] == i) {
            return i;
        }

        return parent[i] = find(parent[i]);
    }

    private static int findNearestIsl(int islN, boolean isGroup) {
        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};

        Queue<Location> que = new LinkedList<>();
        boolean[][] visited = new boolean[R][R];
        if (isGroup) {
            for (int i = 1; i <= N; i++) {
                if (parent[i] == islN ) {
                    que.add(new Location(islands[i].x, islands[i].y));
                    visited[islands[i].x][islands[i].y] = true;
                }
            }
        } else {
            que.add(new Location(islands[islN].x, islands[islN].y));
            visited[islands[islN].x][islands[islN].y] = true;
        }

        while (!que.isEmpty()) {
            Location curLoc = que.poll();
            int curX = curLoc.x;
            int curY = curLoc.y;
            visited[curX][curY] = true;

            for (int i = 0; i < 4; i++) {
                int nextX = curX + dx[i];
                int nextY = curY + dy[i];

                if (nextX >= 0 && nextX < R && nextY >= 0 && nextY < R && !visited[nextX][nextY]) {  // 다음 위치가 범위 안인지 확인
                    if (map[nextX][nextY] != 0) { // 현재 위치가 섬이면 섬 번호 return
                        if (isGroup) {  // 그룹에서 가장 가까운 다른 섬을 찾는 경우이면
                            if (map[nextX][nextY] != islN) {
                                return map[nextX][nextY];
                            }
                        }

                        return map[nextX][nextY];
                    }

                    que.add(new Location(nextX, nextY));
                }
            }
        }
        return 0;
    }

    private static class Location {
        int x;
        int y;

        private Location(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Island {
        int x;
        int y;
        int parent;

        private Island(int x, int y, int parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }
}