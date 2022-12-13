package expression;

public class Count extends UnaryOperation {
    private final static String symbol = "count";

    public Count(ExpressionToString child) {
        super(child, symbol);
    }

    @Override
    protected int calc(int value) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if ((value & (1 << i)) != 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected double calc(double value) {
        throw new RuntimeException("Double count");
    }

}
