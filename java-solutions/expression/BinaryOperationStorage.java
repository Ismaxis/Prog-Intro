package expression;

public class BinaryOperationStorage {
    public static final int minPriority = 1;
    public static final int maxPriority = 2;
    private static final BinaryOperation[][] storage = {
            null,
            new BinaryOperation[]{new Add(null, null), new Subtract(null, null)},
            new BinaryOperation[]{new Multiply(null, null), new Divide(null, null)},
    };

    public static BinaryOperation[] getClassesWithPriority(int priority) {
        return storage[priority];
    }

    public static int getPriority(Class<?> cl) {
        int priority = minPriority;

        if (cl == Add.class || cl == Subtract.class) {
            return priority;
        }
        priority++;

        if (cl == Multiply.class || cl == Divide.class) {
            return priority;
        }

        throw new RuntimeException("Priority is not defined for class '" + cl + "'");
    }
}
