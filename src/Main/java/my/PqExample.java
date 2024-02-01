package my;

import java.util.PriorityQueue;
import java.util.Scanner;

public class PqExample {

	public static void main(String[] args) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		Scanner sc = new Scanner(System.in);
	  	int N = sc.nextInt();
		for (int i = 0; i < N; i++) {
			int num = sc.nextInt();
			String str = sc.next();
			pq.offer(new Node(num, str));
		}
		while (!pq.isEmpty()) {
			Node node = pq.poll();
			System.out.println(node.num + ", " + node.str);

		}
	}
	
	static class Node implements Comparable<Node>{
		int num;
		String str;
		
		Node(int num, String str) {
			this.num = num;
			this.str = str;
		}

		@Override
		public int compareTo(Node o) {
			
			if (this.num != o.num) {
				return this.num > o.num ? 1 : -1;
			}
			return this.str.compareTo(o.str);
		}
	}
}
