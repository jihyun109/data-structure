package SWEA.알고리즘특강.코드배틀;

import java.util.Arrays;

class UserSolution {
    private final static int NUM_PEOPLE = 1000000;
    private final static int MAX_ROOM = 1000000;
    private final static int MEMBER = 1000; // 조직원 수

    void investigate(MagicRoom api) {

        int arrestedN = 0;  // 체포된 조직원 수
        int classifiedN = 0;    // 분류된 일반인 수

        for (int i = 0; i < NUM_PEOPLE; i++) {
            api.putln(i, i);

        }

//        System.out.println(Arrays.toString(api.getRoom()));

        int front = 0;
        int end = 999999;

        while (front <= end) {
            if (arrestedN == MEMBER) {
                break;
            }
            int mid = (front + end) / 2;

            for (int i = front; i <= mid; i++) {
                if (api.closeDoor(i) == 1) {
                    api.arrest(i);
                    arrestedN++;
                } else {
                    classifiedN++;
                }

                if (arrestedN == MEMBER) {
                    break;
                } else if (classifiedN == NUM_PEOPLE - MEMBER) {
                    for (int j = i; j < MAX_ROOM; j++) {
                        api.arrest((j));
                        arrestedN++;
                    }
                    break;
                }
            }
            front = mid + 1;
        }

        System.out.println("arrestedN: " + arrestedN);
        System.out.println(api.getScore());
    }
}