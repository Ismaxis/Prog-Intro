public class Move {
    private int row;
    private int col;
    private Cell cell;

    public Move(int row, int col, Cell cell) {
        this.row = row;
        this.col = col;
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
