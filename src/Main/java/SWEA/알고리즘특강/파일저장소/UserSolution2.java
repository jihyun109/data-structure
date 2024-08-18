package SWEA.알고리즘특강.파일저장소;

import java.util.*;

class UserSolution2 {
    private static TreeMap<Integer, Integer> storage;   // 저장소에 저장된 순서대로 파일 ID 저장 (key: 파일 시작 위치, value: 파일 ID)
    private static HashMap<Integer, TreeMap<Integer, Integer>> startIdxs;    // key: 파일 ID, value: 해당 파일의 모든 시작 위치, size. 빈 공간은 ID:0
    private static HashMap<Integer, Integer> compKeys;  // key: 실제 ID, value: 압축된 ID
    private static int storageSize;  // 총 저장소 크기
    private static int curSize; // 현재 저장소에 저장된 크기
    private static int key; // 압축된 ID 에 사용
    public void init(int N) {
        storage = new TreeMap<>();
        startIdxs = new HashMap<>();
        compKeys = new HashMap<>();
        storageSize = N;
        key = 0;
        curSize = 0;

        // 빈공간 설정
        startIdxs.put(0, new TreeMap<>());
        startIdxs.get(0).put(1, storageSize);
        storage.put(1, 0);
    }

    public int add(int mId, int mSize) {
//        System.out.println("add");

        // 저장할 공간이 부족하면
        if (curSize + mSize > storageSize) {
            return -1;
        }

        // 파일 추가
        compKeys.put(mId, key++);   // 압축된 Key 생성

        // 가장 앞서는 빈 공간부터 저장
        int sizeToAdd = mSize;  // 저장해야 할 크기
        TreeMap<Integer, Integer> blanks = new TreeMap<>(startIdxs.get(0));    // key: 빈칸의 시작 index, value: 빈칸 size

        for (int sIdx: blanks.keySet()) { // 빈 공간 시작 위치
            int blankSize = blanks.get(sIdx); // 현재 빈 공간의 크기
            storage.put(sIdx, mId); // 파일 시작 위치, id 저장

            int storeCap = Math.min(blankSize, sizeToAdd);    // 저장 가능 용량
            startIdxs.computeIfAbsent(mId, k -> new TreeMap<>()).put(sIdx, storeCap);   // 현재 저장한 파일 mId의 시작 idx, 크기 입력
            curSize += storeCap;   // 현재 저장된 크기 업데이트

            // 빈 칸 수정
            startIdxs.get(0).remove(sIdx);  // 기존 빈칸 정보 삭제

            int leftBlank = blankSize - storeCap;  // 저장한 후 남은 빈칸
            if (leftBlank > 0) { // 빈 공간이 남으면
                startIdxs.get(0).put(sIdx + storeCap, blankSize - storeCap);  // 빈 칸 정보 새로 넣기
                storage.put(sIdx + storeCap, 0);
            }

            sizeToAdd -= storeCap;  // 저장해야하는 size 수정
            if (sizeToAdd == 0) {   // 파일을 모두 저장했으면
                break;
            }
        }

        return startIdxs.get(mId).firstKey();
    }

    public int remove(int mId) {
//        System.out.println("remove: " + startIdxs.get(mId));
        int cnt = 0;    // 파일 조각의 수
        TreeMap<Integer, Integer> sIdxs = startIdxs.get(mId);    // 삭제할 파일의 모든 시작 위치
//        TreeMap<Integer, Integer> copy = new TreeMap<>(sIdxs);
        for (int sIdx: sIdxs.keySet()) {
            storage.remove(sIdx);
            curSize -= sIdxs.get(sIdx); // 현재 저장된 크기 업데이트
            cnt++;
            startIdxs.get(0).put(sIdx, sIdxs.get(sIdx));    // 빈칸 업데이트
        }
        startIdxs.remove(mId);

        // 빈 공간이 연속되어 존재하는 경우, 하나로 합치기
        List<Integer> to_erase = new ArrayList<>();
        if (startIdxs.get(0).size() >= 2) {
            Iterator<Integer> it = startIdxs.get(0).keySet().iterator();
            Integer prev = it.next();
            while (it.hasNext()) {
                Integer next = it.next();
                if (next - prev == startIdxs.get(0).get(prev)) to_erase.add(next);
                prev = next;
            }
        }
        for (int i : to_erase) {
            startIdxs.get(0).remove(i);
            storage.remove(i);
        }
//        // 합쳐야할 빈칸 찾기
//        HashMap<Integer, File> toErase = new HashMap<>();  // key: 지워야할(합쳐야할) 빈칸의 처음 인덱스, value: 합칠 빈칸의 index, 빈칸의 길이
//        if (startIdxs.get(0).size() >= 2) { // 나뉘어진 빈 공간이 2개 이상이어야 합칠 수 있음.
//            Iterator<Integer> it = startIdxs.get(0).keySet().iterator();    // 빈칸인 부분의 첫번째 인덱스들
//            int prev = it.next();
//            while (it.hasNext()) {
//                int next = it.next();
//                if (next - prev == startIdxs.get(0).get(prev)) {
//                    int nextSize = startIdxs.get(0).get(next);  // 지워야할 빈칸의 크기
//
//                    if (toErase.containsKey(prev)) {    // 지워야할 것에 prev 가 들어있으면
//                        int sIdx = toErase.get(prev).prevIdx;   // 합칠 index
//                        toErase.put(next, new File(sIdx, nextSize));
//                        prev = next;
//                        continue;
//                    }
//                    toErase.put(next, new File(prev, nextSize));
//                }
//                prev = next;
//            }
//        }
//
//        // 빈칸 합치기
//        for (int idxToRM : toErase.keySet()) {
//            int repIdx = toErase.get(idxToRM).prevIdx;  // 대표 index(합칠 index)
//            int len = toErase.get(idxToRM).length;  // 합칠 길이
//            int originLen = startIdxs.get(0).get(repIdx);   // 대표 index 의 원래 길이
//            startIdxs.get(0).remove(idxToRM);   // 원래 있던 빈칸 삭제
//            startIdxs.get(0).put(repIdx, originLen + len);    // 길이 더해서 추가
//            storage.remove(idxToRM);
//            storage.put(repIdx, 0);
//        }
        return cnt;
    }

    public int count(int mStart, int mEnd) {
//        System.out.println("count");

        int fileN = 0;  // 파일 개수
        boolean[] visited = new boolean[12001]; // 해당 파일을 선택했는지 check

        Map.Entry<Integer, Integer> s = storage.floorEntry(mStart); // 범위의 가장 첫 파일

        while (s != null && s.getKey() <= mEnd) {
            int fileId = s.getValue();  // 파일 id
            if (fileId == 0) {
                s = storage.higherEntry(s.getKey());
                continue;
            }

            int compKey = compKeys.get(fileId); // 압축된 파일 id
            if (!visited[compKey]) {
                fileN++;
                visited[compKey] = true;
            }
            s = storage.higherEntry(s.getKey());
        }
        return fileN;
    }

    private static class File {
        int prevIdx;
        int length;
        private File ( int prevIdx, int length) {
            this.length = length;
            this.prevIdx = prevIdx;
        }
    }
}