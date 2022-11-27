package expression;

public interface ExpressionToString extends Expression {
    void toString(StringBuilder sb);

    void toMiniString(StringBuilder sb, boolean needToShielded);
}
