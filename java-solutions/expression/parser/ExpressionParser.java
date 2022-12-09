package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements TripleParser {
    private boolean bracketsMet;
    @Override
    public TripleExpression parse(final String expression) {
        source = new StringCharSource(expression);
        take();
        return expr();
    }

    private ExpressionToString expr() {
        ExpressionToString left = term();

        while(true) {
            skipWhitespace();
            if (take('+')) {
                left = new Add(left, term());
            } else if (take('-')) {
                left = new Subtract(left, term());
            } else {
                return left;
            }
        }
    }

    private ExpressionToString term() {
        ExpressionToString left = prim();

        while(true) {
            skipWhitespace();
            if (take('*')) {
                left = new Multiply(left, prim());
            } else if (take('/')) {
                left = new Divide(left, prim());
            } else {
                return left;
            }
        }
    }

    private ExpressionToString prim() {
        skipWhitespace();
        if (take('(')) {
            final ExpressionToString res = expr();
            skipWhitespace();
            expect(')');
            bracketsMet = true;
            return res;
        } else if (take('-')) {
            bracketsMet = false;
            final ExpressionToString neg = new Negate(prim(), bracketsMet);
            bracketsMet = false;
            return neg;
        } else {
            return parseSingle();
        }
    }

    private ExpressionToString parseSingle() {
        skipWhitespace();
        if (Character.isDigit(pick())) {
            return parseConst();
        } else if (Character.isAlphabetic(pick())) {
            return parseVariable();
        } else {
            throw source.error("Variable or constant expexted '" + take() + "' found");
        }
    }
    private ExpressionToString parseVariable() {
        StringBuilder sb = new StringBuilder();
        sb.append(take());
        while(Character.isAlphabetic(pick())) {
            sb.append(take());
        }
        return new Variable(sb.toString());
    }

    private Operation parseOperation() {
        if (take('+')) {
            return new Add();
        } else if (take('-')) {
            return new Subtract();
        } else if (take('*')) {
            return new Multiply();
        } else if (take('/')) {
            return new Divide();
        } else {
            return null;
        }
    }

    private ExpressionToString parseConst() {
        StringBuilder sb = new StringBuilder();
        while (Character.isDigit(pick())) {
            sb.append(take());
        }

        Number num;
        try {
            num = Integer.parseInt(sb.toString());
            return new Const(num.intValue());
        } catch (NumberFormatException e) {
            num = Long.parseLong(sb.toString());
            return new Const(num.longValue());
        }
    }

    private void skipWhitespace() {
        while(Character.isWhitespace(pick())) {
            take();
        }
    }
}
