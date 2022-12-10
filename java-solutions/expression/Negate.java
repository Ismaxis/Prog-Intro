package expression;

public class Negate extends UnaryOperation {
    private final static String symbol = "-";
    public Negate(ExpressionToString child) {
        super(child, symbol);
    }


    @Override
    protected int calc(int value) {
        return -1 * value;
    }

    @Override
    protected double calc(double value) {
        return -1.0 * value;
    }
}
