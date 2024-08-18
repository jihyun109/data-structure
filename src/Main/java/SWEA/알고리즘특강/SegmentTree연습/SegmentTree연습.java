package SWEA.알고리즘특강.SegmentTree연습;

import java.util.Arrays;
import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
    private static int[] arr;
    private static boolean isMax;

    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/SegmentTree연습/sample_in.txt"));

        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            int arrLen = sc.nextInt();  // 배열 길이
            int queryN = sc.nextInt();  // 쿼리 수
            arr = new int[arrLen + 1];

            for (int i = 1; i <= arrLen; i++) {
                arr[i] = sc.nextInt();
            }
//            System.out.println(Arrays.toString(arr));

            // 세그먼트 트리 만들기
            int[] minSegTree = new int[arrLen * 4];   // 최솟값 세그먼트 트리 저장 배열
            int[] maxSegTree = new int[arrLen * 4];   // 최댓값 세그먼트 트리 저장 배열
            isMax = true;
            init(maxSegTree, 1, 1, arrLen);    // 루트 노드(1)에 전체 범위의 최댓값 입력
            isMax = false;
            init(minSegTree, 1, 1, arrLen);    // 루트 노드(1)에 전체 범위의 최솟값 입력
            System.out.print("#" + test_case + " ");

            for (int i = 0; i < queryN; i++) {
                int command = sc.nextInt(); // 명령
                int n1 = sc.nextInt();
                int n2 = sc.nextInt();

                switch (command) {
                    case (0): {
                        int diff = n2 - arr[n1 + 1];    // 더할 값과 원래 값의 차
                        arr[n1 + 1] = n2;
                        // 루트노드부터 모든 범위에서 n1+1 노드의 값에 n2-arr[n1]을 더함
                        isMax = true;
                        update(maxSegTree, 1, 1, arrLen, n1 + 1, diff);
                        isMax = false;
                        update(minSegTree, 1, 1, arrLen, n1 + 1, diff);
                        break;
                    }
                    case (1): {
                        isMax = true;
                        int max = query(maxSegTree, 1, 1, arrLen, n1 + 1, n2 );
                        isMax = false;
                        int min = query(minSegTree, 1, 1, arrLen, n1 + 1, n2 );
                        System.out.print((max - min) + " ");
                        break;
                    }
                }
            }
            System.out.println();
        }
    }

    private static int init(int[] segTree, int nodeN, int s, int e) {
        if (s == e) {
            return segTree[nodeN] = arr[s];
        }

        int m = (s + e) / 2;
        int a = init(segTree, nodeN * 2, s, m); // 왼쪽 자식 노드
        int b = init(segTree, nodeN * 2 + 1, m + 1, e); // 오른쪽 자식 노드
        return isMax ? (segTree[nodeN] = Math.max(a, b)) : (segTree[nodeN] = Math.min(a, b));
    }


    private static int update(int[] segTree, int nodeN, int s, int e, int i, int value) {
        // 대상 범위 안에 있을 때만 갱신
        if (s > i || i > e) {
            return segTree[nodeN];
        }
        segTree[nodeN] += value;
        if (s == e) {
            return segTree[nodeN];
        }

        int m = (s + e) / 2;
        int a = update(segTree, nodeN * 2, s, m, i, value);
        int b = update(segTree, nodeN * 2 + 1, m + 1, e, i, value);
        return isMax ? (segTree[nodeN] = Math.max(a, b)) : (segTree[nodeN] = Math.min(a, b));
    }

    private static int query(int[] segTree, int nodeN, int ts, int te, int qs, int qe) {
        // 경우 1: 트리 구간이 질의구간에 완전히 포함됨
        if (ts >= qs && qe >= te) return segTree[nodeN];

        // 경우 2: 트리구간과 질의구간이 전혀 겹치지 않음
        if (te < qs || qe < ts) {
            return isMax ? 0 : Integer.MAX_VALUE;
        }

        // 경우 3: 일부 겹침
        int m = (ts + te) / 2;
        int a = query(segTree, nodeN * 2, ts, m, qs, qe);
        int b = query(segTree, nodeN * 2 + 1, m + 1, te, qs, qe);
        return isMax ? Math.max(a, b) : Math.min(a, b);
    }
}