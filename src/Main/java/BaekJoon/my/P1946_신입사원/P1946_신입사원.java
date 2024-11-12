package BaekJoon.my.P1946_신입사원;

import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.CheckedInputStream;

public class P1946_신입사원 {
    public static Result[] results;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCaseN = sc.nextInt();

        for (int t = 0; t < testCaseN; t++) {
            int applicantN = sc.nextInt();  // 지원자 수
            sc.nextLine();  // 숫자 뒤의 줄바꿈(Enter) 처리

            // 지원자들의 성적 입력받기
            results = new Result[applicantN];  // 지원자들의 성적 저장 배열
            for (int a = 0; a < applicantN; a++) {
                // 한 줄 입력을 받아서 공백으로 나눈 후 성적 입력
                String[] scores = sc.nextLine().split(" ");
                int docScore = Integer.parseInt(scores[0]);
                int interviewScore = Integer.parseInt(scores[1]);
                results[a] = new Result(docScore, interviewScore);
            }
            Arrays.sort(results);   // 서류 등수 오름차순 정렬

            // 선발할 수 있는 신입사원의 수 구하기
            int highestLank = results[0].interviewScore;    // 현재까지 가장 높은 면접 점수
            int newEmployeeCnt = 1; // 서류 등수가 1인 사람은 신입사원이 될 수 있음
            for (int i = 1; i < results.length; i++) {  // highestLank 보다 등수가 높으면 pass
                int interviewLank = results[i].getInterviewScore();
                if (interviewLank < highestLank) {
                    newEmployeeCnt++;
                    highestLank = interviewLank;
                }
            }

            System.out.println(newEmployeeCnt);
        }
        sc.close();
    }

    private static class Result implements Comparable<Result> {
        int docScore;   // 서류 점수
        int interviewScore; // 면접 점수

        public Result(int docScore, int interviewScore) {
            this.docScore = docScore;
            this.interviewScore = interviewScore;
        }

        public int getInterviewScore() {
            return interviewScore;
        }

        @Override
        public int compareTo(Result o) {
            return Integer.compare(this.docScore, o.docScore);
        }
    }
}
