package expression;

public interface ExpressionToString extends Expression, DoubleExpression, TripleExpression {
    void toString(StringBuilder sb);

    void toMiniString(StringBuilder sb, boolean needToShielded);
}
