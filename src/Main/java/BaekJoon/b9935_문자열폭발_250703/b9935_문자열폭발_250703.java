package BaekJoon.b9935_문자열폭발_250703;

import java.util.*;
import java.io.*;

public class b9935_문자열폭발_250703 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		String bomb = br.readLine();
		
		int bombSize = bomb.length();

		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			stack.add(c);

			if (stack.size() >= bombSize) {
				int idx = 0;
				for (int j = stack.size() - bombSize; j < stack.size(); j++) {
					if (stack.get(j) != bomb.charAt(idx)) {
						break;
					}
					idx++;
				}

				if (idx == bombSize) {
					for (int z = 0; z < bombSize; z++) {
						stack.pop();
					}
				}
			}
		}
		
		
		if (stack.isEmpty()) {
			System.out.println("FRULA");
		}
		
		StringBuilder sb = new StringBuilder();
		for (char c: stack) {
			sb.append(c);
		}

		System.out.println(sb);
	}
}
