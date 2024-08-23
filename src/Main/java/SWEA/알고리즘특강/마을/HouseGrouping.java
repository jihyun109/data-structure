package SWEA.알고리즘특강.마을;

import java.util.*;

public class HouseGrouping {

    // 원본 ID -> 압축 ID
    static int new_idx;
    static HashMap<Integer, Integer> idx = new HashMap<>();

    // 압축 ID -> (x, y)
    static ArrayList<Pair<Integer, Integer>> id_to_xy = new ArrayList<>();

    // x -> (y, 압축 ID)
    static ArrayList<TreeSet<Pair<Integer, Integer>>> x_to_yid = new ArrayList<>();

    // 압축 그룹 ID -> 그룹 내 원본 ID set
    static ArrayList<TreeSet<Integer>> S = new ArrayList<>();

    // init의 L, R과 동일
    static int L, R;

    // 전체 그룹 개수
    static int total_cnt;

    // Union Find group
    static int[] group = new int[25010];

    // Union Find 구현
    static int Find(int x) {
        if (group[x] == x) return x;
        return group[x] = Find(group[x]);
    }

    // 두 그룹을 합친다.
    // Smaller To Larger Technique
    static void Union(int x, int y) {
        x = Find(x);
        y = Find(y);
        if (x == y) return;

        // 크기가 큰 집합에 작은 집합을 삽입
        if (S.get(x).size() < S.get(y).size()) {
            int temp = x;
            x = y;
            y = temp;
        }

        S.get(x).addAll(S.get(y));
        S.get(y).clear();

        group[y] = x;
        total_cnt--;
    }

    static void init(int L, int R) {
        new_idx = 0;
        total_cnt = 0;

        idx.clear();
        id_to_xy.clear();
        x_to_yid.clear();
        S.clear();

        for (int i = 0; i < 25003; i++) {
            S.add(new TreeSet<>());
            id_to_xy.add(new Pair<>(0, 0));
        }

        for (int i = 0; i < R + 3; i++) {
            x_to_yid.add(new TreeSet<>());
        }

        // 전역 변수 L, R에 지역 변수 대입
        HouseGrouping.L = L;
        HouseGrouping.R = R;
    }

    static int add(int mId, int mX, int mY) {
        // 새로운 압축 ID 생성
        idx.put(mId, ++new_idx);
        S.get(new_idx).add(mId);

        // 압축 ID 대입
        mId = new_idx;

        // 현재 mId의 그룹 번호는 mId
        group[mId] = mId;
        total_cnt++;

        for (int i = Math.max(-L, 1 - mX); i <= Math.min(L, R - mX); ) {

            // x = [mX+i], y = [mY-L+abs(i), mY+L-abs(i)]
            // 위의 구간 내의 원소 탐색 및 그룹 합치기
            int x = mX + i;

            int L_y = mY - L + Math.abs(i);
            int R_y = mY + L - Math.abs(i);

            // x좌표에 해당하는 집이 없는 경우
            if (x_to_yid.get(x).isEmpty()) {
                i++;
                continue;
            }

            int cur_y = L_y;
            while (true) {
                // y좌표가 cur_y 이상인 집 탐색
                Pair<Integer, Integer> curPair = new Pair<>(cur_y, -1);
                TreeSet<Pair<Integer, Integer>> yidSet = x_to_yid.get(x);
                Pair<Integer, Integer> foundPair = yidSet.ceiling(curPair);

                // 집이 없는 경우
                if (foundPair == null) {
                    i++;
                    break;
                }
                int pos = foundPair.getKey();
                int pId = foundPair.getValue();

                // 찾은 집이 범위를 넘어가는 경우
                if (pos > R_y) {
                    i++;
                    break;
                }
                Union(mId, pId);

                // 다음으로 탐색할 집
                // 현재 추가한 집보다 좌표가 L+1만큼 더 큰 집
                cur_y = pos + L + 1;

                if (cur_y > R_y) {
                    i++;
                    break;
                }
            }
        }
        id_to_xy.set(mId, new Pair<>(mX, mY));
        x_to_yid.get(mX).add(new Pair<>(mY, mId));

        // mId 그룹의 크기 반환
        return S.get(Find(mId)).size();
    }

    static int remove(int mId) {
        // mId가 없는 경우
        if (!idx.containsKey(mId)) return -1;

        int realmID = mId;
        mId = idx.get(mId);
        idx.remove(realmID);

        // 압축 그룹 ID
        int gId = Find(mId);

        S.get(gId).remove(realmID);
        // 집 삭제 후 그룹의 크기
        int ret = S.get(gId).size();

        // 집 삭제 후 그룹이 빈다면 그룹 개수 감소
        if (ret == 0) total_cnt--;

        Pair<Integer, Integer> xy = id_to_xy.get(mId);
        x_to_yid.get(xy.getKey()).remove(new Pair<>(xy.getValue(), mId));

        return ret;
    }

    static int check(int mId) {
        // mId가 없는 경우
        if (!idx.containsKey(mId)) return -1;

        // 압축 ID 대입
        mId = idx.get(mId);

        // 그룹 내의 가장 작은 ID 반환
        return S.get(Find(mId)).first();
    }

    static int count() {
        return total_cnt;
    }

    // Utility class to represent pairs
    static class Pair<K, V> implements Comparable<Pair<K, V>> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public int compareTo(Pair<K, V> other) {
            int comp = ((Comparable<K>) this.key).compareTo(other.key);
            if (comp != 0) return comp;
            return ((Comparable<V>) this.value).compareTo(other.value);
        }
    }

    public static void main(String[] args) {
        // Test the implementation with some sample data
    }
}

