package expression;

public class Add extends Operation {
    private static final String op = "+";
    private static final int prior = 1;

    public Add(ExpressionToString left, ExpressionToString right) {
        super(left, right, prior, op);
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) + right.evaluate(x);
    }

    @Override
    boolean needRightToShield() {
        return false;
    }

    @Override
    boolean needLeftToShield() {
        return false;
    }

    @Override
    public double evaluate(double x) {
        return left.evaluate(x) + right.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return left.evaluate(x, y, z) + right.evaluate(x, y, z);
    }

    @Override
    int calc(int left, int right) {
        return left + right;
    }

    @Override
    double calc(double left, double right) {
        return left + right;
    }

}
