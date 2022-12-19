package expression.exceptions;

import expression.Divide;
import expression.ExpressionToString;
import expression.TripleExpression;
import expression.Variable;

public class CheckedDivide extends Divide {
    public CheckedDivide(ExpressionToString left, ExpressionToString right) {
        super(left, right);
    }

    @Override
    protected int calc(int left, int right) {
        if (left == Integer.MIN_VALUE && right == -1) {
            throw new ArithmeticException("Overflow " + left + " / " + right);
        }
        return super.calc(left, right);
    }
}
