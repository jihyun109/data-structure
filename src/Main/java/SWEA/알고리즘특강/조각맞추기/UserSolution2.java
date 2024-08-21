package SWEA.알고리즘특강.조각맞추기;

import java.util.*;

class UserSolution2 {
    // 상대높이 패턴과 그 위치 저장 맵. key: 상대높이 패턴, value: 해당 패턴을 가진 부분의 가장 왼쪽 위치(오른쪽에 위치한 것부터 정렬)
    private static HashMap<String, TreeSet<Integer>> relHeight;
    private static ArrayList<Stick> walls;   // 세워진 벽 정보 저장 배열. walls[i] : 왼쪽으로부터 i번째 Stick 의 정보
    private static int lastX;   // 마지막 stick 의 위치
    private static final int maxStickN = 100001;    // 최대 stick 의 개수

    void init() {
        relHeight = new HashMap<>();
        walls = new ArrayList<>();
        lastX = -1;
    }

    void makeWall(int mHeights[]) {
        int firstX = lastX + 1; // 벽의 첫번째 위치
        // walls 에 벽 세우기
        for (int i = 0; i < 5; i++) {
            lastX++;
            walls.add(new Stick(mHeights[i], lastX - 1, lastX + 1, false));
        }

        // 현재 벽의 pattern 추가
        int[] patternArr = findRelPattern(mHeights);
        String pattern = arrToString(patternArr);
        relHeight.computeIfAbsent(pattern, k -> new TreeSet<>(Comparator.reverseOrder())).add(firstX);
        walls.get(firstX).pattern = pattern;
//        walls[firstX].pattern = pattern;

        // 추가한 벽의 앞 stick 이 지워졌으면 가장 첫번째 stick 의 leftX 수정
        int prev = firstX - 1;
        if (prev >= 0) {
//            Stick prevStick = walls[firstX - 1]; // 앞의 stick
            Stick prevStick = walls.get(firstX - 1);
            if (prevStick.isRM) {    // 이전 스틱이 지워짐
                walls.get(firstX).leftX = prevStick.leftX;
//                walls[firstX].leftX = prevStick.leftX;
            }
        }

        // 추가된 상대 높이 패턴 추가 in relHeight
        addPattern(firstX); // 새로 추가된 벽을 포함하는 상대높이 패턴 정보 추가 walls 에도 추가. (자기 자신 제외)
//        System.out.println("relHeight: " + relHeight.toString());
//        System.out.println("walls: ");
//        for (int i = 0; i < walls.size(); i++) {
//            System.out.println(walls.get(i).toString());
//        }
    }

    int matchPiece(int mHeights[]) {
        // 매치되는 패턴이 있는지 확인
        String matchPattern = findMatchPattern(mHeights);   // 매치되는 패턴 찾기
        if (!relHeight.containsKey(matchPattern) || relHeight.get(matchPattern).isEmpty()) { // 매치되는 패턴이 없음
            return -1;
        }

        // 매치되는 부분 삭제
        int firstX = relHeight.get(matchPattern).first();   // 삭제할 부분의 첫 index
        for (int i = 0; i < 5; i++) {
            int x = firstX + i;
            String pattern = walls.get(x).pattern;
            if (pattern == null) {
                break;
            }
            walls.get(x).isRM = true;
//            String pattern = walls.[x].pattern;
//            walls[x].isRM = true;  // walls 에서 삭제
            relHeight.get(pattern).remove(x);   // x 가 가장 첫 위치인 pattern 삭제
        }

        // 매치된 부분의 앞, 뒤 정보 수정
        int prevX = firstX - 1; // 삭제된 부분의 앞 stick
        int backX = firstX + 5; // 삭제된 부분의 뒤 stick
        if (prevX < 0) {
            if (backX < walls.size()){
                walls.get(backX).leftX = -1;
                addPattern(backX);
            }
        } else {
            if (backX < walls.size()) {
                walls.get(prevX).rightX = backX;
                walls.get(backX).leftX = prevX;
                addPattern(backX);
            }

        }

//        walls[prevX].rightX = backX;
//        walls[backX].leftX = prevX;

        // 매치된 부분을 삭제한 후 새로운 패턴 추가
//
        System.out.println("relHeight: " + relHeight.toString());
        System.out.println("walls: ");
        for (int i = 0; i < walls.size(); i++) {
            System.out.println(walls.get(i).toString());
        }

        return firstX + 1;
    }

    private static String findMatchPattern(int heights[]) {
        int[] reversed = reverse(heights);  // 벽 뒤집기
        int[] relReversed = findRelPattern(reversed); // 뒤집은 벽의 상대 패턴 배열

        // 뒤집은 벽과 매치되는 패턴 찾기
        int max = findMax(relReversed);
        for (int i = 0; i < 5; i++) {
            heights[i] = max - relReversed[i];
        }
        String matchPattern = arrToString(heights);

        return matchPattern;
    }

    private static int[] reverse(int arr[]) {
        int[] reversed = new int[5];
        for (int i = 0; i < 5; i++) {
            reversed[i] = arr[4 - i];
        }
        return reversed;
    }

    // firstX 이 가장 왼쪽 stick 인 벽을 포함하는 상대높이 패턴 정보 추가 (자기 자신 제외)
    private static void addPattern(int firstX) {
        // firstX 앞으로 4개 stick(x값, height) 찾기
        Stack<Integer> sticks = new Stack<>(); // firstX 앞의 최대 4개의 stick 좌표
        int x = firstX;
        for (int i = 1; i <= 4; i++) {
            int idx = walls.get(x).leftX;
//            int idx = walls[x].leftX;
            if (idx < 0) {
                break;
            }
            sticks.add(idx);
            x = idx;
        }

        // sticks 를 가장 왼쪽으로 하는 pattern 저장
        while (!sticks.isEmpty()) {
            int leftX = sticks.pop();

            // 패턴 구하기
            int[] heights = new int[5]; // 패턴을 뽑을 x들의 높이
            int idx = leftX;
            for (int i = 0; i < 5; i++) {
                if (idx < 0 || idx >= walls.size()) {
                    break;
                }
                heights[i] = walls.get(idx).height;
                idx = walls.get(idx).rightX;
//                heights[i] = walls[idx].height;
//                idx = walls[idx].rightX;
            }
            if (idx == -1) {
                continue;
            }
            heights = findRelPattern(heights);  // 상대 높이 구하기
            String pattern = arrToString(heights);
            relHeight.computeIfAbsent(pattern, k -> new TreeSet<>()).add(leftX);
            walls.get(leftX).pattern = pattern;
//            walls[leftX].pattern = pattern;
        }
    }

    private static int[] findRelPattern(int heights[]) {
        int min = findMin(heights); // stick 들의 최소 높이 구하기
        // 상대높이로 배열 수정
        for (int i = 0; i < 5; i++) {
            heights[i] -= min;
        }

        return heights;
    }

    private static String arrToString(int arr[]) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            stringBuilder.append(arr[i]);
        }
        return stringBuilder.toString();
    }

    private static int findMin(int arr[]) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            min = Math.min(min, arr[i]);
        }
        return min;
    }

    private static int findMax(int arr[]) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }

    private static class Stick {
        int height;
        int leftX;
        int rightX;
        boolean isRM;
        String pattern;    // 현재 위치부터 시작하는 패턴

        public Stick(int height, int leftX, int rightX, boolean isRM) {
            this.height = height;
            this.leftX = leftX;
            this.rightX = rightX;
            this.isRM = isRM;
        }

        @Override
        public String toString() {
            return "Stick{" +
                    "height=" + height +
                    ", leftX=" + leftX +
                    ", rightX=" + rightX +
                    ", isRM=" + isRM +
                    ", pattern='" + pattern + '\'' +
                    '}';
        }
    }
}

