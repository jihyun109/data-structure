package SWEA.메모장프로그램;

import java.util.HashMap;
import java.util.LinkedList;

class UserSolution
{
    private LinkedList<Character>[] memo;  // 행 별로 메모장 정보 저장 리스트 배열
    private int cursorR;    // 커서 행 번호
    private int cursorC;    // 커서 열 번호
    private int H;
    private int W;
    private HashMap<Character, Integer>[] charN;    // 행 별 문자 개수 저장 해시맵 (key: 문자, value: 개수)

    void init(int H, int W, char mStr[])
    {
        this.H = H;
        this.W = W;

        // 커서 초기 위치 설정
        cursorR = 0;
        cursorC = 0;

        // memo, hashMap
        for (int i = 1; i <= H; i++) {
            memo[i] = new LinkedList<>();
            charN[i] = new HashMap<>();
        }

        // 초기 문자열 입력받기
        int n = 0;
        int curRow = 1; // 현재 행
        int curCol = 1; // 현재 열

        for (char c : mStr) {
            if (c == '\u0000') {
                break;
            }
            memo[curRow].add(curCol, c);
//            charN[curRow].computeIfAbsent(c, k -> );
            curRow++;
            curCol++;
        }
    }

    void insert(char mChar)
    {

    }

    char moveCursor(int mRow, int mCol)
    {
        return '$';
    }

    int countCharacter(char mChar)
    {
        return -1;
    }
}