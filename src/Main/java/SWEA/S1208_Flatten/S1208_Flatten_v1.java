package S1208_Flatten;

import java.util.*;
import java.io.*;

public class S1208_Flatten_v1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		for (int tc  = 1; tc <= 10; tc++) {
			int dumpN = Integer.parseInt(br.readLine());
			
			List<Integer> boxs = new ArrayList<>(100);
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int c = 0; c < 100; c++) {
				int h = Integer.parseInt(st.nextToken());
				boxs.add(h);
			}
			
			// dump 수행
			for (int d = 0; d < dumpN; d++) {
				Collections.sort(boxs);
				int maxH = boxs.remove(99);
				int minH = boxs.remove(0);
				
				if (maxH - minH == 0 || maxH - minH == 1) {
					boxs.add(maxH);
					boxs.add(minH);
					break;
				}
				
				boxs.add(maxH - 1);
				boxs.add(minH + 1);
			}
			Collections.sort(boxs);
			int maxH = boxs.remove(99);
			int minH = boxs.remove(0);
			
			bw.append("#" + tc + " " + (maxH - minH) + '\n');
		}
		
		bw.flush();

	}

}

class Box implements Comparable<Box>{
	int height;
	int c;
	
	public Box(int height, int c) {
		this.height = height;
		this.c = c;
	}
	
	@Override
	public int compareTo(Box o) {
		return Integer.compare(this.height, o.height);
	}
}
