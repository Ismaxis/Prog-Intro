package game;

import java.util.Map;

public class MNKPosition implements Position {
    private final int m;
    private final int n;
    private final int offsetX;
    private final int offsetY;

    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0",
            Cell.Z, "Z");

    private final Cell[][] field;
    private Turn turn;

    public MNKPosition(Cell[][] field, Turn turn, int m, int n) {
        this.m = m;
        this.n = n;

        this.offsetX = (int) Math.log10(n) + 1;
        this.offsetY = (int) Math.log10(m) + 1;

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
                && field[move.getRow()][move.getCol()] != Cell.Z
                && turn.getCell() == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ".repeat(offsetY + 1));
        int curSizeX = 0;
        int nextTenPowerX = 10;
        for (int c = 1; c <= n; c++) {
            sb.append(c);
            if (c == nextTenPowerX) {
                nextTenPowerX = (int) Math.pow(10, ++curSizeX);
            }
            sb.append(" ".repeat(offsetX - curSizeX));
        }
        sb.append(System.lineSeparator());

        int curSizeY = 0;
        int nextTenPowerY = 10;
        for (int r = 0; r < m; r++) {
            sb.append(r + 1);
            if (r + 1 == nextTenPowerY) {
                nextTenPowerY = (int) Math.pow(10, ++curSizeY);
            }
            sb.append(" ".repeat(offsetY - curSizeY));
            for (int c = 0; c < n; c++) {
                curSizeX = 0;
                nextTenPowerX = 10;
                Cell cell = field[r][c];
                sb.append(CELL_TO_STRING.get(cell));
                sb.append(" ".repeat(offsetX - curSizeX));
                if (c == nextTenPowerX) {
                    nextTenPowerX = (int) Math.pow(10, ++curSizeX);
                }
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
