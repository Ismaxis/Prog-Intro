package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements TripleParser {

    @Override
    public TripleExpression parse(final String expression) {
        source = new StringCharSource(expression);
        take();
        return parseTopLevel();
    }

    private ExpressionToString parseTopLevel() {
        return parsePriority(BinaryOperationStorage.minPriority);
    }

    private ExpressionToString parsePriority(int priority) {
        if (priority > BinaryOperationStorage.maxPriority) {
            return parseFactor();
        }
        ExpressionToString left = parsePriority(priority + 1);

        BinaryOperation[] operations = BinaryOperationStorage.getClassesWithPriority(priority);

        main:
        while (true) {
            skipWhitespace();

            for (BinaryOperation operation : operations) {
                if (take(operation.getSymbol())) {
                    left = operation.createInstance(left, parsePriority(priority + 1));
                    continue main;
                }
            }
            return left;
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
            if (take('(')) {
                final ExpressionToString neg = new Negate(parseTopLevel());
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
