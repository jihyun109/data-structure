package SWEA.알고리즘특강.출근길라디오;

import java.util.ArrayList;
import java.util.LinkedList;

class UserSolution {
    private static int[] transitT;
    private static int[] transitTSegTree;
    private static LinkedList<Integer>[] roadType;
    private static int spotN;
    private static int[] leafNodeIdx;
    private boolean needUpdate;

    void init(int N, int M, int mType[], int mTime[]) {
        spotN = N;  // 지점 개수
        transitT = new int[spotN];    // 구간 통과 시간 저장 배열
        roadType = new LinkedList[M + 1]; // 도로 종류에 해당되는 구간 저장 리스트
        leafNodeIdx = new int[spotN];   // segTree 에서 각 구간의 leaf node 위치 저장 배열
        needUpdate = false;
        for (int i = 0; i < M; i++) {
            roadType[i] = new LinkedList<>();
        }

        // 구간의 통과 시간, 도로 종류 저장.
        for (int stage = 1; stage < spotN; stage++) {
            transitT[stage] = mTime[stage - 1]; // 현재 구간의 통과 시간 저장
            int type = mType[stage - 1];    // 도로 종류
            roadType[type].add(stage);
        }

        // 구간 통과 시간 세그먼트 트리 만들기
        transitTSegTree = new int[spotN * 4];  // 구간 통과 시간 세그먼트 트리
        init(1, 1, spotN - 1);
    }

    void destroy() {

    }

    void update(int mID, int mNewTime) {
        transitT[mID + 1] = mNewTime;
        needUpdate = true;
//        updateSegTreeV2(1, 1, spotN - 1);
    }

    int updateByType(int mTypeID, int mRatio256) {
        int sum = 0;

        // 도로의 종류가 mTypeID인 구간을 모두 업데이트
        for (int stage : roadType[mTypeID]) {
            int originT = transitT[stage];   // 원래 소요 시간
            int newT = originT * mRatio256 / 256;
            transitT[stage] = newT;
            sum += newT;

            int leafNode = leafNodeIdx[stage];
            transitTSegTree[leafNode] = newT;
        }
        // 한꺼번에 세그먼트 트리 업데이트
        needUpdate = true;
//        updateSegTreeV2(1, 1, spotN - 1);
        return sum;
    }

    int calculate(int mA, int mB) {
        if (needUpdate) {
            updateSegTreeV2(transitTSegTree,1, 1, spotN - 1);
            needUpdate = false;
        }
        int ans = caculateT(transitTSegTree,1, 1, spotN - 1, Math.min(mA, mB) + 1, Math.max(mA, mB));
        return ans;
    }

    private static int init(int node, int s, int e) {
        // s, e : arr 의 시작, 끝 index
        if (s == e) {
            leafNodeIdx[s] = node;
            return transitTSegTree[node] = transitT[s];
        }

        int m = (s + e) / 2;
        int a = init(node * 2, s, m);
        int b = init(node * 2 + 1, m + 1, e);
        return transitTSegTree[node] = a + b;
    }

    private static int updateSegTreeV2(int[] tree, int node, int s, int e) {
        // 갱신
        if (s == e) {
            return transitTSegTree[node];
        }

        int m = (s + e) / 2;
        int a = init(node * 2, s, m);
        int b = init(node * 2 + 1, m + 1, e);
        return transitTSegTree[node] = a + b;
    }

    private static int caculateT(int[] tree, int node, int ts, int te, int qs, int qe) {
        if (ts >= qs && qe >= te) {
            return transitTSegTree[node];
        }

        if (te < qs || qe < ts) {
            return 0;
        }

        int m = (ts + te) / 2;
        int a = caculateT(tree,node * 2, ts, m, qs, qe);
        int b = caculateT(tree,node * 2 + 1, m + 1, te, qs, qe);
        return a + b;
    }
}