public class AccurateMovement {
    public static void main(String[] args) {
        if (args.length < 3) {
            return;
        }

        int a = Integer.parseInt(args[0]); // short
        int b = Integer.parseInt(args[1]); // long
        int n = Integer.parseInt(args[2]); // box 
    
        System.out.print(2*(n / (b - a)) - 1);
    }
}
