package SWEA.알고리즘특강.전송시간;

import java.util.*;

class UserSolution {
    // 루트노드와 대표노드로 이루어진 그래프 (key: 노드 번호, value: (key:연결된 노드번호, value: 전송시간))
    private static HashMap<Integer, HashMap<Integer, Integer>> graph1;
    // 소규모 그룹 별 대표노드와 말단노드로 이루어진 그래프 (key: 노드 번호, value: (key: 연결된 노드 번호, value: 전송시간))
    private static HashMap<Integer, HashMap<Integer, Integer>>[] graph2;
    // 각 루트노드에서 다른 루트노드와 대표노드까지 최소시간 저장 맵 (minTime1[i]: i번 루트노드와 다른노드들 간 최소시간, key: 노드 번호, value: 최소시간)
    private static HashMap<Integer, Integer>[] minTime1;
    // 소규모 그룹 별 대표노드1,2,3 과 다른 노드들 간 최소시간 저장 맵 (minTime2[i][j]: i그룹 j 대표노드와 다른 노드들 사이 최소 시간, key: 노드번호, value: 최소시간)
    private static int[][][] minTime2;
    private static boolean needUpdateMin1;  // minTime1을 업데이트 해야하는지 flag
    private static boolean needUpdateMin2;  // minTime2을 업데이트 해야하는지 flag
    private static HashSet<Integer> updatedGroup;   // 수정된 그룹번호 저장 set
    private static int groupN;  // 그룹 수

    public void init(int N, int K, int mNodeA[], int mNodeB[], int mTime[]) {
        groupN = N; // 그룹 수
        graph1 = new HashMap<>();
        graph2 = new HashMap[N + 1];
        minTime1 = new HashMap[4];
        minTime2 = new int[N + 1][4][31];
        updatedGroup = new HashSet<>();
        for (int i = 1; i <= 3; i++) {
            minTime1[i] = new HashMap<>();
        }
        for (int i = 1; i <= N; i++) {
            graph2[i] = new HashMap<>();
        }
        needUpdateMin1 = false;
        needUpdateMin2 = false;

        // 라인 입력
        for (int i = 0; i < K; i++) {
            int nodeA = mNodeA[i];
            int nodeB = mNodeB[i];
            int time = mTime[i];
            // 두 노드의 그룹 번호 구하기
            int groupA = findGroup(mNodeA[i]);
            int groupB = findGroup(mNodeB[i]);

            if (groupA != groupB) { // 두 노드가 서로 다른 그룹에 있음
                graph1.computeIfAbsent(nodeA, k -> new HashMap<>()).put(nodeB, time);
                graph1.computeIfAbsent(nodeB, k -> new HashMap<>()).put(nodeA, time);
                needUpdateMin1 = true;

            } else {    // 두 노드가 같은 그룹에 있음
                // 그룹 내에서의 노드 번호
                nodeA = nodeA - (groupA * 100);
                nodeB = nodeB - (groupA * 100);

                // 그래프에 추가
                graph2[groupA].computeIfAbsent(nodeA, k -> new HashMap<>()).put(nodeB, time);
                graph2[groupA].computeIfAbsent(nodeB, k -> new HashMap<>()).put(nodeA, time);
                updatedGroup.add(groupA);
                needUpdateMin2 = true;
            }
        }
    }

    public void addLine(int mNodeA, int mNodeB, int mTime) {
        int groupA = findGroup(mNodeA);
        int groupB = findGroup(mNodeB);

        if (groupA != groupB) {
            // graph1에 라인 추가
            graph1.computeIfAbsent(mNodeA, k -> new HashMap<>()).put(mNodeB, mTime);
            graph1.computeIfAbsent(mNodeB, k -> new HashMap<>()).put(mNodeA, mTime);
            needUpdateMin1 = true;
        } else {
            mNodeA = mNodeA - (groupA * 100);
            mNodeB = mNodeB - (groupB * 100);
            // graph2에 라인 추가
            graph2[groupA].computeIfAbsent(mNodeA, k -> new HashMap<>()).put(mNodeB, mTime);
            graph2[groupA].computeIfAbsent(mNodeB, k -> new HashMap<>()).put(mNodeA, mTime);
            updatedGroup.add(groupA);
            needUpdateMin2 = true;
        }
    }

    public void removeLine(int mNodeA, int mNodeB) {
        int groupA = findGroup(mNodeA);
        int groupB = findGroup(mNodeB);

        if (groupA != groupB) {
            // graph1에서 라인 삭제
            graph1.get(mNodeA).remove(mNodeB);
            graph1.get(mNodeB).remove(mNodeA);
            needUpdateMin1 = true;
        } else {    // 같은 그룹일 때
            mNodeA = mNodeA - (groupA * 100);
            mNodeB = mNodeB - (groupB * 100);
            // graph2에서 라인 삭제
            if (!graph2[groupA].containsKey(mNodeA) || !graph2[groupA].get(mNodeA).containsKey(mNodeB)) {  // 해당 라인이 없는 경우
                return;
            }
            graph2[groupA].get(mNodeA).remove(mNodeB);
            graph2[groupA].get(mNodeB).remove(mNodeA);
            updatedGroup.add(groupA);
            needUpdateMin2 = true;
        }
    }

    public int checkTime(int mNodeA, int mNodeB) {
        // 업데이트가 필요한 최단거리 업데이트
        if (needUpdateMin2) {
            // 수정된 그룹의 최소시간 배열 수정 & graph1에 해당 그룹의 대표노드 간 수정된 최소시간 update
            for (int g : updatedGroup) {
                updateMinTime2(g);   // 그룹 g 의 최소시간 배열 업데이트
                updateGraph1(g);    // 수정된 그룹 g 의 대표 노드 간 최소시간 graph1에 업데이트
            }
            updatedGroup.clear();
            updateMinTime1();   // minTime1 업데이트
            needUpdateMin1 = false;
            needUpdateMin2 = false;
        } else if (needUpdateMin1) {
            updateMinTime1();
            needUpdateMin1 = false;
        }
        // 시간 check
        int time = minTime1[mNodeA].get(mNodeB);
        return time;
    }

    private static int findGroup(int node) {    // 0이면 루트 노드
        return node / 100;
    }

    private static void updateMinTime1() {
        // dijkstra 를 위해 minTime1 초기화
        for (int root = 1; root <= 3; root++) { // 루트 번호
            for (int group = 0; group <= groupN; group++) { // 그룹 번호 (0이면 루트노드임)
                for (int rep = 1; rep <= 3; rep++) { // 대표 노드 번호(1~3)
                    int nodeN = 100 * group + rep;  // 노드 번호
                    if (root == nodeN) {    // 자기 자신의 최소거리이면 0으로 set
                        minTime1[root].put(nodeN, 0);
                        continue;
                    }
                    minTime1[root].put(nodeN, Integer.MAX_VALUE);
                }
            }
        }

        // 모든 루트노드에서 graph1 의 다른 노드들 까지 최소 거리 구하기
        for (int root = 1; root <= 3; root++) {
            dijkstraGraph1(root);
        }
    }

    private static void updateMinTime2(int g) {
        // g 그룹의 모든 대표노드사이의 거리 업데이트
        for (int repNode = 1; repNode <= 3; repNode++) {
            dijkstraGraph2(g, repNode); // g 그룹의 repNode 와 다른 노드들 사이의 최소거리 구하기
        }
    }

    private static void updateGraph1(int group) {
        for (int i = 1; i <= 3; i++) {  // 해당 그룹의 모든 대표노드
            for (int j = 1; j <= 3; j++) {
                int node = group * 100 + i; // 노드 번호
                int relatedNode = group * 100 + j;  // 연결된 노드 번호

                if (node == relatedNode) {  // 두 노드가 같으면 continue
                    continue;
                }


                int time = minTime2[group][i][j];
                if (time != Integer.MAX_VALUE) {    // 최소 거리가 구해져있으면 최소시간 수정
                    graph1.computeIfAbsent(node, k -> new HashMap<>()).put(relatedNode, time);
                }
            }
        }
    }

    private static void dijkstraGraph1(int root) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        HashSet<Integer> visited = new HashSet<>(); // 최소 시간 계산 완료한 노드번호 저장 set
        pq.add(new Node(root, 0));

        int rootCnt = 0;    // 최소 시간을 구한 자신을 제외한 루트 노드의 개수
        while (!pq.isEmpty()) {
            Node curNode = pq.poll();
            visited.add(curNode.node);  // 방문 처리

            // 모든 루트노드까지의 거리를 구했으면 break
            if (visited.contains(1) && visited.contains(2) && visited.contains(3)) {
                break;
            }

            HashMap<Integer, Integer> nextNodes = graph1.get(curNode.node); // curNode 와 연결되어있는 노드들
            for (Map.Entry<Integer, Integer> nextEntry : nextNodes.entrySet()) {
                int nextNode = nextEntry.getKey();  // 다음 노드 번호
                if (visited.contains(nextNode)) { // 이미 최소 시간이 계산되었으면 continue
                    continue;
                }

                int nextTime = curNode.time + nextNodes.get(nextNode);
                if (nextTime < minTime1[root].get(nextNode)) {  // 현재까지 구해진 최소시간보다 작으면 업데이트
                    minTime1[root].put(nextNode, Math.min(nextTime, minTime1[root].get(nextNode))); // next 노드 최소시간 갱신
                    pq.add(new Node(nextNode, minTime1[root].get(nextNode)));
                }
            }
        }
    }

    private static void dijkstraGraph2(int group, int repNode) {
        // minTime2 초기화
        for (int i = 1; i <= 30; i++) {
            minTime2[group][repNode][i] = Integer.MAX_VALUE;
        }

        // 다익스트라
        PriorityQueue<Node> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[31];
        minTime2[group][repNode][repNode] = 0;
        pq.add(new Node(repNode, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            visited[cur.node] = true;

            if (visited[1] && visited[2] && visited[3]) {   // 모든 대표노드까지의 거리를 구했으면 return
                return;
            }

            if (!graph2[group].containsKey(cur.node)) {
                continue;
            }
            for (Map.Entry<Integer, Integer> nextEntry : graph2[group].get(cur.node).entrySet()) {
                int nextNode = nextEntry.getKey();
                if (visited[nextNode]) {
                    continue;
                }
                int nextTime = cur.time + nextEntry.getValue();

                if (nextTime < minTime2[group][repNode][nextNode]) {
                    minTime2[group][repNode][nextNode] = nextTime;
                    pq.add(new Node(nextNode, nextTime));
                }
            }
        }
    }

    private static class Node implements Comparable<Node> {
        int node;
        int time;

        public Node(int node, int time) {
            this.node = node;
            this.time = time;
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.time, o.time);
        }
    }
}

