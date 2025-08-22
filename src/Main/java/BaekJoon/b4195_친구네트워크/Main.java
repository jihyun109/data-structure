package BaekJoon.b4195_친구네트워크;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    private static List<Rel> parents;

    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int testcaseN = Integer.parseInt(st.nextToken());

        for (int t = 0; t < testcaseN; t++) {
            st = new StringTokenizer(br.readLine());
            int relN = Integer.parseInt(st.nextToken()); // 관계 수
            int idx = 0;

            parents = new ArrayList<>();
            Map<String, Integer> idxs = new HashMap<>();

            for (int r = 0; r < relN; r++) {
                st = new StringTokenizer(br.readLine());
                String p1 = st.nextToken();
                String p2 = st.nextToken();

                if (!idxs.containsKey(p1)) {
                    idxs.put(p1, idx);
                    parents.add(new Rel(idx++, 1));
                }

                if (!idxs.containsKey(p2)) {
                    idxs.put(p2, idx);
                    parents.add(new Rel(idx++, 1));
                }

                int groupN = union(idxs.get(p1), idxs.get(p2));
                bw.append(groupN + "\n");
            }
        }

        bw.flush();
        bw.close();
        br.close();

    }

    private static int union(int a, int b) {
        int pA = find(a);
        int pB = find(b);

        if (pA == pB) {
            parents.get(a).parent = parents.get(b).parent = pA;
            return parents.get(pA).groupN;
        }

        int groupN = 0;
        if (pA < pB) {
            int bGroupN = parents.get(pB).groupN;

            parents.get(pB).parent = pA;
            groupN = parents.get(pA).groupN += bGroupN;
        } else {
            int aGroupN = parents.get(pA).groupN;

            parents.get(pA).parent = pB;
            groupN = parents.get(pB).groupN += aGroupN;
        }

        return groupN;
    }

    private static int find(int a) {
        int parent = parents.get(a).parent;
        if (parent == a) {
            return a;
        }
        return parents.get(parent).parent = parents.get(a).parent = find(parent);
    }
}

class Rel {
    int parent;
    int groupN; // 네트워크의 인원 수

    public Rel(int parent, int groupN) {
        this.parent = parent;
        this.groupN = groupN;
    }
}
