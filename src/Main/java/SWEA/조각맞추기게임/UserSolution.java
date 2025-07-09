package 조각맞추기게임;

import java.util.*;

class UserSolution {
	private List<Stick> wall; // 벽 정보 저장
	private Map<String, TreeSet<Integer>> startPointByPiece; // k: 조각 모양, v: 해당 조각의 시작 위치
	private TreeMap<Integer, String> shapesByStartPoint; // k: 조각의 시작 위치, 조각 모양
	private Map<Integer, Set<Integer>> contentOfPiece;	// k: 조각의 시작 위치, v: 조각을 구성하는 막대기의 idx
	private SegTree deletedStickN; // 삭제된 막대 수 저장 세그먼트 트리
	
	private int lastIdx; // 추가된 막대기의 마지막 idx
	private final int PIECE_SIZE = 5; // 하나의 조각을 구성하는 막대기 개수

	void init() {
		wall = new ArrayList<>();
		startPointByPiece = new HashMap<>();
		shapesByStartPoint = new TreeMap<>();
		contentOfPiece = new HashMap<>();
		deletedStickN = new SegTree();
		lastIdx = -1;
	}

	void makeWall(int mHeights[]) {
		// 벽 정보 저장
		int firstIdx = lastIdx + 1;	// 추가된 벽이 저장되는 첫번째 idx
		contentOfPiece.put(firstIdx, new HashSet<>());
		
		for (int i = 0; i < PIECE_SIZE; i++) {
			int idx = ++lastIdx;
			int leftIdx = findLeftIdx(idx);
			Stick stick = new Stick(mHeights[i], leftIdx, -1, false);
			wall.add(++lastIdx, stick);
			contentOfPiece.get(firstIdx).add(idx);
			
			// 왼쪽 막대기의 오른쪽 idx 수정
			wall.get(leftIdx).setRightIdx(idx);
		}

		// 벽을 추가함으로써 생기는 조각 모양 저장
		addPieces(firstIdx);
	}

	int matchPiece(int mHeights[]) {
		// mHeights 과 매칭되는 조각 구하기
		String piece = findMatchingPiece(mHeights);
		
		// 매칭되는 조각이 없는 경우
		if (!startPointByPiece.containsKey(piece) || startPointByPiece.get(piece).size() == 0) {
			return -1;
		}
		
		// 가장 오른쪽에 위치한 매칭되는 조각 찾아 없애기
		int matchingPieceIdx = startPointByPiece.get(piece).getFirst();
		
		// 매칭되는 조각이 포함되는 조각들 삭제
		deletePieces(matchingPieceIdx);
		
		// wall 에서 삭제된 막대기 표시
		
		// 새로 생성된 조각들 추가
		
		// matchingPieceIdx 의 왼쪽에서부터 idx 구하기
		
		return -1;
	}
	
	// idx로 시작하는 조각을 구성하는 막대기를 포함하는 모든 조각 삭제
	private Queue<Integer> deletePieces(int idx) {
		Queue<Integer> startPoints = new LinkedList<>();	// 삭제된 조각의 시작 idx
		
		int startIdx = findStartIdx(idx);	// idx 가 마지막 막대기인 조각의 첫번째 막대기 idx
		Map<Integer, String> tailMap = shapesByStartPoint.tailMap(startIdx);
		
		for (Map.Entry<Integer, String> entry : tailMap.entrySet()) {
			int startPoint = entry.getKey();
			String piece = entry.getValue();
			
//			startPointByPiece.
			
			
		}
		
		return startPoints;
	}
	
	private int findStartIdx(int idx) {
		int sIdx = idx;
		for (int i = 0; i < 4; i++) {
			sIdx = findLeftIdx(sIdx);
		}
		return sIdx;
	}
	
	private String findMatchingPiece(int[] piece) {
		// piece에서 최대 높이 구하기
		int max = 0;
		for (int i = 0; i < PIECE_SIZE; i++) {
			max = Math.max(max,  piece[i]);
		}
		
		// 매칭되는 조각 구하기
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < PIECE_SIZE; i++) {
			int idx = PIECE_SIZE - (i + 1);
			sb.append(max - piece[idx]);
		}
		
		return new String(sb);
	}

	private int findLeftIdx(int idx) {
		int leftIdx = idx - 1;

		while (leftIdx >= 0) {
			Stick left = wall.get(leftIdx);
			if (!left.isDeleted()) {
				return leftIdx;
			}
			
			leftIdx--;
		}

		return -1;
	}
	
	// idx에 조각이 추가됐을 때 생성될 수 있는 조각들 추가
	private void addPieces(int idx) {
		int startIdx = (idx - 4) < 0 ? 0 : idx - 4;
		
		for (int firstIdx = startIdx; firstIdx <= idx; firstIdx++) {	// 조각의 첫번째 idx
			String piece = findPiece(firstIdx);
			
			startPointByPiece.computeIfAbsent(piece, k -> new TreeSet<>(Comparator.reverseOrder()));
			startPointByPiece.get(piece).add(firstIdx);
			shapesByStartPoint.put(firstIdx, piece);
		}
	}
	
	// idx 에서 시작하는 조각 모양 return
	private String findPiece(int idx) {
		// idx 에서 시작하는 조각 추출
		int min = Integer.MAX_VALUE;	// 조각 중 가장 짧은 막대의 길이
		int cnt = 0;	// 채운 막대의 수
		
		int[] piece = new int[PIECE_SIZE];
		piece[cnt++] = wall.get(idx).getHeight();
		
		while (cnt < 5) {
			int nextIdx = wall.get(idx).getRightIdx();
			
			// nextIdx가 없는 경우
			if (nextIdx == -1) {
				return null;
			}
			
			int height = wall.get(nextIdx).getHeight();
			piece[cnt++] = height;
			min = Math.min(min,  height);
		}
		
		// 조각을 최소 막대기의 높이에 맞춰 수정
		String editedPiece = editPiece(piece, min);
		
		return editedPiece;
	}
		
	private String editPiece(int[] piece, int min) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < PIECE_SIZE; i++) {
			sb.append(piece[i] - min);
		}
		
		return new String(sb);
	}
}

class Stick {
	private int height;
	private int leftIdx;
	private int rightIdx;
	private boolean isDeleted;

	public Stick(int height, int leftIdx, int rightIdx, boolean isDeleted) {
		this.height = height;
		this.leftIdx = leftIdx;
		this.rightIdx = rightIdx;
		this.isDeleted = isDeleted;
	}

	public int getHeight() {
		return this.height;
	}
	
	public int getRightIdx() {
		return this.rightIdx;
	}
	
	public boolean isDeleted() {
		return this.isDeleted;
	}
	
	public void setRightIdx(int idx) {
		this.rightIdx = idx;
	}
	


}

class SegTree {
	private int[] tree;
	private int firstLeafNode;
	private final int MAX_NODE_N = 20000; // 최대 노드의 개수

	public SegTree() {
		firstLeafNode = findFirstLeafNode();
		tree = new int[firstLeafNode * 2];
	}

	public void update(int idx, int value) {
		int leafIdx = firstLeafNode + idx;

		while (leafIdx > 1) {
			leafIdx /= 2;

			tree[leafIdx] = tree[leafIdx * 2] + tree[leafIdx * 2 + 1];
		}
	}

	public int findSum(int s, int e) {
		int sum = 0;
		int sLeafIdx = firstLeafNode + s;
		int eLeafIdx = firstLeafNode + e;

		while (sLeafIdx < eLeafIdx) {
			if (sLeafIdx % 2 == 1) {
				sum += tree[sLeafIdx];
			}
			if (eLeafIdx % 2 == 0) {
				sum += tree[eLeafIdx];
			}

			sLeafIdx /= 2;
			eLeafIdx /= 2;
		}

		if (sLeafIdx == eLeafIdx) {
			sum += tree[sLeafIdx];
		}

		return sum;
	}

	private int findFirstLeafNode() {
		int height = 0;

		while (true) {
			int siblingN = (int) Math.pow(2, height); // 높이가 height인 형제 노드의 수
			if (siblingN >= MAX_NODE_N) {
				return siblingN;
			}
		}
	}
}