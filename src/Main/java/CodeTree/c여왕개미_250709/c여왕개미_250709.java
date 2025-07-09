package CodeTree.c여왕개미_250709;

import java.util.*;
import java.io.*;

public class c여왕개미_250709 {
    // private static TreeMap<Integer, Integer> ants;     // 개미집 정보 저장(k: 개미집 번호, v: 좌표)
    private static List<Integer> ants;
    private static List<Boolean> isDestroyed;
    private static int lastIdx; // 가장 마지막에 위치한 개미집 번호
    private static int minDistBWHouse; // 거리가 가장 먼 개미집 사이의 거리
    private static int initialHouseN;
    private static int houseN;

    private static final int MAX_HOUSE_N = 20000;   // 최대로 건설될 수 있는 개미집의 수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int Q = Integer.parseInt(st.nextToken());   // 명령의 수
        ants = new ArrayList(Arrays.asList(0));
        isDestroyed = new ArrayList<>(Arrays.asList(false));
        minDistBWHouse = Integer.MAX_VALUE;

        // 명령 입력받기
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());

            int query = Integer.parseInt(st.nextToken());   // 명령

            switch(query) {
                case 100:
                    initialHouseN = Integer.parseInt(st.nextToken());   // 초기 개미집의 수

                    // 개미집 정보 입력받기
                    int frontPoint = 0;   // 현재 개미집 앞에 위치한 개미집의 좌표
                    for (int idx = 1; idx <= initialHouseN; idx++) {
                        int point = Integer.parseInt(st.nextToken());   // 개미집 좌표
                        ants.add(point);
                        isDestroyed.add(false);

                        // maxBWHouse update
                        if (frontPoint != 0) {    // 앞이 여왕개미집인 경우는 계산 X
                            int dist = point - frontPoint;
                            minDistBWHouse = Math.min(dist, minDistBWHouse);
                        }

                        frontPoint = point;
                    }

                    lastIdx = initialHouseN;
                    houseN = initialHouseN;
                    break;

                case 200:
                    int point = Integer.parseInt(st.nextToken());

                    buildHouse(point);
                    break;

                case 300:
                    int idx = Integer.parseInt(st.nextToken()); // 철거해야하는 집 번호

                    destroyHouse(idx);

                    break;

                case 400:
                    int antN = Integer.parseInt(st.nextToken());    // 정찰 개미의 수
                    int minTime = search(antN);
                    bw.append(minTime + "\n");
                    // System.out.println();
                    break;
            }
        }

        br.close();
        bw.flush();
        bw.close();
    }

    private static void buildHouse (int point) {
        ants.add(point);
        isDestroyed.add(false);
        lastIdx++;
        houseN++;
    }

    private static void destroyHouse(int idx) {
        isDestroyed.set(idx, true);
        // System.out.println("-----");
        // for (boolean b: isDestroyed) {
        //     System.out.print(b + " ");
        // }
        // System.out.println();
        // System.out.println();
        houseN--;
    }

    private static int search(int antN) {
        if (antN == houseN) {
            return 0;
        }

        int s = 0;
        int e = ants.get(lastIdx);

        while (s <= e) {
            int m = s + (e -s) / 2;

            // antN의 정찰개미로 m초만에 정찰을 할 수있는지 확인
            boolean allSafe = findAllSafe(m, antN);

            // System.out.println("m: " + m);
            // System.out.println("allSafe: " + allSafe);
            // System.out.println();

            if (allSafe) {
                e = m - 1;
            } else {
                s = m + 1;
            }
        }

        return s;
    }

    private static boolean findAllSafe(int time, int antN) {
        int sumTime = 0;    // 현재 정찰 개미로 이동한 시간
        int usedAntN = 0;   // 사용된 정찰 개미의 수

        int frontIdx = 0;
        for (int i = 1; i <= lastIdx; i++) {
            // 정찰 개미를 모두 사용한 경우
            if (antN < usedAntN) {
                return false;
            }

            if (isDestroyed.get(i)) {
                continue;
            }

            // i에서 정찰 시작
            if (frontIdx == 0) {
                frontIdx = i;
                usedAntN++;
                continue;
            }

            int curPoint = ants.get(frontIdx);
            int nextPoint = ants.get(i);

            int dist = nextPoint - curPoint;
            // System.out.println("dist: "+ dist);

            if (dist > time) {
                usedAntN++;
                frontIdx = i;
            } else if (dist == time) {

                frontIdx = 0;
            } else {

            }
        }

        if (usedAntN > antN) {
            return false;
        }
        return true;
    }
}