package B;
import java.util.Scanner;

public class BadTreap {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        final int n = scn.nextInt();
        scn.close();

        final int step = 710;

        for (int i = 0; i < n; i++) {
            int cur = step*(-25000 + i);
            System.out.println(cur);
        }
    }   
}
