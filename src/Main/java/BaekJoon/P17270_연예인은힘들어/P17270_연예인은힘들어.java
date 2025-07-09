package BaekJoon.P17270_연예인은힘들어;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P17270_연예인은힘들어 {
    private static int meetingPlaceN;
    private static List<List<Load>> loads;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        meetingPlaceN = Integer.parseInt(st.nextToken());   // 약속장소 후보 수
        int loadN = Integer.parseInt(st.nextToken());  // 약속장소들을 잇는 길의 수
        loads = new ArrayList<>(meetingPlaceN + 1);  // 약속장소를 잇는 길의 정보 저장 리스트
        for (int i = 1; i <= loadN; i++) {
            loads.set(i, new ArrayList<>());
        }

        // 약속장소를 잇는 길 정보 입력받기
        for (int i = 0; i < loadN; i++) {
            st = new StringTokenizer(br.readLine());
            int point1 = Integer.parseInt(st.nextToken());  // 길을 잇는 두 약속 장소의 번호
            int point2 = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());    // 길을 지나는데 걸리는 시간

            loads.get(point1).add(new Load(point2, time));
        }

        st = new StringTokenizer(br.readLine());
        int jihunPos = Integer.parseInt(st.nextToken());    // 지헌이가 위치한 장소
        int sunghaPos = Integer.parseInt(st.nextToken());   // 성하가 위치한 장소
        br.close();

        int newMeetingPlace = findNewMeetingPlace(jihunPos, sunghaPos);    // 새로운 약속 장소

        // 답 출력
        System.out.println(newMeetingPlace);
    }

    // 새로운 악쇽 장소 찾기
    private static int findNewMeetingPlace(int jihunPos, int sunghaPos) {
        // 지헌이가 있는 위치와 성하가 있는 위치에서 모든 약속장소 후보들까지의 최단 시간 구하기
        int[] jihunMinTime = new int[meetingPlaceN + 1];    // jihunMinTime[i]: 지헌이의 위치에서 i장소 까지의 최단 시간
        int[] sunghaMinTime = new int[meetingPlaceN + 1];

        findMinTimes(jihunPos, jihunMinTime, sunghaPos); // jihunMinTime 배열 채우기
        findMinTimes(sunghaPos, sunghaMinTime, jihunPos); // sunghaMinTime 배열 채우기

        // 약속장소 고르기
        int meetingPlace = chooseMeetingPlace(jihunMinTime, sunghaMinTime);

        return meetingPlace;
    }

    // 약속장소 고르기
    private static int chooseMeetingPlace(int[] minTime1, int[] minTime2) {

        return 0;
    }

    // startPoint 에서 otherPos 를 제외한 모든 장소까지의 최소 시간 구하기
    private static void findMinTimes(int startPos, int[] minTime, int otherPos) {
        // minTime 배열 최댓값으로 초기화
        for (int i = 1; i <= meetingPlaceN; i++) {
            minTime[i] = Integer.MAX_VALUE;
        }

        minTime[startPos] = 0;

        Queue<Integer> que = new LinkedList<>();    // 탐색 시 사용할 큐
        boolean[] visited = new boolean[meetingPlaceN + 1];
        que.add(startPos);
        visited[startPos] = true;
        while (!que.isEmpty()) {
            int cur = que.poll();
            visited[cur] = true;

            // cur 에서 이어지는 모든 길 탐색
            for (Load load : loads.get(cur)) {
                int nextPlace = load.getEnd();
                int time = load.getTime();

                if (nextPlace == otherPos || nextPlace == startPos || visited[nextPlace]) {
                    continue;
                }

                minTime[nextPlace] = Math.min(minTime[nextPlace], minTime[cur] + time);
            }
        }
    }
}

class Load {
    private int end;    // 길의 끝에 위치한 장소
    private int time;   // 길을 지나는데 걸리는 시간

    public Load(int end, int time) {
        this.end = end;
        this.time = time;
    }

    public int getEnd() {
        return end;
    }

    public int getTime() {
        return time;
    }
}