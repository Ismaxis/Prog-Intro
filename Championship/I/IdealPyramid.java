package I;

import java.util.Scanner;

public class IdealPyramid {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        try {
            int n = scn.nextInt();

            int xl = Integer.MAX_VALUE;
            int xr = Integer.MIN_VALUE;
            int yl = Integer.MAX_VALUE;
            int yr = Integer.MIN_VALUE;

            for (int i = 0; i < n; i++) {
                int[] coords = new int[3];
                for (int j = 0; j < 3; j++) {
                    coords[j] = scn.nextInt();
                }
                xl = Math.min(coords[0] - coords[2], xl);
                xr = Math.max(coords[0] + coords[2], xr);
                yl = Math.min(coords[1] - coords[2], yl);
                yr = Math.max(coords[1] + coords[2], yr);
            }

            int x = (xl + xr) / 2;
            int y = (yl + yr) / 2;
            int h = (Math.max(xr - xl, yr - yl) + 1)/2;

            System.out.println(String.format("%d %d %d", x, y, h));
        } finally {
            scn.close();
        }
    }
}
