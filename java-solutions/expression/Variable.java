package expression;

public class Variable implements ExpressionToString {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public void toString(StringBuilder sb) {
        sb.append(name);
    }

    @Override
    public void toMiniString(StringBuilder sb, boolean needToShielded) {
        sb.append(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            Variable var = ((Variable) obj);
            return name.equals(var.name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
