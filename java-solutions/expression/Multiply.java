package expression;

public class Multiply extends BinaryOperation {
    private static final String symbol = "*";
    private static final int prior = BinaryOperationStorage.getPriority(Multiply.class);
    private static final BinaryOperationProperties props =
            new BinaryOperationProperties(true, true);

    public Multiply(ExpressionToString left, ExpressionToString right) {
        super(left, right, symbol, prior, props);
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
