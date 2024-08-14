package SWEA.알고리즘특강.SocialMedia;

import java.util.*;

class PostFull implements Comparable<PostFull> {
    int timestamp, like;
    boolean in1000;

    PostFull(int timestamp, int like, boolean in1000) {
        this.timestamp = timestamp;
        this.like = like;
        this.in1000 = in1000;
    }

    @Override
    public int compareTo(PostFull r) {
        if (this.in1000 && r.in1000) {
            if (this.like != r.like)
                return Integer.compare(r.like, this.like);
            else
                return Integer.compare(r.timestamp, this.timestamp);
        } else if (!this.in1000 && !r.in1000) {
            return Integer.compare(r.timestamp, this.timestamp);
        } else {
            return this.in1000 ? -1 : 1;
        }
    }
}

public class UserSolution3 {
    int[] timeToPid = new int[100010];
    int[] pidToLike = new int[100010];
    int[] timeToUid = new int[100010];

    int n;
    List<Deque<Integer>> posts;
    boolean[][] following;

    public void init(int N) {
        n = N;
        Arrays.fill(timeToPid, 0);
        Arrays.fill(pidToLike, 0);
        Arrays.fill(timeToUid, 0);

        posts = new ArrayList<>(N + 2);
        for (int i = 0; i < N + 2; i++) {
            posts.add(new ArrayDeque<>());
        }

        following = new boolean[1010][1010];
        for (int i = 1; i <= N; i++) {
            following[i][i] = true;
        }
    }

    public void follow(int uID1, int uID2, int timestamp) {
        following[uID1][uID2] = true;
    }

    public void makePost(int uID, int pID, int timestamp) {
        timeToPid[timestamp] = pID;
        timeToUid[timestamp] = uID;

        if (posts.get(uID).size() == 10)
            posts.get(uID).pollFirst();
        posts.get(uID).addLast(timestamp);
    }

    public void like(int pID, int timestamp) {
        pidToLike[pID]++;
    }

    public void getFeed(int uID, int timestamp, int[] pIDList) {
        PriorityQueue<PostFull> getIn1000 = new PriorityQueue<>();
        for (int i = Math.max(1, timestamp - 1000); i <= timestamp; i++) {
            if (timeToPid[i] <= 0)
                continue;
            if (!following[uID][timeToUid[i]])
                continue;
            getIn1000.add(new PostFull(i, pidToLike[timeToPid[i]], true));
            if (getIn1000.size() > 10)
                getIn1000.poll();
        }

        PriorityQueue<Integer> getOut1000 = new PriorityQueue<>();
        if (getIn1000.size() < 10) {
            for (int i = 1; i <= n; i++) {
                if (!following[uID][i])
                    continue;
                for (int j = posts.get(i).size() - 1; j >= 0; j--) {
                    int postTimestamp = ((ArrayDeque<Integer>) posts.get(i)).peekLast();
                    if (postTimestamp >= timestamp - 1000)
                        continue;
                    getOut1000.add(postTimestamp);
                    if (getOut1000.size() > 10 - getIn1000.size())
                        getOut1000.poll();
                }
            }
        }

        while (!getOut1000.isEmpty()) {
            int i = getOut1000.poll();
            getIn1000.add(new PostFull(i, 0, false));
        }

        int T = getIn1000.size();
        for (int i = T - 1; i >= 0; i--) {
            PostFull tmp = getIn1000.poll();
            pIDList[i] = timeToPid[tmp.timestamp];
        }
    }
}
