package my.P17472;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class P17472_다리만들기2 {

    static int row;
    static int column;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static PriorityQueue<Bridge> pq;
    static int[] parentIsland;
    static int islandNum;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // init start
        row = sc.nextInt(); // 지도의 세로 크기
        column = sc.nextInt();  // 지도의 가로 크기
        map = new int[row][column]; // 지도 정보 저장 배열
        visited = new boolean[row][column]; // 방문 상태 배열
        pq = new PriorityQueue<Bridge>();   // 다리 정보 저장 pq

        // 지도 정보 입력받기
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        // init end
        sc.close();

        // BFS를 이용해 map 배열에서 연결된 땅들을 하나의 섬으로 표현(섬 번호 붙여주기)
        islandNum = 0;  // 섬 번호 & 개수
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                // 해당 좌표가 땅이고, 방문하지 않았다면 BFS 실행
                if (map[i][j] == 1 && !visited[i][j]) {
                    islandNum++;
                    BFS(i, j, islandNum);
                }
            }
        }

        parentIsland = new int[islandNum + 1];  // 대표 섬번호 저장 배열
        for (int i = 1; i <= islandNum; i++) {  // parentIsland 배열 초기화
            parentIsland[i] = i;
        }

        // 섬들 사이에 만들 수 있는 다리 정보를 모두 구해 pq에 삽입하기
        findAllBridge();

        // 입력받은 pq로 최소 신장 트리 만들어 모든 섬을 잇는 다리 길이의 최솟값 구하기
        int answer = findMinBridge();

        // 답 출력
        System.out.println(answer);
    }

    private static void BFS(int x, int y, int islandNum) {
        Queue<Land> queue = new LinkedList<>();

        // 출발 땅 설정
        queue.add(new Land(x, y));
        map[x][y] = islandNum;  // 섬 번호 지정해주기
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            // 현재 위치
            Land cur = queue.poll();
            int curX = cur.x;   // 현재 위치의 x좌표
            int curY = cur.y;   // 현재 위치의 y좌표

            // 다음 땅 섬 번호 지정해주기.
            for (int dir = 0; dir < 4; dir++) {
                int nextX = curX + dx[dir];   // cur 과 연결된 위치의 x좌표
                int nextY = curY + dy[dir];   // cur 과 연결된 위치의 y좌표

                // 다음 선택된 좌표가 지도 범위 내이고
                if (nextX >= 0 && nextX < row && nextY >= 0 && nextY < column) {
                    // 다음 선택된 좌표가 땅이고, 방문하지 않았다면
                    if (map[nextX][nextY] == 1 && !visited[nextX][nextY]) {
                        map[nextX][nextY] = islandNum;
                        queue.add(new Land(nextX, nextY));
                        visited[nextX][nextY] = true;
                    }
                }
            }

        }
    }

    static void findAllBridge() {
        for (int curX = 0; curX < row; curX++) {
            for (int curY = 0; curY < column; curY++) {

                // 현재 좌표가 땅이면
                if (map[curX][curY] != 0) {
                    int curIsland = map[curX][curY];    // 현재 좌표의 섬 번호

                    // 현재 좌표의 네 방향 탐색
                    for (int dir = 0; dir < 4; dir++) {
                        int nextX = curX + dx[dir];
                        int nextY = curY + dy[dir];
                        int bridgeLength = 0;   // cur에서 출발한 다리 길이

                        // 다리가 지도 범위를 벗어날 때까지 직진 반복
                        while (nextX >= 0 && nextX < row && nextY >= 0 && nextY < column) {

                            // 다음 좌표가 땅이고,
                            if (map[nextX][nextY] != 0) {

                                // 자신의 섬이면 break
                                if (map[nextX][nextY] == curIsland) {
                                    break;
                                }

                                // 자신이 아닌 섬이고 다리의 길이가 2이상이면 pq에 다리 정보 저장 후 break
                                else if (map[nextX][nextY] != curIsland) {
                                    // 다리 길이가 2미만인지 확인
                                    if (bridgeLength < 2) {
                                        break;
                                    }

                                    int nextIsland = map[nextX][nextY]; // 다음 섬의 번호
                                    // pq에 삽입
                                    pq.add(new Bridge(curIsland, nextIsland, bridgeLength));
                                    break;
                                }
                            }

                            // next 좌표가 바다이면 다리 설치 후 next 좌표 직진
                            else if (map[nextX][nextY] == 0) {
                                bridgeLength += 1;  // 다리 설치

                                // 다음 좌표를 같은 방향으로 직진
                                nextX += dx[dir];
                                nextY += dy[dir];
                            }
                        }
                    }
                }
            }
        }
    }

    private static int findMinBridge() {
        int bridgeNum = 0;  // 연결한 다리 개수
        int minBridge = 0;  // 모든 섬을 연결하는 다리 길이의 최솟값 저장 변수(답)
        while (!pq.isEmpty()) {

            // 현재 다리 정보
            Bridge curBridge = pq.poll();
            int island1 = curBridge.island1;
            int island2 = curBridge.island2;
            int bridgeLength = curBridge.brideLength;

            island1 = find(island1);    // island1의 대표 섬 번호를 구해 저장
            island2 = find(island2);    // island2의 대표 섬 번호를 구해 저장

            // 두 개의 섬이 연결되어 있지 않으면 다리로 연결 후 minBridge에 현재 다리 길이 더하기
            if (island1 != island2) {
                union(island1, island2);
                bridgeNum++;
                minBridge += bridgeLength;
            }

            // 모든 다리를 이었으면
            if (bridgeNum == islandNum - 1) {
                return minBridge;
            }
        }

        // while문이 끝났는데 모든 섬을 잇지 못했으면 return -1
        return -1;
    }

    // 대표 node를 찾는 메서드
    private static int find(int node) {
        if (node == parentIsland[node]) {
            return parentIsland[node];
        } else {
            return parentIsland[node] = find(parentIsland[node]);
        }
    }

    // union 메서드
    private static void union(int node1, int node2) {
        node1 = find(node1);
        node2 = find(node2);

        parentIsland[node2] = node1;
    }

    // Land 클래스
    static class Land {
        int x;  // x좌표
        int y;  // y좌표

        private Land(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Bridge 클래스
    static class Bridge implements Comparable<Bridge> {
        int island1;
        int island2;
        int brideLength;

        private Bridge(int island1, int island2, int brideLength) {
            this.island1 = island1;
            this.island2 = island2;
            this.brideLength = brideLength;
        }

        @Override
        public int compareTo(Bridge o) {
            return Integer.compare(this.brideLength, o.brideLength);
        }
    }
}
