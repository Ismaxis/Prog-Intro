package expression;

public class Subtract extends Operation {
    private static final String op = "-";

    public Subtract(ExpressionToString left, ExpressionToString right) {
        super(left, right, 1, op);
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
    public double evaluate(double x) {
        return left.evaluate(x) - right.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return left.evaluate(x, y, z) - right.evaluate(x, y, z);
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
