package expression;

public class Negate implements ExpressionToString {
    private final ExpressionToString child;
    private final static String miniOpenTag = "-";
    private final static String openTag = "-(";
    private final static String closeTag = ")";
    public Negate(ExpressionToString child) {
        this.child = child;
    }

    @Override
    public int evaluate(int x) {
        return -1 * child.evaluate(x);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append(openTag);
        child.toString(sb);
        sb.append(closeTag);
    }

    @Override
    public void toMiniString(StringBuilder sb, boolean needToShielded) {
        sb.append(miniOpenTag);
        child.toMiniString(sb, true);
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        toMiniString(sb, false);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Negate that) {
            return this.child.equals(that.child);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 67*(67*'-' + child.hashCode());
    }

    @Override
    public double evaluate(double x) {
        return -1.0 * child.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -1 * child.evaluate(x, y, z);
    }
}
