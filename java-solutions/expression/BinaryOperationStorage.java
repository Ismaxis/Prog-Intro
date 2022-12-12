package expression;

public class BinaryOperationStorage {
    public static final int minPriority = 0;
    public static final int maxPriority = 2;
    private static final BinaryOperation[][] storage = {
            new BinaryOperation[]{new Min(null, null), new Max(null, null)},
            new BinaryOperation[]{new Add(null, null), new Subtract(null, null)},
            new BinaryOperation[]{new Multiply(null, null), new Divide(null, null)},
    };

    public static BinaryOperation[] getClassesWithPriority(int priority) {
        return storage[priority];
    }

    public static int getPriority(Class<?> cl) {
        int priority = minPriority;

        if (cl == Min.class || cl == Max.class) {
            return priority;
        }
        priority++;


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
