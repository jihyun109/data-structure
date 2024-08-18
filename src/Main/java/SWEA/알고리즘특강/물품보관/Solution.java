package SWEA.알고리즘특강.물품보관;

import java.util.Scanner;

class Solution
{
    private static final int CMD_INIT 			= 100;
    private static final int CMD_STOCK 			= 200;
    private static final int CMD_SHIP 			= 300;
    private static final int CMD_GET_HEIGHT		= 400;

    private static UserSolution usersolution = new UserSolution();

    private static boolean run(Scanner sc) throws Exception
    {
        int Q;
        int N, mLoc, mBox;

        int ret = -1, ans;

        Q = sc.nextInt();

        boolean okay = false;

        for (int q = 0; q < Q; ++q)
        {
            int cmd;

            cmd = sc.nextInt();
            switch(cmd)
            {
                case CMD_INIT:
                    N = sc.nextInt();
                    usersolution.init(N);
                    okay = true;
                    break;
                case CMD_STOCK:
                    mLoc = sc.nextInt();
                    mBox = sc.nextInt();
                    ret = usersolution.stock(mLoc, mBox);
                    ans = sc.nextInt();
                    if (ans != ret)
                        okay = false;
                    break;
                case CMD_SHIP:
                    mLoc = sc.nextInt();
                    mBox = sc.nextInt();
                    ret = usersolution.ship(mLoc, mBox);
                    ans = sc.nextInt();
                    if (ans != ret)
                        okay = false;
                    break;
                case CMD_GET_HEIGHT:
                    mLoc = sc.nextInt();
                    ret = usersolution.getHeight(mLoc);
                    ans = sc.nextInt();
                    if (ans != ret)
                        okay = false;
                    break;
                default:
                    okay = false;
                    break;
            }

        }

        return okay;
    }

    public static void main(String[] args) throws Exception
    {
        System.setIn(new java.io.FileInputStream("src/Main/java/SWEA/알고리즘특강/물품보관/sample_input.txt"));

        Scanner sc = new Scanner(System.in);

        int TC = sc.nextInt();
        int MARK = sc.nextInt();

        for (int testcase = 1; testcase <= TC; ++testcase)
        {
            int score = run(sc) ? MARK : 0;
            System.out.println("#" + testcase + " " + score);
        }

        sc.close();

    }
}