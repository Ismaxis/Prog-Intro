package expression;

public class Add extends BinaryOperation {
    private static final String op = "+";
    private static final int prior = 1;

    public Add(ExpressionToString left, ExpressionToString right) {
        super(left, right, prior, op);
    }

    @Override
    protected boolean needRightToShield() {
        return false;
    }

    @Override
    protected boolean needLeftToShield() {
        return false;
    }

    @Override
    protected int calc(int left, int right) {
        return left + right;
    }

    @Override
    protected double calc(double left, double right) {
        return left + right;
    }

}
