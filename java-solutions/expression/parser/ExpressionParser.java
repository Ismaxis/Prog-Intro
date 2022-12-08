package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements TripleParser {
    @Override
    public TripleExpression parse(final String expression) {
        source = new StringCharSource(expression);
        take();

        ExpressionToString parsed = parseExpression();
        return parsed;
    }

    private ExpressionToString parseExpression() {

        // LEFT
        skipWhitespace();
        final ExpressionToString left;
        if (take('(')) {
            left = parseExpression();
            take();
        } else if (Character.isDigit(pick())) {
            left =  parseConst();
        } else if (take('-')) {
            final ExpressionToString f = parseExpression();
            left = new Negate(f);
        } else if (Character.isAlphabetic(pick())) {
            left = parseVariable();
        } else {
            throw source.error("Operand expexted '" + take() + "' found");
        }


        // OP
        skipWhitespace();
        final Operation operation = parseOperation();
        if (operation != null) {
            // RIGHT
            skipWhitespace();
            final ExpressionToString right = parseExpression();

            operation.setLeft(left);
            operation.setRight(right);

            return operation;
        } else {
            return left;
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
