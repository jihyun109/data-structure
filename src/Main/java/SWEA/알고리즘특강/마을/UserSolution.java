package SWEA.알고리즘특강.마을;

import java.util.*;

class UserSolution {
    private static int D;   // 마을을 이루는 기준 거리
    private static int M;   // X, Y 좌표 최댓값
    private static HashMap<Integer, TreeSet<Integer>> townMembers; // key: 마을 id, value: 마을에 속한 집 id set
    private static HashMap<Integer, HouseInfo> townIds; // key: 집 id, value: 마을 id
    private static int[][] map; // 집의 위치 저장 배열
    private static Set<Integer> townToMerge;   // 합쳐지는 마을 id 저장 큐
    private static int[] dx = new int[]{-1, 1, 0, 0};
    private static int[] dy = new int[]{0, 0, -1, 1};

    public void init(int L, int R) {
        townMembers = new HashMap<>();
        townIds = new HashMap<>();
        map = new int[R + 1][R + 1];
        D = L;
        M = R;
        townToMerge = new HashSet<>();
    }

    public int add(int mId, int mX, int mY) {
//        System.out.println("mId: " + mId + ", x: " + mX + ", y: " + mY);
        map[mX][mY] = mId;
        townMembers.put(mId, new TreeSet<>()); // 자기 자신의 마을 만들기
        townMembers.get(mId).add(mId);
        townIds.put(mId, new HouseInfo(mId, mX, mY));

        // BFS 로 합쳐지는 마을이 있는지 찾기
        int largestTownId = BFS(mX, mY);    // 합쳐질 마을 중 크기가 가장 큰 마을 id. 합쳐질 마을이 없는 경우 0

        if (largestTownId != 0) {   // 합쳐질 마을이 있는 경우
            townToMerge.add(mId);
            // 현재 집의 townId 바꾸기
            townIds.get(mId).townId = largestTownId;

            // 마을 합치기
            mergeTowns(largestTownId);
            townMembers.get(largestTownId).add(mId);
            townMembers.remove(mId);
        }

        int townId = townIds.get(mId).townId;
//        System.out.println(townId);
        return townMembers.get(townId).size();
    }

    public int remove(int mId) {
        if (!townIds.containsKey(mId)) {
            return -1;
        }
        int x = townIds.get(mId).x;
        int y = townIds.get(mId).y;
        map[x][y] = 0;
        int townId = townIds.get(mId).townId;  // mId 가 속한 마을의 id
        townIds.remove(mId);
        townMembers.get(townId).remove(mId);
        int size = townMembers.get(townId).size();
        if (townMembers.get(townId).isEmpty()) {
            townMembers.remove(townId);
        }

        return size;
    }

    public int check(int mId) {
        if (!townIds.containsKey(mId)) {
            return -1;
        }
        int townId = townIds.get(mId).townId;  // mId 가 속한 마을 id
        return townMembers.get(townId).first();
    }

    public int count() {
        return townMembers.size();
    }

    private void mergeTowns(int largestTownId) {
        for (int mergeTownId : townToMerge) {
            if (mergeTownId == largestTownId) {
                continue;
            }
//            System.out.println(mergeTownId);
            TreeSet<Integer> houses = new TreeSet<>(townMembers.get(mergeTownId));   // 합쳐질 마을의 집들
            for (int houseId : houses) {
                townIds.get(houseId).townId = largestTownId; // 해당 집의 마을 id 바꾸기
                townMembers.get(largestTownId).add(houseId);    // largestTown 에 현재 집 추가
            }
            townMembers.remove(mergeTownId);    // 통합된 마을 삭제
        }
    }



    private int BFS(int x, int y) {
        Queue<Location> que = new LinkedList<>();
        boolean[][] visited = new boolean[M + 1][M + 1];
        townToMerge = new HashSet<>();    // 합쳐지는 마을 id 저장 큐
        int largestTownId = 0;    // 크기가 가장 큰 마을의 id
        int largestTownSize = 0;    // 크기가 가장 큰 마을의 크기
        que.add(new Location(x, y, 0));

        while (!que.isEmpty()) {
            Location cur = que.poll();
            visited[cur.x][cur.y] = true;

            int nextDist = cur.dist + 1;
            if (nextDist > D) {
                break;
            }
            for (int i = 0; i < 4; i++) {
                int nextX = cur.x + dx[i];
                int nextY = cur.y + dy[i];

                if (nextX < 0 || nextX > M || nextY < 0 || nextY > M || visited[nextX][nextY]) { // 범위를 벗어나거나 이미 방문했으면 continue
                    continue;
                }

                if (map[nextX][nextY] != 0) {   // 집이 있으면
                    int nextHouseId = map[nextX][nextY];
                    int nextTownId = townIds.get(nextHouseId).townId;   // next 의 마을 id
                    int nextTownSize = townMembers.get(nextTownId).size();
                    townToMerge.add(nextTownId);   // 합칠 마을에 포함시킴.

                    // 합칠 마을 중 크기가 가장 큰 마을과 비교
                    if (nextTownSize > largestTownSize) { // 가장 큰 마을보다 크기가 크면
                        largestTownId = nextTownId;
                        largestTownSize = nextTownSize;
                    }
                }

                que.add(new Location(nextX, nextY, nextDist));
            }
        }
        return largestTownId;
    }

    private class HouseInfo {
        int townId;
        int x;
        int y;

        public HouseInfo(int townId, int x, int y) {
            this.townId = townId;
            this.x = x;
            this.y = y;
        }
    }

    private class Location {
        int x;
        int y;
        int dist;

        public Location(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

}