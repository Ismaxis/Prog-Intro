package expression;

public class Max extends BinaryOperation {
    private static final String symbol = "max";
    private static final BinaryOperationProperties props = new BinaryOperationProperties(0, true, true);

    public Max(ExpressionToString left, ExpressionToString right) {
        super(left, right, symbol, props);
    }

    @Override
    protected int calc(int left, int right) {
        return Math.max(left, right);
    }

    @Override
    protected double calc(double left, double right) {
        return Math.max(left, right);
    }

    @Override
    public boolean bracketsEqualPriority() {
        return true;
    }
}
