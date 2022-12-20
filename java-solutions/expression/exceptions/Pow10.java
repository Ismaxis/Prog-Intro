package expression.exceptions;

import expression.ExpressionToString;
import expression.UnaryOperation;

public class Pow10 extends UnaryOperation {
    private static final String symbol = "pow10";

    public Pow10(ExpressionToString child) {
        super(child, symbol);
    }

    @Override
    protected int calc(int value) {
        if (value < 0) {
            throw new ArithmeticException("INT Argument should be positive: Pow10(" + value + ")");
        } else if (value > 9) {
            throw new ArithmeticException("Overflow '10^" + value + "'");
        }

        int pow = 1;
        for (int i = 0; i < value; i++) {
            pow *= 10;
        }
        return pow;
    }

    @Override
    protected double calc(double value) {
        throw new WrongOperationTypeException("Can't perform CLEAR operation on double value");
    }
}
