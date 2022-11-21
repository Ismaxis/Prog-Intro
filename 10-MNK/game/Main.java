package game;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        int m;
        int n;
        int k;
        while (true) {
            System.out.print("Input m, n and k: ");
            try {
                m = scn.nextInt();
                n = scn.nextInt();
                k = scn.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Wrong input. Try again");
            }
        }

        final int result = new TwoPlayerGame(
                new MNKBoard(m, n, k),
                List.of(
                        new RandomPlayer(m, n),
                        new RandomPlayer(m, n))
        // new HumanPlayer(scn))
        // new CheatingPlayer()
        ).play(false);
        scn.close();
        switch (result) {
            case 1:
                System.out.println("First player won");
                break;
            case 2:
                System.out.println("Second player won");
                break;
            case 0:
                System.out.println("Draw");
                break;
            default:
                throw new AssertionError("Unknown result " + result);
        }
    }
}
