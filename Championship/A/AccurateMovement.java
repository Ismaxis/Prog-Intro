package A;
import myscanner.*;
public class AccurateMovement {
    public static void main(String[] args) {
        MyScanner scn = new MyScanner(System.in, new WhiteSpace());

        int a = scn.nextInt(); // short
        int b = scn.nextInt(); // long
        int n = scn.nextInt(); // box size
        scn.close();

        int delta = b - a;
        int dist = n - b; 

        int i = ceilDiv(dist, delta); // ceil without doubles 
    
        System.out.println(2*i + 1);
    }

    public static int ceilDiv(int a, int b) {
        return a%b == 0 ? a/b : a/b + 1;
    }
}
