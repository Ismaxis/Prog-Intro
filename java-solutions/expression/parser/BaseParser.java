package expression.parser;

public class BaseParser {
    public static final char END = 0;
    protected CharSource source;
    private char ch;
    private char markedCh;

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

    protected boolean take(String s) {
        source.mark();
        markedCh = ch;

        for (int i = 0; i < s.length(); i++) {
            if (!take(s.charAt(i))) {
                source.reset();
                ch = markedCh;
                return false;
            }
        }

        return true;
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
