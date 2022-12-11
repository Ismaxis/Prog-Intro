package expression;

public class TZeros extends UnaryOperation {
    private static final String symbol = "t0";

    public TZeros(ExpressionToString child) {
        super(child, symbol);
    }

    @Override
    protected int calc(int value) {
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((value & mask) > 0) {
                return i;
            } else {
                mask <<= 1;
            }
        }
        return 32;
    }

    @Override
    protected double calc(double value) {
        return 0;
    }
}
