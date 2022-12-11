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

        final TripleExpression err1 = parser.parse("((((y + -4) / x) / y) + (x * ( x * (30 / y))))");
    }

    @Test
    public void testSequential() {
        final TripleExpression addAndSub = parser.parse("(x + y) + (y + -x) - x");
        int x = 102378;
        int y = 95834;
        int z = -190123;
        Assertions.assertEquals(sumSubExpr(x, y, z), addAndSub.evaluate(x, y, z));

        final TripleExpression multAndDiv = parser.parse("x * y / z");
        Assertions.assertEquals(multDivExpr(x, y, z), multAndDiv.evaluate(x, y, z));
    }

    private static int sumSubExpr(int x, int y, int z) {
        return 2 * y - x;
    }

    private static int multDivExpr(int x, int y, int z) {
        return x * y / z;
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

        final ExpressionToString given5 = new Negate(new Add(new Variable("x"), new Variable("x")));
        Assertions.assertEquals(given5, parser.parse(given5.toMiniString()));

        final String negZero = "-(0)";
        Assertions.assertEquals(negZero, parser.parse(negZero).toString());
    }

    @Test
    public void testNegateMini() {
        final String mini1 = "- 0";
        Assertions.assertEquals(mini1, parser.parse(mini1).toMiniString());

        final ExpressionToString mini2 = new Negate(new Multiply(new Variable("x"), new Variable("y")));
        Assertions.assertEquals(mini2.toMiniString(), parser.parse(mini2.toMiniString()).toMiniString());
    }

    @Test
    public void testNegateConst() {
        final ExpressionToString negateOfConst = new Negate(new Const(2147483647));
        final ExpressionToString negConst = new Const(-2147483647);
        Assertions.assertEquals("- 2147483647", negateOfConst.toMiniString());
        Assertions.assertEquals("-(2147483647)", negateOfConst.toString());
        Assertions.assertEquals("-2147483647", negConst.toString());
    }

    @Test
    public void testMinMax() {
        final ExpressionToString min = new Min(new Variable("x"), new Const(3));
        Assertions.assertEquals(3, min.evaluate(10));
        valid(min);

        final ExpressionToString max = new Max(new Variable("x"), new Const(-1000));
        Assertions.assertEquals(10, max.evaluate(10));
        valid(max);
    }

    private void valid(ExpressionToString expected) {
        Assertions.assertEquals(expected, parser.parse(expected.toString()));
    }
}