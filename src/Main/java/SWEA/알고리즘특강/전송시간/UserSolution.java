package SWEA.알고리즘특강.전송시간;

import java.util.HashMap;
import java.util.LinkedList;

class UserSolution {
    private static boolean isUpdated1;
    private static boolean isUpdated2;  // graph2가 업데이트 되었으면 두 그래프 모두 최단거리 다시 계산해야함.
    private static HashMap<Integer, HashMap<Integer, Integer>> graph1;
    private static int[][][] graph2;

    public void init(int N, int K, int mNodeA[], int mNodeB[], int mTime[]) {
        graph1 = new HashMap<>();   // 루트노드와 대표노드로 이루어진 그래프
        graph2 = new int[N + 1][31][31];    // 소규모 그룹 간선 정보 graph2[x][i][j]: x번째 소규모 그룹의 i와 j 노드간 거리

        // 간선 정보 입력받기
        for (int i = 0; i < K; i++) {
            int nodeA = mNodeA[i];
            int nodeB = mNodeB[i];
            int time = mTime[i];
            int aGroup = nodeA / 100;   // 노드 a, b의 그룹 번호
            int bGroup = nodeB / 100;
            int aNode = nodeA % 100;    // 노드 a, b의 그룹 내 번호
            int bNode = nodeB % 100;

            if (aGroup == bGroup) { // 같은 그룹내에 있으면 graph2 에 추가
                graph2[aGroup][aNode][bNode] = time;
                graph2[aGroup][bNode][aNode] = time;
            } else {    // 다른 그룹에 속하면 graph1에 추가
                graph1.computeIfAbsent(nodeA, k -> new HashMap<>()).put(nodeB, time);
                graph1.computeIfAbsent(nodeB, k -> new HashMap<>()).put(nodeA, time);
            }
        }
        isUpdated1 = true;
        isUpdated2 = true;
    }

    public void addLine(int mNodeA, int mNodeB, int mTime) {
        int aGroup = mNodeA / 100;  // 노드 a, b의 그룹 번호
        int bGroup = mNodeB / 100;
        int aNode = mNodeA % 100;    // 노드 a, b의 그룹 내 번호
        int bNode = mNodeB % 100;

        if (aGroup == bGroup) { // 같은 그룹내에 있으면 graph2 에 추가
            graph2[aGroup][aNode][bNode] = mTime;
            graph2[aGroup][bNode][aNode] = mTime;

            isUpdated2 = true;
        } else {    // 두 노드가 다른 그룹에 속하면 graph1에 추가
            graph1.computeIfAbsent(mNodeA, k -> new HashMap<>()).put(mNodeB, mTime);
            graph1.computeIfAbsent(mNodeB, k -> new HashMap<>()).put(mNodeA, mTime);
            isUpdated1 = true;
        }
    }

    public void removeLine(int mNodeA, int mNodeB) {
        int aGroup = mNodeA / 100;  // 노드 a, b의 그룹 번호
        int bGroup = mNodeB / 100;
        int aNode = mNodeA % 100;    // 노드 a, b의 그룹 내 번호
        int bNode = mNodeB % 100;

        if (aGroup == bGroup) { // 같은 그룹내에 있으면 graph2 에 추가
            graph2[aGroup][aNode][bNode] = 0;
            graph2[aGroup][bNode][aNode] = 0;

            // 현재 그룹만 다익스트라. 대표 노드간의 거리 다시 구하기.
            // 다시 구해서 graph1의 대표 노드 그래프 수정
            findMinDistInGroup(aGroup);
            isUpdated2 = true;
        } else {    // 두 노드가 다른 그룹에 속하면 graph1에서 삭제
            graph1.get(mNodeA).remove(mNodeB);
            graph1.get(mNodeB).remove(mNodeA);
            isUpdated1 = true;
        }
    }

    public int checkTime(int mNodeA, int mNodeB) {
        // graph2 가 수정되었으면 graph
        if (isUpdated2) {

            isUpdated1 = false;
            isUpdated2 = false;
        } else if (isUpdated1) {    // graph1 만 수정되었으면 graph1 만 dijkstra

            isUpdated1 = false;
        }
        return 0;
    }

    private static int[] findMinDistInGroup (int groupN) {
        int[] d = new int[3];   // 해당 그룹의 1-2, 1-3, 2-3 간의 최단 거리
        // 해당 그룹의 1번 대표 노드에서 dijkstra
        int firstNode = groupN * 100 + 1;
//        dijkstra(firstNode);

        return null;
    }

    private static void dijikstra(int node) {
        int[] d = new int[4];   // 최단거리 저장 배열
//        for (int i = 0; )
    }


    private static class Edge {
        int node;
        int time;

        private Edge(int node, int time) {
            this.node = node;
            this.time = time;
        }
    }
}