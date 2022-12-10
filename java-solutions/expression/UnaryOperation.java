package expression;

abstract public class UnaryOperation extends Operation {
    protected final ExpressionToString child;
    private final String symbol;

    protected UnaryOperation(ExpressionToString child, String symbol) {
        this.child = child;
        this.symbol = symbol;
    }

    abstract protected int calc(int value);

    abstract protected double calc(double value);

    @Override
    public int evaluate(int x) {
        return calc(child.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return calc(child.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calc(child.evaluate(x, y, z));
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append(symbol).append("(");
        child.toString(sb);
        sb.append(")");
    }

    @Override
    public void toMiniString(StringBuilder sb, boolean needToShielded) {
        boolean shieldNeeded = needChildToBeShielded();
        sb.append(symbol).append(shieldNeeded ? "" : " ");
        child.toMiniString(sb, shieldNeeded);
    }

    private boolean needChildToBeShielded() {
        return child instanceof BinaryOperation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryOperation that) {
            return this.getClass() == that.getClass() && this.child.equals(that.child);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 67 * (67 * symbol.hashCode() + child.hashCode());
    }
}
