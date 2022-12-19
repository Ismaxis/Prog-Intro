package expression;

import java.lang.invoke.WrongMethodTypeException;

public class Clear extends BinaryOperation {
    private static final String symbol = "clear";
    private static final int prior = 0;
    private static final BinaryOperationProperties props = new BinaryOperationProperties(false, true);

    public Clear(ExpressionToString left, ExpressionToString right) {
        super(left, right, symbol, prior, props);
    }

    @Override
    protected int calc(int left, int right) {
        return left & ~(1 << right);
    }

    @Override
    protected double calc(double left, double right) {
        throw new WrongMethodTypeException("Can't perform CLEAR operation on double value");
    }

}
