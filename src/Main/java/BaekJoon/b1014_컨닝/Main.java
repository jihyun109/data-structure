package BaekJoon.b1014_컨닝;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 테스트 케이스 수행
        int testcaseN = Integer.parseInt(st.nextToken()); // 테스트케이스 수
        for (int t = 0; t < testcaseN; t++) {
            // 교실의 행, 열 수
            st = new StringTokenizer(br.readLine());
            int rowN = Integer.parseInt(st.nextToken());
            int colN = Integer.parseInt(st.nextToken());

            // 교실 상태 입력받기. (1: 부서진 책상, 0: 부서지지 않은 책상)
            int[] classroom = new int[rowN]; // 교실 상태 저장
            for (int r = 0; r < rowN; r++) {
                int rowStatus = 0; // r번쨰 행의 책상 상태 저장

                String str = br.readLine();
                for (int c = 0; c < colN; c++) {
                    char status = str.charAt(c); // 책상의 상태

                    if (status == 'x') {
                        rowStatus += (1 << (colN - c - 1));
                    }
                }
                classroom[r] = rowStatus;
            }

            // 최대 학생 수 구하기
            int maxStudentN = findMaxStudentN(classroom, rowN, colN);
            bw.append(maxStudentN + "\n");
        }

        br.close();
        bw.flush();
        bw.close();
    }

    private static int findMaxStudentN(int[] classroom, int rowN, int colN) {
        int max = 0;
        // 한 줄에 가능한 모든 경우의 수 구하기
        List<Integer> allCasesRow = new LinkedList<>();
        findAllCases(allCasesRow, colN);

        // 모든 행의 모든 경우의 수의 max값 구하기
        List<Map<Integer, Integer>> maxes = new ArrayList<>(); // 모든 행의 모든 경우의 수의 최댓값 저장
        maxes.add(0, new HashMap<>());
        maxes.get(0).put(0, 0);
        for (int r = 1; r <= rowN; r++) {
            maxes.add(r, new HashMap<>());

            // 각 경우의 수의 최댓값 구하기
            for (int c : allCasesRow) {
                int numOfCur = Integer.bitCount(c);

                // c가 부서진 테이블에 앉았다면 continue
                boolean sitOnBroken = findBroken(classroom[r - 1], c);
                if (sitOnBroken) {
                    continue;
                }

                // 현재 행에 c로 앉았을 때 이전 행에 앉으면 안되는 자리 1로 표시
                int cantSitBefore = findCanSitBefore(c, colN);

                // 현재 행에 c로 앉았을 때 앉을 수 있는 학생 수 max값 구하기
                int cMax = 0;
                for (Map.Entry<Integer, Integer> before : maxes.get(r - 1).entrySet()) {
                    int beforeC = before.getKey();
                    int beforeMax = before.getValue();

                    // beforeC가 앉으면 안되는 곳에 앉이 않은 경우 max 업데이트
                    boolean isMatch = isMatch(beforeC, cantSitBefore);
                    if (isMatch) {
                        cMax = Math.max(beforeMax + numOfCur, cMax);
                    }
                }
                maxes.get(r).put(c,cMax);
                max = Math.max(cMax, max);
            }
        }

        return max;
    }

    // c가 cantSitBefore에 앉ㅇ아있는지 확인
    private static boolean isMatch(int c, int cantSitBefore) {
        if ((c & cantSitBefore) > 0) {
            return false;
        }
        return true;
    }

    // 한 줄에 가능한 모든 경우의 수 구하기
    private static void findAllCases(List<Integer> allCasesRow, int colN) {
        int allCaseN = 1 << colN;
        for (int c = 0; c < allCaseN; c++) {
            // c에서 모든 학생이 띄어앉아있는지 체크
            boolean isPossible = isPossible(c, colN);
            if (isPossible) {
                allCasesRow.add(c);
            }
        }
    }

    // 부서진 테이블에 앉았는지 체크
    private static boolean findBroken(int tableInfo, int studentStatus) {
        if ((tableInfo & studentStatus) > 0) {
            return true;
        }
        return false;
    }

    // 현재 행에 c로 앉았을 때 이전 행에 앉으면 안되는 자리 1로 표시
    private static int findCanSitBefore(int c, int colN) {
        int t = 0;
        for (int i = 0; i < colN; i++) {
            // c의 i번째가 1인지 확인
            boolean is1 = is1(c, i);
            if (is1) {
                int left = i + 1;
                int right = i - 1;

                if (left < colN && !is1(t, left)) {
                    t += (1 << left);
                }

                if (right >= 0 && !is1(t, right)) {
                    t += (1 << right);
                }
            }
        }
        return t;
    }

    // c에서 모든 학생이 띄어앉아있는지 체크
    private static boolean isPossible(int c, int colN) {
        boolean before = false; // 이전 상태 (true: 앉아있음, false: 앉지 않음)

        for (int i = 0; i < colN; i++) {
            // i번쨰와 i-1번쨰에 앉아있는 경우
            boolean curSitted = ((c & (1 << i)) > 0);
            if (curSitted) {
                if (before) {
                    return false;
                }
                before = true;
                continue;
            }
            before = false;
        }
        return true;
    }

    private static boolean is1(int c, int idx) {
        if ((c & (1 << idx)) > 0) {
            return true;
        }
        return false;
    }
}