public class AccurateMovement {
    public static void main(String[] args) {
        if (args.length < 3) {
            return;
        }

        int a = Integer.parseInt(args[0]); // short
        int b = Integer.parseInt(args[1]); // long
        int n = Integer.parseInt(args[2]); // box 

        int delta = b - a;
        int dist = n - b; 

        int i = ceilDiv(dist, delta); // ceil without doubles 
    
        System.out.println(2*i + 1);
    }

    public static int ceilDiv(int a, int b) {
        return a%b == 0 ? a/b : a/b + 1;
    }
}
