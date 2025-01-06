package BaekJoon.P2751_수정렬하기2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

public class P2751_수정렬하기2 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   // 수의 개수
        int[] arr = new int[N];

        // 수 입력받기
        for (int i = 0; i < N; i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();

        int[] sortedArr = mergeSort(arr);

        // 답 출력
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int i = 0; i < sortedArr.length; i++) {
            bw.write(sortedArr[i] + "\n");
        }
        bw.flush();
        bw.close();
    }

    private static int[] mergeSort(int[] arr) {
        if (arr.length == 1) {   // 배열의 크기가 1이면 return
            return arr;
        }

        int m = arr.length / 2; // 배열을 반으로 나눌 기준 index
        int[] arr1 = Arrays.copyOfRange(arr, 0, m);
        int[] arr2 = Arrays.copyOfRange(arr, m, arr.length);

        int[] sortedArr1 = mergeSort(arr1); // 정렬된 arr1
        int[] sortedArr2 = mergeSort(arr2); // 정렬된 arr2

        // sortedArr1과 sortedArr2를 사용해 arr 정렬
        int[] sortedArr = new int[arr.length];  // sortedArr1과 sortedArr2를 사용해 정렬한 arr 를 저장할 배열
        int p = 0;  // sortedArr 의 index
        int p1 = 0; // sortedArr1 과 sortedArr2의 인덱스
        int p2 = 0;
        while (p < arr.length) {
            if (p1 >= sortedArr1.length || p2 >= sortedArr2.length) {   // 두 배열 중 하나의 배열의 정렬이 다 끝났으면
                int[] lastArr = (p1 >= sortedArr1.length) ? sortedArr2 : sortedArr1;
                int lastP = (p1 >= sortedArr1.length) ? p2 : p1;

                while (p < sortedArr.length) {
                    sortedArr[p++] = lastArr[lastP++];
                }
                return sortedArr;
            }

            int n1 = sortedArr1[p1];
            int n2 = sortedArr2[p2];
            if (n1 < n2) {
                sortedArr[p++] = n1;
                p1++;
            } else {
                sortedArr[p++] = n2;
                p2++;
            }
        }

        return sortedArr;
    }
}
