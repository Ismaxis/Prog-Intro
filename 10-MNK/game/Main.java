package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int result = new TwoPlayerGame(
                new MNKBoard(9, 9, 9),
                new RandomPlayer(),
                new HumanPlayer(new Scanner(System.in))
        // new CheatingPlayer()
        ).play(true);
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

// Random player should now about board size
// Add user input for board size
// Work on graphic view
// Add human player error checking system
