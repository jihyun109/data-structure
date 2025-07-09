package 뉴스알림;

import java.util.*;

class UserSolution {
	private Queue<News> alarmsToSend; // 보내야하는 뉴스 알람 리스트 (전송시간, 뉴스 id 오름차순)
	private Map<Integer, Stack<Integer>> alarmsByUser; // 사용자 별 받은 뉴스 알람 (k: 사용자id, v: 받은 뉴스 알람)
	private Set<Integer> deletedNews; // 삭제된 뉴스 id 저장
	private Map<Integer, List<Integer>> subscribeUsers; // 채널 별 구독한 유저 저장.(k: 채널 id, v: 구독한 사용자 list)
	private int userN; // 사용자 수
	private int channelN; // 채널 수

	void init(int N, int K) {
		alarmsToSend = new PriorityQueue<>();
		alarmsByUser = new HashMap<>();
		deletedNews = new HashSet<>();
		subscribeUsers = new HashMap<>();

		userN = N;
		channelN = K;
	}

	void registerUser(int mTime, int mUID, int mNum, int mChannelIDs[]) {
		// 전송 시간이 mTime 이하인 뉴스 알림 보내기
		sendAlarmsIn(mTime);

		// 사용자 등록
		for (int i = 0; i < mNum; i++) {
			int channelId = mChannelIDs[i];
			subscribeUsers.computeIfAbsent(channelId, k -> new LinkedList<>());
			subscribeUsers.get(channelId).add(mUID);
		}
	}

	int offerNews(int mTime, int mNewsID, int mDelay, int mChannelID) {
		int offerTime = mTime + mDelay; // 뉴스가 구독자들에게 제공되는 시간

		alarmsToSend.add(new News(mNewsID, mChannelID, offerTime));

		int subscriberN = subscribeUsers.get(mChannelID).size();
		return subscriberN;
	}

	void cancelNews(int mTime, int mNewsID) {
		deletedNews.add(mNewsID);
	}

	int checkUser(int mTime, int mUID, int mRetIDs[]) {
		// 전송 시간이 mTime 이하인 뉴스 알림 보내기
		sendAlarmsIn(mTime);

		// 사용자의 뉴스 알림 개수 세며 top3 저장
		int top3Cnt = 0; // 저장된 top3 수
		int cnt = 0; // 알람 수

		Stack<Integer> alarms = alarmsByUser.get(mUID); // 사용자가 받은 뉴스 알림
		while (alarms != null && !alarms.isEmpty()) {
			int newsId = alarms.pop();

			// 해당 뉴스가 삭제되지 않은 경우
			if (!deletedNews.contains(newsId)) {
				cnt++;

				// 최근 3개의 뉴스가 다 뽑히지 않은 경우
				if (top3Cnt < 3) {
					mRetIDs[top3Cnt++] = newsId;
				}
			}
		}

		return cnt;
	}

	private void sendAlarmsIn(int time) {
		while (!alarmsToSend.isEmpty()) {
			News news = alarmsToSend.peek();
			int newsId = news.getId();
			int offerTime = news.getOfferTime();

			// 전송 시간이 time 이하이고, 해당 뉴스가 삭제되지 않은 경우 뉴스알림 전송
			if (offerTime <= time) {
				if (!deletedNews.contains(newsId)) {
					sendAlarms(news);
				}
				alarmsToSend.poll();
			} else {
				return;
			}
		}
	}

	private void sendAlarms(News news) {
		int channelId = news.getChannelId();
		int newsId = news.getId();

		// 채널 구독자들에 알림 저장
		for (int subscriberId : subscribeUsers.get(channelId)) {
			alarmsByUser.computeIfAbsent(subscriberId, k -> new Stack<>());
			alarmsByUser.get(subscriberId).push(newsId);
		}
	}
}

class News implements Comparable<News> {
	private int offerTime; // 전송 시간
	private int id;
	private int channelId;

	public News(int id, int channelId, int offerTime) {
		this.id = id;
		this.offerTime = offerTime;
		this.channelId = channelId;
	}

	public int getId() {
		return this.id;
	}

	public int getChannelId() {
		return this.channelId;
	}

	public int getOfferTime() {
		return this.offerTime;
	}

	@Override
	public int compareTo(News o) {
		if (this.offerTime == o.offerTime) {
			return this.id - o.id;
		}

		return this.offerTime - o.offerTime;
	}
}
