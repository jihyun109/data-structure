package BaekJoon.P2098_외판원순회;

import java.util.*;

public class P2098_외판원순회 {
    public static int cityN;    // 총 도시의 수
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        cityN = sc.nextInt();
        int[][] roads = new int[cityN][cityN];   // 길 정보 저장 배열. roads[i][j]: i에서 j로 가는 길의 비용
        RouteInfo[][] minRoute = new RouteInfo[cityN][cityN];    // minRoute[i][j]: i 번 째로 j 도시를 방문하는데 드는 최소 비용 저장 배열. (0 <= i < cityN)

        // 길 정보 입력받기
        for (int s = 0; s < cityN; s++) {   // s: 길의 시작 도시, e: 길의 도착 도시
            for (int e = 0; e < cityN; e++) {
                int cost = sc.nextInt();
                roads[s][e] = cost;    // 길정보 입력받기
            }
        }
        sc.close();

        int minCost = Integer.MAX_VALUE;    // 모든 도시를 순회할 수 있는 최소 비용
        for (int s = 0; s < cityN; s++) {   // s: 출발 도시
            // minRoute 초기화
            for (int i = 0; i < cityN; i++) {
                for (int j = 0; j < cityN; j++) {
                    minRoute[i][j] = new RouteInfo(Integer.MAX_VALUE, new boolean[cityN]);

                }
                minRoute[0][s].setSumCost(0);  // 0 번째로 s 도시를 방문하는데 드는 비용 저장.
                minRoute[0][s].setVisited(s);    // s 도시 방문 처리
            }

            for (int t = 1; t < cityN; t++) {  // t: 방문한 순서
                for (int e = 0; e < cityN; e++) {   // e: 도착 도시
                    // 외판원이 s 도시에서 출발했을 때 t 번쨰로 e 도시에 방문하는 모든 경우를 찾아 최소 비용 구하기
                    if (e == s) {
                        continue;
                    }
                    for (int i = 0; i < cityN; i++) {   // e 도시에 방문할 수 있는 모든 길 순회
                        if (roads[i][e] == 0) { // 길이 없는 경우 pass
                            continue;
                        }

                        int roadStart = i;
                        int roadCost = roads[i][e];
                        if (minRoute[t - 1][roadStart].visited[e]) {  // t-1 번쨰에 길의 시작 도시를 방문한 최소루트에서 e를 방문했다면 다른 길 찾기.
                            continue;
                        }
                        int eMinCost = Math.min(minRoute[t][e].sumCost, minRoute[t-1][roadStart].sumCost + roadCost);  // t 번째에 e 도시에 방문하는 최소 비용
                        minRoute[t][e].setSumCost(eMinCost);
                    }
                    minRoute[t][e].setVisited(e);

                    // 모든 도시를 다 돈 경우 처음 출발지로 돌아간 후 minCost 업데이트
                    if (t == cityN - 1) {
                        int roadCost = roads[e][s]; // 마지막 방문 도시에서 출발도시까지의 비용
                        if (roadCost != 0) { // 길이 있는 경우
                            minCost = Math.min(minCost, minRoute[t][e].getSumCost() + roadCost);
                        }

                        for (int i = 0; i < cityN; i++) {
                            for (int j = 0; j < cityN; j++) {
                                System.out.print(minRoute[j][i].getSumCost() + " ");
                            }
                            System.out.println();
                        }
                        System.out.println();
                    }
                }
            }
        }

//        for (int i = 0;  i< cityN; i++) {
//            System.out.println(Arrays.toString(minRoute[i]));
//        }
        System.out.println(minCost);
    }

    public static class Road {
        private int startCity;
        private int cost;

        public Road(int startCity, int cost) {
            this.startCity = startCity;
            this.cost = cost;
        }
    }

    public static class RouteInfo {
        @Override
        public String toString() {
            return " " + sumCost
                    ;
        }

        private int sumCost;
        private boolean[] visited;

        public RouteInfo(int sumCost, boolean[] visited) {
            this.sumCost = sumCost;
            this.visited = visited;
        }

        public void setSumCost(int sumCost) {
            this.sumCost = sumCost;
        }

        public void setVisited(int s) {
            this.visited[s] = true;
        }

        public int getSumCost() {
            return sumCost;
        }
    }
}
