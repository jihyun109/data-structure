package chap06;

import java.util.Arrays;
import java.util.Scanner;

public class heapSortMy {
	static void downHeap (int[] a, int left, int right) {
		int parent;
		int child;
		int tmp = a[left];
		
		for (parent = left; parent < (right + 1) / 2; parent = child) {
			int cl = parent * 2 + 1;
			int cr = cl + 1;
			child = (cr <= right && a[cr] > a[cl]) ? cr : cl;
			if (tmp >= a[child])
				break;
			a[parent] = a[child];
		}
		a[parent] = tmp;
	}
	
	static void heapSort(int[] a, int n) {
		for (int i = (n - 2) / 2; i >= 0; i--)
			downHeap(a, i, n - 1);
		System.out.println(Arrays.toString(a));

		for(int i = n-1; i > 0; i--) {
			swap(a, 0, i);
			downHeap(a, 0, i-1);
		}
	}

	static void swap(int[] a, int idx1, int idx2) {
		int t = a[idx1];
		a[idx1] = a[idx2];
		a[idx2] = t;
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.print("요솟수: ");
		int n = s.nextInt();
		int[] a = new int[n];
		
		for (int i = 0; i < n; i++) {
			System.out.print("a[" + i +"]:");
			a[i] = s.nextInt();
		}
		
		heapSort(a, n);
		
		
		System.out.println("오름차순으로 정렬하였습니다.");
		System.out.println(Arrays.toString(a));
	}

}