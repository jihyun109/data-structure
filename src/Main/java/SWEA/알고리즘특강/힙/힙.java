package SWEA.알고리즘특강.힙;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileInputStream;

class Solution {
    static ArrayList<Integer> heap;
    static int lastIdx;

    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/힙/sample_input.txt"));

        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            System.out.print("#" + test_case + " ");
            int N = sc.nextInt();   // 수행해야 하는 연산의 수
            heap = new ArrayList<>();
            heap.add(0);
            lastIdx = 0;

            // 연산 수행
            for (int i = 0; i < N; i++) {
                int commend = sc.nextInt();
                if (commend == 1) {
                    int value = sc.nextInt();
                    add(value);
                } else if (commend == 2) {
                    int root = findRoot();
                    System.out.print(root + " ");
                }
            }
            System.out.println();
        }
    }

    private static void add(int n) {
        heap.add(n);
        lastIdx++;
        heapSort();
//        System.out.println(heap);
    }

    private static int findRoot() {
        if (heap.size() == 1) {
            return -1;
        }
        int root = heap.get(1);
        heap.set(1, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        lastIdx--;
        downHeap(1);

        return root;
    }

    // node 를 루트로 하는 서브트리를 정렬
    private static void downHeap(int node) {
        int curNode = node; // node 의 현재 위치
        while (curNode * 2 <= lastIdx) {    // curNode 가 리프노드가 될 때까지
            int c1 = curNode * 2;
            int c2 = curNode * 2 + 1;

            // 둘 중 값이 큰 자식 구하기
            int child = 0;
            if (c2 > lastIdx) { // 자식 노드가 하나밖에 없으면
                child = c1;
            } else {
                child = heap.get(c1) >= heap.get(c2) ? c1 : c2;
            }

            // 자식과 비교해 정렬
            if (heap.get(curNode) >= heap.get(child)) {
                break;
            } else {
                int tmp = heap.get(curNode);
                heap.set(curNode, heap.get(child));
                heap.set(child, tmp);
                curNode = child;
            }
        }

    }

    private static void heapSort() {
        int nodeToSort = lastIdx / 2;   // 정렬할 노드의 index

        while (nodeToSort != 0) {
            downHeap(nodeToSort);
            nodeToSort /= 2;
        }
    }
}