package SWEA.알고리즘특강.코드배틀;

import java.util.Scanner;
import java.util.Random;

class MagicRoom {
    private final static int NUM_PEOPLE = 1000000;
    private final static int MAX_ROOM = 1000000;

    private static int SCORE = 0;
    private boolean room[] = new boolean[MAX_ROOM];
    private boolean citizen[] = new boolean[NUM_PEOPLE];
    private boolean arrested[] = new boolean[NUM_PEOPLE];

    public void putln(int n, int a) {
        if (n < 0 || n >= MAX_ROOM) return;
        if (a < 0 || a >= NUM_PEOPLE) return;
        if (citizen[a]) room[n] = true;
    }
    public int closeDoor(int n) {
        SCORE += 1;
        if (n < 0 || n >= MAX_ROOM) return 0;
        return (room[n] ? 1 : 0);
    }
    public void clearRoom() {
        for(int i = 0; i < NUM_PEOPLE; i++) room[i] = false;
    }
    public void arrest(int a) {
        SCORE += 10;
        if (a < 0 || a >= NUM_PEOPLE) return;
        if (citizen[a] == false) {
            SCORE += 10000;
            System.out.println("fail");
        }
        arrested[a] = true;
    }

    public int getScore(){
        int score = SCORE;
        for (int i = 0; i < NUM_PEOPLE; i++) {
            if(citizen[i] == true && arrested[i] == false) {
                score += 90000;
//                System.out.println("fail");

            }
        }
        return score;
    }

    public boolean[] getRoom() {
        return room;
    }

    public MagicRoom() {
        Random random = new Random();

        SCORE = 0;
        for (int i = 0; i < MAX_ROOM; i++) {
            room[i] = false;
        }
        for (int i = 0; i < NUM_PEOPLE; i++) {
            citizen[i] = false;
            arrested[i] = false;
        }

        for (int i = 0; i < 1000; i++) {
            int index = random.nextInt(NUM_PEOPLE);
            if (citizen[index]) {
                i--;
                continue;
            }
            citizen[index] = true;
        }
    }
}

class Solution {
    private static UserSolution userSolution = new UserSolution();
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int TC = sc.nextInt();
        int totalScore = 0;

        for(int test = 1; test <= TC; test++) {
            MagicRoom api = new MagicRoom();
            userSolution.investigate(api);

            int score = api.getScore();
            if (score < 86400) System.out.println("#" + test + " 100 " + score);
            else System.out.println("#" + test + " 0 0");

            totalScore += score;
        }
        System.out.println("TOTAL SCORE : " + totalScore);
    }
}