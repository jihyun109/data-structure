package SWEA.알고리즘특강.단어검색;

import java.util.HashMap;

class UserSolution {
    private static int cnt; // 해당되는 단어의 수 (구하는 것)
    private static boolean isRM;    // 삭제 연산인지
    private static TrieNode rootNode;   // 루트노드
    private static char[] searchWord;   // 검색어

    void init() {
        cnt = 0;
        isRM = false;
        rootNode = new TrieNode();
        searchWord = null;
    }

    int add(char str[]) {
        cnt = 0;
        TrieNode thisNode = rootNode;

        for (int i = 0; i < str.length; i++) {
            char ch = str[i];
            if (ch == '\u0000') {
                break;
            }
            thisNode = thisNode.childNodes.computeIfAbsent(ch, c -> new TrieNode());
            thisNode.jarisu = i;
        }

        thisNode.wordCount++;
        thisNode.isLeafNode = true;
        return thisNode.wordCount;
    }

    int remove(char str[]) {
        cnt = 0;
        isRM = true;
        searchWord = strAbstract(str);
        DFS(rootNode, new StringBuilder());
        return cnt;
    }

    int search(char str[]) {
        cnt = 0;
        searchWord = strAbstract(str);
        isRM = false;
        DFS(rootNode, new StringBuilder());
        return cnt;
    }

    private void DFS(TrieNode thisNode, StringBuilder curString) {
        if (thisNode.isLeafNode && thisNode.jarisu == searchWord.length - 1) {  // 리프노드이면
            cnt += thisNode.wordCount;
            if (isRM) {
                thisNode.wordCount = 0;
            }
            return;
        }

        if (!thisNode.childNodes.isEmpty()) {
            for (char c : thisNode.childNodes.keySet()) {
                int jarisu = thisNode.childNodes.get(c).jarisu;
                if (jarisu > searchWord.length - 1) {
                    return;
                }

                // 다음 노드의 글자가 searchWord 의 현재 자리수의 글자와 같거나 searchWord 의 현재 자리의 값이 ? 인 경우
                if (c == searchWord[jarisu] || searchWord[jarisu] == '?') {
                    curString.append(c);
                    DFS(thisNode.childNodes.get(c), curString);
                    curString.deleteCharAt(curString.length() - 1);
                }
            }
        }
    }

    private static char[] strAbstract(char[] strChar) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strChar.length; i++) {
            if (strChar[i] == '\u0000') {
                return stringBuilder.toString().toCharArray();
            }
            stringBuilder.append(strChar[i]);
        }
        return null;
    }


    private static class TrieNode {
        HashMap<Character, TrieNode> childNodes;    // 자식 노드 저장 맵 (key: 문자, value: 이어진 노드)
        boolean isLeafNode;
        int wordCount;
        int jarisu;

        private TrieNode() {
            this.childNodes = new HashMap<>();
            this.isLeafNode = false;
            this.wordCount = 0;
            this.jarisu = 0;
        }
    }
}