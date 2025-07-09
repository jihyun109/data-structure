package 파일저장소;

import java.util.*;

class UserSolution {
	private int emptySpaceSize; // 비어있는 저장공간의 크기
	private TreeMap<Integer, Integer> emptySpaces; // 비어있는 저장공간 정보 저장(k: 가장 왼쪽 좌표, v: 차지하는 저장공간 크기)
	private TreeMap<Integer, File> savedFiles; // 저장된 파일 정보 저장.(k: 가장 왼쪽 좌표, v: 차지하는 저장공간 크기)
	private Map<Integer, List<Integer>> locOfFile; // 파일의 저장 위치. (k: 파일 id, v: 파일이 저장된 구간의 가장 왼쪽 좌표들)

	public void init(int N) {
		emptySpaceSize = N;
		emptySpaces = new TreeMap<>();
		savedFiles = new TreeMap<>();
		locOfFile = new HashMap<>();

		emptySpaces.put(1, N);
	}

	public int add(int mId , int mSize) {
		// 저장공간이 충분하지 않은 경우
		if (mSize > emptySpaceSize) {
			return -1;
		}

		int firstIdx = emptySpaces.firstKey(); // 저장된 공간 중 가장 앞서는 주소

		// 빈 공간에 파일 저장
		int leftFileSize = mSize; // 저장하지 못한 파일 크기
		Queue<Integer> filledEmptyIdx = new LinkedList<>();	// 채워진 빈 공간의 인덱스 저장
		
		for (Map.Entry<Integer, Integer> emptySpace : emptySpaces.entrySet()) {
			int idx = emptySpace.getKey();
			int size = emptySpace.getValue();

			locOfFile.computeIfAbsent(mId, k -> new LinkedList<>());
			locOfFile.get(mId).add(idx);
			filledEmptyIdx.add(idx);
			

			// 현재 빈 공간이 저장할 파일 크기보다 작은 경우
			if (leftFileSize < size) {
				int emptySpaceIdx = idx + leftFileSize; // 파일을 저장하고 남은 빈공간의 시작 인덱스
				int leftEmptySpaceSize = size - leftFileSize; // 파일을 저장하고 남은 빈공간의 크기

				savedFiles.put(idx, new File(mId, leftFileSize));
				emptySpaces.put(emptySpaceIdx, leftEmptySpaceSize);
				emptySpaceSize -= leftFileSize;
				leftFileSize -= leftFileSize;
				break;
			} else {
				savedFiles.put(idx, new File(mId, size));
				emptySpaceSize -= size;
				leftFileSize -= size;
			}

			// 파일을 모두 저장한 경우
			if (leftFileSize == 0) {
				break;
			}
		}
		
		// 지워진 빈 공간 삭제
		while (!filledEmptyIdx.isEmpty()) {
			int idx = filledEmptyIdx.poll();
			emptySpaces.remove(idx);
		}
		
		print("add " + mId + " " + mSize);

		return firstIdx;
	}

	public int remove(int mId) {
		if (mId == 800) {
			System.out.println();
		}
		for (int fileIdx : locOfFile.get(mId)) {
			int fileSize = savedFiles.get(fileIdx).getSize(); // 현재 위치에 저장된 파일의 크기
			emptySpaces.put(fileIdx, fileSize);
			emptySpaceSize += fileSize;

			// 추가한 빈 공간 앞 or 뒤에도 빈공간이면 빈공간 합치기.
			mergeEmptySpace(fileIdx);

			savedFiles.remove(fileIdx);
		}

		int filePieceN = locOfFile.get(mId).size();
		locOfFile.remove(mId);

		print("remove " + mId);
		return filePieceN;
	}

	public int count(int mStart, int mEnd) {
		int sIdx = savedFiles.floorKey(mStart);
		int eIdx = savedFiles.floorKey(mEnd);

		NavigableMap<Integer, File> subMap = savedFiles.subMap(sIdx, true, eIdx, true);
		Set<Integer> files = new HashSet<>();
		for (File file : subMap.values()) {
			int fileId = file.getFileId();
			files.add(fileId);
		}

		return files.size();
	}

	private void mergeEmptySpace(int idx) {
		if (idx == 13) {
			System.out.print(false);
		}
		int size = emptySpaces.get(idx); // 현재 반 공간의 크기
		int nextIdx = idx + size; // idx 바로 뒤 idx

		// idx 위치의 빈공간 뒤에 연속한 빈공간이 있는 경우
		if (emptySpaces.containsKey(nextIdx)) {
			int nextSize = emptySpaces.get(nextIdx);
			emptySpaces.put(idx, size + nextSize);
			emptySpaces.remove(nextIdx);
		}

		// idx 위치의 빈공간 앞에 연속한 빈공간이 있는 경우
		Map.Entry<Integer, Integer> frontEmptyBlock = emptySpaces.lowerEntry(idx);
		if (frontEmptyBlock != null) {
			int frontIdx = frontEmptyBlock.getKey();
			int frontSize = frontEmptyBlock.getValue();

			if (frontIdx + frontSize == idx) {
				emptySpaces.put(frontIdx, frontSize + size);
				emptySpaces.remove(idx);
			}
		}
	}

	private void print(String msg) {
		System.out.println("------" + msg + "------");
		System.out.println("--files--");
		for (Map.Entry<Integer, File> entry : savedFiles.entrySet()) {
			System.out.print("idx: " + entry.getKey() + ", fileId: " + entry.getValue().getFileId() + ", size: "
					+ entry.getValue().getSize());
			System.out.println();
		}
		System.out.println();
		System.out.println("--empty--");
		for (Map.Entry<Integer, Integer> entry : emptySpaces.entrySet()) {
			System.out.print("idx: " + entry.getKey() + ", size: " + entry.getValue());
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
}

class File {
	private int fileId;
	private int size; // 파일이 차지하는 크기

	public File(int fileId, int size) {
		this.fileId = fileId;
		this.size = size;
	}

	public int getSize() {
		return this.size;
	}

	public int getFileId() {
		return this.fileId;
	}
}