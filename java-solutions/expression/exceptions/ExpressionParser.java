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
        expectEnd();
        return res;
    }

    private void expectEnd() {
        if (!take(END)) {
            throw new PrematureEndExceptions(source.getPos(), pick());
        }
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
            expectCloseBracket();
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

    private void expectCloseBracket() {
        if (!take(')')) {
            throw new CloseBracketsException(source.getPos(), pick());
        }
    }

    private ExpressionToString parseBrackets(Class<?> cl) {
        if (take('(')) {
            final ExpressionToString neg = UnaryOperation.createInstance(cl, parseTopLevel());
            skipWhitespace();
            expectCloseBracket();
            return neg;
        } else {
            return UnaryOperation.createInstance(cl, parseFactor());
        }
    }

    private ExpressionToString parsePrimitive(boolean isNegative) {
        ExpressionToString primitive;
        skipWhitespace();
        if (Character.isDigit(pick())) {
            primitive = parseConst(isNegative);
        } else if (isVariableStart(pick())) {
            final ExpressionToString variable = parseVariable();
            primitive = isNegative ? new CheckedNegate(variable) : variable;
        } else {
            throw primitiveStartParseError(pick());
        }
        validEndOfPrimitive();
        return primitive;
    }

    private void validEndOfPrimitive() {
        if (!validEndOfPrimitive(pick())) {
            throw primitiveEndParseError(pick());
        }
    }

    private PrimitiveParseException primitiveStartParseError(char found) {
        return new PrimitiveParseException(source.getPos(), "Start of Variable or Const", found);
    }

    private PrimitiveParseException primitiveEndParseError(char found) {
        return new PrimitiveParseException(source.getPos(), "Whitespace or END", found);
    }

    private ExpressionToString parseVariable() {
        StringBuilder sb = new StringBuilder();
        sb.append(take());
        while (isVariablePart(pick())) {
            sb.append(take());
        }
        return new Variable(sb.toString());
    }

    private static boolean isVariableStart(char ch) {
        return ch == 'x' || ch == 'y' || ch == 'z';
    }

    private static boolean isVariablePart(char ch) {
        return false;
    }

    private static boolean validEndOfPrimitive(char ch) {
        return Character.isWhitespace(ch) || isOperationSymbol(ch) || (ch == END) || (ch == ')');
    }

    private static boolean isOperationSymbol(char ch) {
        return (ch == '+') || (ch == '-') || (ch == '*') || (ch == '/');
    }

    private ExpressionToString parseConst(boolean isNegative) {
        StringBuilder sb = new StringBuilder(isNegative ? "-" : "");
        while (Character.isDigit(pick())) {
            sb.append(take());
        }
        return new Const(Integer.parseInt(sb.toString()));
    }
}
