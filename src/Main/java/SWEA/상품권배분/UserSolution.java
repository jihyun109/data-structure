package 상품권배분;

import java.util.*;

class UserSolution {
	private int sumPeopleN; // 총 인원 수
	private Map<Integer, DepartmentInfo> departments; // 각 부서의 정보 저장(k: 부서 id, v: 부서 정보)
	private Map<Integer, Set<Integer>> subDeparts;	// 각 부서의 하위 부서 정보 저장
	private Map<Integer, Integer> idsOfRootDepart; // 최상위 부서들의 id 저장 (k: 최상위 부서의 id, v: id)
	private SegmentTree maxSegTree; // 최상위 부서들 중 총 인원수 최댓값 트리
	private int maxDepartPeopleN; // 인원이 제일 많은 최상위 부서의 인원 수
	private final int MAX_SUBDEPART_N = 3; // 한 부서가 가질 수 있는 최대 하위 부서의 수
	private final int HEAD_OF_ROOT_DEAPART = -1; // 최상위 부서의 상위 부서를 -1로 설정

	public void init(int N, int mId[], int mNum[]) {
		sumPeopleN = 0;
		departments = new HashMap<>();
		subDeparts = new HashMap<>();
		idsOfRootDepart = new HashMap<>();
		maxSegTree = new SegmentTree(N);
		maxDepartPeopleN = 0;

		for (int i = 0; i < N; i++) {
			int rootDepartId = mId[i]; // 최상위 부서 id
			int peopleN = mNum[i]; // 부서 인원수

			sumPeopleN += peopleN;
			departments.put(rootDepartId,
					new DepartmentInfo(HEAD_OF_ROOT_DEAPART, peopleN, peopleN));

			maxDepartPeopleN = Math.max(maxDepartPeopleN, peopleN);
			idsOfRootDepart.put(rootDepartId, i);
			maxSegTree.update(i, peopleN);
			subDeparts.put(rootDepartId, new HashSet<>());
		}
	}

	public int add(int mId, int mNum, int mParent) {
		// parent 부서의 하위 부서의 수가 3개인 경우 추가 실패
		if (subDeparts.get(mParent) != null && subDeparts.get(mParent).size() >= MAX_SUBDEPART_N) {
			return -1;
		}

		// parent 부서에 하위 부서 추가
		subDeparts.get(mParent).add(mId);
		sumPeopleN += mNum;

		// 부서 추가
		departments.put(mId, new DepartmentInfo(mParent, mNum, mNum));
		subDeparts.put(mId, new HashSet<>());

		// 상위 부서들의 총 인원수 update
		updateParentPeopleN(mId, mNum);

		return departments.get(mParent).getSumPeopleN();
	}

	public int remove(int mId) {
		// mId 부서와 상위 부서들 중 삭제된 부서가 있는지 확인
		boolean isRemoved = findIsRemoved(mId);

		if (isRemoved) {
			return -1;
		}

		// 상위 부서들의 총 인원수 업데이트
		int sumPeopleN = departments.get(mId).getSumPeopleN(); // mId 부서 포함 서브트리 인원수 합
		updateParentPeopleN(mId, -sumPeopleN);

		// mId 부서 삭제
		int parent = departments.get(mId).getParentId();
		subDeparts.get(parent).remove(mId);
		departments.remove(mId);
		this.sumPeopleN -= sumPeopleN;

		return sumPeopleN;
	}

	public int distribute(int K) {
		// 전체 인원이 상품권 개수보다 적을 때 인원수가 최대인 그룹의 인원수 return
		int maxSumPeople = maxSegTree.getMaxValue();
		if (sumPeopleN < K) {
			return maxSumPeople;
		}

		int limitMax = findLimitMax(K); // 상한 개수
		return limitMax;
	}

	// departId 의 상위 부서들의 총 인원수 num 만큼 업데이트
	private void updateParentPeopleN(int departId, int num) {
		while (true) {
			int parentId = departments.get(departId).getParentId();

			// 부모 부서의 총 인원수 업데이트
			DepartmentInfo parent = departments.get(parentId);

			// 현재 부서가 최상위 부서인 경우 최상위 부서 총 인원수 최댓값 업데이트 & return
			if (parentId == -1) {
				int id = idsOfRootDepart.get(departId);
				int updatedPeopleN = departments.get(departId).getSumPeopleN();
				maxSegTree.update(id, updatedPeopleN);

				return;
			}

			parent.setSumPeopleN(parent.getSumPeopleN() + num);
			departId = parentId;
		}
	}

	// mId 부서와 상위 부서들 중 삭제된 부서가 있는지 확인
	private boolean findIsRemoved(int departId) {
		if (!departments.containsKey(departId)) {
			return true;
		}
		
		int parentId = departments.get(departId).getParentId();
		while (parentId != HEAD_OF_ROOT_DEAPART) {
			if (!departments.containsKey(parentId)) {
				return true;
			}
			
			departId = parentId;
			parentId = departments.get(departId).getParentId();
		}
		
		return false;
	}

	// 상품권 개수가 K개일 때 상한 개수 구하기
	private int findLimitMax(int K) {
		int maxRootSum = maxSegTree.getMaxValue(); // 최상위 부서 중 인원수가 최대인 부서의 인원 수

		int s = 1;
		int e = maxRootSum;

		while (s <= e) {
			int m = s + (e - s) / 2;
			
			// 상품권의 상한 개수가 m일 때 모든 부서에 상품권을 나눠줄 수 있는지 확인
			boolean canDistribute = canDistribute(m, K);
			
			if (canDistribute) {
				s = m + 1;
			} else {
				e = m - 1;
			}
		}

		return e
				;
	}
	
	// 상품권의 상한 개수가 limit 일 때 모든 부서에 상품권을 나눠줄 수 있는지 확인
	private boolean canDistribute(int limit, int couponN) {
		// 최상위 부서들을 돌며 모두 나눠줄 수 있는지 확인
		for (int rootId : idsOfRootDepart.keySet()) {
			int sumPeopleN = departments.get(rootId).getSumPeopleN();
			
			if (sumPeopleN > limit) {
				couponN -= limit;
			} else {
				couponN -= sumPeopleN;
			}
			
			if (couponN < 0) {
				return false;
			}
		}
		
		return true;
	}
	
}

class DepartmentInfo {
	private int parentId; // 상위 부서 id
	private int peopleN;
	private int sumPeopleN; // 자신 포함 총 인원 수

	public DepartmentInfo(int parentId, int peopleN, int sumPeopleN) {
		this.parentId = parentId;
		this.peopleN = peopleN;
		this.sumPeopleN = sumPeopleN;

	}

	public int getParentId() {
		return this.parentId;
	}

	public int getSumPeopleN() {
		return this.sumPeopleN;
	}


	public void setSumPeopleN(int n) {
		this.sumPeopleN = n;
	}
	
}

class SegmentTree {
	private int[] tree;
	private int firstLeafNode;
	private int n; // 원소의 개수

	public SegmentTree(int n) {
		this.n = n;
		tree = new int[n * 4];
		firstLeafNode = findFirstLeafNode();
	}

	public void update(int idx, int value) {
		int leafNode = firstLeafNode + idx;
		tree[leafNode] = value;

		while (leafNode > 1) {
			leafNode /= 2;
			int lChildNode = leafNode * 2;
			int rChildNode = leafNode * 2 + 1;

			tree[leafNode] = Math.max(tree[lChildNode], tree[rChildNode]);
		}
	}

	public int getMaxValue() {
		return tree[1];
	}

	private int findFirstLeafNode() {
		int height = 0;
		while (true) {
			int siblingN = (int) Math.pow(2, height);
			if (siblingN >= n) {
				return siblingN;
			}

			height++;
		}
	}
}