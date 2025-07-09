package 성적조회;

import java.util.*;

class UserSolution {
	private Map<Integer, Student> students; // 학생 정보 저장. (k: 학생 id, v: 학생 정보)
	private Map<Integer, TreeSet<StudentInfo>> studentsOrdered; // 분류별로 점수, id 오름차순 정렬 저장.(k: 분류, v: 해당되는 학생)
	private final int max = (1 << 5) - 1; // 분류 종류

	public void init() {
		students = new HashMap<>();
		studentsOrdered = new HashMap<>();

		// studentsOrdered 의 분류 정보 저장
		for (int i = 1; i <= max; i++) {
			studentsOrdered.put(i, new TreeSet<>());
		}
	}

	public int add(int mId, int mGrade, char mGender[], int mScore) {
		String gender = findGender(mGender);
		students.put(mId, new Student(mGrade, gender, mScore));

		// 표함되는 분류대로 studentsOrdered 에 넣기
		int num = addInStudentsOrdered(mId, mGrade, gender, mScore, true); // 분류 번호

		// mGrade학년 mGender인 학생 중에서 점수가 가장 높은 학생의 ID return
		if (studentsOrdered.get(num) == null) {
			System.out.println();
		}
		
		StudentInfo student = studentsOrdered.get(num).last();
		print(String.format("add id: %d, grade: %d, gender: %s", mId, mGrade, gender));
		
		return student.getId();
	}

	public int remove(int mId) {
		if (!students.containsKey(mId)) {
			return 0;
		}
		Student student = students.get(mId);

		// 포함되는 분류에서 학생 삭제
		int num = addInStudentsOrdered(mId, student.getGrade(), student.getGender(), student.getScore(), false);
		students.remove(mId);

		// mId 학생의 학년과 성별이 동일한 학생 중에서 점수가 가장 낮은 학생의 ID
		if (studentsOrdered.get(num) == null || studentsOrdered.get(num).size() == 0) {
			return 0;
		}
		StudentInfo s = studentsOrdered.get(num).first();
		print(String.format("-----remove %d --------", mId));

		return s.getId();
	}

	public int query(int mGradeCnt, int mGrade[], int mGenderCnt, char mGender[][], int mScore) {
		String[] genders = findGenders(mGender);
		int num = findNum(mGrade, genders);

		// 점수가 mScore 이상인 조건을 만족하는 학생이 없는 경우
		if (studentsOrdered.get(num) == null || studentsOrdered.get(num).size() == 0) {
			return 0;
		}

		int maxScore = studentsOrdered.get(num).last().getScore(); // 조건을 만족하는 학생들 중 점수가 가장 높은 학생의 점수
		if (maxScore < mScore) {
			return 0;
		}

		for (StudentInfo student : studentsOrdered.get(num)) {
			int id = student.getId();
			int score = student.getScore();

			// 점수가 mScore 이상인 경우
			if (score >= mScore) {
				return id;
			}
		}
		return 0;
	}

	private int addInStudentsOrdered(int id, int grade, String gender, int score, boolean isAdd) {
		String[] genderArr = new String[] { gender };
		int[] gradeArr = new int[] { grade };

		int num = findNum(gradeArr, genderArr); // 분류 숫자

		for (int i = num; i <= max; i++) {
			// 해당 분류에 포함되는 경우
			if ((i & num) == num) {
				if (isAdd) {
					studentsOrdered.get(i).add(new StudentInfo(id, score));
				} else {
					studentsOrdered.get(i).remove(new StudentInfo(id, score));
				}
			}
		}
		return num;
	}

	private String[] findGenders(char[][] gendersCharArr) {
		String[] genders = new String[gendersCharArr.length];
		
		for (int i = 0; i < gendersCharArr.length; i++) {
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < gendersCharArr[i].length; j++) {
				char c = gendersCharArr[i][j];
				
				if (c == '\0') {
					break;
				}
				
				sb.append(c);
			}
			genders[i] = new String(sb);
		}

		return genders;

	}

	private int findNum(int[] grade, String[] gender) {
		int num = 0;

		for (int i = 0; i < gender.length; i++) {
			String g = gender[i];
			if (g.equals("male")) {
				num += 1 << 4;
			} else if (g.equals("female")) {
				num += 1 << 3;
			}
		}

		for (int i = 0; i < grade.length; i++) {
			int g = grade[i];
			switch (g) {
			case 1:
				num += 1 << 0;
				break;
			case 2:
				num += 1 << 1;
				break;
			case 3:
				num += 1 << 2;
				break;
			}
		}

		return num;
	}

	private String findGender(char[] genderArr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < genderArr.length; i++) {
			char c = genderArr[i];

			if (c == '\0') {
				break;
			}

			sb.append(c);
		}

		return new String(sb);
	}
	
	private void print(String msg) {
//		System.out.println("-------" + msg + "-------");
//		for (Map.Entry<Integer, TreeSet<StudentInfo>> e : studentsOrdered.entrySet()) {
//			System.out.print(e.getKey() + ": ");
//			for (StudentInfo s : e.getValue()) {
//				System.out.print(String.format("/ %s %s / ", s.getId(), s.getScore()));
//			}
//			System.out.println();
//		}
//		System.out.println();
//		System.out.println();
		
	}
}

class Student {
	private int grade; // 학년
	private String gender; // 성별
	private int score; // 점수

	public Student(int grade, String gender, int score) {
		this.grade = grade;
		this.gender = gender;
		this.score = score;
	}

	public int getGrade() {
		return this.grade;
	}

	public String getGender() {
		return this.gender;
	}

	public int getScore() {
		return this.score;
	}
}

class StudentInfo implements Comparable<StudentInfo> {
	private int id;
	private int score;

	public StudentInfo(int id, int score) {
		this.id = id;
		this.score = score;
	}

	public int getId() {
		return this.id;
	}

	public int getScore() {
		return this.score;
	}

	@Override
	public int compareTo(StudentInfo o) {
		if (this.score == o.score) {
			return this.id - o.id;
		}

		return this.score - o.score;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		StudentInfo that = (StudentInfo) o;
		return this.id == that.id && this.score == that.score;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, score);
	}
}