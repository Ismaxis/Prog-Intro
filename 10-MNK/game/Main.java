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

        int countOfObstacles;
        int[][] obstacles;
        while (true) {
            System.out.print("Enter count of obstacles: ");
            try {
                countOfObstacles = scn.nextInt();
                if (countOfObstacles >= m * n) {
                    System.out.println("To much obstacles. Try again");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Wrong input. Try again");
            }
        }

        MNKBoard board = new MNKBoard(m, n, k);
        if (countOfObstacles > 0) {
            obstacles = new int[countOfObstacles][2];
            System.out.println("Now enter coords of obstacles");
            for (int i = 0; i < countOfObstacles; i++) {
                try {
                    System.out.format("Coords of %d\n", i + 1);
                    obstacles[i][0] = getCoord(scn, "x", n);
                    obstacles[i][1] = getCoord(scn, "y", n);
                } catch (Exception e) {
                    System.out.format("Wrong input. Enter coords for %d again\n", i + 1);
                    i--;
                }
            }
            board.addObstacles(obstacles);
        }

        final int result = new TwoPlayerGame(
                board,
                List.of(
                        new RandomPlayer(m, n),
                        new HumanPlayer(scn))
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

    public static int getCoord(Scanner scn, String coordName, int bound) {
        while (true) {
            System.out.print(coordName + ": ");
            int input = scn.nextInt();
            if (0 < input && input < bound) {
                return input;
            } else {
                System.out.println("Coord out of bounce. Enter again");
            }
        }
    }
}
