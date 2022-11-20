package game;

public class TicTacToePosition implements Position {
    private final Cell[][] field;
    private Cell turn;

    public TicTacToePosition(Cell[][] field, Cell turn) {
        this.field = field;
        this.turn = turn;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < 3
                && 0 <= move.getCol() && move.getCol() < 3
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

}
