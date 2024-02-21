package ctChap08;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P1717_집합표현하기_my {
	static int[] arr;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();	// 원소 최댓값
		int m = sc.nextInt();	// 연산 개수
		arr = new int[n + 1];	// 대표 노드 저장 배열
		for (int i = 0; i <= n; i++) {	// 배열 value 채우기
			arr[i] = i;
		}
		
		Queue<Boolean> sameSet = new LinkedList<Boolean>();
		for (int i = 0; i < m; i++) {	// 연산 입력받고 수행
			int cal = sc.nextInt();	// 연산 종류
			int element1 = sc.nextInt();	// 원소 1
			int element2 = sc.nextInt();	// 원소 2
			
			if (cal == 0) {	// 0이면 union 연산
				union(element1, element2);
//				System.out.println(Arrays.toString(arr));
			} else if (cal == 1) {	// 1이면 두 원소의 대표 노드가 같은지 확인 & 답 result큐에 저장
				boolean result = isSameSet(element1, element2);
				sameSet.add(result);
//				System.out.println(Arrays.toString(arr));
//				System.out.println(result);

			}
		}
		sc.close();
		
		while (!sameSet.isEmpty()) {	// 저장해놓은 답 출력
			if (sameSet.peek()) {
				System.out.println("YES");
			} else if (!sameSet.peek()) {
				System.out.println("NO");
			}
			sameSet.poll();
		}
	}

	private static void union(int element1, int element2) {
		int parent1 = find(element1);	// 두 원소의 대표 노드 찾기
		int parent2 = find(element2);
		// 대표노드중 작은 값으로 대표노드 연결
		arr[parent1] = Math.min(parent1, parent2);
		arr[parent2] = Math.min(parent1, parent2);
	}

	private static boolean isSameSet(int element1, int element2) {
		int parent1 = find(element1);	// 두 원소의 대표 노드 찾기
		int parent2 = find(element2);
		if (parent1 == parent2) {
			return true;
		}
		return false;
	}
	
	private static int find(int elem) {
		if (elem == arr[elem]) {	// elem이 대표 노드이면
			return elem;
		} else if (elem != arr[elem]) {	// elem이 대표 노드가 아니면 대표 노드 찾기
			int parent = find(arr[elem]);	// 대표 노드 찾아서 대입.
			arr[elem] = parent;
		}
		return arr[elem];
	}
}
