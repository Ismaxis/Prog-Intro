package game;

import java.util.Map;

public class MNKPosition implements Position {
    private int m;
    private int n;

    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0");

    private final Cell[][] field;
    private Turn turn;

    public MNKPosition(Cell[][] field, Turn turn, int m, int n) {
        this.m = m;
        this.n = n;

        this.field = field;
        this.turn = turn;
    }

    @Override
    public Cell getTurn() {
        return turn.getCell();
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getCol() && move.getCol() < n
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn.getCell() == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int c = 1; c <= n; c++) {
            sb.append(c);
        }
        sb.append(System.lineSeparator());
        for (int r = 0; r < m; r++) {
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
