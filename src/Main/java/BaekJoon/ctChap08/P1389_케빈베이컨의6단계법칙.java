package ctChap08;
// 플로이드-워셜 알고리즘을 이용해 kevinBakerGame배열에 kevinBaker게임을 했을 떄 나오는 단계를 저장하고, kevinBaker배열에 모두의 kevinBaker값을 저장한 후 최소인 사람을 찾는다..
import java.util.Scanner;

public class P1389_케빈베이컨의6단계법칙 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   // 유저의 수
        int M = sc.nextInt();   // 친구 관계의 수
        int[][] kevinBakerGame = new int[N + 1][N + 1]; // 케빈 베이커 게임 결과 저장 배열

        // kevinBaker 배열 초기화
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    kevinBakerGame[i][j] = 0;
                } else {
                    kevinBakerGame[i][j] = 10000001;
                }
            }
        }

        // 친구 관계를 kevinBaker 배열에 입력 받기
        for (int i = 0; i < M; i++) {
            int a = sc.nextInt();   // 친구 a
            int b = sc.nextInt();   // 친구 b
            kevinBakerGame[a][b] = 1;
            kevinBakerGame[b][a] = 1;
        }

        // 플로이드-워셜 알고리즘 수행
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (kevinBakerGame[i][j] > kevinBakerGame[i][k] + kevinBakerGame[k][j]) {
                        kevinBakerGame[i][j] = kevinBakerGame[i][k] + kevinBakerGame[k][j];
                    }
                }
            }
        }

        // 케빈베이커 값 구하기
        int kevinBaker[] = new int[N + 1];  // kevinBaker값 저장 배열
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                kevinBaker[i] += kevinBakerGame[i][j];
            }
        }

        // kevinBaker 수가 가장 작은 사람 찾기
        int minKevinBaker = Integer.MAX_VALUE;    // kevinBaker의 최솟값
        int answer = 0; // 케빈베이커 수가 가장 작은 사람
        for (int i = 1; i <= N; i++) {
            if (minKevinBaker > kevinBaker[i]) {
                answer = i;
                minKevinBaker = kevinBaker[i];
            } else if (minKevinBaker == kevinBaker[i]) {
                answer = answer < i ? answer : i;
            }
        }

        System.out.println(answer);
    }
}
