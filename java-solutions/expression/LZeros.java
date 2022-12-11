package expression;

public class LZeros extends UnaryOperation {
    private static final String symbol = "l0";

    public LZeros(ExpressionToString child) {
        super(child, symbol);
    }

    @Override
    protected int calc(int value) {
        int mask = 1 << 31;
        for (int i = 0; i < 32; i++) {
            if ((value & mask) > 0) {
                return i;
            } else {
                mask >>= 1;
            }
        }
        return 0;
    }

    @Override
    protected double calc(double value) {
        return 0;
    }
}
