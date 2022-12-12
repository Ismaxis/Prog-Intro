package expression;

public class Max extends BinaryOperation {
    private static final String symbol = "max";
    private static final int prior = BinaryOperationStorage.getPriority(Max.class);
    private static final BinaryOperationProperties props =
            new BinaryOperationProperties(true, true);

    public Max(ExpressionToString left, ExpressionToString right) {
        super(left, right, symbol, prior, props);
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
