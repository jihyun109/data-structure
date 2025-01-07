package BaekJoon.P14728_벼락치기;

import java.util.Scanner;

public class P14728_벼락치기 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   // 단원 개수
        int T = sc.nextInt();   // 시험까지 공부할 수 있는 시간

        // 각 단원의 정보 입력받기
        Unit[] units = new Unit[N + 1]; // 단원 정보 저장 배열
        for (int i = 1; i <= N; i++) {
            int expectedTime = sc.nextInt();
            int score = sc.nextInt();
            units[i] = new Unit(expectedTime, score);
        }
        sc.close();

        // T 시간을 공부해 얻을 수 있는 최대 점수 구하기
        int[][] maxScore = new int[N + 1][T + 1];
        for (int unit = 1; unit <= N; unit++) {  // maxScore 배열을 채워가며 최대 점수 찾기
            for (int time = 0; time <= T; time++) {
                int expectedTime = units[unit].getExpectedTIme();   // 현재 과목의 예상 공부 시간
                int score = units[unit].getScore(); // 현재 과목의 배점
                int spareTime = time - expectedTime;    // 현재 시간(time)에 현재 단원(unit)을 공부하고 남는 시간
                if (spareTime >= 0) {   // 현재 단원을 공부할 수 있는 시간이라면
                    maxScore[unit][time] = Math.max(maxScore[unit - 1][time], maxScore[unit - 1][time - expectedTime] + score);
                } else {
                    maxScore[unit][time] = maxScore[unit - 1][time];
                }
            }
        }

        // 답 출력
        System.out.println(maxScore[N][T]);
    }
}

class Unit {
    private int expectedTIme;
    private int score;

    public Unit(int expectedTIme, int score) {
        this.expectedTIme = expectedTIme;
        this.score = score;
    }

    public int getExpectedTIme() {
        return expectedTIme;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
