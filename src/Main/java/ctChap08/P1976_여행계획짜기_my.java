package ctChap08;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class P1976_여행계획짜기_my {
	static Set<Integer> plannedCity;
	static int[] parent;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();	// 도시의 수
		int M = sc.nextInt();	// 여행 계획한 도시의 수
		parent = new int[N + 1];	// 각 도시의 대표 도시 저장 배열
		plannedCity = new HashSet<>();
		for (int i = 1; i <= N; i++) {	// 대표 도시 자기 자신으로 설정
			parent[i] = i;
		}
		
		// 각 도시와의 연결 여부 입력받기
		for (int city1 = 1; city1 <= N; city1++) {	
			for (int city2 = 1; city2 <= N; city2++) {
				int isConnected = sc.nextInt();
				if (isConnected == 1) {	// 연결되었으면 연결하기
					union(city1, city2);
				}
			}
		}
		
		// HashSet에 여행 계획한 도시 중복 없이 입력받기
		for (int i = 0; i < M; i++) {
			int cityN = sc.nextInt();
			plannedCity.add(cityN);
		}
		
		// 계획대로 여행이 가능한지(여행 계획한 도시들이 모두 연결되어 있는지) 확인
		String answer = isPossible();
		
		System.out.println(answer);
	}

	private static void union(int city1, int city2) {
		int parent1 = find(city1);
		int parent2 = find(city2);
		if (parent1 != parent2) {
			parent[parent1] = Math.min(parent1, parent2);
			parent[parent2] = Math.min(parent1, parent2);
		}
	}
	
	private static int find(int city) {
		if (city == parent[city]) {
			return city;
		} else {
			return parent[city] = find(parent[city]);
		}
	}
	
	private static String isPossible() {
		Iterator<Integer> iterator = plannedCity.iterator();
		// 여행 게획한 도시들의 대표 도시가 모두 일치하는지 확인
		int mainCity = find(iterator.next());	// 첫번째 여행 도시의 대표도시 저장
		while (iterator.hasNext()) {
			int city = iterator.next();
			int parentCity = find(city);	// 대표 도시 찾기
			if (parentCity != mainCity) {	// 대표 도시가 다르면
				return "NO";
			}
		}
		return "YES";
	}
}
