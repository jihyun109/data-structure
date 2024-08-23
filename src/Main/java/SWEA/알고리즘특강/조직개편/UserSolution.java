package SWEA.알고리즘특강.조직개편;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

class UserSolution {
    private static HashMap<Integer, Department> departmentHashMap;  // 부서 정보 저장 해시 맵(key: 부서 id, value: 부서 정보)
    private static HashMap<Integer, Integer> subTreeSum;    // key: 부서 번호, value: key 를 루트노드로 하는 서브트리의 총 인원 수
    private static int root; // 최상위 부서
    private static final int rootParentId = 0;  // 루트의 parentId: 0

    public void init(int mId, int mNum) {
        System.out.println("//////////////");
        root = mId;
        departmentHashMap = new HashMap<>();
        departmentHashMap.put(root, new Department(mId, mNum, rootParentId));
        subTreeSum = new HashMap<>();
        subTreeSum.put(root, mNum);
    }

    public int add(int mId, int mNum, int mParent) {
        System.out.println("add " + mId);
        // 부서 생성
        Department newD = new Department(mId, mNum, mParent);
        departmentHashMap.put(mId, newD);

        // 부모 부서의 child 에 넣기
        Department parent = departmentHashMap.get(mParent);
        if (parent.child1 == null) { // 비어있는 자식 부서에 추가
            parent.child1 = newD;
        } else if (parent.child2 == null) {
            parent.child2 = newD;
        } else {    // 두 자식 부서 모두 채워져있으면 추가 실패
            return -1;
        }

        // subTreeSum 업데이트
        subTreeSum.put(mId, mNum);
        subTreeSumUpdate(mId, mNum, true);    // mId의 모든 부모 트리 subTreeSum 에 +mNum

        return subTreeSum.get(mParent);
    }

    public int remove(int mId) {
        System.out.println("remove: " + mId);

        if (!departmentHashMap.containsKey(mId)) {  // mId 존재하지 않음
            return -1;
        }

        int parentID = departmentHashMap.get(mId).parentId; // mId 의 parentId

        // mId 아래 자식 노드들도 삭제해야함.
        // 1. 본인 포함 자식노드들 삭제 - return 삭제된 인원수 (departmentHashMap, subSumTree 에서 본인은 나중에 삭제, 인원수만 포함)
        int removedP = rmSubTree(mId);

        // 2. mId 의 부모 노드 수정 departmentHashMap 에서
        // 부모 부서의 child 에서 삭제
        Department child1 = departmentHashMap.get(parentID).child1;
        Department child2 = departmentHashMap.get(parentID).child2;
        if (child1 != null && child1.id == mId) {
            departmentHashMap.get(parentID).child1 = null;
        } else if (child2 != null && child2.id == mId) {
            departmentHashMap.get(parentID).child2 = null;
        }

        // 3. mId의 모든 상위 노드들에 -삭제된 인원수
        subTreeSumUpdate(mId, removedP, false);

        // 본인 정보 삭제
        subTreeSum.remove(mId);
        departmentHashMap.remove(mId);

        return removedP;
    }

    public int reorganize(int M, int K) {
//        System.out.println("reorganize");

        Department rootD = departmentHashMap.get(root);
        // 현재 루트 노드를 포함하는 서브 트리의 인원수 합과 분할된 수 구하기
        Pair<Integer, Integer> ans = rootD.getAns(K);
        int minGroupN = ans.getDividedN() + 1;   // 모든 그룹을 K명 이하로 만들 수 있는 최소 그룹 수
//        System.out.println(minGroupN);
//        System.out.println(M);
//        System.out.println(departmentHashMap);
//        System.out.println("minGroupN" + minGroupN);
//        System.out.println(M);
        if (ans.getDividedN() == -1) {
            return 0;
        } else {
            if (minGroupN <= M) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    // id 의 서브 트리 삭제 (return 삭제된 인원 수) (자기 자신 제외)
    public int rmSubTree(int id) {
        // deparmentHashMap 에서 삭제, subTreeSum 에서 삭제.
        int removedP = 0;
        // 서브트리 내의 id 들을 모두 탐색해 삭제
        Queue<Integer> que = new LinkedList<>();    // 삭제할 노드들의 id 저장 큐
        que.add(id);
        while (!que.isEmpty()) {
            int cur = que.poll();
            removedP += departmentHashMap.get(cur).peopleN;
            Department child1 = departmentHashMap.get(cur).child1;
            Department child2 = departmentHashMap.get(cur).child2;
            if (child1 != null) {
                que.add(child1.id);
            }
            if (child2 != null) {
                que.add(child2.id);
            }

            // cur 삭제
            if (cur == id) {    // cur 이 루트노드면 삭제하지 않고 일단 놔둠
                continue;
            }
            departmentHashMap.remove(cur);
            subTreeSum.remove(cur);
        }

        // id의 부모 노드 수정
        int parentId = departmentHashMap.get(id).parentId;
        if (departmentHashMap.get(parentId).child1 != null && departmentHashMap.get(parentId).child1.id == id) {
            departmentHashMap.get(parentId).child1 = null;
        } else if (departmentHashMap.get(parentId).child2 != null && departmentHashMap.get(parentId).child2.id == id) {
            departmentHashMap.get(parentId).child2 = null;
        }

        // 삭제된 인원수 return
        return removedP;
    }


    // mID의 모든 상위 노드의 subTreeSum 값에 +-num
    private static void subTreeSumUpdate(int id, int num, boolean plus) {
        int parent = departmentHashMap.get(id).parentId;
        while (parent != rootParentId) {
//            System.out.println(parent);
//            System.out.println(subTreeSum.get(parent));
            int origintSum = subTreeSum.get(parent);
            if (plus) {
                subTreeSum.put(parent, origintSum + num);
            } else {
                subTreeSum.put(parent, origintSum - num);
            }
            parent = departmentHashMap.get(parent).parentId;
        }
    }

    private class Department {
        @Override
        public String toString() {
            return "Department{" +
                    "id=" + id +
                    ", peopleN=" + peopleN +
                    ", parentId=" + parentId +
                    ", child1=" + child1 +
                    ", child2=" + child2 +
                    '}';
        }

        int id;
        int peopleN;
        int parentId;
        Department child1;
        Department child2;

        public Department(int id, int peopleN, int parentId) {
            this.id = id;
            this.peopleN = peopleN;
            this.parentId = parentId;
        }

        private Pair<Integer, Integer> getAns(int K) {
            if (peopleN > K) {  // 현재 부서의 인원수가 K를 넘는 경우
                return new Pair<>(-1, 0);
            }

            if (child1 == null && child2 == null) { // 자식 부서가 없는 경우
                return new Pair<>(0, peopleN);
            }

            if (child2 == null) {   // child1 만 존재하는 경우
                Pair<Integer, Integer> cAns = child1.getAns(K);
                if (cAns.dividedN == -1) {  // 자식의 divided 가 -1 인 경우
                    return new Pair<>(-1, 0);
                }

                if (cAns.peopleN + peopleN > K) {   // 자식의 서브트리 인원수 + 현재 부서의 인원수 > K 인 경우
                    return new Pair<>(cAns.dividedN + 1, peopleN);
                }

                if (cAns.peopleN + peopleN <= K) {  // 자식의 서브트리에 포함될 수 있는 경우
                    return new Pair<>(cAns.dividedN, cAns.peopleN + peopleN);
                }
            }

            if (child1 == null) {   // child2 만 존재하는 경우
                Pair<Integer, Integer> cAns = child2.getAns(K);
                if (cAns.dividedN == -1) {  // 자식의 divided 가 -1 인 경우
                    return new Pair<>(-1, 0);
                }

                if (cAns.peopleN + peopleN > K) {   // 자식의 서브트리 인원수 + 현재 부서의 인원수 > K 인 경우
                    return new Pair<>(cAns.dividedN + 1, peopleN);
                }

                if (cAns.peopleN + peopleN <= K) {  // 자식의 서브트리에 포함될 수 있는 경우
                    return new Pair<>(cAns.dividedN, cAns.peopleN + peopleN);
                }
            }

            if (child1 != null && child2 != null) { // 자식이 2개 존재하는 경우
                // 두 자식 중 하나라도 divided 가 -1 인 경우
                Pair<Integer, Integer> cAns1 = child1.getAns(K);
                if (cAns1.dividedN == -1) {
                    return new Pair<>(-1, 0);
                }
                Pair<Integer, Integer> cAns2 = child2.getAns(K);
                if (cAns2.dividedN == -1) {
                    return new Pair<>(-1, 0);
                }

                if (cAns1.peopleN + cAns2.peopleN + peopleN <= K) { // 두 자식 트리와 같은 그룹에 있을 수 있는 경우
                    return new Pair<>(cAns1.dividedN + cAns2.dividedN, cAns1.peopleN + cAns2.peopleN + peopleN);
                }

                if (cAns1.peopleN + peopleN > K && cAns2.peopleN + peopleN > K) {   // 두 자식트리와 모두 같은 그룹에 있을 수 없는 경우
                    return new Pair<>(cAns1.dividedN + cAns2.dividedN + 2, peopleN);
                }

                if (cAns1.peopleN + peopleN <= K && cAns2.peopleN + peopleN > K) { // 자식 1의 트리와만 같은 그룹에 있을 수 있는 경우
                    return new Pair<>(cAns1.dividedN + cAns2.dividedN + 1, cAns1.peopleN + peopleN);
                }

                if (cAns2.peopleN + peopleN <= K && cAns1.peopleN + peopleN > K) { // 자식 2의 트리와만 같은 그룹에 있을 수 있는 경우
                    return new Pair<>(cAns1.dividedN + cAns2.dividedN + 1, cAns2.peopleN + peopleN);
                }

                // 둘 다 가능한 경우 둘 중 작은쪽의 서브트리와 한 그룹 되기
                return new Pair<>(cAns1.dividedN + cAns2.dividedN + 1, Math.min(cAns1.peopleN, cAns2.peopleN) + peopleN);
            }
            return null;
        }
    }

    private class Pair<D, P> {
        private final D dividedN;   // 현재 노드를 루트노드로 포함하는 서브트리의 분할된 횟수
        private final P peopleN;    // 현재 노드를 루트노드로 포함하는 서브트리의 총 인원 수

        public D getDividedN() {
            return dividedN;
        }

        public P getPeopleN() {
            return peopleN;
        }

        public Pair(D dividedN, P peopleN) {
            this.dividedN = dividedN;
            this.peopleN = peopleN;
        }
    }
}