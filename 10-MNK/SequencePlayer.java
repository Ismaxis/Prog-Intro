public class SequencePlayer implements Player {

    @Override
    public Move move(Position position) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final Move move = new Move(i, j, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}
