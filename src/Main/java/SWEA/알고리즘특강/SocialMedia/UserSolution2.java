package SWEA.알고리즘특강.SocialMedia;

import java.util.*;

class UserSolution2 {
    //    private static ArrayList<Integer>[] follows;
    private static HashMap<Integer, Feed> feedHashMap;  // key: pId, value : 피드 정보
    private static ArrayList<Feed> feeds;
    private static HashMap<Integer, List<Integer>> writerFeed;    // 작성자별 피드 정보 key: 작성자 ID value: 피드 리스트
    private static HashMap<Integer, List<Integer>> follows; // user 가 follow 하는 사용자id 리스트 key: userId,
    private static int lastFeedID;

    public void init(int N) {
        feeds = new ArrayList<>();
        feedHashMap = new HashMap<>();
        follows = new HashMap<>(); // 사용자가 follow 하는 사용자 Id 저장 리스트
//        for (int i = 1; i <= N; i++) {
//            follows[i] = new ArrayList<>();
//        }
        writerFeed = new HashMap<>();  // timeStamp 내림차순으로 정렬됨.
//        feeds.add(null);
        lastFeedID = 0;
    }

    public void follow(int uID1, int uID2, int timestamp) {
        follows.computeIfAbsent(uID1, k -> new LinkedList<>()).add(uID1);
        follows.get(uID1).add(uID2);
//        follows[uID1].add(uID2);
    }

    public void makePost(int uID, int pID, int timestamp) {
        Feed feed = new Feed(pID, timestamp, uID, 0, false);
        writerFeed.computeIfAbsent(uID, k -> new LinkedList<>()).add(pID);
        feeds.add(feed);
        lastFeedID++;
        feedHashMap.put(pID, feed);
    }

    public void like(int pID, int timestamp) {
        feedHashMap.get(pID).likes++;
    }

    public void getFeed(int uID, int timestamp, int pIDList[]) {
        PriorityQueue<Feed> pq = new PriorityQueue<>();
        // 사용자가 follow 하는 작성자들의 피드 넣기
        for (int writerId: follows.get(uID)) {
            if (!writerFeed.containsKey(writerId)) {
                return;
            }
            for (int pId : writerFeed.get(writerId)) {
                boolean in1000 = isIn1000(pId, timestamp);
                Feed feed = feedHashMap.get(pId);
                feed.in1000 = in1000;
                pq.add(feed);
            }
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
        int writerId = feedHashMap.get(feedN).writerId;
        if (writerId == uID) {
            return true;
        }
        return follows.get(uID).contains(writerId);
    }

    private static boolean isIn1000(int pId, int curTime) {
        int timeStamp = feedHashMap.get(pId).timeStamp;
        return (curTime - timeStamp) <= 1000;
    }

    private static class Feed implements Comparable<Feed> {
        int pID;
        int timeStamp;
        int writerId;
        int likes = 0;
        boolean in1000;

        private Feed(int pID, int timeStamp, int likes, boolean in1000) {
            this.pID = pID;
            this.timeStamp = timeStamp;
            this.likes = likes;
            this.in1000 = in1000;
        }

        public Feed(int pID, int timeStamp, int writerId, int likes, boolean in1000) {
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
