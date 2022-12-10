package expression;

public class Multiply extends BinaryOperation {
    private static final String symbol = "*";
    private static final int prior = 2;
    private static final BinaryOperationProperties props =
            new BinaryOperationProperties(prior, true, true);


    public Multiply(ExpressionToString left, ExpressionToString right) {
        super(left, right, symbol, props);
    }

    @Override
    protected int calc(int left, int right) {
        return left * right;
    }

    @Override
    protected double calc(double left, double right) {
        return left * right;
    }

}
