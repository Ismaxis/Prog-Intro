package expression;

public class Multiply extends Operation {
    private static final String op = "*";

    public Multiply(ExpressionToString left, ExpressionToString right) {
        super(left, right, 2, op);
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) * right.evaluate(x);
    }

    @Override
    boolean needRightToShield() {
        if (right instanceof Operation) {
            Operation oper = ((Operation) right);
            return this.prior > oper.prior || oper instanceof Divide;
        }

        return false;
    }

    @Override
    boolean needLeftToShield() {
        if (left instanceof Operation) {
            Operation oper = ((Operation) left);
            return this.prior > oper.prior;
        }

        return false;
    }

    @Override
    public double evaluate(double x) {
        return left.evaluate(x) * right.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return left.evaluate(x, y, z) * right.evaluate(x, y, z);
    }
}
