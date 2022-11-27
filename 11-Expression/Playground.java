import expression.*;

public class Playground {

    public static void main(String[] args) {
        ExpressionToString x = new Variable("x");
        ExpressionToString y = new Variable("y");

        Expression ex1 = new Multiply(
                new Subtract(x, y),
                new Divide(x, y));

        System.out.println(ex1.toString());
        System.out.println(ex1.toMiniString());
    }
}
