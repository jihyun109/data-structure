package SWEA.알고리즘특강.조각맞추기;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

class UserSolution {
    private static int[] segTree;   // 왼쪽에서 몇 개의 블록이 삭제되었는지 구하는 segTree
    private static Stick[] wall;  // 만들어진 벽 정보
    private static HashMap<String, TreeSet<Integer>> pieces;   // 벽을 구성하고있는 조각들 정보 (key: 상대적 높이, value: 조각의 가장 왼쪽 좌표)
    private static int lastLoc; // 가장 마지막 스틱의 위치


    void init() {
        wall = new Stick[100000];
        for (int i = 0; i < 100000; i++) {
            wall[i] = new Stick(-1, -1, 0, false);
        }
        segTree = new int[600000];
        pieces = new HashMap<>();
        lastLoc = -1;
    }

    void makeWall(int mHeights[]) {
        int leftX = lastLoc + 1;    // 현재 벽의 가장 왼쪽 좌표
        for (int i = 0; i < 5; i++) {
            int x = ++lastLoc;    // 현재 스틱의 위치
            wall[x] = new Stick(-1, -1, mHeights[i], false);
            setPxNx(x); // 현재 스틱의 앞, 뒤 스틱의 위치 정보 설정
        }

        int[] relHs = findRelH(mHeights);  // 상대 높이 구하기
        String relH = arrayToStr(relHs);    // 상대 높이 숫자로 변형
        pieces.computeIfAbsent(relH, k -> new TreeSet<>(Comparator.reverseOrder())).add(leftX);
    }

    int matchPiece(int mHeights[]) {
//        for (int i = 0; i <= lastLoc; i++) {
//            System.out.println(wall[i].height);
//        }
        String hash = findMatchHash(mHeights);
        if (!pieces.containsKey(hash) || pieces.get(hash).isEmpty()) {    // 매칭되는 것이 없음
            return -1;
        }
        int matchPieceL = pieces.get(hash).first();   // 매치되는 가장 오른쪽 조각의 왼쪽 좌표


        // 조각 삭제
        int leftX = matchPieceL - 1;
        int rightX = matchPieceL + 5;
        if (leftX > -1) {
            wall[leftX].nextIdx = rightX;

        }
        wall[rightX].prevIdx = leftX;
        for (int i = matchPieceL; i <= 5; i++) {
            wall[i].isRemoved = true;
        }

        pieces.get(hash).remove(matchPieceL);

        // 세그먼트 트리 업데이트
        updateSeg(matchPieceL, 1, 0, 75000);
        matchPieceL = matchPieceL - sum(0, matchPieceL - 1, 1, 0, 75000) + 1; // x를 0부터 시작하는 것으로 해서 +1

        return matchPieceL;
    }

    private static class Stick {
        int prevIdx;
        int nextIdx;
        int height;
        boolean isRemoved;

        public Stick(int prevIdx, int nextIdx, int height, boolean isRemoved) {
            this.prevIdx = prevIdx;
            this.nextIdx = nextIdx;
            this.height = height;
            this.isRemoved = isRemoved;
        }
    }

    private static void setPxNx(int x) {
        int pX = lastLoc - 1;   // x의 앞에 위치하는 스틱의 좌표
        if (pX != -1) {    // x가 제일 첫번째가 아니고
            if (wall[pX].isRemoved) {   // 앞의 스틱이 제거된 경우
                pX = wall[pX].prevIdx;
            }
            wall[x].prevIdx = pX;   // x의 앞 스틱 좌표 set

            if (pX != -1) { // x가 제일 첫번째가 아니면
                wall[pX].nextIdx = x;   // x 앞 스틱의 next 좌표 set
            }
        }
        wall[x].nextIdx = -1;   // 뒤에 아무도 없음
    }

    private static int[] findRelH(int[] heights) {
        int min = findMin(heights); // 최솟값
        int[] relHs = new int[5];    // 상대 높이 배열
        for (int i = 0; i < 5; i++) {   // 상대 높이 구하기
            relHs[i] = heights[i] - min;
        }
        return relHs;    // 배열을 숫자로 변환해 return
    }

    private static String arrayToStr(int[] arr) {
        int min = findMin(arr);
        int hash = 0;
        for (int i = 0; i < arr.length; i++) {
            hash = hash << 4; // 4bit마다 한 열씩
            hash |= arr[i] - min; // 상대적인 높이
        }
        return Integer.toString(hash); // 해시 값을 문자열로 반환
    }
//    private static String arrayToStr(int[] arr) {
//        StringBuilder sb = new StringBuilder();
//        for (int number : arr) {
//            sb.append(number); // 각 숫자를 문자열로 추가
//        }
//        return sb.toString(); // 최종 문자열 반환
//    }

    private static int findMin(int[] height) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 5; i++) {
            min = Math.min(min, height[i]);
        }
        return min;
    }

    private static int findMax(int[] arr) {
        int max = 0;
        for (int i = 0; i < 5; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }

    //    private static String findMatchHash(int[] heights) {
//        int[] relHs = findRelH(heights);   // heigts 의 상대 높이 배열
//        String matchingHash = reverse(relHs); // 매칭되는 hash
//        if (!pieces.containsKey(matchingHash)) {    // 해당되는 값이 없으면 -1 return
//            return null;
//        }
//        return matchingHash;
//    }
    private static String findMatchHash(int[] heights) {
        int[] relHs = findRelH(heights);   // 상대 높이 배열
        String matchingHash = arrayToStr(relHs); // 매칭되는 해시
        if (!pieces.containsKey(matchingHash)) {    // 해당되는 값이 없으면 null 반환
            return null;
        }
        return matchingHash;
    }

    private static String reverse(int[] arr) {
        int max = findMax(arr);   // 배열의 최댓값 찾기
        int[] reversed = new int[5];
        for (int i = 0; i < 5; i++) {
            reversed[i] = max - arr[4 - i];
        }
        return arrayToStr(reversed);
    }

    private static int updateSeg(int i, int node, int s, int e) {
        if (i < s || e < i) return segTree[node];
        if (s == e) return segTree[node] = segTree[node] + 5;
        int mid = (s + e) / 2;
        return segTree[node] = updateSeg(i, node * 2, s, mid) + updateSeg(i, node * 2 + 1, mid + 1, e);
    }

    int sum(int s, int e, int i, int nl, int nr) {
        int mid = (nl + nr) / 2;
        if (s <= nl && nr <= e) return segTree[i];
        if (nr < s || e < nl) return 0;
        return sum(s, e, i * 2, nl, mid) + sum(s, e, i * 2 + 1, mid + 1, nr);
    }

}
