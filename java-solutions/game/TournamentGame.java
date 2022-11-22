package game;

import java.util.List;

public class TournamentGame {
    private final Board board;
    private final List<Player> players;
    private final int[] score;

    public TournamentGame(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
        this.score = new int[players.size()];
    }

    public void play(boolean log) {
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                if (i == j) {
                    continue;
                }
                System.out.format("Roung between %d(X) %d(O)\n", i + 1, j + 1);
                int result = startRound(players.get(i), players.get(j), log);
                if (result == 1) {
                    score[i] += 2;
                    printWinner(i + 1);
                } else if (result == 2) {
                    score[j] += 2;
                    printWinner(j + 1);
                } else if (result == 0) {
                    System.out.println("DRAW");
                    score[i] += 1;
                    score[j] += 1;
                } else {
                    throw new AssertionError("Unknown startRound result " + result);
                }
                board.reset();
                printScore();
            }
        }
        System.out.println("Final score:");
        printScore();
    }

    public void printScore() {
        System.out.println("-----SCORE-----");
        for (int i = 0; i < score.length; i++) {
            System.out.format("%d %d\n", i + 1, score[i]);
        }
        System.out.println("---------------");
    }

    private void printWinner(int no) {
        System.out.format("%d Wins\n", no);
    }

    private int startRound(Player pl1, Player pl2, boolean log) {
        boolean firstTurn = true;
        while (true) {
            final int result = playerMoves(firstTurn ? pl1 : pl2, firstTurn ? 1 : 2, log);
            firstTurn = !firstTurn;
            if (result != -1) {
                System.out.println("Final position");
                System.out.println(board);
                return result;
            }
        }
    }

    private int playerMoves(Player player, int no, boolean log) {
        try {
            return makeMove(player, no, log);
        } catch (Exception e) {
            technicalLoose(no);
            return 3 - no;
        }
    }

    private void technicalLoose(int id) {
        System.out.format("Player %d crashed. Technical loose\n", id);
    }

    private int makeMove(Player player, int no, boolean log) {
        final Move move = player.makeMove(board.getPosition());
        final GameResult result = board.makeMove(move);
        if (log) {
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
            System.out.println("Result: " + result);
        }
        switch (result) {
            case WIN:
                return no;
            case LOOSE:
                return 3 - no;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
