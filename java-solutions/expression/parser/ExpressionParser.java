package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements TripleParser {

    @Override
    public TripleExpression parse(final String expression) {
        source = new StringCharSource(expression);
        take();
        return parse0();
    }

    private ExpressionToString parse0() {
        ExpressionToString left = parseExpression();

        while (true) {
            skipWhitespace();
            if (take('m')) {
                if (take("in")) {
                    left = new Min(left, parseExpression());
                } else if (take("ax")) {
                    left = new Max(left, parseExpression());
                } else {
                    throw source.error("min or max expected '" + take() + take() + "' found");
                }
            } else {
                return left;
            }
        }
    }

    private ExpressionToString parseExpression() {
        ExpressionToString left = parseTerm();

        while (true) {
            skipWhitespace();
            if (take('+')) {
                left = new Add(left, parseTerm());
            } else if (take('-')) {
                left = new Subtract(left, parseTerm());
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
                left = new Multiply(left, parseFactor());
            } else if (take('/')) {
                left = new Divide(left, parseFactor());
            } else {
                return left;
            }
        }
    }

    private ExpressionToString parseFactor() {
        skipWhitespace();
        if (take('(')) {
            final ExpressionToString res = parse0();
            skipWhitespace();
            expect(')');
            return res;
        } else if (take('-')) {
            if (take('(')) {
                final ExpressionToString neg = new Negate(parse0());
                skipWhitespace();
                expect(')');
                return neg;
            } else if (Character.isDigit(pick()) || Character.isAlphabetic(pick())) {
                return parsePrimitive(true);
            } else {
                return new Negate(parseFactor());
            }
        } else {
            return parsePrimitive(false);
        }
    }

    private ExpressionToString parsePrimitive(boolean isNegative) {
        skipWhitespace();
        if (Character.isDigit(pick())) {
            return parseConst(isNegative);
        } else if (Character.isAlphabetic(pick())) {
            final ExpressionToString variable = parseVariable();
            return isNegative ? new Negate(variable) : variable;
        } else {
            throw source.error("Variable or constant expexted '" + take() + "' found");
        }
    }

    private ExpressionToString parseVariable() {
        StringBuilder sb = new StringBuilder();
        sb.append(take());
        while (Character.isAlphabetic(pick())) {
            sb.append(take());
        }
        return new Variable(sb.toString());
    }

    private ExpressionToString parseConst(boolean isNegative) {
        StringBuilder sb = new StringBuilder(isNegative ? "-" : "");
        while (Character.isDigit(pick())) {
            sb.append(take());
        }
        return new Const(Integer.parseInt(sb.toString()));
    }
}
