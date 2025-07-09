package 단어미로;

import java.util.*;

class UserSolution {
	private String cur; // 현재 위치
	private Map<String, Room> rooms; // 방 정보 저장
	private Map<String, TreeSet<String>> frontRooms; // k: 방들의 앞(2~4) 단어, v: 해당되는 단어
	private Map<String, TreeSet<String>> backRooms; // k: 방들의 뒤(2~4) 단어, v: 해당되는 단어
	private Map<String, TreeSet<String>> middleRooms; // k: 방들의 중간 단어, v: 해당되는 단어

	void init() {
		cur = null;
		rooms = new HashMap<>();
		frontRooms = new HashMap<>();
		backRooms = new HashMap<>();
		middleRooms = new HashMap<>();
	}

	void addRoom(int mID, char mWord[], int mDirLen[]) {
		// mWord 의 앞뒤(2, 4) 단어, 중간 단어 추출
		String word = String.copyValueOf(mWord, 0, 11);
		String[] subs = findSubs(mWord);

		String front = mDirLen[0] == 2 ? subs[0] : subs[1];
		String back = mDirLen[2] == 2 ? subs[3] : subs[4];
		rooms.put(word, new Room(mID, new String[] { front, subs[2], back }));

		updateSubs(subs, word, true);

	}

	void setCurrent(char mWord[]) {
		cur = String.copyValueOf(mWord, 0, 11);

	}

	int moveDir(int mDir) {
		String[] dirWords = rooms.get(cur).getDirWord();
		String dirWord = dirWords[mDir];
		String moveWord = null;

		switch (mDir) {
		case 0: // 앞 방향으로 이동하는 경우
			// 이동할 방이 없는 경우
			if (backRooms.get(dirWord) == null || backRooms.get(dirWord).size() == 0) {
				return 0;
			}

			for (String word : backRooms.get(dirWord)) {
				if (word.equals(cur)) {
					continue;
				}
				moveWord = word;
				break;
			}
			
			if (moveWord == null) {
				return 0;
			}
			
			break;
		case 1: // 뒷 방향으로 이동하는 경우
			// 이동할 방이 없는 경우
			if (middleRooms.get(dirWord) == null || middleRooms.get(dirWord).size() == 0) {
				return 0;
			}
			
			for (String word : middleRooms.get(dirWord)) {
				if (word.equals(cur)) {
					continue;
				}
				moveWord = word;
				break;
			}
			
			if (moveWord == null) {
				return 0;
			}
			
			break;
		case 2:
			// 이동할 방이 없는 경우
			if (frontRooms.get(dirWord) == null || frontRooms.get(dirWord).size() == 0) {
				return 0;
			}

			for (String word : frontRooms.get(dirWord)) {
				if (word.equals(cur)) {
					continue;
				}
				moveWord = word;
				break;
			}
			
			if (moveWord == null) {
				return 0;
			}
			
			break;
		}
		
		cur = moveWord;
		

		return rooms.get(moveWord).getId();
	}

	void changeWord(char mWord[], char mChgWord[], int mChgLen[]) {
		// front, middle, back rooms 에서 mWord 지우기
		String[] wordSubs = findSubs(mWord);
		String word = String.copyValueOf(mWord, 0, 11);
		updateSubs(wordSubs, word, false);
		
		// 바뀐 단어 추가
		String[] chgWordSubs = findSubs(mChgWord);
		String chgWord = String.copyValueOf(mChgWord, 0, 11);
		updateSubs(chgWordSubs, chgWord, true);
		
		// 방 정보 수정
		int id = rooms.get(word).getId();
		String front = mChgLen[0] == 2 ? chgWordSubs[0] : chgWordSubs[1];
		String back = mChgLen[2] == 2 ? chgWordSubs[3] : chgWordSubs[4];
		
		rooms.remove(word);
		rooms.put(chgWord, new Room(id, new String[] {front, chgWordSubs[2], back}));
		
		if (cur.equals(word)) {
			cur = chgWord;
		}
		
	}

	private String[] findSubs(char[] word) {
		// [0]: 앞 2단어, [1]: 앞 4단어, [2]: 중간 3단어, [3]: 뒤 2단어, [4]: 뒤 4단어
		String[] subs = new String[5];
		
		subs[0] = String.copyValueOf(word, 0, 2);
		subs[1] = String.copyValueOf(word, 0, 4);
		subs[2] = String.copyValueOf(word, 4, 3);
		subs[3] = String.copyValueOf(word, 9, 2);
		subs[4] = String.copyValueOf(word, 7, 4);

		return subs;
	}
	
	private void updateSubs(String[] subs, String word, boolean isAdd) {
		if (isAdd) {
			frontRooms.computeIfAbsent(subs[0], k -> new TreeSet<>());
			frontRooms.get(subs[0]).add(word);
			frontRooms.computeIfAbsent(subs[1], k -> new TreeSet<>());
			frontRooms.get(subs[1]).add(word);
			backRooms.computeIfAbsent(subs[3], k -> new TreeSet<>());
			backRooms.get(subs[3]).add(word);
			backRooms.computeIfAbsent(subs[4], k -> new TreeSet<>());
			backRooms.get(subs[4]).add(word);
			middleRooms.computeIfAbsent(subs[2], k -> new TreeSet<>());
			middleRooms.get(subs[2]).add(word);
		} else {
			frontRooms.get(subs[0]).remove(word);
			frontRooms.get(subs[1]).remove(word);
			backRooms.get(subs[3]).remove(word);
			backRooms.get(subs[4]).remove(word);
			middleRooms.get(subs[2]).remove(word);
		}
	}
}

class Room {
	private int id;
	private String[] dirWord; // 앞, 중간, 뒤 방향으로 이동할 떄 사용할 단어

	public Room(int id, String[] dirWord) {
		this.id = id;
		this.dirWord = dirWord;
	}

	public int getId() {
		return this.id;
	}

	public String[] getDirWord() {
		return this.dirWord;
	}
}