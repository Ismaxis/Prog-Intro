public interface Position {
    Cell getTurn();

    boolean isValid(Move move);
}
