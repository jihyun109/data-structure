package SWEA.알고리즘특강.조각맞추기;

import java.util.Arrays;

class Edge {
    int x; // 위치 좌표
    Edge next;
}

class Mm {
    int height;
    int hash;
    int nextx; // 다음 열의 index값(x값)
    int prevx; // 이전 열의 index값(x값)
    int removed;
}

public class WallMatch {

    int[] T = new int[400000]; // Seg Tree
    int right = -1;
    Mm[] map = new Mm[100000];
    Edge[] hashTable = new Edge[1060000]; // 해시
    Edge[] edgeBuffer = new Edge[100000];
    int et = -1, ebt = -1;

    public WallMatch() {
        init();
    }

    Edge createEdge(int x) {
        ebt++; // Edge_buffer_top
        edgeBuffer[ebt] = new Edge();
        edgeBuffer[ebt].x = x;
        edgeBuffer[ebt].next = null;
        return edgeBuffer[ebt];
    }

    int _min(int a, int b) {
        return a < b ? a : b;
    }

    int _min5(int[] a) {
        int r = a[0];
        r = _min(r, a[1]);
        r = _min(r, a[2]);
        r = _min(r, a[3]);
        r = _min(r, a[4]);
        return r;
    }

    int _max(int a, int b) {
        return a > b ? a : b;
    }

    int _max5(int[] a) {
        int r = a[0];
        r = _max(r, a[1]);
        r = _max(r, a[2]);
        r = _max(r, a[3]);
        r = _max(r, a[4]);
        return r;
    }

    int updateT(int x, int i, int nl, int nr) {
        if (x < nl || nr < x) return T[i];
        if (nl == nr) return T[i] = T[i] + 5;
        int mid = (nl + nr) / 2;
        return T[i] = updateT(x, i * 2, nl, mid) + updateT(x, i * 2 + 1, mid + 1, nr);
    }

    int sumT(int l, int r, int i, int nl, int nr) {
        int mid = (nl + nr) / 2;
        if (l <= nl && nr <= r) return T[i];
        if (nr < l || r < nl) return 0;
        return sumT(l, r, i * 2, nl, mid) + sumT(l, r, i * 2 + 1, mid + 1, nr);
    }

    int calHash(int[] a) { // 해시 계산
        int min = _min5(a); // a에서의 최솟값
        int hash = 0;
        for (int i = 0; i < 5; i++) {
            hash = hash << 4; // 4bit마다 한 열씩
            hash |= a[i] - min; // 상대적인 높이
        }
        return hash;
    }

    int calHashInMap(int x) { // map에서 해시 계산
        int[] mList = new int[5];
        for (int i = 0; i < 5; i++) {
            mList[i] = map[x].height;
            if (x == -1) {
                return -1;
            }
            x = map[x].nextx;
        }
        return calHash(mList);
    }

    int find(int h) {
        if (h == -1) return -1;
        Edge tempEdge = hashTable[h];
        int x = -1;
        int r = -1;
        while (tempEdge.next != null) {
            x = tempEdge.next.x;
            if (map[x].hash == h) {
                r = _max(r, x);
            }
            tempEdge = tempEdge.next;
        }
        return r;
    }

    void pushH(int h, int x) {
        if (h == -1) return;
        Edge node = createEdge(x);
        node.next = hashTable[h].next;
        hashTable[h].next = node;
        map[x].hash = h;
    }

    void setPxNx(int x) {
        int px = right - 1; // Prevx
        if (px != -1) { // x가 제일 첫번째가 아니고
            if (map[px].removed == 1) { // 앞의 스틱이 제거됐으면
                px = map[px].prevx; // 앞의 스틱은 앞의 스틱의 앞.
            }
            map[x].prevx = px;  // 제거되지 않았으면 현재 스틱의 앞은 px
            if (px != -1) { // x가 제일 첫번째가 아니면
                map[px].nextx = x;      // 이전의 next는 현재
            }
        }
        map[x].nextx = -1;  // 현재의 next는 없음.
    }

    void init() {
        Arrays.fill(T, 0);
        Arrays.fill(map, null);
        right = -1;
        for (int i = 0; i < 100000; i++) {
            map[i] = new Mm();
            map[i].height = -1;
            map[i].nextx = -1;
            map[i].prevx = -1;
            map[i].hash = -1;
            map[i].removed = 0;
        }
        for (int i = 0; i < 1060000; i++) {
            hashTable[i] = new Edge();
            hashTable[i].x = -1;
            hashTable[i].next = null;
        }
        et = -1;
        ebt = -1;
    }

    void makeWall(int[] mHeights) {
        for (int i = 0; i < 5; i++) {
            map[++right].height = mHeights[i];
            setPxNx(right);
        }
        int x = right - 4;
        for (int i = 0; i < 5; i++) {
            int h = calHashInMap(x);
            if (h != -1) {
                pushH(h, x);
            }
            map[x].hash = h;
            x = map[x].prevx;
            if (x == -1) break;
        }
    }

    void convert(int[] a) { // 180도 회전
        int mx = _max5(a);
        int[] b = Arrays.copyOf(a, 5);
        a[0] = mx - b[4];
        a[1] = mx - b[3];
        a[2] = mx - b[2];
        a[3] = mx - b[1];
        a[4] = mx - b[0];
    }

    int matchPiece(int[] mHeights) {
        int r = -1;
        convert(mHeights);
        int h = calHash(mHeights);
        int x = find(h);
        r = x;
        if (x == -1) {
            return -1;
        }
        int px = map[x].prevx;
        for (int i = 0; i < 5; i++) {
            map[x].hash = -1;
            map[x].removed = 1;
            map[x].prevx = px;
            x = map[x].nextx;
            if (x == -1) break;
        }
        if (px != -1) {
            map[px].nextx = x;
            if (x != -1) {
                map[x].prevx = px;
            }
            x = px;
            for (int i = 0; i < 5; i++) {
                int hNew = calHashInMap(x);
                pushH(hNew, x);
                map[x].hash = hNew;
                x = map[x].prevx;
                if (x == -1) break;
            }
        }
        updateT(r, 1, 0, 75000); // r은 삭제된 블록의 x값
        r = r - sumT(0, r - 1, 1, 0, 75000) + 1; // x를 0부터 시작하는 것으로 해서 +1
        return r;
    }

    public static void main(String[] args) {
        WallMatch wm = new WallMatch();
        // 테스트 코드 작성
    }
}
