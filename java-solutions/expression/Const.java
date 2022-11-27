package expression;

public class Const implements ExpressionToString {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int x) {
        return value;
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append(value);
    }

    @Override
    public void toMiniString(StringBuilder sb, boolean needToShielded) {
        sb.append(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            Const con = ((Const) obj);
            return value == con.value;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
