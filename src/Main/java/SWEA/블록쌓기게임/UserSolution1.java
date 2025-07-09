package 블록쌓기게임;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.TreeMap;

class UserSolution1 {
	private TreeMap<Integer, Integer> orderByIdx;

	private int col;
	private long blockN;
	private final int NUM = 1000000; // 남아있는 블록 수를 나눌 값

	void init(int C) {
		col = C;
		orderByIdx = new TreeMap<>(); // k: idx, v: idx부터 다음 idx 전까지의 높이
		orderByIdx.put(0, 0);
		blockN = 0;
	}

	Solution1.Result dropBlocks(int mCol, int mHeight, int mLength) {
		Solution1.Result ret = new Solution1.Result();

		blockN += mHeight * mLength;

		int sIdx = mCol; // 블럭이 떨어지는 시작 인덱스
		int eIdx = mCol + mLength - 1; // 블럭이 떨어지는 끝 인덱스

		orderByIdx.put(sIdx, orderByIdx.getOrDefault(sIdx, orderByIdx.floorEntry(sIdx).getValue()) + mHeight);

		if (orderByIdx.lowerEntry(sIdx) != null && orderByIdx.lowerEntry(sIdx).getValue() == orderByIdx.get(sIdx)) {
			orderByIdx.remove(sIdx);
		}

		NavigableMap<Integer, Integer> subMap = orderByIdx.subMap(sIdx, false, eIdx, true);
		if (subMap != null) {
			for (Map.Entry<Integer, Integer> entry : subMap.entrySet()) {
				int idx = entry.getKey();
				int height = entry.getValue();

				orderByIdx.put(idx, height + mHeight);
			}
		}

		if (!orderByIdx.containsKey(eIdx + 1) && eIdx + 1 < col) {
			Map.Entry<Integer, Integer> lastEntryOfSubMap = orderByIdx.lowerEntry(eIdx + 1);
			orderByIdx.put(eIdx + 1, lastEntryOfSubMap.getValue() - mHeight);
		}

		// 최소 높이 구하기
		int minHeight = Collections.min(orderByIdx.values());
		int maxHeight = Collections.max(orderByIdx.values());
		
		for (int height : orderByIdx.values()) {
			minHeight = Math.min(minHeight, height);
			maxHeight = Math.max(maxHeight, height);
		}

		long deletedBlockN = minHeight * col;

		ret.top = maxHeight - minHeight;
		long cnt = (blockN - deletedBlockN) % NUM;
		ret.count = (int) cnt;

		return ret;
	}

}
