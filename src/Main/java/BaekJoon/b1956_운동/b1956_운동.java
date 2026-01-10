package BaekJoon.b1956_운동;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class b1956_운동 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int townN = Integer.parseInt(st.nextToken());
        int roadN = Integer.parseInt(st.nextToken());

        // 도로 정보 입력받기
        List<List<Road>> roads = initRoads(townN);
        for (int i = 0; i < roadN; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int length = Integer.parseInt(st.nextToken());

            roads.get(s).add(new Road(e, length));
        }
        br.close();

        int minCycle = findMinCycle(townN, roads);

        System.out.println(minCycle);
    }

    private static List<List<Road>> initRoads(int townN) {
        List<List<Road>> roads = new ArrayList<>();
        for (int i = 0; i <= townN; i++) {
            roads.add(new ArrayList<>());
        }

        return roads;
    }

    private static int findMinCycle(int townN, List<List<Road>> roads) {
        // 모든 마을로부터 모든 마을까지 최소 경로 구하기
        return findMinCosts(townN, roads);
    }

    private static int findMinCosts(int townN, List<List<Road>> roads) {
        int[][] minCosts = initMinCost(townN);
        int min = Integer.MAX_VALUE;

        // 모든 마을에서 출발
        for (int s = 1; s <= townN; s++) {
            PriorityQueue<Road> pq = new PriorityQueue<>();

            // s로부터 출발하는 도로 pq에 넣기
            for (Road road : roads.get(s)) {
                pq.add(new Road(road.e, road.length));
                minCosts[s][road.e] = road.length;
            }

            while (!pq.isEmpty()) {
                Road road = pq.poll();
                int curE = road.e;
                int length = road.length;

                // 도착지로 돌아온 경우
                if (curE == s) {
                    minCosts[s][curE] = length;
                    min = Math.min(min, length);
                    break;
                }
                if (length > minCosts[s][curE]) {
                    continue;
                }

                // 연결된 이웃 마을로 가기
                for (Road nextRoad : roads.get(curE)) {
                    int nextE = nextRoad.e;
                    int nextLength = nextRoad.length + length;

                    if (nextLength < minCosts[s][nextE]) {
                        pq.add(new Road(nextE, nextLength));
                        minCosts[s][nextE] = nextLength;
                    }
                }
            }
        }

//        print(minCosts);

        return min == Integer.MAX_VALUE ? -1 : min;
    }

    private static int[][] initMinCost(int townN) {
        int[][] arr = new int[townN + 1][townN + 1];
        for (int i = 1; i <= townN; i++) {
            for (int j = 1; j <= townN; j++) {
                arr[i][j] = Integer.MAX_VALUE;
            }
        }
        return arr;
    }

    private static void print(int[][] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class Road implements Comparable<Road> {
    int e;  // 도착지
    int length;   // 도로 길이

    public Road(int e, int length) {
        this.e = e;
        this.length = length;
    }

    @Override
    public int compareTo(Road o) {
        return this.length - o.length;
    }
}
