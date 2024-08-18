package SWEA.알고리즘특강.뉴스알림;

import java.util.*;

class UserSolution {

    private static Map<Integer, List<Integer>> subScribers;     // key: 채널Id, value: 해당 채널을 구독하고 있는 user list
    private static Map<Integer, List<News>> usersNewsList;   // key: userId, value: 해당 유저가 받은 뉴스 list
    private static Map<Integer, News> newsInfo;     // key: newsId, value: 뉴스 정보
    private static PriorityQueue<News> newsToSend;     // 보낼 뉴스들 저장(시간 낮은 뉴스 우선)

    void init(int N, int K) {
        subScribers = new HashMap<>();
        usersNewsList = new HashMap<>();
        newsInfo = new HashMap<>();
        newsToSend = new PriorityQueue<>();
    }

    // 구독
    void registerUser(int mTime, int mUID, int mNum, int mChannelIDs[]) {
        // 보내야하는 뉴스 알림 보내기
        sendNews(mTime);

        // 채널 구독자에 mUID 추가
        for (int i = 0; i < mNum; i++) {
            int channelId = mChannelIDs[i]; //  채널 ID
            subScribers.computeIfAbsent(channelId, k -> new ArrayList<>()).add(mUID);
        }
    }

    int offerNews(int mTime, int mNewsID, int mDelay, int mChannelID) {
        News news = new News(mNewsID, mChannelID, mTime + mDelay);   // 제공된 뉴스
        newsInfo.put(mNewsID, news);
        newsToSend.add(news);

        int userN = subScribers.get(mChannelID).size();
        return userN;
    }

    void cancelNews(int mTime, int mNewsID) {
        sendNews(mTime);    // 뉴스 보내기

        News news = newsInfo.get(mNewsID);  // 해당 뉴스
        news.isDeleted = true;  // 뉴스 삭제
    }

    int checkUser(int mTime, int mUID, int mRetIDs[]) {
        sendNews(mTime);    // 뉴스 보내기

        if (!usersNewsList.containsKey(mUID)) { // 뉴스가 없으면 return 0
            return 0;
        }

        List<News> newsList = usersNewsList.get(mUID);  // 사용자의 뉴스 리스트
        int alarmN = newsList.size(); // 총 받은 알람 개수
        int cnt = 0;    // 실제 받은 뉴스 개수

        for (int i = alarmN - 1; i >= 0; i--) {
            News news = newsList.get(i);    // 뉴스
            if (news.isDeleted) {  // 삭제되었으면 pass
                continue;
            }
            if (cnt < 3) {
                mRetIDs[cnt++] = news.newsId;
                continue;
            }
            cnt++;
        }
        usersNewsList.get(mUID).clear();
        return cnt;
    }

    private static void sendNews(int time) {
        while (!newsToSend.isEmpty()) {
            // 보낼 뉴스가 없음
            if (newsToSend.peek().time > time) {
                return;
            }

            News news = newsToSend.poll();
            if (news.isDeleted) {   // 뉴스가 삭제됐으면 보내지 않음
                continue;
            }
            int channelId = news.channerId;
            // 해당 뉴스 구독자들에게 뉴스 보내기
            for (int user : subScribers.get(channelId)) {
                usersNewsList.computeIfAbsent(user, k -> new ArrayList<>()).add(news);
            }
        }
    }

    private static class News implements Comparable<News> {
        int newsId;
        int channerId;
        int time;
        boolean isDeleted;

        private News(int newsId, int channerId, int time) {
            this.newsId = newsId;
            this.channerId = channerId;
            this.time = time;
            this.isDeleted = false;
        }

        @Override
        public int compareTo(News o) {
            if (this.time == o.time) {
                return Integer.compare(this.newsId, o.newsId);
            } else {
                return Integer.compare(this.time, o.time);
            }
        }
    }
}