package SWEA.알고리즘특강.SocialMedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class UserSolution2 {
    private static ArrayList<Integer>[] follows;
    private static ArrayList<Feed> feeds;
    private static int lastFeedID;

    public void init(int N) {

        follows = new ArrayList[N + 1]; // 사용자가 follow 하는 사용자 Id 저장 리스트
        for (int i = 1; i <= N; i++) {
            follows[i] = new ArrayList<>();
        }

        feeds = new ArrayList<>();  // timeStamp 내림차순으로 정렬됨.
        feeds.add(null);
        lastFeedID = 0;
    }

    public void follow(int uID1, int uID2, int timestamp) {
        follows[uID1].add(uID2);
    }

    public void makePost(int uID, int pID, int timestamp) {
        feeds.add(new Feed(pID, timestamp, uID, 0, false));
//        System.out.println("makePost");
        lastFeedID++;
    }

    public void like(int pID, int timestamp) {
        Feed feed = feeds.get(pID);
        feed.likes++;
    }

    public void getFeed(int uID, int timestamp, int pIDList[]) {
        if (feeds.size() == 1) {
            return;
        }
        PriorityQueue<Feed> pq = new PriorityQueue<>();
        for (int f = 1; f < feeds.size(); f++) {
            boolean in1000 = isIn1000(f, timestamp);
            Feed feed = feeds.get(f);
            feed.in1000 = in1000;
            pq.add(feed);
        }

        int selectedN = 0;      // 선택된 피드 수
        while (!pq.isEmpty()) {
            if (selectedN == 10) {
                break;
            }

            Feed feed = pq.poll();
            if (isFollow(feed.pID, uID)) {
                pIDList[selectedN++] = feed.pID;
            }
        }
//        System.out.println(Arrays.toString(pIDList));
    }

    private static boolean isFollow(int feedN, int uID) {
        int writerId = feeds.get(feedN).writerId;
        if (writerId == uID) {
            return true;
        }

        for (int fID : follows[uID]) {
            if (fID == writerId) {
                return true;
            }
        }
        return false;
    }

    private static boolean isIn1000(int feedN, int curTime) {
        int timeStamp = feeds.get(feedN).timeStamp;
        return (curTime - timeStamp) <= 1000;
    }

    private static class Feed implements Comparable<Feed> {
        int pID;
        int timeStamp;
        int writerId;
        int likes = 0;
        boolean in1000;

        private Feed(int pID, int timeStamp, int writerId, int likes, boolean in1000) {
            this.pID = pID;
            this.timeStamp = timeStamp;
            this.writerId = writerId;
            this.likes = likes;
            this.in1000 = in1000;
        }

        @Override
        public int compareTo(Feed o) {
            if (this.in1000 != o.in1000) {
                return Boolean.compare(o.in1000, this.in1000);
            } else if (this.in1000) {
                if (this.likes != o.likes) {
                    return Integer.compare(o.likes, this.likes);
                } else {
                    return Integer.compare(o.timeStamp, this.timeStamp);
                }
            } else {
                return Integer.compare(o.timeStamp, this.timeStamp);
            }
        }

        @Override
        public String toString() {
            return pID + ", " + timeStamp + ", " + writerId + ", " + likes;
        }
    }
}
