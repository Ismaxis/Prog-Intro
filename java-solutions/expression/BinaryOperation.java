package expression;

import java.util.Objects;

abstract public class BinaryOperation extends Operation {
    protected final ExpressionToString left;
    protected final ExpressionToString right;
    protected final String symbol;
    protected final BinaryOperationProperties props;

    protected BinaryOperation(ExpressionToString left, ExpressionToString right, String symbol, BinaryOperationProperties props) {
        this.left = left;
        this.right = right;
        this.symbol = symbol;
        this.props = props;
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

    protected boolean needRightToShield() {
        if (right instanceof BinaryOperation binaryOperation) {
            return binaryOperation.props.priority < props.priority ||
                    binaryOperation.props.priority == props.priority && !(this.props.isCommutative && binaryOperation.props.isAssociative);
        }
        return false;
    }

    protected boolean needLeftToShield() {
        if (left instanceof BinaryOperation binaryOperation) {
            return binaryOperation.props.priority < props.priority;
        }
        return false;
    }

    abstract protected int calc(int left, int right);

    abstract protected double calc(double left, double right);

    @Override
    public int evaluate(int x) {
        return calc(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return calc(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calc(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    private void appendOp(StringBuilder sb) {
        sb.append(' ');
        sb.append(symbol);
        sb.append(' ');
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation that) {
            boolean leftEq = Objects.equals(left, that.left);
            boolean rightEq = Objects.equals(right, that.right);
            return this.getClass() == that.getClass() && leftEq && rightEq;
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
