package game;

import java.util.List;

public class TwoPlayerGame {
    private final Board board;
    private final List<Player> players;
    // private final Player player1;
    // private final Player player2;

    public TwoPlayerGame(Board board, List<Player> players) {
        this.board = board;
        this.players = players;

        // this.player1 = player1;
        // this.player2 = player2;
    }

    public int play(boolean log) {
        int turn = 0;
        while (true) {
            final int result = playerMoves(players.get(turn), turn + 1, log);
            turn = (turn + 1) % players.size();
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
