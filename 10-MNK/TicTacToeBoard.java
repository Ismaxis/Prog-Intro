import java.util.Arrays;

public class TicTacToeBoard implements Board, Position {

    private final Cell[][] field = new Cell[3][3];
    private int emptyCells;

    private Cell turn;

    public TicTacToeBoard() {
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        emptyCells = 9;

        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Result makeMove(Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        field[move.getRow()][move.getCol()] = move.getCell();

        for (int i = 0; i < 3; i++) {
            if (field[i][0] == turn && field[i][1] == turn && field[i][2] == turn) {
                return Result.WIN;
            }
            if (field[0][i] == turn && field[1][i] == turn && field[2][i] == turn) {
                return Result.WIN;
            }
        }

        if (field[0][0] == turn && field[1][1] == turn && field[2][2] == turn) {
            return Result.WIN;
        }

        emptyCells--;
        if (emptyCells == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.O ? Cell.X : Cell.O;

        return Result.UNKNOWN;
    }

    public boolean isValid(Move move) {
        return 0 <= move.getRow() && move.getRow() < 3 &&
                0 <= move.getCol() && move.getCol() < 3 &&
                field[move.getRow()][move.getCol()] == Cell.E;

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" 123\n +---\n");

        for (int i = 0; i < field.length; i++) {
            sb.append(i + 1).append("|");
            for (int j = 0; j < field.length; j++) {
                sb.append(field[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

}
