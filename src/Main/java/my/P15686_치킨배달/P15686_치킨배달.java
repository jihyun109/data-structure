package my.P15686_치킨배달;

import java.util.Scanner;

public class P15686_치킨배달 {

    static int[][] house;
    static int houseN;
    static int chickenN;
    static int[][] selectedChicken;

    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        int citySize = sc.nextInt();    // 도시의 크기: citySize X citySize
        chickenN = sc.nextInt();    // 도시에 남길 치킨집 수

        // 도시의 정보 입력받기
        int sumChickenN = 0;    // 총 치킨집 수 저장 변수
        houseN = 0; // 집의 수 저장 변수
        int[][] chicken = new int[13][2];    // 왼->오 위->아래 방향으로 i번째 치킨집의 좌표 정보 저장 배열
        house = new int[2 * citySize][2];  // 모든 집의 좌표 저장 배열

        for (int r = 0; r < citySize; r++) {
            for (int c = 0; c < citySize; c++) {
                int input = sc.nextInt();   // 입력 받은 값

                if (input == 1) {   // input 이 1(집)일 때 house 배열에 집의 좌표 저장
                    house[houseN++] = new int[]{r, c};
                } else if (input == 2) {    // input 이 2(치킨집)일 때 chicken 배열에 치킨집의 좌표 저장
                    chicken[sumChickenN++] = new int[]{r, c};
                }
            }
        }

        // init end
        sc.close();

        // bitmasking 으로 도시에 남길 치킨집 조합 구하기
        int minCityChickenDist = Integer.MAX_VALUE; // 도시의 치킨거리의 최솟값(답)
        for (int chickenComb = 0; chickenComb < Math.pow(2, sumChickenN); chickenComb++) {  // chickenComb: 전체 치킨집 중 남길 치킨 집을 고르는 변수
            selectedChicken = new int[chickenN][2];  // 도시에 남길 치킨집 좌표 저장 배열
            int selectedChickenN = 0;   // 선택된 치킨집의 수

            // chickenComb 와 bit 연산을 해 digit 번째 자라가 1인지 겁사
            for (int digit = 0; digit < sumChickenN; digit++) {
                int tmp = (int) (1 << digit);   // chickenComb 와 bit 연산을 해 digit 번째 자라가 1인지 겁사할 수

                // chickenComb 의 digit 번쨰 수가 1이면 digit 번째 치킨집을 도시에 남기기로 선택
                if ((chickenComb & tmp) > 0) {

                    // 이미 chickenN(도시에 남길 치킨짐 개수)만큼 치킨집을 선택했는데 digit 번째 수가 또 1이라면 초과이므로 break
                    if (selectedChickenN == chickenN) {
                        break;
                    }

                    // digit 번째 치킨집을 selectedChicken 배열에 저장
                    selectedChicken[selectedChickenN++] = new int[]{chicken[digit][0], chicken[digit][1]};
                }
            }

            // 총 선택한 치킨집의 수가 chickenN 개라면 도시의 치킨 거리 구하기
            int cityChickenDist = 0;
            if (selectedChickenN == chickenN) {
                cityChickenDist = findCityChickenDist();

                // 도시의 치킨거리 최솟값 업데이트
                minCityChickenDist = Math.min(minCityChickenDist, cityChickenDist);
            }
        }

        // 답 출력
        System.out.println(minCityChickenDist);
    }

    // 도시의 치킨 거리 구하는 메서드
    private static int findCityChickenDist() {
        int cityChickenDist = 0;    // 도시의 치킨 거리

        // 모든 집의 치킨거리를 구해 도시의 치킨거리 구하기
        for (int h = 0; h < houseN; h++) {
            int chickenDist = Integer.MAX_VALUE;    // h 번째 집의 치킨 거리

            // h번째 집과 모든 치킨집과의 거리를 비교해 h번째 집의 치킨거리 구하기
            for (int c = 0; c < chickenN; c++) {
                // h번째 집과 c번째 치킨집 사이의 거리(|r1-r2| + |c1-c2|)
                int dist = Math.abs(house[h][0] - selectedChicken[c][0]) + Math.abs(house[h][1] - selectedChicken[c][1]);

                // 치킨 거리 값 업데이트
                chickenDist = Math.min(chickenDist, dist);
            }

            // h번쨰 집의 치킨거리를 도시의 치킨거리에 더하기
            cityChickenDist += chickenDist;
        }

        return cityChickenDist;
    }
}
