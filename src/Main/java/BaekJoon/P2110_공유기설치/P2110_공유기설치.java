package BaekJoon.P2110_공유기설치;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class P2110_공유기설치 {
    private static int[] houses;
    private static int houseN;
    private static int routerN;
    private static int maxCoordination;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        houseN = Integer.parseInt(st.nextToken());  // 집의 개수
        routerN = Integer.parseInt(st.nextToken()); // 공유기 수

        // 집들의 좌표 입력받기
        houses = new int[houseN]; // 집들의 좌표 정보 저장 배열
        for (int i = 0; i < houseN; i++) {
            houses[i] = Integer.parseInt(br.readLine());
        }
        br.close();

        // 집들의 좌표 정렬
        Arrays.sort(houses);
        maxCoordination = houses[houseN - 1];   // 최대 좌표값

        // 공유기를 routerN개 설치했을 때 가장 인접한 두 공유기 사이의 최대 거리 구하기
        int maxDist = findMaxDist();

        System.out.println(maxDist);
    }

    private static int findMaxDist() {
        // 이분탐색으로 maxDist 찾기
        int l = 0;  // 탐색할 maxDist 범위의 왼쪽
        int r = maxCoordination - houses[0] + 1;  // 탐색할 maxDist 범위의 오른

        while (l < r) {
            int m = (l + r) / 2;

            // 공유기를 routerN개 설치했을 때 m이 집 사이의 최소 거리가 될 수 있는지 확인
            boolean canInstall = canInstall(m);

            if (canInstall) {   // 최소거리가 될 수 있는 경우
                l = m + 1;
            } else {
                r = m;
            }
        }

        return l - 1;
    }

    private static boolean canInstall(int dist) {
        int installedN = 1; // 설치된 공유기의 개수 / 첫번째 집에 설치
        int h1 = 0;
        for (int h2 = 1; h2 < houseN; h2++) {
            if (installedN == routerN) {
                return true;
            }

            // h1과 c2 사이의 거리가 dist 보다 길거나 같은 경우 공유기를 설치.
            if (houses[h2] - houses[h1] >= dist) {
                installedN++;
                h1 = h2;
            }
        }

        if (installedN < routerN) {
            return false;
        } else {
            return true;
        }
    }
}
