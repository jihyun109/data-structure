package BaekJoon.P17135_캐슬디펜스;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class P17135_캐슬디펜스 {
    private static int R;
    private static int C;
    private static int D;
    private static int farthestEnemyR;
    private static int[] dx = new int[]{0, -1, 0};
    private static int[] dy = new int[]{-1, 0, 1};
    private static int[][] board;
    private static int[][] backupBoard;

    public static void main(String[] args) throws IOException {
        // 데이터 입력받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());   // 행의 수
        C = Integer.parseInt(st.nextToken());   // 열의 수
        D = Integer.parseInt(st.nextToken());   // 궁수의 공격 거리 제한

        board = new int[R][C];  // 게임판의 정보 저장 배열
        backupBoard = new int[R][C];  // 게임판의 정보 저장 배열
        farthestEnemyR = R; // 성에서 가장 먼 적의 행 값
        boolean findFarthestEnemy = false;  // 성에서 가장 먼 적을 찾았는지 나타냄
        for (int r = 0; r < R; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < C; c++) {
                int hasEnemy = Integer.parseInt(st.nextToken());    // 적이 있으면 1, 없으면 0
                backupBoard[r][c] = hasEnemy;

                if (hasEnemy == 1) {
                    if (!findFarthestEnemy) {   // 성에서 가장 먼 적을 찾지 못한 경우
                        farthestEnemyR = r;
                        findFarthestEnemy = true;
                    }
                }
            }
        }

        br.close();

        // 궁수로 인해 제거할 수 있는 적의 최대 수 구하기
        int maxEnemyNRemoveByArcher = findMaxEnemyNRemoveByArcher();
        System.out.println(maxEnemyNRemoveByArcher);
    }

    private static int findMaxEnemyNRemoveByArcher() {
        int maxEnemyNRemoveByArcher = 0;    // 궁수들에 의해 제거될 수 있는 최대 적의 수
        // 궁수가 위치할 수 있는 모든 위치의 조합에서 게임 실행.
        for (int archer1 = 0; archer1 < C; archer1++) {
            for (int archer2 = archer1 + 1; archer2 < C; archer2++) {
                for (int archer3 = archer2 + 1; archer3 < C; archer3++) {
                    // 3명의 궁수가 archer1, archer2, archer3에 위치할 때 궁수에 의해 제거되는 적의 수 구하기
                    for (int r = 0; r < R; r++) {
                        board[r] = Arrays.copyOf(backupBoard[r], C);
                    }
                    int[] archersPos = new int[]{archer1, archer2, archer3};    // 3명의 궁수의 위치
                    int removedEnemyN = findRemovedEnemyN(archersPos);

                    // maxEnemyNRemoveByArcher 업데이트
                    maxEnemyNRemoveByArcher = Math.max(maxEnemyNRemoveByArcher, removedEnemyN);
                }
            }
        }
        return maxEnemyNRemoveByArcher;
    }

    private static int findRemovedEnemyN(int[] archersPos) {
        int totalGameTurn = R - farthestEnemyR; // 총 수행하는 게임 turn 수 (한 turn: 공격 -> 적 이동)
        int removedEnemyNByArcher = 0;  // 궁수에 의해 제거된 적의 수
        for (int turn = 0; turn < totalGameTurn; turn++) {  // totalGameTurn 만큼 수행

            // 공격
            // archer1, archer2, archer3 이 공격할 적 찾기
            Set<Position> enemiesToAttack = new HashSet<>();    // 공격할 적 저장 맵(중복 제거)
            for (int i = 0; i < 3; i++) {
                Position enemyToAttack = findEnemyToAttack(archersPos[i]); // 해당 궁수가 공격할 적의 좌표 찾기
                if (enemyToAttack != null) {
                    enemiesToAttack.add(enemyToAttack);
                }
            }

            // 공격할 적 제거
            removedEnemyNByArcher += enemiesToAttack.size();    // 궁수에 의해 제거된 적의 수 업데이트
            removeEnemiesToAttack(enemiesToAttack);

            // 적 이동
            moveEnemies();
        }

        return removedEnemyNByArcher;
    }

    // 공격할 적들 제거
    private static void removeEnemiesToAttack(Set<Position> enemiesToAttack) {
        for (Position enemy : enemiesToAttack) {
            int x = enemy.getX();
            int y = enemy.getY();

            board[x][y] = 0;
        }
    }

    // archer 가 공격할 적을 찾고 좌표 return
    private static Position findEnemyToAttack(int archer) {
        Queue<Status> que = new LinkedList<>();
        boolean[][] visited = new boolean[R][C];
        que.add(new Status(new Position(R, archer), 0));

        while (!que.isEmpty()) {
            Status cur = que.poll();

            for (int d = 0; d < 3; d++) {
                int nextX = cur.getPosition().getX() + dx[d];
                int nextY = cur.getPosition().getY() + dy[d];
                int nextDist = cur.getDist() + 1;

                if (nextDist > D) { // 궁수의 공격 가능 거리를 벗어난 경우 return null
                    return null;
                }

                if (nextX >= 0 && nextX < R && nextY >= 0 && nextY < C) {   // next 좌표가 게임판 범위 내에 위치하는지 확인
                    if (!visited[nextX][nextY]) {   // 방문 확인
                        // next 에 적이 있는 경우 현재 좌표 return
                        if (board[nextX][nextY] == 1) {
                            return new Position(nextX, nextY);
                        }

                        // 적이 없는 경우
                        visited[nextX][nextY] = true;
                        que.add(new Status(new Position(nextX, nextY), nextDist));
                    }
                }
            }
        }

        return null;
    }

    private static void moveEnemies() {
        for (int y = 0; y < C; y++) {
            for (int x = R - 1; x >= 0; x--) {
                int nextX = x + 1;

                if (board[x][y] == 1) {
                    board[x][y] = 0;
                    if (nextX < R) {
                        board[nextX][y] = 1;
                    }
                }
            }
        }

    }

}


class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) && Objects.equals(y, position.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Status {
    Position position;
    int dist;   // 궁수에서 현재 위치까지의 거리

    public Status(Position position, int dist) {
        this.position = position;
        this.dist = dist;
    }

    public Position getPosition() {
        return position;
    }

    public int getDist() {
        return dist;
    }
}