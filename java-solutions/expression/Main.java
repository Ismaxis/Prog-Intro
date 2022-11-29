package expression;

public class Main {
    public static void main(String[] args) {
        Expression ex1 = new Add(new Multiply(new Variable("x"), new Variable("x")),
                new Add(new Multiply(new Const(-2), new Variable("x")), new Const(1)));
        System.out.println(ex1.evaluate(Integer.parseInt(args[0])));
        System.out.println(ex1.toMiniString());
    }
}
