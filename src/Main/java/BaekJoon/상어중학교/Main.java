package BaekJoon.상어중학교;

import java.util.*;
import java.io.*;

public class Main {
	private static int size; // 격자 한 변의 크기
	private static int colorN; // 색상의 개수
	private static Map<Integer, TreeMap<Integer, Integer>> board; // 열 별 격자 정보 저장 (k: 열, v: 행, 색) / 행 오름차순

	private static int[] dx = new int[] { 1, -1, 0, 0 };
	private static int[] dy = new int[] { 0, 0, -1, 1 };

	private static final int RAINBOW = 0;
	private static final int BLACK = -1;

	public static void main(String[] args) throws IOException {
		init();

		int sumScore = autoPlay();

		System.out.println(sumScore);
	}

	private static void init() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		size = Integer.parseInt(st.nextToken());
		colorN = Integer.parseInt(st.nextToken());

		board = new HashMap<>();
		for (int r = 0; r < size; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < size; c++) {
				int color = Integer.parseInt(st.nextToken());

				board.computeIfAbsent(c, k -> new TreeMap<>(Comparator.reverseOrder()));
				board.get(c).put(r, color);
			}
		}

		br.close();
	}

	private static int autoPlay() {
		int sumScore = 0;

		// 블록 그룹이 존재할 때까지 반복
		while (true) {
			// 1. 크기가 가장 큰 블록그룹 찾기
			Group largestGroup = findLargestGroup();

			// largestGroup 이 없으면 break
			if (largestGroup == null) {
				break;
			}

			// 2. 그룹의 모든 블록 제거. return: 점수
			int score = removeGroup(largestGroup);
			sumScore += score;

			// 3. 중력 작용
			gravity();

			// 4. 90도 반시계 회전
			rotate();

			// 5. 중력 작용
			gravity();
		}

		return sumScore;
	}

	private static Group findLargestGroup() {

		// 모든 블록을 돌면서 그룹에 포함되지 않은 블록(방문하지 않은 블록)이 없을 때까지 블록 그룹 찾기
		PriorityQueue<Group> pq = findAllGroups();

		// 블록그룹이 없는 경우
		if (pq.isEmpty()) {
			return null;
		}

		return pq.poll();
	}

	private static int removeGroup(Group group) {
		Queue<Point> blocksToRM = group.getBlocks();
		int removedBlockN = blocksToRM.size(); // 삭제한 블록 개수

		while (!blocksToRM.isEmpty()) {
			Point block = blocksToRM.poll();
			int x = block.getX();
			int y = block.getY();

			board.get(y).remove(x);
			if (board.get(y).size() == 0) {
				board.remove(y);
			}
		}
		return (int) Math.pow(removedBlockN, 2);
	}

	private static void gravity() {
		Map<Integer, TreeMap<Integer, Integer>> newBoard = new HashMap<>(); // 중력이 적용된 보드

		// 각 열의 가장 아래에 위치한 블록부터 수행
		for (Map.Entry<Integer, TreeMap<Integer, Integer>> entry1 : board.entrySet()) {
			int y = entry1.getKey();

			for (Map.Entry<Integer, Integer> entry2 : entry1.getValue().entrySet()) {
				int x = entry2.getKey();
				int color = entry2.getValue();

				// 블록의 색이 검정색인 경우 그대로 놓기
				if (color == BLACK) {
					newBoard.computeIfAbsent(y, k -> new TreeMap<>(Comparator.reverseOrder()));
					newBoard.get(y).put(x, color);
					continue;
				}

				// newBoard 의 y열에 블록이 있는 경우
				if (newBoard.containsKey(y) && newBoard.get(y).size() != 0) {
					int highestX = newBoard.get(y).lastKey(); // newBoard 에서 x 값이 가장 작은(가장 위에 위치한) 블록의 x좌표 찾기
					newBoard.get(y).put(highestX + 1, color);
				}
				// newBoard 의 y열에 블록이 없는 경우 맨 밑에 놓기
				else {
					newBoard.computeIfAbsent(y, k -> new TreeMap<>(Comparator.reverseOrder()));
					newBoard.get(y).put(size - 1, color);
				}
			}
		}

		board = newBoard;
	}

	private static void rotate() {
		Map<Integer, TreeMap<Integer, Integer>> newBoard = new HashMap<>(); // 90도 반시계 회전된 보드

		for (Map.Entry<Integer, TreeMap<Integer, Integer>> entry1 : board.entrySet()) {
			int y = entry1.getKey();

			for (Map.Entry<Integer, Integer> entry2 : entry1.getValue().entrySet()) {
				int x = entry2.getKey();
				int color = entry2.getValue();
				
				newBoard.computeIfAbsent(x, k -> new TreeMap<>(Comparator.reverseOrder()));
				newBoard.get(x).put(size - y - 1, color);
			}
		}
	}

	private static PriorityQueue<Group> findAllGroups() {
		Set<Point> includedInGroup = new HashSet<>(); // 그룹에 포함된 블록 저장 (방문한 블랙 포함)
		PriorityQueue<Group> pq = new PriorityQueue<>(); // 가장 큰 블럭그룹을 찾기 위한 pq

		for (Map.Entry<Integer, TreeMap<Integer, Integer>> entry : board.entrySet()) {
			int y = entry.getKey();

			for (Map.Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
				int x = entry2.getKey();
				int color = entry2.getValue();
				Point point = new Point(x, y);

				// 검정 블록이 아니고 그룹에 포함되지 않은 블록인 경우 (x, y) 블록에서 블록 그룹 찾기
				Group group = null;
				if (color != -1 && !includedInGroup.contains(point)) {
					group = BFS(x, y, color, includedInGroup);
				}
				// 검정 블록인 경우 방문처리 후 continue
				else {
					includedInGroup.add(point);
					continue;
				}

				// group 의 블럭 수가 2개 이상이고, 일반 블럭의 수가 1개 이상인 경우 pq 에 넣기
				if (group.getBlockN() >= 2 && group.getNormalBlockN() >= 1) {
					pq.add(group);
				}
			}
		}
		return pq;
	}

	private static Group BFS(int x, int y, int color, Set<Point> includedInGroup) {
		Queue<Point> que = new LinkedList<>(); // BFS 에 사용할 큐

		Queue<Point> blocks = new LinkedList<>(); // 그룹에 포함되는 블록들의 좌표 저장
		int normalBlockN = 0;
		int rainbowBlockN = 0;
		Point standard = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);

		que.add(new Point(x, y));

		while (!que.isEmpty()) {
			Point cur = que.poll();
			int curX = cur.getX();
			int curY = cur.getY();
			int curColor = board.get(curY).get(curX);

			// 이미 방문한 블록인 경우
			if (includedInGroup.contains(cur)) {
				continue;
			}
			includedInGroup.add(cur); // 방문 처리

			// 그룹에 포함
			blocks.add(cur);

			if (curColor == RAINBOW) {
				rainbowBlockN++;
			} else {
				normalBlockN++;
			}

			// 기준 블록 정하기
			if (color != RAINBOW && curX <= standard.getX()) {
				if (curX == standard.getX()) {
					if (curY < standard.getY()) {
						standard = new Point(curX, curY);
					}
				} else {
					standard = new Point(curX, curY);
				}
			}

			// 4방향 격자 탐색
			for (int i = 0; i < 4; i++) {
				int nextX = curX + dx[i];
				int nextY = curY + dy[i];
				Point next = new Point(nextX, nextY);

				// next 가 격자 내에 위치하고 방문하지 않았는지 확인
				if (nextX >= 0 && nextX < size && nextY >= 0 && nextY < size && !includedInGroup.contains(next)) {

					// 현재 위치에 블럭이 있으면 색 확인
					if (board.containsKey(nextY) && board.get(nextY).containsKey(nextX)) {
						int nextColor = board.get(nextY).get(nextX);

						// color 가 무지개색이고 next 색이 검은색이 아닌 경우
						if (color == RAINBOW && nextColor != BLACK) {
							que.add(next);
							color = nextColor;
						}
						// next 색이 무지개색이거나 서로 색이 같은 경우
						else if (nextColor == color || nextColor == RAINBOW) {
							que.add(next);
						}
					}
				}
			}
		}

		return new Group(blocks.size(), normalBlockN, rainbowBlockN, standard, blocks);
	}
}

class Group implements Comparable<Group> {
	private int blockN; // 전체 블록 수
	private int normalBlockN; // 일반 블록 수
	private int rainbowBlockN; // 무지개 블록 수
	private Point standard; // 기준 블럭 좌표
	private Queue<Point> blocks; // 구성하는 블럭의 좌표 저장

	public Group(int blockN, int normalBlockN, int rainbowBlockN, Point standard, Queue<Point> blocks) {
		this.blockN = blockN;
		this.normalBlockN = normalBlockN;
		this.rainbowBlockN = rainbowBlockN;
		this.standard = standard;
		this.blocks = blocks;
	}

	public int getBlockN() {
		return this.blockN;
	}

	public int getNormalBlockN() {
		return this.normalBlockN;
	}

	public Queue<Point> getBlocks() {
		return this.blocks;
	}

	public int compareTo(Group o) {
		if (this.blockN == o.blockN) {
			if (this.rainbowBlockN == o.rainbowBlockN) {
				if (this.standard.getX() == o.standard.getX()) {
					return o.standard.getY() - this.standard.getY();
				} else {
					return o.standard.getX() - this.standard.getX();
				}
			} else {
				return o.rainbowBlockN - this.rainbowBlockN;
			}
		} else {
			return o.blockN - this.blockN;
		}
	}
}

class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else {
			Point that = (Point) o;
			return this.x == that.x && this.y == that.y;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}