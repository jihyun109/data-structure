package SWEA.알고리즘특강.파일저장소;

import java.util.*;

public class FileSystem {
    // ID_pos[i]: 파일 ID i에 대한 시작 위치들
    List<Set<Integer>> ID_pos;

    // 파일 ID, 압축 ID
    Map<Integer, Integer> idx;
    // 압축한 ID
    int new_idx;

    // 시작 위치, 파일 ID
    TreeMap<Integer, Integer> storage;

    // 현재 저장된 파일 크기, 최대 파일 크기
    int cur_size, Max_size;

    public FileSystem() {
        ID_pos = new ArrayList<>();
        idx = new HashMap<>();
        storage = new TreeMap<>();
    }

    // 시작 위치 pos에 저장된 파일의 크기
    int get_size(int pos) {
        Map.Entry<Integer, Integer> it = storage.lowerEntry(pos);
        return storage.higherKey(it.getKey()) - it.getKey();
    }

    public void init(int N) {
        Max_size = N;
        new_idx = 0;
        cur_size = 0;

        ID_pos.clear();
        for (int i = 0; i < 12010; i++) {
            ID_pos.add(new HashSet<>());
        }
        ID_pos.get(0).add(1);

        storage.clear();
        storage.put(1, 0);
        storage.put(N + 1, -1);

        idx.clear();
        idx.put(0, 0);
    }

    public int add(int mId, int mSize) {
        // 저장 공간이 부족한 경우
        if (cur_size + mSize > Max_size) return -1;

        // 새로운 압축 ID 할당
        int compress_ID = ++new_idx;
        idx.put(mId, compress_ID);

        // 현재 저장 공간 업데이트
        cur_size += mSize;

        // 삭제할 빈 공간 위치들
        List<Integer> to_erase = new ArrayList<>();
        int to_update = -1;

        // 반환할 위치
        int ret = ID_pos.get(0).iterator().next();
        // 빈 공간을 스위핑
        for (int i : ID_pos.get(0)) {
            int sz = get_size(i);
            storage.put(i, compress_ID);
            ID_pos.get(compress_ID).add(i);
            to_erase.add(i);

            if (sz > mSize) {
                // to_update 위치에 빈 공간 표시 필요
                to_update = i + mSize;
                break;
            } else if (sz == mSize) break;
            else mSize -= sz;
        }
        // to_update 위치는 빈 공간
        if (to_update >= 0) {
            ID_pos.get(0).add(to_update);
            storage.put(to_update, 0);
        }
        // 빈 공간 정보 삭제
        for (int i : to_erase) ID_pos.get(0).remove(i);
        return ret;
    }

    public int remove(int mId) {
        int compress_ID = idx.get(mId);
        idx.remove(mId);
        int ret = ID_pos.get(compress_ID).size();

        // 저장된 위치 스위핑하면서 빈 공간으로 업데이트
        for (int i : ID_pos.get(compress_ID)) {
            cur_size -= get_size(i);
            storage.put(i, 0);
            ID_pos.get(0).add(i);
        }
        ID_pos.get(compress_ID).clear();

        // 빈 공간이 연속되어 존재하는 경우, 하나로 합치기
        List<Integer> to_erase = new ArrayList<>();
        if (ID_pos.get(0).size() >= 2) {
            Iterator<Integer> it = ID_pos.get(0).iterator();
            Integer prev = it.next();
            while (it.hasNext()) {
                Integer next = it.next();
                if (next - prev == get_size(prev)) to_erase.add(next);
                prev = next;
            }
        }
        for (int i : to_erase) {
            ID_pos.get(0).remove(i);
            storage.remove(i);
        }

        return ret;
    }

    public int count(int mStart, int mEnd) {
        int ret = 0;
        boolean[] cnt = new boolean[12010];
        Arrays.fill(cnt, false);

        // 구간 시작 위치 이분탐색으로 구하기
        Map.Entry<Integer, Integer> it = storage.floorEntry(mStart);

        // 구간을 스위핑하며 개수 계산
        while (it != null && it.getKey() <= mEnd) {
            if (!cnt[it.getValue()]) {
                cnt[it.getValue()] = true;
                if (it.getValue() > 0) ret++;
            }
            it = storage.higherEntry(it.getKey());
        }
        return ret;
    }

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        fs.init(100);
        System.out.println(fs.add(1, 20)); // 예시 출력
        System.out.println(fs.remove(1));  // 예시 출력
        System.out.println(fs.count(1, 50));  // 예시 출력
    }
}
