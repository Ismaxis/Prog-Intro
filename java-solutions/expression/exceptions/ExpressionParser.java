package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringCharSource;

public class ExpressionParser extends BaseParser implements TripleParser {

    @Override
    public TripleExpression parse(final String expression) {
        source = new StringCharSource(expression);
        take();
        var res = parseTopLevel();
        expect(END);
        return res;
    }

    private ExpressionToString parseTopLevel() {
        return parseExpression();
    }

    private ExpressionToString parseExpression() {
        ExpressionToString left = parseTerm();

        while (true) {
            skipWhitespace();
            if (take('+')) {
                left = new CheckedAdd(left, parseTerm());
            } else if (take('-')) {
                left = new CheckedSubtract(left, parseTerm());
            } else {
                return left;
            }
        }
    }

    private ExpressionToString parseTerm() {
        ExpressionToString left = parseFactor();

        while (true) {
            skipWhitespace();
            if (take('*')) {
                left = new CheckedMultiply(left, parseFactor());
            } else if (take('/')) {
                left = new CheckedDivide(left, parseFactor());
            } else {
                return left;
            }
        }
    }

    private ExpressionToString parseFactor() {
        skipWhitespace();
        if (take('(')) {
            final ExpressionToString res = parseTopLevel();
            skipWhitespace();
            expect(')');
            return res;
        } else if (take('-')) {
            if (Character.isDigit(pick()) || Character.isAlphabetic(pick())) {
                return parsePrimitive(true);
            } else {
                return parseBrackets(CheckedNegate.class);
            }
        } else if (take("count")) {
            return parseBrackets(Count.class);
        } else {
            return parsePrimitive(false);
        }
    }

    private ExpressionToString parseBrackets(Class<?> cl) {
        if (take('(')) {
            final ExpressionToString neg = UnaryOperation.createInstance(cl, parseTopLevel());
            skipWhitespace();
            expect(')');
            return neg;
        } else {
            return UnaryOperation.createInstance(cl, parseFactor());
        }
    }

    private ExpressionToString parsePrimitive(boolean isNegative) {
        skipWhitespace();
        if (Character.isDigit(pick())) {
            return parseConst(isNegative);
        } else if (isVariableStart(pick())) {
            final ExpressionToString variable = parseVariable();
            return isNegative ? new CheckedNegate(variable) : variable;
        } else {
            throw source.error("Expected: 'Variable or constant', Found: '" + take() + "'");
        }
    }

    private ExpressionToString parseVariable() {
        StringBuilder sb = new StringBuilder();
        sb.append(take());
        while (isVariablePart(pick())) {
            sb.append(take());
        }
        if (Character.isAlphabetic(pick()) && pick() != END) {
            throw source.error("Variable name error. Expected: 'Whitespace or END', Found: '" + pick() + "'");
        }
        return new Variable(sb.toString());
    }

    private static boolean isVariableStart(char ch) {
        return ch == 'x' || ch == 'y' || ch == 'z';
    }

    private static boolean isVariablePart(char ch) {
        return false;
    }

    private ExpressionToString parseConst(boolean isNegative) {
        StringBuilder sb = new StringBuilder(isNegative ? "-" : "");
        while (Character.isDigit(pick())) {
            sb.append(take());
        }
        return new Const(Integer.parseInt(sb.toString()));
    }
}
