package my.P17135_캐슬디펜스;

import java.util.*;

public class P17135_캐슬디펜스 {
    static int row;
    static int column;
    static int attackLimit;
    static int[][] map;
    static int sumEnemyN;
    static int curEnemyN;
    static int[] dx = new int[] {0, -1, 0};
    static int[] dy = new int[] {-1, 0, 1};

    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        row = sc.nextInt();   // 행의 수
        column = sc.nextInt();   // 열의 수
        attackLimit = sc.nextInt();   // 공격 거리 제한
        map = new int[row+1][column];   // 게임이 진행되는 격자판
        List<Coordinate> enemyLoc = new LinkedList<>();
        sumEnemyN = 0;  // 총 적군의 수
        curEnemyN = 0;  // 현재 적군의 수

        // map(격자판)의 상태 입력받기
        for(int r = 0; r < row; r++) {
            for(int c = 0; c < column; c++) {
                int input = sc.nextInt();   // 입력받은 값
                map[r][c] = input;

                // 입력받은 칸에 적군이 있다면 적군의 수를 증가시키고, 위치 저장
                if (input == 1) {
                    sumEnemyN++;
                    enemyLoc.add(new Coordinate(r, c));
                }
            }
        }

        curEnemyN = sumEnemyN;
        sc.close();

        // 궁수 3명 위치의 모든 경우에서 게임을 한 후 궁수의 공격으로 제거할 수 있는 최대 적의 수를 구함.
        int maxKilledEnemy = 0;  // 궁수가 제거할 수 있는 최대 적의 수 저장 배열

        for(int i = 0; i < (int)Math.pow(2,column); i++) {  // bit masking 으로 궁수가 3명이 있는 경우 구하기
            int archerN = 0;    // 궁수의 수
            int[] archerY = new int[3]; // 궁수의 Y좌표 위치 저장 배열

            for(int digit = 0; digit < column; digit++) {
                int tmp = (int)(1 << digit);    // i 와 bit 연산을 해 i의 digit 번째 자라가 1인지 겁사할 수

                // i의 digit 번째 수가 1이면 궁수의 Y좌표 저장
                if ((i & tmp) > 0) {

                    // 궁수의 수가 3보다 많으면 궁수의 수 초기화하고 break
                    if (archerN > 3) {
                        archerN = 0;
                        break;
                    }

                    archerY[archerN] = digit;   // 궁수의 Y좌표 저장
                    archerN++;  // 궁수의 수 업데이트
                }
            }

            // 궁수의 수가 3이면 game start
            if(archerN == 3) {
                int killedEnemyN = gameStart(archerY);
                maxKilledEnemy = Math.max(maxKilledEnemy, killedEnemyN);
            }
        }
        System.out.println(maxKilledEnemy);
    }

    // 궁수의 위치를 archerY에 두고 게임을 진행해 궁수가 제거한 적의 수를 구하는 메서드
    private static int gameStart(int[] archerY) {
        int killedEnemyN = 0;   // 궁수가 제거한 적의 수

        while(curEnemyN > 0) {  // 적군이 아무도 남아있지 않을 때까지 반복
            Queue<Arrow> que = new LinkedList<>();

            // 궁수가 화살을 쏘기 전 화살 위치 que 에 add
            for (int i = 0; i < 3; i++) {
                que.add(new Arrow(row, archerY[i], 0));
            }

            // 제거할 적군 구하기
            Queue<Coordinate> willKill = new LinkedList<>();    // 제거할 적군 저장 큐
            while(!que.isEmpty()) {

                // 현재 화살의 x, y 좌표와 날아온 dist
                Arrow cur = que.poll();
                int curX = cur.x;
                int curY = cur.y;
                int curDist = cur.dist;

                // 궁수 3명이 각각 제거할 적군의 좌표 구하기
                for(int next = 0; next < 3; next++) {
                    int nextX = curX + dx[next];
                    int nextY = curY + dy[next];
                    int nextDist = curDist + 1;
                    boolean[][] visited = new boolean[row][column];

                    // next 위치에 적군이 있으면 (방문하지 않고, 범위 내)
                    if (!visited[nextX][nextY] && nextX >= 0 && nextY >= 0 && nextX < row && nextY < column) {
                        if (map[nextX][nextY] == 1) {
                            willKill.add(new Coordinate(nextX, nextY));
                            for(int j = 0; j < row; j++) {
                                Arrays.fill(visited[row], false);
                            }
                            continue;
                        } else {    // 적군이 없으면

                        }
                    }
                }

            }

        }


        return killedEnemyN;
    }


    static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Arrow {
        int x;
        int y;
        int dist;

        public Arrow(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

}


