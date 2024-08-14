package SWEA.알고리즘특강.물류허브;

import java.util.*;

class UserSolution {
    private static List<Integer> vNum;
    private static ArrayList<Edge>[] edges;
    private static ArrayList<Edge>[] reverseEdges;
    private static int cityN;

    public int init(int N, int sCity[], int eCity[], int mCost[]) {
        // 좌표 압축을 위해 도시 번호를 중복 제거해 추출
        vNum = new ArrayList<>(); //  좌표 압축을 위한 도시 번호 저장 리스트
        for (int i = 0; i < N; i++) {
            vNum.add(sCity[i]);
            vNum.add(eCity[i]);
        }
        Collections.sort(vNum); // 정렬
        vNum = new ArrayList<>(new HashSet<>(vNum));    // 중복 제거
        cityN = vNum.size();    // 도시 수

        // 도로 정보 입력
        edges = new ArrayList[cityN];   // 도로 정보 저장 리스트
        reverseEdges = new ArrayList[cityN];   // 도로의 방향을 뒤집은 저장 리스트

        // arrList 배열 초기화
        for (int i = 0; i < cityN; i++) {
            edges[i] = new ArrayList<>();
            reverseEdges[i] = new ArrayList<>();
        }

        for (int i = 0; i < N; i++) {
            int compSCity = vNum.indexOf(sCity[i]); // 압축된 sCity 번호
            int compECity = vNum.indexOf(eCity[i]); // 압축된 eCity 번호
            int cost = mCost[i];

            edges[compSCity].add(new Edge(compECity, cost));
            reverseEdges[compECity].add(new Edge(compSCity, cost));
        }

        return cityN;
    }

    public void add(int sCity, int eCity, int mCost) {
        int compSCity = vNum.indexOf(sCity);
        int compECity = vNum.indexOf(eCity);

        edges[compSCity].add(new Edge(compECity, mCost));
        reverseEdges[compECity].add(new Edge(compSCity, mCost));
        return;
    }

    public int cost(int mHub) {
        int sumCost = 0;
        int compHubN = vNum.indexOf(mHub);
        // mHub 에서 모든 도시까지 총 편도 운송 비용 구하기
        sumCost += dijkstra(compHubN, edges);

        // 모든 도시에서 mHub 까지 편도 운송 비용 구하기
        sumCost += dijkstra(compHubN, reverseEdges);

        return sumCost;
    }

    private static int dijkstra(int hubN, ArrayList<Edge>[] edge) {
        int[] minCost = new int[cityN];   // hub 로부터 도시 i까지 최소 비용 저장 배열
        // minCost 초기화
        Arrays.fill(minCost, Integer.MAX_VALUE);
        minCost[hubN] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[cityN];
        pq.add(new Edge(hubN, 0));
        while (!pq.isEmpty()) {
            Edge cur = pq.poll();
            int curCity = cur.eCity;
            if (visited[curCity]) {
                continue;
            }
            visited[curCity] = true;

            for (Edge next : edge[curCity]) {
                int nextCost = cur.cost + next.cost;
                if (nextCost < minCost[next.eCity]) {
                    minCost[next.eCity] = nextCost;
                    pq.add(new Edge(next.eCity, nextCost));
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < minCost.length; i++) {
            sum += minCost[i];
        }
        return sum;
    }

    private static class Edge implements Comparable<Edge> {
        int eCity;
        int cost;

        private Edge(int eCity, int cost) {
            this.eCity = eCity;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
}