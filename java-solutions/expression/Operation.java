package expression;

abstract public class Operation implements ExpressionToString {
    protected ExpressionToString left;
    protected ExpressionToString right;
    protected final int prior;
    protected final String op;

    protected Operation(ExpressionToString left, ExpressionToString right, int prior, String op) {
        this(prior, op);
        this.left = left;
        this.right = right;
    }
    protected Operation(int prior, String op) {
        this.prior = prior;
        this.op = op;
    }

    public void setLeft(ExpressionToString left) {
        this.left = left;
    }
    public void setRight(ExpressionToString right) {
        this.right = right;
    }
    public int getPrior() {
        return prior;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append('(');
        left.toString(sb);
        appendOp(sb);
        right.toString(sb);
        sb.append(')');

    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        toMiniString(sb, false);
        return sb.toString();
    }

    @Override
    public void toMiniString(StringBuilder sb, boolean needToShielded) {
        if (needToShielded) {
            sb.append('(');
        }
        left.toMiniString(sb, needLeftToShield());
        appendOp(sb);
        right.toMiniString(sb, needRightToShield());
        if (needToShielded) {
            sb.append(')');
        }
    }

    abstract boolean needLeftToShield();

    abstract boolean needRightToShield();

    abstract int calc(int left, int right);

    abstract double calc(double left, double right);

    @Override
    public int evaluate(int x) {
        return calc(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calc(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public double evaluate(double x) {
        return calc(left.evaluate(x), right.evaluate(x));
    }

    private void appendOp(StringBuilder sb) {
        sb.append(' ');
        sb.append(op);
        sb.append(' ');
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Operation) {
            Operation oper = ((Operation) obj);
            boolean leftEq = (left == null) ? oper.left == null : left.equals(oper.left);
            boolean rightEq = (right == null) ? oper.right == null : right.equals(oper.right);
            return this.getClass() == oper.getClass() && leftEq && rightEq;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int leftHash = (left == null) ? 0 : left.hashCode();
        int rightHash = (right == null) ? 0 : right.hashCode();
        return (((this.getClass().hashCode() * 67) + leftHash) * 67 + rightHash) * 67;
    }
}
