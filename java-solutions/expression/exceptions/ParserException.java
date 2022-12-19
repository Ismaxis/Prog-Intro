package expression.exceptions;

public class ParserException extends RuntimeException {
    public ParserException(int pos, String expected, char found) {
        super(pos + ": Expected: '" + expected + "', Found: '" + found + "'");
    }
}
