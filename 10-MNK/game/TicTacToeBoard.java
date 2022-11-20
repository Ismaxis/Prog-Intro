package game;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0");

    private final Cell[][] field;
    private Cell turn;

    private final TicTacToePosition position;

    public TicTacToeBoard() {
        field = new Cell[3][3];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        position = new TicTacToePosition(field, turn);
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!position.isValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        if (checkWin()) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        int count = 0;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (field[r][c] == Cell.E) {
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        for (int r = 0; r < 3; r++) {
            int count = 0;
            for (int c = 0; c < 3; c++) {
                if (field[r][c] == turn) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            }
        }
        for (int c = 0; c < 3; c++) {
            int count = 0;
            for (int r = 0; r < 3; r++) {
                if (field[r][c] == turn) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            }
        }
        return field[0][0] == turn && field[1][1] == turn && field[2][2] == turn
                || field[0][2] == turn && field[1][1] == turn && field[2][0] == turn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" 123").append(System.lineSeparator());
        for (int r = 0; r < 3; r++) {
            sb.append(r + 1);
            for (Cell cell : field[r]) {
                sb.append(CELL_TO_STRING.get(cell));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
