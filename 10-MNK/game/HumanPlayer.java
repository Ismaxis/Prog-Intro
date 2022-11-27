package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());

        Move move;
        while (true) {
            int y;
            int x;
            try {
                y = in.nextInt() - 1;
                x = in.nextInt() - 1;
            } catch (InputMismatchException e) {
                System.out.println("Wrong input. Coords must be an integers");
                in.next();
                continue;
            }

            move = new Move(y, x, position.getTurn());
            if (position.isValid(move)) {
                break;
            } else {
                System.out.println("This move is invalid. Try again");
            }
        }

        return move;
    }
}
