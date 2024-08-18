package SWEA.알고리즘특강.K번째접미어;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.Stack;


class Solution {
    private static TrieNode rootNode;
    private static int k;
    private static int cnt;

    public static void main(String args[]) throws Exception {
        System.setIn(new FileInputStream("src/Main/java/SWEA/알고리즘특강/K번째접미어/input.txt"));

        Scanner sc = new Scanner(System.in);
        int T;
        T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            k = sc.nextInt();   // 사전적 순서로 K번째에 오는 접미사 구하기
            String str = sc.next();
            rootNode = new TrieNode(); // 루트 노드
            cnt = 0;

            // str 을 트라이로 만들기
            buildSuffixTrie(str);

            // 접미어를 DFS 로 순회하면서 사전적으로 k번째 접미어 찾기
            String suffixK = DFS(rootNode, new StringBuilder());

            System.out.println("#" + test_case + " " + suffixK);
        }
    }

    private static String DFS(TrieNode node, StringBuilder curSuffix) {
        if (node.isLeafNode) {
            cnt++;
            if (cnt == k) {
                return curSuffix.toString();
            }
        }
        if (node.childNodes.size() > 0) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (node.childNodes.containsKey(c)) {
                    curSuffix.append(c);
                    DFS(node.childNodes.get(c), curSuffix);
                    if (cnt == k) {
                        return curSuffix.toString();
                    }
                    curSuffix.deleteCharAt(curSuffix.length() - 1);
                }
            }
        }
        return null;
    }

    private static void buildSuffixTrie(String str) {
        for (int i = 0; i < str.length(); i++) {
            insertSuffix(str.substring(i));
        }
    }

    // 주어진 접미어를 트라이에 삽입
    private static void insertSuffix(String suff) {
        TrieNode thisNode = rootNode;

        for (int i = 0; i < suff.length(); i++) {
            char ch = suff.charAt(i);
            thisNode = thisNode.childNodes.computeIfAbsent(ch, c -> new TrieNode());
        }
        thisNode.isLeafNode = true;  // 접미사의 끝을 표시
    }

    private static class TrieNode {
        Map<Character, TrieNode> childNodes = new HashMap<>();  // key: 문자열, value: 이어지는 자식 노드
        boolean isLeafNode;
    }
}