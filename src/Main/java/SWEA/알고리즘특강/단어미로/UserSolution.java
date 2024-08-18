package SWEA.알고리즘특강.단어미로;

import java.util.*;

class UserSolution {
    private static Map<String, Set<String>> subFront;   // key: 문자열 앞의 2 or 4번쨰까지 부분문자열, value: 해당되는 문자열 set
    private static Map<String, Set<String>> subMid;     // key: 문자열의 중간 5, 6, 7번쨰 부분문자열, value: 해당되는 문자열 set
    private static Map<String, Set<String>> subEnd;     // key: 문자열 뒤의 2 or 4번쨰까지 부분문자열, value: 해당되는 문자열 set
    private static Map<String, Integer> strIds;     // key: 문자열, value: id
    private static int[][] dirN;    // 문자열 id 별 방향 숫자 저장 배열 (0: 앞, 1: 중간, 2: 뒤)
    private static String curStr;

    void init() {
        subFront = new HashMap<>();
        subMid = new HashMap<>();
        subEnd = new HashMap<>();
        strIds = new HashMap<>();
        dirN = new int[30001][3];
        curStr = null;
    }

    void addRoom(int mID, char mWord[], int mDirLen[]) {
        String str = new String(mWord, 0, 11);    // 입력받은 문자열
        strIds.put(str, mID);   // 문자열 id 저장
        subFront.computeIfAbsent(str.substring(0, 2), k -> new TreeSet<>()).add(str);
        subFront.computeIfAbsent(str.substring(0, 4), k -> new TreeSet<>()).add(str);
        subMid.computeIfAbsent(str.substring(4, 7), k -> new TreeSet<>()).add(str);
        subEnd.computeIfAbsent(str.substring(7, 11), k -> new TreeSet<>()).add(str);
        subEnd.computeIfAbsent(str.substring(9, 11), k -> new TreeSet<>()).add(str);
        dirN[mID][0] = mDirLen[0];  // 방향 숫자 입력받기
        dirN[mID][1] = mDirLen[1];
        dirN[mID][2] = mDirLen[2];
    }

    void setCurrent(char mWord[]) {
        curStr = new String(mWord, 0, 11);
    }

    int moveDir(int mDir) {
        int strId = strIds.get(curStr); // 현재 문자열의 id
        switch (mDir) { // 방향 숫자가 0, 1, 2 인 경우
            case 0: {
                int len = dirN[strId][0];   // 확인해야 하는 문자열 길이
                String sub = curStr.substring(0, len);    // 현재 문자열의 앞 문자열

                // subEnd 에서 뒷 문자열이 sub 인 문자열 찾기
                if (!subEnd.containsKey(sub)) { // 해당되는 문자열이 없는 경우 return 0
                    return 0;
                }

                String nextStr = null;
                for (String str : subEnd.get(sub)) {
                    if (!str.equals(curStr)) {  // 찾은 문자열이 현재 문자열이 아니면 선택
                        nextStr = str;
                        break;
                    }
                }

                if (nextStr == null) {  // 다음 값이 없는 경우 return 0
                    return 0;
                }

                // 찾은 경우
                curStr = nextStr;
                return strIds.get(curStr);
            }
            case 1: {
                String sub = curStr.substring(4, 7);    // 현재 문자열의 중간 문자열

                // subMid 에서 중간 문자열이 sub 인 문자열 찾기
                if (!subMid.containsKey(sub)) { // 해당되는 문자열이 없는 경우 return 0
                    return 0;
                }

                String nextStr = null;
                for (String str : subMid.get(sub)) {
                    if (!str.equals(curStr)) {  // 찾은 문자열이 현재 문자열이 아니면 선택
                        nextStr = str;
                        break;
                    }
                }

                if (nextStr == null) {  // 다음 값이 없는 경우 return 0
                    return 0;
                }

                // 찾은 경우
                curStr = nextStr;
                return strIds.get(curStr);
            }
            case 2: {
                int len = dirN[strId][2];   // 확인해야 하는 문자열 길이
                String sub = curStr.substring(11 - len, 11);    // 현재 문자열의 뒤 문자열

                // subEnd 에서 뒷 문자열이 sub 인 문자열 찾기
                if (!subFront.containsKey(sub)) { // 해당되는 문자열이 없는 경우 return 0
                    return 0;
                }

                String nextStr = null;
                for (String str : subFront.get(sub)) {
                    if (!str.equals(curStr)) {  // 찾은 문자열이 현재 문자열이 아니면 선택
                        nextStr = str;
                        break;
                    }
                }

                if (nextStr == null) {  // 다음 값이 없는 경우 return 0
                    return 0;
                }

                // 찾은 경우
                curStr = nextStr;
                return strIds.get(curStr);
            }
        }
        return 0;
    }

    void changeWord(char mWord[], char mChgWord[], int mChgLen[]) {
        String orginWord = new String(mWord, 0, 11); // 원래 단어
        String chgWord = new String(mChgWord, 0, 11); // 변경할 단어

        // Map 에서 삭제
        subFront.get(orginWord.substring(0, 2)).remove(orginWord);
        subFront.get(orginWord.substring(0, 4)).remove(orginWord);
        subMid.get(orginWord.substring(4, 7)).remove(orginWord);
        subEnd.get(orginWord.substring(7, 11)).remove(orginWord);
        subEnd.get(orginWord.substring(9, 11)).remove(orginWord);
        // Map 에 추가
        subFront.computeIfAbsent(chgWord.substring(0, 2), k -> new TreeSet<>()).add(chgWord);
        subFront.computeIfAbsent(chgWord.substring(0, 4), k -> new TreeSet<>()).add(chgWord);
        subMid.computeIfAbsent(chgWord.substring(4, 7), k -> new TreeSet<>()).add(chgWord);
        subEnd.computeIfAbsent(chgWord.substring(7, 11), k -> new TreeSet<>()).add(chgWord);
        subEnd.computeIfAbsent(chgWord.substring(9, 11), k -> new TreeSet<>()).add(chgWord);

        // strId 수정
        int id = strIds.get(orginWord);
        strIds.remove(orginWord);
        strIds.put(chgWord, id);

        // 방향 숫자 수정
        dirN[id][0] = mChgLen[0];
        dirN[id][1] = mChgLen[1];
        dirN[id][2] = mChgLen[2];

        // curStr 수정
        if (curStr.equals(orginWord)) {
            curStr = chgWord;
        }
    }
}