package expression;

public class Divide extends BinaryOperation {
    private static final String op = "/";
    private static final int prior = 2;

    public Divide(ExpressionToString left, ExpressionToString right) {
        super(left, right, prior, op);
    }

    @Override
    protected boolean needRightToShield() {
        return true;
    }

    @Override
    protected boolean needLeftToShield() {
        if (left instanceof BinaryOperation binaryOperation) {
            return prior > binaryOperation.prior;
        }

        return false;
    }

    @Override
    protected int calc(int left, int right) {
        return left / right;
    }

    @Override
    protected double calc(double left, double right) {
        return left / right;
    }
}
