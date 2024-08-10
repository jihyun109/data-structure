package SWEA.알고리즘특강.메모장프로그램.score6;

import java.util.Arrays;
import java.util.LinkedList;

class UserSolution_score6
{

    static int order = 0;
    LinkedList<Character>[] list;
    static int width;
    static int cursorX;
    static int cursorY;
    static int lastX;   // 마지막 문자 오른쪽 index
    static int lastY;   // 마지막 문자 오른쪽 index
    static int[][] charCount;   // 각 행의 문자 개수 저장 배열
    void init(int H, int W, char mStr[])
    {
        list = new LinkedList[H + 1];
        width = W;
        lastX = 1;
        lastY = 0;
        charCount = new int[H + 1][26];

        for (int i = 1; i <= H; i++) {
            list[i] = new LinkedList<>();
            list[i].add('0');
        }

        // 리스트에 mStr 문자 저장
        for (char c : mStr) {
            if (c == '\u0000') {
                break;
            }

            if (lastY == W) {
                lastX++;
                lastY = 0;
            }

            list[lastX].add(c);
            charCount[lastX][c - 'a']++;
            lastY++;
        }

        cursorX = 1;
        cursorY = 1;
        System.out.println("#" + (++order) +Arrays.toString(list) + " cursor: (" + cursorX + ", " + cursorY + ")");

    }

    void insert(char mChar)
    {
        // 커서 위치에 mChar 삽입
        list[cursorX].add(cursorY, mChar);
        charCount[cursorX][mChar - 'a']++;

        // 문자를 삽입했을 때 삽입한 행의 길이가 W보다 크면 W 길이가 맞을 때까지 맨 뒤의 문자를 아래 행의 맨 앞으로 삽입
        for (int i = cursorX; i <= lastX; i++) {
            // 현재 행의 길이가 알맞으면 break
            if (list[i].size() - 1 <= width) {
                break;
            }

            char tmp = list[i].pollLast();
            charCount[i][tmp - 'a']--;

            list[i + 1].add(1, tmp);
            charCount[i + 1][tmp - 'a']++;
        }

        // 커서 위치 업데이트
        if (cursorY > width) {   // 커서가 현재 행의 끝에 있으면
            cursorX++;
            cursorY = 1;
        } else {
            cursorY++;
        }

        // lastX, Y  업데이트
        lastY++;
        if (lastY > width) {
            lastX++;
            lastY = 1;
        }
        System.out.println("#" + (++order) +Arrays.toString(list) + " cursor: (" + cursorX + ", " + cursorY + ")");
    }

    char moveCursor(int mRow, int mCol)
    {
        // 커서 이동
        cursorX = mRow;
        cursorY = mCol;

        // mRow 행 mCol 열이 비어있는 경우 커서를 문자열의 마지막문자 오른쪽으로 이동, ‘$’ 리턴
        if (cursorX > lastX || (cursorX == lastX && cursorY > lastY)) {
            cursorX = lastX;
            cursorY = lastY + 1;
            System.out.println("#" + (++order) +Arrays.toString(list) + " cursor: (" + cursorX + ", " + cursorY + ")");

            return '$';
        }
        System.out.println("#" + (++order) +Arrays.toString(list) + " cursor: (" + cursorX + ", " + cursorY + ")");

        return list[mRow].get(mCol);
    }

    int countCharacter(char mChar)
    {
        int cnt = 0;    // mChar 의 개수

        System.out.println("hi");
        // 커서가 있는 행의 mChar 개수 계산
        for (int i = cursorY; i < list[cursorX].size(); i++) {
            if (list[cursorX].get(i) == mChar) {
                cnt++;
            }
        }
        System.out.println("hi");


        // 나머지 행의 mChar 개수 계산
        for (int i = cursorX + 1; i <= lastX; i++) {
            cnt += charCount[i][mChar - 'a'];
        }

        System.out.println("#" + (++order) + " countChar " +Arrays.toString(list) + " cursor: (" + cursorX + ", " + cursorY + ")");

        return cnt;
    }
}