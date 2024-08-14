package SWEA.알고리즘특강.문자열관리프로그램;

import java.util.*;

class UserSolution2 {
    private static List<Character> string;
    private static HashMap<String, Integer> subStringN;
    private static boolean flipped;

    void init(char mStr[]) {
        string = new LinkedList<>();  // 문자열 저장 리스트
        subStringN = new HashMap<>();  // 모든 부분문자열 개수 저장 해시맵
        flipped = false;

        // 초기 문자열 입력받기
        for (int i = 0; i < mStr.length; i++) {
            char input = mStr[i];
            if (input == '\u0000') {    // 입력 끝
                break;
            }

            putWord(input);
        }

        // 부분 문자열 개수 업데이트
        for (int len = 2; len < 5; len++) {
            char[] chars = new char[len];
            for (int i = 0; i <= string.size() - len; i++) {
                // string list 의 i 번째부터 len 길이의 문자를 문자열로 변환
                for (int c = 0; c < len; c++) {
                    chars[c] = string.get(i + c);
                }
                String subStr = new String(chars);
                if (subStringN.containsKey(subStr)) {    // subStringN에 이미 존재하면
                    subStringN.put(subStr, subStringN.get(subStr) + 1);
                } else {    // 존재하지 않으면
                    subStringN.put(subStr, 1);
                }
            }
        }
    }

    void appendWord(char mWord[]) {
        if (flipped) {  // 문자열이 뒤집어져 있으면
            // 문자열 추출
            Stack<Character> stack = new Stack<>();
            int mWordL = 0;
            for (int i = 0; i < 4; i++) {
                char word = mWord[i];
                if (word == '\u0000') {
                    break;
                }
                stack.add(word);
            }
            mWordL = stack.size();
            putWord(stack.pop());

            // 부분 문자열 개수 업데이트
            for (int len = 2; len < 5; len++) { // 문자열 길이가 2~4
                char[] chars = new char[len];
                for (int i = 0; i < mWordL; i++) {
                    // string list 의 i 번째부터 len 길이의 문자를 문자열로 변환
                    for (int c = 0; c < len; c++) {
                        chars[c] = string.get(i);
                    }
                    String subStr = new String(chars);  // 부분 문자열
                    if (subStringN.containsKey(subStr)) {    // subStringN에 이미 존재하면
                        subStringN.put(subStr, subStringN.get(subStr) + 1);
                    } else {    // 존재하지 않으면
                        subStringN.put(subStr, 1);
                    }
                }
            }
        } else {    // 뒤집히지 않았으면
            // mWord 문자열에 추가
            for (int i = 0; i < 4; i++) {
                char word = mWord[i];
                if (word == '\u0000') {
                    break;
                }
                putWord(word);
            }
        }
    }

    void cut(int k) {
        if (flipped) {  // 문자열이 뒤집어 있을 때
            // 부분문자열 개수 수정
            for (int len = 1; len < 5; len++) {
                for (int i = 0; i < k; i++) {
                    char[] chars = new char[len];
                    // string list 의 i 번째부터 len 길이의 문자를 문자열로 변환
                    for (int c = 0; c < len; c++) {
                        chars[c] = string.get(i);
                    }
                    String subStr = new String(chars);  // 부분 문자열
                    subStringN.put(subStr, subStringN.get(subStr) - 1);
                }
            }
            // 문자열 앞에서 제거
            for (int i = 0; i < k; i++) {
                string.remove(0);
            }
        } else {    // 뒤집히지 않았을 떄
            // 부분문자열 개수 수정
            for (int len = 1; len < 5; len++) {
                for (int i = string.size() - k; i < string.size(); i++) {
                    char[] chars = new char[len];
                    // string list 의 i 번째부터 len 길이의 문자를 문자열로 변환
                    for (int c = 0; c < len; c++) {
                        chars[c] = string.get(i);
                    }
                    String subStr = new String(chars);  // 부분 문자열
                    subStringN.put(subStr, subStringN.get(subStr) - 1);
                }
            }
            // 문자열 뒤에서 제거
            for (int i = 0; i < k; i++) {
                string.remove(string.size() - 1);
            }
        }
    }

    void reverse() {
        flipped = !flipped;
    }

    int countOccurrence(char mWord[]) {
        // mWord 를 String 으로 변환
        // 문자열 추출
        int len;
        for (len = 0; len < mWord.length; len++) {
            if (mWord[len] == '\u0000') {
                break;
            }
        }
        String subStr = new String(mWord, 0, len);
        if (flipped) {
            char[] flipped = new char[len];
            for (int i = 0; i < len; i++) {
                flipped[i] = mWord[len - i - 1];
            }
            subStr = new String(flipped);
        }

        return subStringN.containsKey(subStr) ? subStringN.get(subStr) : 0;
    }

    private void putWord(char word) {
        // string 수정
        if (flipped) {
            string.add(0, word);
        } else {
            string.add(word);
        }
        // 길이가 1인 부분 문자열 개수 업데이트
        String sWord = String.valueOf(word);   // word 을 String 타입으로 변환
        if (subStringN.containsKey(sWord)) {    // subStringN에 이미 존재하면
            subStringN.put(sWord, subStringN.get(sWord) + 1);
        } else {    // 존재하지 않으면
            subStringN.put(sWord, 1);
        }
    }
}