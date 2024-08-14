package SWEA.알고리즘특강.수만들기;

import java.util.*;
import java.io.*;

class Solution{

    public static void main(String args[]) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for(int tc = 1; tc <= T; ++tc){
            int N = Integer.parseInt(br.readLine());
            int[] A = new int[N];
            String[] arr = br.readLine().split(" ");
            int K = Integer.parseInt(br.readLine());

            int min = Integer.MAX_VALUE;
            for (int i = 0; i < N; i++){
                A[i] = Integer.parseInt(arr[i]);
                min = Math.min(A[i], min);
            }

            Queue<Node> pq = new PriorityQueue<Node>();
            pq.add(new Node(K, 0));

            int cnt = K;

            while(!pq.isEmpty()){
                Node n = pq.poll();

                if (n.left == 0){
                    cnt = n.cnt;
                    break;
                }

                pq.add(new Node(0, n.cnt + n.left));

                for (int i = 0; i < N; i++){
                    pq.add(new Node( n.left / A[i], n.cnt + n.left % A[i] ));
                }
            }

            System.out.println("#" + tc + " " + cnt);
        }
    }

    static class Node implements Comparable<Node>{
        int left, cnt;

        public Node(int left, int cnt){
            this.left = left;
            this.cnt = cnt;
        }

        public int compareTo(Node n){
            if (this.cnt > n.cnt) return 1;
            if (this.cnt == n.cnt) return 0;

            return -1;
        }
    }
}