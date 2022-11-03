package J;

import java.util.Scanner;

public class JustLastDigit {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        try {
            int n = scn.nextInt();
            scn.nextLine();
            int[][] a = new int[n][n];
            for (int i = 0; i < n; i++) {
                String line = scn.nextLine();
                for (int j = 0; j < n; j++) {
                    a[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }
            for (int vert = 0; vert < n; vert++) { 
                for (int j = vert + 1; j < n; j++) { 
                    if (a[vert][j] == 1) {
                        for (int i = j + 1; i < n; i++) {
                            a[vert][i] =  Math.floorMod(a[vert][i] - a[j][i], 10);
                        }
                    } 
                }
            }

            for (int[] line : a) {
                for (int i : line) {
                    System.out.print(i);
                }
                System.out.println();
            }
        } finally {
            scn.close();
        }
    }
}
