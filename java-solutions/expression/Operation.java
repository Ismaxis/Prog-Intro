package expression;

abstract public class Operation implements ExpressionToString  {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        toMiniString(sb, false);
        return sb.toString();
    }
}
