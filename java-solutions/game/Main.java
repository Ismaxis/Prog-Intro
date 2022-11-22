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

        MNKBoard board = new MNKBoard(m, n, k);
        board.addObstacles(getObstacles(m, n));

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
                System.out.format("Enter type of player %d: [Human|Random|Sequential]", i + 1);
                String type = scn.next();
                if (type.startsWith("H")) {
                    players.add(new HumanPlayer(scn));
                } else if (type.startsWith("R")) {
                    players.add(new RandomPlayer(m, n));
                } else if (type.startsWith("S")) {
                    players.add(new SequentialPlayer(m, n));
                } else {
                    System.out.println("Unknown type. Try again");
                    continue;
                }
                break;

            }
        }

        new TournamentGame(
                board, players)
                .play(false);
        scn.close();
    }

    private static int[][] getObstacles(int m, int n) {
        int crossSize = Math.min(m, n);
        int[][] obstacles = new int[2 * crossSize][2];
        int cnt = 0;
        for (int i = 0; i < crossSize; i++) {
            obstacles[cnt][0] = i;
            obstacles[cnt][1] = i;
            cnt++;
            obstacles[cnt][0] = i;
            obstacles[cnt][1] = crossSize - 1 - i;
            cnt++;
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
