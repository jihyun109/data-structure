package SWEA.알고리즘특강.마을;

import java.util.*;

class UserSolution3 {
    private static int L;   // 마을을 이루는 기준 거리
    private static int R;   // X, Y 좌표 최댓값
    private static HashMap<Integer, TreeSet<Integer>> townMembers; // key: 마을 id, value: 마을에 속한 집 id set
    private static HashMap<Integer, HouseInfo> townIds; // key: 집 id, value: 마을 id
    private static HashMap<Integer, TreeMap<Integer, Integer>> xLoc;  // key: x좌표, value: y좌표, 집id
    private static int[][] map; // 집의 위치 저장 배열
    private static Set<Integer> townToMerge;   // 합쳐지는 마을 id 저장 큐
    private static int[] dx = new int[]{-1, 1, 0, 0};
    private static int[] dy = new int[]{0, 0, -1, 1};

    public void init(int L, int R) {
        townMembers = new HashMap<>();
        townIds = new HashMap<>();
        map = new int[R + 1][R + 1];
        UserSolution3.L = L;
        UserSolution3.R = R;
        townToMerge = new HashSet<>();
        xLoc = new HashMap<>();
    }

    public int add(int mId, int mX, int mY) {
//        System.out.println("mId: " + mId + ", x: " + mX + ", y: " + mY);
        map[mX][mY] = mId;
        townMembers.put(mId, new TreeSet<>()); // 자기 자신의 마을 만들기
        townMembers.get(mId).add(mId);
        townIds.put(mId, new HouseInfo(mId, mX, mY));
        xLoc.computeIfAbsent(mX, k -> new TreeMap<>()).put(mY, mId);

        // BFS 로 합쳐지는 마을이 있는지 찾기
        int largestTownId = findSameTowns(mX, mY, mId);    // 합쳐질 마을 중 크기가 가장 큰 마을 id. 합쳐질 마을이 없는 경우 0

        if (largestTownId != 0) {   // 합쳐질 마을이 있는 경우
            townToMerge.add(mId);
            // 현재 집의 townId 바꾸기
            townIds.get(mId).townId = largestTownId;

            // 마을 합치기
            mergeTowns(largestTownId);
            if (largestTownId != mId) {
                townMembers.get(largestTownId).add(mId);
                townMembers.remove(mId);
            }
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
        xLoc.get(x).remove(y);
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


    // 같은 마을이 되는 집 찾기, 가장 큰 마을 id return, townsToMerge 에 합칠 마을 id들 넣기
    private int findSameTowns(int x, int y, int newhouse) {
        townToMerge = new HashSet<>();
        int minX = Math.max(0, x - L);  // (x, y)에서 L 범위 안의 최소 x값
        int maxX = Math.min(R, x + L);  // (x, y)에서 L 범위 안의 최대 x값
        int largestTownId = 0;  // 가장 큰 마을의 Id
        int largestTownSize = 0;    // 가장 큰 마을의 크기

        for (int curX = minX; curX <= maxX; curX++) {
            if (!xLoc.containsKey(curX)) {  // curX에 위치한 집이 있는 경우 진행
                continue;
            }

            int dx = Math.abs(x - curX);    // 추가된 집과 현재 curX 와의 거리
            int dy = L - dx;  // x값이 curX일 때 y좌표가 추가된 집과 떨어질 수 있는 최대 거리
            int minY = Math.max(0, y - dy); // x 좌표가 curX일 때 L범위 안에 들어가는 최소 y값
            int maxY = Math.min(R, y + dy); // x 좌표가 curX일 때 L범위 안에 들어가는 최대 y값

            int curY = minY - 1;
            while (true) {
                Integer houseY = xLoc.get(curX).higherKey(curY); // x좌표가 curX일 때 curY보다 같거나 큰 위치에 있는 집의 y좌표
                if (houseY == null) {   // 해당되는 집이 없음
                    break;
                }
                if (houseY > maxY) {    // 존재하지만 범위를 벗어남
                    break;
                }

                // 조건에 맞는 집이 존재
                int houseId = xLoc.get(curX).get(houseY);
                int townId = townIds.get(houseId).townId;
                int townSize = townMembers.get(townId).size();
                if (houseId != newhouse) {
                    townToMerge.add(townId);

                    // 가장 큰 마을 업데이트
                    if (townSize > largestTownSize) {
                        largestTownId = townId;
                        largestTownSize = townSize;
                    }
                }

                curY = L + houseY;  // 범위 단축

                if (curY > maxY) {
                    break;
                }
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