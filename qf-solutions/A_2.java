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

        int i = dist%delta == 0 ? dist/delta : dist/delta + 1; // ceil without doubles 
    
        System.out.print(2*i + 1);
    }
}
