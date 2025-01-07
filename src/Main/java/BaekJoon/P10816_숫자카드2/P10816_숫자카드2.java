package BaekJoon.P10816_숫자카드2;

import java.io.*;
import java.util.*;

public class P10816_숫자카드2 {
    public static void main(String[] args) throws IOException {
        // 숫자 카드 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());  // 숫자 카드의 개수

        String cards = br.readLine();   // 숫자 카드에 적힌 수
        StringTokenizer st = new StringTokenizer(cards);
        HashMap<Integer, Integer> numOfCard = new HashMap<>(N); // key: 숫자 카드의 번호, value: 해당 숫자 카드의 수

        for (int i = 0; i < N; i++) {
            int card = Integer.parseInt(st.nextToken());
            numOfCard.put(card, numOfCard.getOrDefault(card, 0) + 1);
        }

        // M개의 정수 입력 받기
        int M = Integer.parseInt(br.readLine());
        String nums = br.readLine();    // M개의 정수
        st = new StringTokenizer(nums);

        // 각 정수에 해당하는 숫자 카드 개수 구하기
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out)); // 구하는 값 저장
        for (int i = 0; i < M; i++) {
            int num = Integer.parseInt(st.nextToken());
//            System.out.println(numOfCard.getOrDefault(num, 0));
            bw.write(numOfCard.getOrDefault(num, 0) + " ");
        }

        // 답 출력
        bw.flush();
        bw.close();
    }
}
