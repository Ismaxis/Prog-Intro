package game;

import java.util.ArrayList;
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
                scn.next();
            }
        }

        int countOfObstacles;
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
                scn.next();
            }
        }

        MNKBoard board = new MNKBoard(m, n, k);
        if (countOfObstacles > 0) {
            board.addObstacles(getObstacles(scn, n, countOfObstacles));
        }

        int playersCount;
        while (true) {
            System.out.print("Enter count of players: ");
            try {
                playersCount = scn.nextInt();
                if (playersCount <= 0) {
                    System.out.println("Count must be greater than zero. Try again");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Wrong input. Try again");
                scn.next();
            }
        }

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < playersCount; i++) {
            while (true) {
                System.out.format("Enter type of player %d: [H|R|S]", i + 1);
                try {
                    String type = scn.next();
                    if (type.startsWith("H")) {
                        players.add(new HumanPlayer(scn));
                    } else if (type.startsWith("R")) {
                        players.add(new RandomPlayer(m, n));
                    } else if (type.startsWith("S")) {
                        players.add(new SequentialPlayer(m, n));
                    } else {
                        System.out.println("Wrong type: ");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Wrong input. Try again");
                    scn.next();
                }
            }
        }

        new TournamentGame(
                board, players)
                .play(false);
        scn.close();
    }

    private static int[][] getObstacles(Scanner scn, int n, int countOfObstacles) {
        int[][] obstacles;
        obstacles = new int[countOfObstacles][2];
        System.out.println("Now enter coords of obstacles");
        for (int i = 0; i < countOfObstacles; i++) {
            try {
                System.out.format("Coords of %d\n", i + 1);
                obstacles[i][0] = getCoord(scn, "x", n) - 1;
                obstacles[i][1] = getCoord(scn, "y", n) - 1;
            } catch (Exception e) {
                System.out.format("Wrong input. Enter coords for %d again\n", i + 1);
                scn.next();
                i--;
            }
        }
        return obstacles;
    }

    public static int getCoord(Scanner scn, String coordName, int bound) {
        while (true) {
            System.out.print(coordName + ": ");
            int input = scn.nextInt();
            if (0 < input && input <= bound) {
                return input;
            } else {
                System.out.println("Coord out of bounce. Enter again");
                scn.next();
            }
        }
    }
}
