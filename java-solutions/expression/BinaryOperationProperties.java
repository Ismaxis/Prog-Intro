package expression;

public class BinaryOperationProperties {
    public final int priority;
    public final boolean isCommutative;
    public final boolean isAssociative;

    public BinaryOperationProperties(int priority, boolean isCommutative, boolean isAssociative) {
        this.priority = priority;
        this.isCommutative = isCommutative;
        this.isAssociative = isAssociative;
    }
}
