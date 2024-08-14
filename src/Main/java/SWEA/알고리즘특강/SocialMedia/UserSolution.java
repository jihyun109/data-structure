package SWEA.알고리즘특강.SocialMedia;

import java.util.ArrayList;
import java.util.PriorityQueue;

class UserSolution {
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
        feeds.add(new Feed(pID, timestamp, uID, 0));
//        System.out.println("makePost");
        lastFeedID++;
    }

    public void like(int pID, int timestamp) {
        Feed feed = feeds.get(pID);
        feed.likes++;
    }

    public void getFeed(int uID, int timestamp, int pIDList[]) {
        int selectedFeedN = 0;  // 선택된 피드 수

        if (lastFeedID == 0) {
            return;
        }
//        System.out.println("lastFeedID: " + lastFeedID);

        if (recentlyPosted(lastFeedID, timestamp)) {   // 마지막 피드가 지급으로부터 1,000 초 이내에 게시됨.
            // 1,000 초 이내에 게시된 피드들에서 추가
            PriorityQueue<Feed> candidateFeeds = new PriorityQueue<>(); // 1,000 초이내에 작성, 작성자 follow, 우선순위로 정렬되는 pq.
            int f;  // 1,000 초 이내에 작성되지 않은 첫 피드
            for (f = lastFeedID; f > 0; f--) {
                if (!recentlyPosted(f, timestamp)) {    // 1,000 초 이내에 작성된 것들만
                    break;
                }

                if (isFollow(f, uID)) {  // 이 피드의 작성자를 follow 하는지 check
                    candidateFeeds.add(feeds.get(f));
//                        System.out.println(feeds.get(f).toString());
                }
            }

            // pIDList 에 추가
            while (!candidateFeeds.isEmpty()) {
                if (selectedFeedN == 10) {
                    break;
                }

                pIDList[selectedFeedN++] = candidateFeeds.poll().pID;
            }

            // 10개가 안채워진 경우 1,000 초 이후에 게시된 피드 중에서 선택
            if (selectedFeedN < 10) {
                for (int i = f; i > 0; i--) {
                    if (selectedFeedN == 10) {
                        break;
                    }

                    if (isFollow(i, uID)) {
                        pIDList[selectedFeedN++] = feeds.get(i).pID;
                    }
                }
            }
        } else {
            for (int i = lastFeedID; i > 0; i--) {
                if (selectedFeedN == 10) {
                    break;
                }
                pIDList[selectedFeedN++] = i;
            }
        }

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

    private static boolean recentlyPosted(int feedN, int curTime) {
        int timeStamp = feeds.get(feedN).timeStamp;
        return (curTime - timeStamp) <= 1000;
    }

    private static class Feed implements Comparable<Feed> {
        int pID;
        int timeStamp;
        int writerId;
        int likes = 0;

        private Feed(int pID, int timeStamp, int writerId, int likes) {
            this.pID = pID;
            this.timeStamp = timeStamp;
            this.writerId = writerId;
            this.likes = likes;
        }

        @Override
        public int compareTo(Feed o) {
            if (this.likes != o.likes) {
                return Integer.compare(o.likes, this.likes);
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
