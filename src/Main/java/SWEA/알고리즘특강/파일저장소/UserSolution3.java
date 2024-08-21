package SWEA.알고리즘특강.파일저장소;


import java.util.*;

public class UserSolution3 {
    // 파일 주소는 1부터 시작
    // 파일 ID별 파일정보 저장 맵 (key: id, value: (key: 시작위치, value: 크기))
    private static HashMap<Integer, TreeMap<Integer, Integer>> fileIdMap;
    // 파일 저장 순서대로 정렬된 파일 정보 저장 맵 (key: 시작 주소, value: 파일 id)
    private static TreeMap<Integer, Integer> orderedFileMap;
    private static int storageSize; // 전체 저장공간 크기
    private static final int blankId = 0;   // 빈 칸 id
    private static int sumBlankSpace; // 남은 저장 공간 크기

    public void init(int N) {
        storageSize = N;
        fileIdMap = new HashMap<>();
        orderedFileMap = new TreeMap<>();

        // 빈공간 입력
        fileIdMap.computeIfAbsent(blankId, k -> new TreeMap<>()).put(1, storageSize);
        orderedFileMap.put(1, blankId);
        sumBlankSpace = storageSize;
    }

    public int add(int mId, int mSize) {
        // 파일을 저장할 수 있는지 확인
        if (sumBlankSpace < mSize) {
            return -1;
        }

        // 앞에 위치한 반칸부터 순차적으로 파일 저장
        TreeMap<Integer, Integer> blanks = new TreeMap<>(fileIdMap.get(blankId));   // 모든 빈칸 정보
        int remainFileSize = mSize;
        for (Map.Entry<Integer, Integer> blankEntry : blanks.entrySet()) {
            int sAddress = blankEntry.getKey(); // 시작 주소
            int blankSize = blankEntry.getValue();   // 크기
            int dividedSize = Math.min(remainFileSize, blankSize);   // 현재 저장할 수 있는 크기

            // 파일 저장
            fileIdMap.computeIfAbsent(mId, k -> new TreeMap<>()).put(sAddress, dividedSize);
            orderedFileMap.put(sAddress, mId);
            sumBlankSpace -= dividedSize;
            remainFileSize -= dividedSize;

            // 빈칸 수정
            // 기존 빈 칸 정보 삭제
            fileIdMap.get(blankId).remove(sAddress);
            // 저장한 후 현재 빈 칸이 남았으면 새로운 빈 칸 정보 삽입
            if (blankSize > dividedSize) {
                fileIdMap.get(blankId).put(sAddress + dividedSize, blankSize - dividedSize);
                orderedFileMap.put(sAddress + dividedSize, blankId);
            }

            if (remainFileSize == 0) {  // 파일을 모두 저장함.
                break;
            }
        }

        return fileIdMap.get(mId).firstKey();
    }

    public int remove(int mId) {
        // fileIdMap, orderedFileMap 에서 삭제, 빈칸 정보 추가
        int cnt = 0;    // 파일 조각의 수
        for (Map.Entry<Integer, Integer> fileEntry : fileIdMap.get(mId).entrySet()) {
            int firstAddress = fileEntry.getKey();
            int fileSize = fileEntry.getValue();

            fileIdMap.get(blankId).put(firstAddress, fileSize); // fileIdMap 에 빈칸 추가
            orderedFileMap.remove(firstAddress);
            orderedFileMap.put(firstAddress, blankId);  // 삭제한 부분에 빈칸 표시
            sumBlankSpace += fileSize;  // 총 빈 칸 크기 업데이트
            cnt++;
        }
        fileIdMap.remove(mId);

        // fileIdMap 에서 모든 빈 칸 돌면서 연속하는 빈칸 찾아 합치기
        if (fileIdMap.get(blankId).size() >= 2) {   // 빈칸의 개수가 2개 이상이면 실행
            // 연속하는 빈칸 찾기
            TreeMap<Integer, Integer> blanks = new TreeMap<>(fileIdMap.get(blankId));   // 모든 빈 칸들
            TreeMap<Integer, Integer> blanksToMerge = new TreeMap<>();  // 합쳐야할 빈칸 정보 저장 맵 (key: 시작 주소, value: 크기)
            Iterator<Integer> it = fileIdMap.get(blankId).keySet().iterator();    // 빈 칸의 시작 위치를 도는 it
            int prevAddress = it.next();
            while (it.hasNext()) {
                int nextAddress = it.next();
                int prevSize = blanks.get(prevAddress);
                if (nextAddress - prevSize == prevAddress) {    // prev 와 next 가 연속
                    blanksToMerge.put(nextAddress, blanks.get(nextAddress));
                }
                prevAddress = nextAddress;
            }

            // 합치기
            for (Map.Entry<Integer, Integer> blanksEntry : blanksToMerge.entrySet()) {
                int firstAddress = blanksEntry.getKey();
                int size = blanksEntry.getValue();
                int targetAddress = orderedFileMap.floorKey(firstAddress - 1);  // 합칠 대상의 주소
                int originSize = fileIdMap.get(0).get(targetAddress);   // 합칠 대상의 원래 크기

                // 합칠 대상의 크기 늘리고, 합쳐지는 빈칸 정보 삭제
                fileIdMap.get(blankId).put(targetAddress, originSize + size);
                fileIdMap.get(blankId).remove(firstAddress);
                orderedFileMap.put(targetAddress, blankId);
                orderedFileMap.remove(firstAddress);
            }
        }
        return cnt;
    }

    public int count(int mStart, int mEnd) {
        int startFileAddress = orderedFileMap.floorKey(mStart); // 구간의 시작 파일
        int cnt = 0;    // 구간에 속한 파일의 수

        // 구간에 속하는 파일 개수 구하기
        NavigableMap<Integer, Integer> subMap = orderedFileMap.subMap(startFileAddress, true, mEnd, true);
        HashSet<Integer> fIds = new HashSet<>();    // 구간에 속하는 파일을 중복 없이 저장
        for (int fId : subMap.values()) {
            if (fId == 0) { // 빈칸인 경우 continue
                continue;
            }
            fIds.add(fId);
        }
        cnt = fIds.size();
        return cnt;
    }
}