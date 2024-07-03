package my.P17135_캐슬디펜스;

import java.nio.charset.CoderResult;
import java.util.*;

public class P17135_캐슬디펜스 {
    static int N;
    static int M;
    static int attackLimit;
    static int[][] map;
    static int[][] thisMap;
    static int sumEnemiesN;
    static int thisSumEnemiesN;
    static Queue<Coordinate> enemiesLoc;
    static Queue<Coordinate> thisEnemiesLoc;
    static int[] dx = {0, -1, 0};
    static int[] dy = {-1, 0, 1};

    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();   // 격차판의 행 수
        M = sc.nextInt();   // 격자판의 열 수
        attackLimit = sc.nextInt(); // 공격 거리 제한

        // 격자판 정보 입력받기
        map = new int[N + 1][M];  // default 격자판 정보 저장 배열
        thisMap = new int[N + 1][M];
        sumEnemiesN = 0;    // default 총 적의 수
        enemiesLoc = new LinkedList<>();  // default 적의 위치 좌표 저장 큐
        thisEnemiesLoc = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int input = sc.nextInt();   // 입력 받은 값
                map[i][j] = input;

                // 입력 받은 좌표에 적이 있으면
                if (input == 1) {
                    sumEnemiesN++;
                    enemiesLoc.add(new Coordinate(i, j));
                }
            }
        }

        // init end
        sc.close();

        int max = 0;    // 궁수가 제거할 수 있는 적의 최대 수

        // 궁수가 3명 위치한 모든 조합에서 제거한 적의 수 구하기
        for (int i = 0; i < Math.pow(2, M); i++) {

            // 현재 경우에서 archer 의 수가 3인지 확인
            int[] archerY = new int[3]; // archer 3명의 Y좌표 저장 배열
            archerY = findArcherY(i);   // archer 의 수가 3이면 y좌표 배열 return, 아니면 null 리턴

            // archer 의 수가 3이면 game start
            int killedEnemyN = 0;   // 아번 게임에서 archer 에게 제거된 적의 수
            if (archerY != null) {

                // thisNap 배열 초기화
                for (int k = 0; k < N; k++) {
                    thisMap[k] = map[k].clone();  // 현재 게임 사용될 map
                }

                // thisSumEnemiesN, thisEnemiesLoc
                thisSumEnemiesN = sumEnemiesN;  // 현재 게임 사용될 sumEnemiesN
                thisEnemiesLoc = new LinkedList<>(enemiesLoc);  // 현재 게임에 사용될 enemiesLoc

                // game start
                killedEnemyN = gameStart(archerY);

                // archer 에게 제거된 적의 최대 수 업데이트
                max = Math.max(max, killedEnemyN);
            }


        }

        // 답 출력
        System.out.println(max);
    }

    // 게임 진행. 현재 게임에서 archer 에게 제거된 적의 수 return
    private static int gameStart(int[] archerY) {
        int sumKilledEnemiesN = 0;  // 이번 게임에서 archer 에게 제거된 총 적의 수

        // 적이 모두 남아있지 않을 때까지 공격 -> 적 이동 반복
        while (thisSumEnemiesN != 0) {

            // 공격
            int killedEnemiesN = attack(archerY);   // 공격해서 제거된 적의 수

            // sumKilledEnemiesN 업데이트
            sumKilledEnemiesN += killedEnemiesN;

            // 이동
            moveEnemies();
        }

        return sumKilledEnemiesN;
    }

    // 적 이동
    private static void moveEnemies() {
        int killedEnemiesByMove = 0;    // 이동하다가 제거된 적의 수

        for (int i = 0; i < thisSumEnemiesN; i++) {
            Coordinate enemy = thisEnemiesLoc.poll();
            int nextX = enemy.x + 1;
            int nextY = enemy.y;

            thisMap[enemy.x][enemy.y] = 0;  // 이동

            if (nextX >= N) {   // 이동했을 때 위치가 성 안이면
                killedEnemiesByMove++;
            } else {    // 아니면 이동
                thisMap[nextX][nextY] = 1;
                thisEnemiesLoc.add(new Coordinate(nextX, nextY));
            }
        }
        thisSumEnemiesN -= killedEnemiesByMove;
    }

    // 적 공격
    private static int attack(int[] archerY) {

        // 제거할 적 선택
        Coordinate[] selectedEnemiesY = selectEnemiesToKill(archerY);  // 제거할 적의 좌표 저장 배열

        // 선택한 적 제거
        int killedEnemiesN = killSelectedEnemies(selectedEnemiesY); // 제거된 적의 수

        return killedEnemiesN;
    }

    // 선택한 적 제거
    private static int killSelectedEnemies(Coordinate[] selectedEnemies) {
        int killedEnemyN = 0;   // 제거된 적의 수

        for (int i = 0; i < 3; i++) {
            // 적의 x, y 좌표
            Coordinate selectedEnemy = selectedEnemies[i];
            if (selectedEnemy != null) {
                int enemyX = selectedEnemy.x;
                int enemyY = selectedEnemy.y;

                if (thisMap[enemyX][enemyY] == 1) {    // 제거
                    thisMap[enemyX][enemyY] = 0;
                    killedEnemyN++;

                    // thisEnemiesLoc 에서 제거
                    for (int enemy = 0; enemy < thisSumEnemiesN; enemy++) {
                        Coordinate thisEnemy = thisEnemiesLoc.poll();
                        if (thisEnemy.x != enemyX || thisEnemy.y != enemyY) {   // 선택된 적이 아니면
                            thisEnemiesLoc.add(thisEnemy);
                        } else {    // 선택된 적이면
                            thisSumEnemiesN--;
                        }
                    }
                }
            }
        }

        return killedEnemyN;
    }

    // BFS 로 제거할 적 찾기
    private static Coordinate findEnemy(int archerY) {
        PriorityQueue<Arrow> pq = new PriorityQueue<>();
        boolean[][] visited = new boolean[N + 1][M];    // 방문 처리 배열

        pq.add(new Arrow(N, archerY, 0));   // 궁수의 위치 pq에 add
        visited[N][archerY] = true;

        while (!pq.isEmpty()) {
            Arrow cur = pq.poll();  // 현재 화살의 상태

            // 화살의 next
            for (int i = 0; i < 3; i++) {
                int nextX = cur.x + dx[i];
                int nextY = cur.y + dy[i];
                int nextDist = cur.dist + 1;

                // 다음 화살의 이동 거리가 공격 거리 제한보다 크면 return null
                if (nextDist > attackLimit) {
                    return null;
                }

                // next 가 범위 밖이거나 이미 방문했으면 continue
                if (nextX < 0 || nextX >= N || nextY < 0 || nextY >= M || visited[nextX][nextY]) {
                    continue;
                }

                if (thisMap[nextX][nextY] == 1) {   // next 에 적이 있으면
                    return new Coordinate(nextX, nextY);
                } else {    // 적이 없으면
                    pq.add(new Arrow(nextX, nextY, nextDist));
                    visited[nextX][nextY] = true;
                }
            }
        }
        return null;
    }

    // 제거할 적 선택
    private static Coordinate[] selectEnemiesToKill(int[] archerY) {
        Coordinate[] selectedEnemies = new Coordinate[3];

        // 각 archer 가 공격할 적을 찾아 selectedEnemies 에 저장
        for (int i = 0; i < 3; i++) {
            selectedEnemies[i] = findEnemy(archerY[i]);
        }

        return selectedEnemies;
    }

    // archer 가 3명일 때 y좌표 배열을 return 하는 메서드
    private static int[] findArcherY(int i) {
        int archerN = 0;    // n에 있는 궁수의 수
        int[] archerY = new int[3]; // 궁수의 Y좌표 위치 저장 배열

        // i 에 archer 수(1)가 몇개인지 확인
        for (int digit = 0; digit < M; digit++) {
            int tmp = (int) (1 << digit);    // i 와 bit 연산을 해 i 의 digit 번째 자라가 1인지 검사할 수

            // n의 digit 번째 수가 1이면 궁수의 Y좌표 저장
            if ((i & tmp) > 0) {    // digit 번째 수가 1이면 궁수의 수 증가
                archerN++;

                // 궁수의 수가 3보다 많으면 break
                if (archerN > 3) {
                    return null;
                } else { // 작거나 같으면 궁수의 Y좌표 저장
                    archerY[archerN - 1] = digit;
                }
            }
        }

        // archer 의 수가 3이면 archer 의 y좌표 배열 return, 아니면 null return
        if (archerN == 3) {
            return archerY;
        } else {
            return null;
        }
    }

    // 좌표 클래스
    static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


    // 궁수가 쏜 화살 정보 클래스
    static class Arrow implements Comparable<Arrow> {
        int x;
        int y;
        int dist;

        public Arrow(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public int compareTo(Arrow o) {
            if (this.dist > o.dist) {
                return 1;
            } else if (this.dist < o.dist) {
                return -1;
            } else {
                return Integer.compare(this.y, o.y);
            }
        }
    }
}
