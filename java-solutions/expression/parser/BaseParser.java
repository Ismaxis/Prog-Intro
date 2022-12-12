package expression.parser;

public class BaseParser {
    public static final char END = 0;
    protected CharSource source;
    private char ch;

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean take(char expected) {
        if (ch == expected) {
            take();
            return true;
        } else {
            return false;
        }
    }

    protected char pick() {
        return ch;
    }

    protected void expect(char expected) {
        if (!take(expected)) {
            throw source.error("Expexted '" + expected + "' found '" + take() + "'");
        }
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(pick())) {
            take();
        }
    }
}
