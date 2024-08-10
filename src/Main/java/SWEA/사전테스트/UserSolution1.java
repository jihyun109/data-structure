package SWEA.사전테스트;

import java.util.*;

class UserSolution1 {
    static final int MAXL = 5;
    static final int MAXF = 10;
    Map<Integer, Set<Integer>> relationship;

    public void init(int N) {
        relationship = new HashMap<>();

        for (int i = 1; i <= N; i++) {
            relationship.put(i, new HashSet<>());
        }
    }

    public void add(int id, int F, int ids[]) {
        for (int i = 0; i < F; i++) {
            relationship.get(id).add(ids[i]);
            relationship.get(ids[i]).add(id);
        }
    }

    public void del(int id1, int id2) {
        relationship.get(id1).remove(id2);
        relationship.get(id2).remove(id1);
    }

    public int recommend(int id, int list[]) {
        Set<Integer> friends = relationship.get(id); // 사용자 id의 친구 목록
        Map<Integer, Integer> recommendCandidate = new HashMap<>(); // 추천할 후보와 후보와 함께 아는 친구 수 저장 변수

        for (int friend : friends) {
            for (int friendOfFriend : relationship.get(friend)) {
                if (friendOfFriend != id && !friends.contains(friendOfFriend)) {
                    recommendCandidate.put(friendOfFriend, recommendCandidate.getOrDefault(friendOfFriend, 0) + 1);
                }
            }
        }

        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(recommendCandidate.entrySet());
        sortedList.sort((a, b) -> {
            if (b.getValue().equals(a.getValue())) {
                return Integer.compare(a.getKey(), b.getKey());
            }
            return Integer.compare(b.getValue(), a.getValue());
        });

        int listLength = Math.min(MAXL, sortedList.size());
        for (int i = 0; i < listLength; i++) {
            list[i] = sortedList.get(i).getKey();
        }

        return listLength;
    }
}
