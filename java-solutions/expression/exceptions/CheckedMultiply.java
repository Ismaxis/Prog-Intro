package expression.exceptions;

import expression.ExpressionToString;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(ExpressionToString left, ExpressionToString right) {
        super(left, right);
    }

    @Override
    protected int calc(int left, int right) {
        int r = left * right;
		// :NOTE: почему именно 15? Немного не хватает комментариев и константы
        if (((Math.abs(left) | Math.abs(right)) >>> 15 != 0)) {
            if (((right != 0) && (r / right != left)) ||
                    (left == Integer.MIN_VALUE && right == -1)) {
                throw new IntOverflowException(symbol, left, right);
            }
        }
        return r;
    }
}
