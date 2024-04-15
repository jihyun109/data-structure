package my.P14502_연구소_bitmasking;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;

public class P14502_연구소_bitmasking {
    static Queue<int[]> que;
    static Queue<int[]> firstInfected;

    public static void main(String[] args) {
        que = new LinkedList<>();  // 바이러스가 있는 방 저장 큐(BFS 메서드에서 사용할 큐)
        firstInfected = new LinkedList<>();     // 바이러스가 퍼지기 전 이미 바이러스가 있던 방 위치 저장 큐(다른 조합을 시도할 떄 que 에 다시 넣어주기 위한 것)

        // init start
        Scanner sc = new Scanner(System.in);
        int row = sc.nextInt(); // 지도의 행 개수
        int column = sc.nextInt();  // 지도의 열 개수

        int[][] lab = new int[row][column];
        int[] labBit = new int[row];   // 연구소 각 행의 정보를 bit 로 저장한 수 저장 배열

        // 연구소 정보 입력받기(lab, labBit 배열 채우기)
        int blank = 0;  // 처음 빈 칸의 개수
        int firstInfectedN = 0 ;    // 바이러스가 퍼지기 전 이미 바이러스가 있던 방의 개수
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int status = sc.nextInt();  // 입력받은 방의 상태

                lab[i][j] = status;

                if (status == 0) {
                    blank++;
                } else if (status == 1) {
                    labBit[i] = labBit[i] & (int)Math.pow(2, column - j);   // 연구소의 정보를 bit 에 저장
                } else if (status == 2) {
                    labBit[i] = labBit[i] & (int)Math.pow(2, column - j);   // 연구소의 정보를 bit 에 저장

                    que.offer(new int[]{i, j}); // 바이러스가 있는 방의 x, y 좌표를 que 에 저장

                    firstInfected.offer(new int[]{i, j});
                    firstInfectedN++;
                }
            }
        }

        // init end
        sc.close();

        // 벽 3개를 세우는 모든 조합에서 바이러스를 퍼트려(BFS) 안전 영역을 구한 후 안전영역의 최대 크기 구하기
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {

            }
        }
    }

}
