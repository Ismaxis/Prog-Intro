package expression.exceptions;

public class PrimitiveParseException extends ParserException {
    public PrimitiveParseException(int pos, String expected, char found) {
        super(pos, expected, found);
    }
}
