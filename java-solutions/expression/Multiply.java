package expression;

public class Multiply extends BinaryOperation {
    private static final String op = "*";
    private static final int prior = 2;

    public Multiply(ExpressionToString left, ExpressionToString right) {
        super(left, right, prior, op);
    }

    @Override
    protected boolean needRightToShield() {
        if (right instanceof BinaryOperation binaryOperation) {
            return prior > binaryOperation.prior || binaryOperation instanceof Divide;
        }

        return false;
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
        return left * right;
    }

    @Override
    protected double calc(double left, double right) {
        return left * right;
    }

}
