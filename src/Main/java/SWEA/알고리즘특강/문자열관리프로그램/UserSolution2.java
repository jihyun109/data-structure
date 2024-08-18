package SWEA.알고리즘특강.문자열관리프로그램;

import java.util.*;

class UserSolution2 {
    private static List<Character> string;
    private static HashMap<String, Integer> subStringN;
    private static boolean flipped;

    void init(char mStr[]) {
        // mStr 추출
//        mStr = abstractMWord(mStr);

        string = new ArrayList<>();  // 문자열 저장 리스트
        subStringN = new HashMap<>();  // 모든 부분문자열 개수 저장 해시맵
        flipped = false;

        addToStr(mStr); // string 에 추가

        // 부분 문자열 개수 업데이트
        updateSubStrN(0, string.size() - 1, true);
    }

    void appendWord(char mWord[]) {
        // 문자열 추출
//        mWord = abstractMWord(mWord);
        int mWordL = addToStr(mWord);

        // 부분 문자열 수 업데이트
        int s;  // 부분 문자열의 첫번쨰 index
        int e;  // 부분 문자열의 마지막 index

        if (!flipped) {
            s = string.size() - mWordL;
            e = string.size() -1;
        } else {
            s = 0;
            e = mWordL - 1;
        }
        updateSubStrN(s, e, true);
    }

    void cut(int k) {   // 현재 문자열의 끝에서부터 k개의 문자 제거
        // 부분 문자열 수 업데이트
        int s;
        int e;
        if (!flipped) {
            s = string.size() - k;
            e = string.size() - 1;
        } else {
            s = 0;
            e = k - 1;
        }
        updateSubStrN(s, e, false);

        // string 수정
        if (!flipped) {
            for (int i = 0; i < k; i++) {
                string.remove(string.size() - 1);
            }
        } else {
            for (int i = 0; i < k; i++) {
                string.remove(0);
            }
        }
    }

    void reverse() {
        flipped = !flipped;
    }

    int countOccurrence(char mWord[]) {
        // mWord 를 String 으로 변환
        // 문자열 추출
        mWord = abstractMWord(mWord);
        int len = mWord.length;
        String str = new String(mWord);

        if (flipped) {
            char[] flipped = new char[len];
            for (int i = 0; i < len; i++) {
                flipped[i] = mWord[len - i - 1];
            }
            str = new String(flipped);
        }
        return subStringN.containsKey(str) ? subStringN.get(str) : 0;
    }

    // char 배열을 String 으로 변환
    private char[] abstractMWord(char[] mWord) {
        int len;
        for (len = 0; len < mWord.length; len++) {
            if (mWord[len] == '\u0000') {
                break;
            }
        }
        String subStr = new String(mWord, 0, len);
        return subStr.toCharArray();
    }

    private int addToStr(char[] mWord) {    // 주어진 문자열을 string 에 추가
        int mWordL = 0;
        if (!flipped) { // 뒤집어지지 않은 경우
            for (int i = 0; i < mWord.length; i++) {
                if (mWord[i] == '\u0000') {
                    mWordL = i;
                    break;
                }
                string.add(mWord[i]);
            }
        } else {
            for (int i = 0; i < mWord.length; i++) {
                if (mWord[i] == '\u0000') {
                    mWordL = i;
                    break;
                }
                string.add(0, mWord[i]);
            }
        }
        return mWordL;
    }

    // idx 가 subS~subE 인 문자를 포함한 부분 문자열 개수 수정
    private void updateSubStrN(int subS, int subE, boolean append) {
        if (!flipped) { // 뒤집어지지 않았으면 string 의 맨 오른쪽부터 길이가 1~4 인 부분 문자열을 만들어 update
            for (int i = subS; i <= subE; i++) {    // i번째 문자가 제일 마지막인 부분 문자열 만들기
                for (int len = 1; len < 5; len++) {
                    char[] subStrChar = new char[len];  // 부분 문자열
                    int idx = 0;
                    int s1 = Math.max(i - len + 1, 0);
                    for (int s = s1; s <= i; s++) {
                        subStrChar[idx++] = string.get(s);
                    }
                    String subStr = new String(subStrChar);  // subStr

                    // subStr 의 개수 수정
                    if (append) {   // 추가
                        if (subStringN.containsKey(subStr)) {    // subStringN에 이미 존재하면
                            subStringN.put(subStr, subStringN.get(subStr) + 1);
                        } else {    // 존재하지 않으면
                            subStringN.put(subStr, 1);
                        }
                    } else {    // 삭제
                        subStringN.put(subStr, subStringN.get(subStr) - 1); // 부분 문자열 개수 삭제
                    }
                }
            }
        } else {
            for (int i = subS; i <= subE; i++) {    // i번째 문자가 제일 첫글자인 부분 문자열 만들기
                for (int len = 1; len < 5; len++) {
                    char[] subStrChar = new char[len];  // 부분 문자열
                    int idx = 0;
                    int e = Math.min(string.size() - 1, i + len);
                    for (int s = i; s < e; s++) {
                        subStrChar[idx++] = string.get(s);
                    }
                    String subStr = new String(subStrChar);  // subStr

                    // subStr 의 개수 수정
                    if (append) {   // 추가
                        if (subStringN.containsKey(subStr)) {    // subStringN에 이미 존재하면
                            subStringN.put(subStr, subStringN.get(subStr) + 1);
                        } else {    // 존재하지 않으면
                            subStringN.put(subStr, 1);
                        }
                    } else {    // 삭제
                        subStringN.put(subStr, subStringN.get(subStr) - 1); // 부분 문자열 개수 삭제
                    }
                }
            }
        }
    }
}