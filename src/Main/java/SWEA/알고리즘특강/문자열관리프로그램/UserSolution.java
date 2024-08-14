package SWEA.알고리즘특강.문자열관리프로그램;

import java.util.*;

class UserSolution {
    private static HashMap<Character, List<Integer>> hashMap;
    private static ArrayList<Character> arrayList;
    private static int lastIdx;
    private static boolean flipped;

    void init(char mStr[]) {
        arrayList = new ArrayList<>();  // 문자열 저장 리스트
        hashMap = new HashMap<>();  // key: 문자, value: 문자가 있는 index 들
        lastIdx = 0; // 문자열 길이
        flipped = false;    // 뒤집어져 있는지 확인
        for (int i = 0; i < mStr.length; i++) {
            if (mStr[i] == '\u0000') {
                break;
            }

            char c = mStr[i];
            arrayList.add(c);
            hashMap.computeIfAbsent(c, k -> new LinkedList<>()).add(i);
            lastIdx = i;
        }

//        System.out.println("arrayList: " + arrayList);
//        System.out.println("hashMap: " + hashMap.toString());
//        System.out.println("hashMapList: " + hashMap.toString());

    }

    void appendWord(char mWord[]) {
        for (int i = 0; i < mWord.length; i++) {
            char word = mWord[i];
            if (word == '\u0000') {
                break;
            }

            arrayList.add(word);
            hashMap.computeIfAbsent(word, k -> new LinkedList<>()).add(lastIdx + 1);
            lastIdx++;
        }
    }

    void cut(int k) {
        for (int i = 0; i < k; i++) {
            int idx = lastIdx - i;  // 삭제할 문자의 index
            char c = arrayList.get(idx);    // 삭제할 문자
            arrayList.remove(idx);

            // hashMap 에서 삭제
            for (int j = 0; j < hashMap.get(c).size(); j++) {
                int jIdx = hashMap.get(c).get(j);   // j번쨰에 들어있는 값
                if (jIdx == idx) {
                    hashMap.get(c).remove(j);
                }
            }
        }
        lastIdx -= k;
    }

    void reverse() {
        flipped = !flipped;
//        // arrayList 에서 reverse
//        Collections.reverse(arrayList);
//
//        // hashMap reverse
//        for (Map.Entry<Character, List<Integer>> entry : hashMap.entrySet()) {
//            int length = entry.getValue().size();   // 리스트의 길이
//            for (int i = 0; i < length; i++) {
//                int idx = entry.getValue().get(i);
//                entry.getValue().set(i, lastIdx - idx);
//            }
//        }
    }

    int countOccurrence(char mWord[]) {
        char firstW = mWord[0]; // 첫번째 단어
        if (!hashMap.containsKey(firstW)) { // 첫번째 문자가 존재하지 않으면 return 0
            return 0;
        }

        List<Integer> idxs = new LinkedList<>(hashMap.get(firstW));
        int cnt = 0;    // 문자열의 수
        for (int i = 0; i < idxs.size(); i++) {  // firstW 이 위치하는 곳에 mWord 가 있는지 확인
            int firstWIdx = idxs.get(i);    // firstW가 위치한 index
            boolean exist = true;   // 현재 위치에 mWord 가 존재하는지
            for (int j = 1; j < mWord.length; j++) {    // mWord 의 다른 문자열도 확인
                char compareWord = mWord[j];    // 문자열과 비교할 문자
                if (compareWord == '\u0000') {
                    break;
                }
                if (firstWIdx + j >= arrayList.size()) {     // mWord 가 범위를 넘어가면
                    exist = false;
                    break;
                }

                char word = arrayList.get(firstWIdx + j);
                if (word != mWord[j]) {
                    exist = false;
                    break;
                }
            }
            if (exist) {
                cnt++;
            }
        }
        return cnt;
    }
}