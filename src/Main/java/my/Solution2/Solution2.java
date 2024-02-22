package my.Solution2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution2 {
	static ArrayList<Edge>[] pathList;
	static boolean[] visited;
	static int[] ITS;
	static PriorityQueue<Point> pq;
	static int point1;
	static int point2;
	static int hours;
	static int answerIntensity;
	static int answerSummit;
	static Queue<Value> que;
	static int[] point;
	static int gateN;
	static int summitN;
	static int valueIntensity;
	static int valueSummit;
	static Value value;
	static Point cur;
	static int curNum;
	static int curIntensity;
	static int nextNum;
	static int nextHour;
	static int nextIntensity;

	public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {

		pathList = new ArrayList[n + 1]; // 등산로 저장 리스트
		visited = new boolean[n + 1]; // 방문 배열
		ITS = new int[n + 1]; // 최소 intensity값 저장 배열
		que = new LinkedList<Value>(); // 하나의 gate에서 산봉우리까지의 intensity값 저장 큐
		point = new int[n + 1]; // 각 지점의 상태 저장 배열(출입구, 쉽터, 산봉우리)
		pq = new PriorityQueue<Point>();

		for (int i = 1; i <= n; i++) { // pathList 선언
			pathList[i] = new ArrayList<Edge>();
		}

		// 등산로 정보 PathList에 입력
		for (int i = 0; i < paths.length; i++) {
			point1 = paths[i][0]; // 지점 1
			point2 = paths[i][1]; // 지점 2
			hours = paths[i][2]; // 걸리는 시간

			pathList[point1].add(new Edge(point2, hours));
			pathList[point2].add(new Edge(point1, hours));
		}

		// 각 지점의 정보(출입구, 쉼터, 산봉우리) 정보 point[]에 저장
		for (int i = 0; i < gates.length; i++) { // 출입구
			gateN = gates[i]; // 출입구 지점 번호
			point[gateN] = 1;
		}

		for (int i = 0; i < summits.length; i++) { // 산봉우리
			summitN = summits[i]; // 산봉우리 지점 번호
			point[summitN] = 2;
		}

		// 답 변수 Integer.MAX_VALUE로 설정
		answerIntensity = Integer.MAX_VALUE; // intensity의 최솟값(답) 저장 변수
		answerSummit = Integer.MAX_VALUE; // intensity가 최소가 되는 산봉우리 번호(답)

		// 출입구와 산봉우리를 선택하고 Dijkstra를 이용해 intensity 최솟값 구하기
		for (int g = 0; g < gates.length; g++) {

			// visited, ITS 초기화
			Arrays.fill(visited, false);
			Arrays.fill(ITS, Integer.MAX_VALUE);

			// 출입구가 gates[g]일 때 등산 코스 중 intensity가 최소가 되는 코스의 intensity값과 그 떄의 산봉우리 번호를 que에
			// 저장
			Dijkstra(gates[g]);

			// queue에서 intensity의 최솟값과 그 때 산봉우리 번호 구하기
			while (!que.isEmpty()) {
				Value value = que.poll();
				valueIntensity = value.tmpIntensity;
				valueSummit = value.tmpSummitN;

				if (answerIntensity > valueIntensity) { // 기존의 intensity 최솟값이 tmpIntensity보다 클 때
					answerIntensity = valueIntensity;
					answerSummit = valueSummit;
				} else if (answerIntensity == valueIntensity) { // 두 값이 같을 때 산봉우리 번호가 작은 것 선택
					answerSummit = Math.min(answerSummit, valueSummit);
				}
			}
		}

		// 답 return
		int[] answer = { answerSummit, answerIntensity };
		return answer;
	}

	private void Dijkstra(int gate) {
		// gate에서 출발
		pq.add(new Point(gate, 0));
		ITS[gate] = 0;
		int leastIntensity = Integer.MAX_VALUE;

		while (!pq.isEmpty()) {
			cur = pq.poll();
			curNum = cur.num;
			curIntensity = cur.intensity;

			// 산봉우리이면 que에 값 넣고 방문처리한 후 continue
			if (point[curNum] == 2 && curIntensity <= answerIntensity && curIntensity < leastIntensity) {
				que.add(new Value(curNum, curIntensity));
				leastIntensity = curIntensity;
				visited[curNum] = true;
				continue;
			}

			// cur을 방문했는지 확인
			if (visited[curNum]) {
				continue;
			}
			visited[curNum] = true;

			// 다음 지점으로 이동
			for (Edge nextPath : pathList[curNum]) {
				nextNum = nextPath.destination; // 다음 지점
				nextHour = nextPath.h; // 현재 지점에서 다음 지점까지 걸리는 시간
				nextIntensity = 0; // 다음 지점의 intensity

				// next를 방문했거나, nextIntensity가 이전 과정에서 구한 answerIntensity값보다 크거나, next가 출입구이거나,
				// continue.
				if (visited[nextNum] || nextIntensity > answerIntensity || point[nextNum] == 1) {
					continue;
				}

				// next의 Intensity 업데이트
				nextIntensity = Math.max(curIntensity, nextHour);
				if (ITS[nextNum] > nextIntensity) {
					ITS[nextNum] = nextIntensity;

					pq.add(new Point(nextNum, nextIntensity));
				}
			}
		}
	}
}

class Edge {
	int destination; // 도착지
	int h; // 걸리는 시간

	public Edge(int destination, int h) {
		this.destination = destination;
		this.h = h;
	}
}

class Point implements Comparable<Point> {
	int num; // 지점 번호
	int intensity; // 지점을 지나는 코스의 intensity

	public Point(int num, int intensity) {
		this.num = num;
		this.intensity = intensity;
	}

	@Override
	public int compareTo(Point o) {
		return Integer.compare(this.intensity, o.intensity);
	}
}

class Value {
	int tmpSummitN; // 산봉우리 번호
	int tmpIntensity; // gate에서 tmpSummitN까지의 최소 intensity값

	public Value(int tmpSummitN, int tmpIntensity) {
		this.tmpSummitN = tmpSummitN;
		this.tmpIntensity = tmpIntensity;
	}
}

// enum활용
