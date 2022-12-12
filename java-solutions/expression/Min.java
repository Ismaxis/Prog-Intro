package expression;

public class Min extends BinaryOperation {
    private static final String symbol = "min";
    private static final BinaryOperationProperties props = new BinaryOperationProperties(0, true, true);

    public Min(ExpressionToString left, ExpressionToString right) {
        super(left, right, symbol, props);
    }

    @Override
    protected int calc(int left, int right) {
        return Math.min(left, right);
    }

    @Override
    protected double calc(double left, double right) {
        return Math.min(left, right);
    }

    @Override
    public boolean bracketsEqualPriority() {
        return true;
    }
}
