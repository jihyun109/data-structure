package SWEA.알고리즘특강.Directory;

import java.util.*;

class UserSolution {

    private final static int NAME_MAXLEN = 6;
    private final static int PATH_MAXLEN = 1999;
    private static Directory[] dir;
    private static Queue<Integer> availableKey;

    void init(int n) {
        dir = new Directory[50010];  // 디렉터리 정보 저장 배열

        // 사용 가능한 key 값 초기화
        availableKey = new LinkedList<>();
        for (int i = 1; i < 50010; i++) {
            availableKey.add(i);
        }

        // 루트 dir 생성
        dir[0] = new Directory("/", 0, new LinkedList<>(), 0);
    }

    void cmd_mkdir(char[] path, char[] name) {
        int parentDir = findDirKey(path); // 하위에 dir 를 생성할 dir

        // 이름 추출
        int nameLength = 0;
        for (int i = 0; i < name.length; i++) {
            if (name[i] == '\u0000') {
                break;
            }
            nameLength++;
        }

        String newDirName = new String(name, 0, nameLength);

        createNewDir(newDirName, parentDir);    // parentDir 하위에 dir 생성
    }

    void cmd_rm(char[] path) {
        int selectedDir = findDirKey(path);

        // dirToRM 삭제
        Queue<Integer> dirToRM = new LinkedList<>();    // dir 와 dir 의 하위 dir 의 key 저장 큐
        dirToRM.add(selectedDir);

        // 서브 트리의 루트 dir 의 부모 dir 에서 삭제
        int parentKey = dir[selectedDir].parentKey;
        for (int i = 0; i < dir[parentKey].childKeyList.size(); i++) {
            if (dir[parentKey].childKeyList.get(i) == selectedDir) {
                dir[parentKey].childKeyList.remove(i);
                break;
            }
        }

        while (!dirToRM.isEmpty()) {
            int curDirKey = dirToRM.poll();
            // curDir 의 하위 dir 를 dirToRM에 add
            for (int childDir : dir[curDirKey].childKeyList) {
                dirToRM.add(childDir);
            }


            // curDir 삭제
            dir[curDirKey] = null;
            availableKey.add(curDirKey);
        }
    }

    void cmd_cp(char[] srcPath, char[] dstPath) {
//        System.out.println("copy start");
        int srcDir = findDirKey(srcPath);
        String srcDirName = dir[srcDir].name;
        int dstDirKey = findDirKey(dstPath);

        // srcDir 를 dstDir 아래에 복사
        int copiedDir = createNewDir(srcDirName, dstDirKey);

        // srcDir 의 하위 dir 를 dirsToCP 에 add
        Queue<Directory> dirsToCP = new LinkedList<>();   // 복사할 Dir 정보 저장 que
        for (int childDirKey : dir[srcDir].childKeyList) {
            dirsToCP.add(new Directory(childDirKey, copiedDir));
        }

        // 하위 dir 복사
        while (!dirsToCP.isEmpty()) {
            Directory curDir = dirsToCP.poll(); // 복사할 dir
            copiedDir = createNewDir(dir[curDir.key].name, curDir.parentKey);

            for (int childDirKey : dir[curDir.key].childKeyList) {
                dirsToCP.add(new Directory(childDirKey, copiedDir));
            }
        }
    }

    void cmd_mv(char[] srcPath, char[] dstPath) {
        int srcDir = findDirKey(srcPath);
        int dstDir = findDirKey(dstPath);

        int formerParentKey = dir[srcDir].parentKey;    // 이동하기 전 parentKey
        dir[srcDir].parentKey = dstDir;
        dir[dstDir].childKeyList.add(srcDir);
        for (int i = 0; i < dir[formerParentKey].childKeyList.size(); i++) {
            if (dir[formerParentKey].childKeyList.get(i) == srcDir) {
                dir[formerParentKey].childKeyList.remove(i);
                break;
            }
        }
    }

    int cmd_find(char[] path) {
        int selectedDir = findDirKey(path);

        Queue<Integer> subDirs = new LinkedList<>();
        subDirs.add(selectedDir);
        int subDirN = 0;

        while (!subDirs.isEmpty()) {
            int curDir = subDirs.poll();
            for (int subDir : dir[curDir].childKeyList) {
                subDirs.add(subDir);
                subDirN++;
            }
        }
        return subDirN;
    }

    private int findDirKey(char[] path) {
        // path[]에서 dir 이름만 추출
        Queue<String> paths = new LinkedList<>();   // path[]의 dir 이름 저장 큐
//        paths.add("/"); // 루트 dir 큐에 삽입

        int sName = 1;
        int eName = 1;
        for (int i = 1; i < path.length; i++) {
            if (path[i] == '\u0000') {
                break;
            }

            if (path[i] == '/') {
                String dirName = new String(path, sName, i - sName);
                paths.add(dirName);
                sName = i + 1;
            }
        }

        int curDirN = 0;    // 루트 dir 에서부터 시작
        while (!paths.isEmpty()) {
            // curDirN의 하위 dir 중 nextDirName 인 dir의 key 찾기
            String nextDirName = paths.poll();
            for (int childDir : dir[curDirN].childKeyList) {
                if (Objects.equals(dir[childDir].name, nextDirName)) {
                    curDirN = childDir;

                    break;
                }
            }
        }
        return curDirN;
    }

    private int createNewDir(String name, int parentKey) {
        // 새로운 dir 생성
        int newDirKey = availableKey.poll();
        dir[newDirKey] = new Directory(name, parentKey, new LinkedList<>(), 0);

        // 부모 dir 수정
        dir[parentKey].childKeyList.add(newDirKey);
        dir[parentKey].subDirN++;

        return newDirKey;
    }

    private static class Directory {
        int key;
        String name;    // 디렉토리 이름
        int parentKey;  // 부모 디렉터리 키
        List<Integer> childKeyList; // 자식 디렉터리 키 저장 리스트
        int subDirN;    // 자식 디렉터리 개수

        Directory(int key, int parentKey) {
            this.key = key;
            this.parentKey = parentKey;
        }

        Directory(String name, int parentKey, List<Integer> childKeyList, int subDirN) {
            this.name = name;
            this.parentKey = parentKey;
            this.childKeyList = childKeyList;
            this.subDirN = subDirN;
        }
    }
}


