package BaekJoon.b2580_스도쿠;

import java.io.*;
import java.util.*;

public class Main {
    private static int[][] board;
    private static List<Set<Integer>> notExistRow;
    private static List<Set<Integer>> notExistCol;
    private static List<Set<Integer>> notExistBlock;
    private static List<Point> blanks;
    private static int idx;

    public static void main(String[] args) throws IOException{
        init();

        if (blanks.isEmpty()) {
            print();
        }

        idx = 0;
        Point startPoint = blanks.get(idx);
        findAnswer(startPoint);

        print();
    }

    private static void init() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        board = new int[9][9];
        notExistRow = new ArrayList<>();
        notExistCol = new ArrayList<>();
        notExistBlock = new ArrayList<>();
        blanks = new ArrayList<>();
        setting(notExistRow, notExistCol, notExistBlock);

        for (int r = 0; r < 9; r++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int c = 0; c < 9; c++) {
                int num = Integer.parseInt(st.nextToken());

                board[r][c] = num;

                if (num == 0) {
                    blanks.add(new Point(r, c));
                } else {
                    notExistRow.get(r).remove(num);
                    notExistCol.get(c).remove(num);

                    int blockNo = findBlockNo(r, c);
                    notExistBlock.get(blockNo).remove(num);
                }
            }
        }
        br.close();
    }

    private static void setting(List<Set<Integer>> notExistRow, List<Set<Integer>> notExistCol, List<Set<Integer>> notExistBlock) {
        for (int i = 0; i < 9; i++) {
            notExistRow.add(new HashSet<>());
            notExistCol.add(new HashSet<>());
            notExistBlock.add(new HashSet<>());
            for (int num = 1; num <= 9; num++) {
                notExistRow.get(i).add(num);
                notExistCol.get(i).add(num);
                notExistBlock.get(i).add(num);
            }
        }
    }

    private static int findBlockNo(int r, int c) {
        if (r >= 0 && r <= 2) {
            if (c >= 0 && c <= 2) {
                return 0;
            } else if (c >= 3 && c <= 5) {
                return 1;
            } else {
                return 2;
            }
        } else if (r >= 3 && r <= 5) {
            if (c >= 0 && c <= 2) {
                return 3;
            } else if (c >= 3 && c <= 5) {
                return 4;
            } else {
                return 5;
            }
        } else {
            if (c >= 0 && c <= 2) {
                return 6;
            } else if (c >= 3 && c <= 5) {
                return 7;
            } else {
                return 8;
            }
        }
    }

    private static void findAnswer(Point point) {
        int r = point.getR();
        int c = point.getC();
        int blockNo = findBlockNo(r, c);

        // point 좌표에 올 수 있는 모든 수 구하기
        Queue<Integer> candidates = findCandidates(point);

        // 현재 좌표를 채우고 다음에 채울 좌표
        while (!candidates.isEmpty()) {
            int num = candidates.poll();

            // point 좌표를 num으로 채우기
            board[r][c] = num;
            notExistRow.get(r).remove(num);
            notExistCol.get(c).remove(num);
            notExistBlock.get(blockNo).remove(num);

            // 모든 블럭이 다 채워진 경우
            if (++idx == blanks.size()) {
                return;
            }

            Point nextPoint = blanks.get(idx);
            if (nextPoint == null) {
                return;
            }

            findAnswer(nextPoint);

            if (idx == blanks.size()) {
                return;
            }

            // point 좌표 비우기
            board[r][c] = 0;
            notExistRow.get(r).add(num);
            notExistCol.get(c).add(num);
            notExistBlock.get(blockNo).add(num);
        }

        // 전체 빈 칸을 채우지 못했을 경우
        idx--;
    }

    private static Queue<Integer> findCandidates(Point pToFill) {
        Queue<Integer> candidates = new LinkedList<>();

        int r = pToFill.getR();
        int c = pToFill.getC();

        // 사용되지 않은 수들
        Set<Integer> notUsedRow = notExistRow.get(r);
        Set<Integer> notUsedCol = notExistCol.get(c);
        int blockNo = findBlockNo(r, c);
        Set<Integer> notUsedBlock = notExistBlock.get(blockNo);

        for (int num : notUsedRow) {
            if (notUsedCol.contains(num) && notUsedBlock.contains(num)) {
                candidates.add(num);
            }
        }

        return candidates;
    }

    private static void print() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                bw.append(board[r][c] + " ");
            }
            bw.append("\n");
        }

        bw.flush();
        bw.close();
    }
}

class Point {
    private int r;
    private int c;

    public Point(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public int getR() {
        return this.r;
    }

    public int getC() {
        return this.c;
    }
}