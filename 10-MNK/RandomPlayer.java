import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();

    @Override
    public Move move(Position position) {
        while (true) {
            final Move move = new Move(random.nextInt(3), random.nextInt(3), position.getTurn());
            if (position.isValid(move)) {
                return move;
            }
        }
    }

}
