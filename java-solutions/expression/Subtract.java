package expression;

public class Subtract extends Operation {
    private static final String op = "-";

    private static final int prior = 1;

    public Subtract(ExpressionToString left, ExpressionToString right) {
        super(left, right, prior, op);
    }

    public Subtract() {
        super(prior, op);
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) - right.evaluate(x);
    }

    @Override
    public int getPrior() {
        return prior;
    }

    @Override
    boolean needRightToShield() {
        if (right instanceof Operation) {
            Operation oper = ((Operation) right);
            return this.prior >= oper.prior;
        }
        return false;
    }

    @Override
    boolean needLeftToShield() {
        return false;
    }

    @Override
    int calc(int left, int right) {
        return left - right;
    }

    @Override
    double calc(double left, double right) {
        return left - right;
    }

}
