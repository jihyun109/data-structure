package my.P1414;

import java.util.PriorityQueue;
import java.util.Scanner;

public class P1414_불우이웃돕기 {

    static PriorityQueue<LAN> pq;
    static int computerN;
    static int[] parentComp;

    public static void main(String[] args) {
        pq = new PriorityQueue<>();

        // init start
        Scanner sc = new Scanner(System.in);

        computerN = sc.nextInt();   // 컴퓨터의 개수
        parentComp = new int[computerN];    // 대표 컴퓨터 번호 저장 배열

        // parentComp 배열 초기화
        for (int i = 0; i < computerN; i++) {
            parentComp[i] = i;
        }
        int allLanSum = 0;  // 모든 랜선 길이의 합

        // 랜선의 길이를 입력받아 변환해 allLanSum 에 더하고, 자기 자신과 연결되어있지 않은 랜선의 정보를 pq에 add
        for (int i = 0; i < computerN; i++) {
            String input = sc.next();   // 입력받은 문자열
            for (int j = 0; j < computerN; j++) {
                char lanChar = input.charAt(j);    // 입력받은 랜선 길이 문자
                int lanLength = convertToNum(lanChar);  // 입력받은 랜선 길이를 숫자로 변환한 수

                // 입력받은 랜선 길이 숫자로 변환
                if (lanLength != 0) {

                    // 모든 랜선 길이의 합을 저장하는 변수에 입력받은 랜선 길이 더하기
                    allLanSum += lanLength;

                    // 입력받은 랜선이 다른 컴퓨터와 연결되어 있으면 pq에 랜선 정보 add
                    if (i != j) {
                        pq.add(new LAN(i, j, lanLength));
                    }
                }
            }
        }

        // init close
        sc.close();

        // 최소 신장 트리 알고리즘으로 모든 컴퓨터를 연결하는 랜선 길이의 합 최솟값 구하기
        int minLanSum = findMinLanSum();
        if (minLanSum == -1) {
            System.out.println(minLanSum);
        } else {
            System.out.println(allLanSum - minLanSum);
        }
    }

    private static int convertToNum(char lanChar) {
        int converted = 0;  // 변환된 숫자 저장 변수

        if (Character.isLowerCase(lanChar)) {   // lanChar 이 소문자이면
            converted = lanChar - 'a' + 1;
        } else if (Character.isUpperCase(lanChar)) {    // lanChar 이 대문자이면
            converted = lanChar - 'A' + 27;
        }
        return converted;
    }

    private static int findMinLanSum() {
        int relatedLan = 0;    // 연결에 쓰인 랜선 개수
        int minLanSum = 0;  // 모든 컴퓨터를 잇는 랜선 길이 합의 최솟값
        while (!pq.isEmpty()) {
            // 현재 랜선 정보
            LAN cur = pq.poll();
            int computer1 = cur.computer1;  // 현재 랜선에 연결된 컴퓨터1
            int computer2 = cur.computer2;  // 현재 랜선에 연결된 컴퓨터1
            int lanLength = cur.lanLength;  // 현재 랜선의 길이

            computer1 = find(computer1);
            computer2 = find(computer2);

            // 두 컴퓨터가 연결되어있지 않으면
            if (computer1 != computer2) {
                union(computer1, computer2);   // 두 컴퓨터 연결
                relatedLan += 1;    // 연결에 사용된 랜선 개수 +1
                minLanSum += lanLength; // minLanSum 에 relatedLan 더하기
            }

            // 모든 컴퓨터가 연결되었으면 return
            if (relatedLan == computerN - 1) {
                return minLanSum;
            }
        }

        // 모든 컴퓨터가 연결되지 않고 while문이 끝나면
        return -1;
    }

    // computerNum의 대표 컴퓨터 번호 찾는 find 메서드
    private static int find(int computerNum) {
        if (computerNum == parentComp[computerNum]) {
            return computerNum;
        }

        return parentComp[computerNum] = find(parentComp[computerNum]);
    }

    // 두개의 컴퓨터를 랜선으로 잇는 union 메서드
    private static void union(int computer1, int computer2) {
        computer1 = find(computer1);
        computer2 = find(computer2);

        if (computer1 != computer2) {
            parentComp[computer2] = computer1;
        }
    }

    // LAN 클래스
    static class LAN implements Comparable<LAN> {
        int computer1;  // 랜선에 연결된 컴퓨터1
        int computer2;  // 랜선에 연결된 컴퓨터2
        int lanLength;  // 랜선의 길이

        private LAN(int computer1, int computer2, int lanLength) {
            this.computer1 = computer1;
            this.computer2 = computer2;
            this.lanLength = lanLength;
        }

        @Override
        public int compareTo(LAN o) {
            return Integer.compare(this.lanLength, o.lanLength);
        }
    }
}
