package expression;

public class BinaryOperationStorage {
    private static final BinaryOperation[][] storage = {
            null,
            new BinaryOperation[]{new Add(null, null), new Subtract(null, null)},
            new BinaryOperation[]{new Multiply(null, null), new Divide(null, null)},
    };

    public static BinaryOperation[] getClassesWithPriority(int priority) {
        return storage[priority];
    }
}
