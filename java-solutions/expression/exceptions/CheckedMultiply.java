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
        if (((Math.abs(left) | Math.abs(right)) >>> 15 != 0)) {
            if (((right != 0) && (r / right != left)) ||
                    (left == Integer.MIN_VALUE && right == -1)) {
                throw new ArithmeticException("Overflow " + left + " * " + right);
            }
        }
        return r;

//        if (left != 0 && right != 0) {
//            if (right > Integer.MAX_VALUE / left) {
//                throw new ArithmeticException("Overflow " + left + " * " + right);
//            } else if (right < Integer.MIN_VALUE / left) {
//                throw new ArithmeticException("Overflow " + left + " * " + right);
//            }
//        }
//        return super.calc(left, right);

//        if (left == 0 || right == 0) {
//            return 0;
//        }
//        if ((left ^ right) >= 0) {
//            if (right > 0) {
//                if (left > Integer.MAX_VALUE / right) {
//                    throw new ArithmeticException("Overflow " + left + " * " + right);
//                }
//            } else {
//                if (left < Integer.MAX_VALUE / right) {
//                    throw new ArithmeticException("Overflow " + left + " * " + right);
//                }
//            }
//        } else {
//            if (right > 0) {
//                if (left < Integer.MIN_VALUE / right) {
//                    throw new ArithmeticException("Overflow " + left + " * " + right);
//                }
//            } else {
//                if (right > Integer.MIN_VALUE / left) {
//                    throw new ArithmeticException("Overflow " + left + " * " + right);
//                }
//            }
//        }
    }
}
