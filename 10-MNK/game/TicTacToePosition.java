package game;

import java.util.Map;

public class TicTacToePosition implements Position {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0");

    private final Cell[][] field;
    private Turn turn;

    public TicTacToePosition(Cell[][] field, Turn turn) {
        this.field = field;
        this.turn = turn;
    }

    @Override
    public Cell getTurn() {
        return turn.getCell();
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < 3
                && 0 <= move.getCol() && move.getCol() < 3
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn.getCell() == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
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
