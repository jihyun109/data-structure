package my.P2156_포도주시식;

import java.util.Scanner;

public class P2156_포도주시식 {
    public static void main(String[] args) {
        // init start
        Scanner sc = new Scanner(System.in);

        int totalWineGlassN = sc.nextInt();  // 총 포도주잔의 갯수
        int[] glassOfWine = new int[totalWineGlassN + 1];    // 포도주 잔의 포도주 양 저장 배열

        // 각 잔의 포도주 양 입력받기
        for (int i = 1; i <= totalWineGlassN; i++) {
            glassOfWine[i] = sc.nextInt();
        }

        // init end
        sc.close();

        // 가장 많이 마실 수 있는 포도주의 양 구하기
        int[][] sumWine = new int[totalWineGlassN + 1][3];   // 포도주를 연속으로 마신 횟수에 따른 최대 마신 포도주의 양 저장 배열

        // base case
        sumWine[1][1] = glassOfWine[1];

        for (int glassN = 2; glassN <= totalWineGlassN; glassN++) {
            // glassN 번쨰 와인을 마시지 않았을 떄 최대로 마신 포도주의 양 구하기
            for (int i = 0; i < 3; i++) {
                sumWine[glassN][0] = Math.max(sumWine[glassN - 1][i], sumWine[glassN][0]);
            }

            sumWine[glassN][1] = sumWine[glassN - 1][0] + glassOfWine[glassN];
            sumWine[glassN][2] = sumWine[glassN - 1][1] + glassOfWine[glassN];
        }

        int answer = 0;
        for (int i = 0; i < 3; i++) {
            answer = Math.max(answer, sumWine[totalWineGlassN][i]);
        }

        System.out.println(answer);
    }
}
