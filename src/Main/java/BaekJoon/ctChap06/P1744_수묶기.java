package ctChap06;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class P1744_수묶기 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		// 양수, 음수를 PriorityQueue에 따로 저장
		PriorityQueue<Integer> pqPlus = new PriorityQueue(Collections.reverseOrder());
		PriorityQueue<Integer> pqMinus = new PriorityQueue();
		int max = 0;
		int zero = 0;    // 0의 개수
		int one = 0;
		
		for (int i = 0;  i< N; i++) {	// 카드 묶음을 입력받아 큐에 저장
			int pack = sc.nextInt();
			if (pack > 1) {
				pqPlus.offer(pack);
			}
			else if (pack == 1) {
				one += 1;
			}
			else if (pack < 0) {
				pqMinus.offer(pack);
			}
			else {
				zero += 1;
			}
		}
		
		// 양수 큐 작은 순으로 두 개씩 곱해서 max에 더함
		while (pqPlus.size() > 1) {
			if (pqPlus.size() == 1) {    // 하나 남으면 그냥 더함.
				max += pqPlus.poll();
				break;
			}
			else {
				int pack1 = pqPlus.poll();
				int pack2 = pqPlus.poll();
				max += pack1 * pack2;
			}
		}
		if (!pqPlus.isEmpty()) {    // 양수가 하나 남아있으면
			max += pqPlus.poll();
		}
		
		
		// 음수 큐 작은 순으로 두 개씩 곱해서 max에 더함
		while (pqMinus.size() > 1) {
			int pack1 = pqMinus.poll();
			int pack2 = pqMinus.poll();
			max += pack1 * pack2;
		}
		
		if (!pqMinus.isEmpty()) {    // 음수가 한 개 남아있으면
			if (zero == 0) {    // 0이 없으면 그냥 더함. 없으면 더하지 않음(0이랑 곱해서 0이 되니까)
				max += pqMinus.poll();
			}
		}
		
		max += one;	// 1 더하기
		
		System.out.println(max);
	}
}
