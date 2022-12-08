package expression.parser.tests;

import expression.*;
import expression.parser.ExpressionParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpressionParserTest {
    ExpressionParser parser = new ExpressionParser();
    @Test
    public void testConst() {
        Assertions.assertEquals(new Const(5), parser.parse("5"));
        Assertions.assertEquals(new Const(5), parser.parse("   5 "));
        Assertions.assertEquals(new Const(228), parser.parse("\t\t228\n\n"));
    }

    @Test
    public void testWhiteSpace() {
        final ExpressionToString given = new Add(new Variable("x"), new Const(2));

        Assertions.assertEquals(given, parser.parse("x+2"));
        Assertions.assertEquals(given, parser.parse("x + 2"));
        Assertions.assertEquals(given, parser.parse("x    +  2"));
        Assertions.assertEquals(given, parser.parse("x\n+\n2"));
    }
    @Test
    public void testMultipleOperations() {
        final ExpressionToString given1 = new Multiply(
                new Add(
                        new Variable("x"),
                        new Const(2)),
                new Variable("y"));
        valid(given1);

        final ExpressionToString given2 = new Add(
                new Multiply(
                        new Add(
                                new Variable("x"),
                                new Const(2)),
                        new Divide(
                                new Const(1),
                                new Const(4))
                ),
                new Subtract(
                        new Const(5),
                        new Divide(
                                new Variable("y"),
                                new Variable("z"))));
        valid(given2);
    }

    @Test
    public void testNegate() {
        final ExpressionToString given1 = new Negate(new Const(5));
        valid(given1);

        final ExpressionToString given2 = new Negate(new Variable("x"));
        valid(given2);

        final ExpressionToString given3 = new Negate(new Variable("x"));
        valid(given3);

        final ExpressionToString given4 = new Add(new Negate(new Const(1)), new Const(0));
        valid(given4);
    }

    @Test
    public void tempNegateTest() {
        final ExpressionToString given1 = new Negate(new Const(2147483648L));
        Assertions.assertEquals("-2147483648", given1.toMiniString());
        Assertions.assertEquals("-2147483648", given1.toString());
    }

    private void valid(ExpressionToString expected) {
        Assertions.assertEquals(expected, parser.parse(expected.toString()));
    }
}