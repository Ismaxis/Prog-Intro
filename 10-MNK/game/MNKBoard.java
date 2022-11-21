package game;

import java.util.Arrays;

public class MNKBoard implements Board {
    private int m;
    private int n;
    private int k;

    private final Cell[][] field;
    private Turn turn;
    private final MNKPosition position;
    private int turnsLeft;

    public MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;

        field = new Cell[m][n];
        turnsLeft = m * n;
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = new Turn(Cell.X);
        position = new MNKPosition(field, turn, m, n);
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
        if (checkWin(move)) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }
        turn.setCell(turn.getCell() == Cell.X ? Cell.O : Cell.X);
        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        return turnsLeft <= 0;
    }

    private boolean checkWin(Move move) {
        Cell curCell = turn.getCell();
        int x = move.getCol();
        int y = move.getRow();

        // Vert
        int i = 1;
        int bottom = 0;
        while (y + i < m && field[y + i][x] == curCell) {
            bottom++;
            i++;
        }
        int top = 0;
        i = 1;
        while (0 <= y - i && field[y - i][x] == curCell) {
            top++;
            i++;
        }
        if (top + bottom + 1 >= k) {
            return true;
        }

        // Hor
        i = 1;
        int right = 0;
        while (x + i < n && field[y][x + i] == curCell) {
            right++;
            i++;
        }
        int left = 0;
        i = 1;
        while (0 <= x - i && field[y][x - i] == curCell) {
            left++;
            i++;
        }
        if (left + right + 1 >= k) {
            return true;
        }

        // Main diag
        i = 1;
        int rightBottom = 0;
        while (x + i < n && y + i < m && field[y + i][x + i] == curCell) {
            rightBottom++;
            i++;
        }
        int leftTop = 0;
        i = 1;
        while (0 <= x - i && 0 <= y - i && field[y - i][x - i] == curCell) {
            leftTop++;
            i++;
        }
        if (leftTop + rightBottom + 1 >= k) {
            return true;
        }

        // Sub diag
        i = 1;
        int rightTop = 0;
        while (0 <= x - i && y + i < m && field[y + i][x - i] == curCell) {
            rightTop++;
            i++;
        }
        int leftBottom = 0;
        i = 1;
        while (x + i < n && 0 <= y - i && field[y - i][x + i] == curCell) {
            leftBottom++;
            i++;
        }
        if (leftBottom + rightTop + 1 >= k) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
