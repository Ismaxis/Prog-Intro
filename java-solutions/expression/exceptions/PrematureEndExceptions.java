package expression.exceptions;

public class PrematureEndExceptions extends ParserException {
    public PrematureEndExceptions(int pos, char found) {
        super(pos, "END", found);
    }
}
