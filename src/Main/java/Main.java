import java.util.Scanner;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Integer> cl = new LinkedList<>();
        Stack<Integer> space = new Stack<>();
        Stack<Integer> snack = new Stack<>();
        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        for(int i=0; i<n; i++) {
            cl.offer(s.nextInt());
        }
        System.out.println(cl.peek());
         try {
             while(true) {
                 System.out.println(cl.peek());

                 if(!cl.isEmpty()&&cl.peek() == snack.size()+1)
                     snack.add(((LinkedList<Integer>) cl).pop());
                 else if(!space.empty()&& space.peek() == snack.size()+1)
                     snack.add(space.pop());
                 else if(!cl.isEmpty()&&cl.peek()!=snack.size() && !space.isEmpty()&& space.peek()!=snack.size())
                     space.add(((LinkedList<Integer>) cl).pop());

                 if(cl.size()==0 && !space.isEmpty() && space.peek()!=snack.size()) {
                     System.out.println("Sad");
                     break;
                 } else if(snack.size()==n) {
                     System.out.println("Nice");
                     break;
                 }
             }
         }catch(Exception e){
             e.printStackTrace();
        }


    }
}

// only in progress  , if done move to another space please
// add some comment