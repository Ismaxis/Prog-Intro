package game;

import java.util.Arrays;

public class TicTacToeBoard implements Board {
    private final Cell[][] field;
    private Turn turn;
    private final TicTacToePosition position;
    private int turnsLeft;

    public TicTacToeBoard() {
        field = new Cell[3][3];
        turnsLeft = 3 * 3;
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = new Turn(Cell.X);
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

        turnsLeft--;
        field[move.getRow()][move.getCol()] = move.getValue();
        if (checkWin()) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }
        turn.setCell(turn.getCell() == Cell.X ? Cell.O : Cell.X);
        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        if (turnsLeft <= 0) {
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        Cell curCell = turn.getCell();
        for (int r = 0; r < 3; r++) {
            int count = 0;
            for (int c = 0; c < 3; c++) {
                if (field[r][c] == curCell) {
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
                if (field[r][c] == curCell) {
                    count++;
                }
            }
            if (count == 3) {
                return true;
            }
        }
        return field[0][0] == curCell && field[1][1] == curCell && field[2][2] == curCell
                || field[0][2] == curCell && field[1][1] == curCell && field[2][0] == curCell;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
