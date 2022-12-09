package expression;

public class Negate implements ExpressionToString {
    private final ExpressionToString child;
    private final String openTag;
    private final String closeTag;

    public Negate(ExpressionToString child, boolean brackets) {
        this.child = child;
        if (brackets) {
            this.openTag = "-(";
            this.closeTag = ")";
        } else {
            this.openTag = "-";
            this.closeTag = "";
        }
    }

    public Negate(ExpressionToString child) {
        this(child, false);
    }

    @Override
    public int evaluate(int x) {
        return -1 * child.evaluate(x);
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append(openTag);
        child.toString(sb);
        sb.append(closeTag);
    }

    @Override
    public void toMiniString(StringBuilder sb, boolean needToShielded) {
        sb.append("-");
        child.toMiniString(sb, true);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
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
