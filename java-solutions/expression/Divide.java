package expression;

public class Divide extends Operation {
    private static final String op = "/";
    private static final int prior = 2;

    public Divide(ExpressionToString left, ExpressionToString right) {
        super(left, right, prior, op);
    }

    public Divide() {
        super(prior, op);
    }

    @Override
    boolean needRightToShield() {
        return true;
    }

    @Override
    boolean needLeftToShield() {
        if (left instanceof Operation oper) {
            return prior > oper.prior;
        }

        return false;
    }

    @Override
    int calc(int left, int right) {
        return left / right;
    }

    @Override
    double calc(double left, double right) {
        return left / right;
    }

}
