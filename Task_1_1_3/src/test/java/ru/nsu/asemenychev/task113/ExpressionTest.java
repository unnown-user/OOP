package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void printNumber() {
        assertEquals("42", new Number(42).toString());
    }

    @Test
    void printVariable() {
        assertEquals("x", new Variable("x").toString());
    }

    @Test
    void printComplexExpression() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(3+(2*x))", e.toString());
    }

    @Test
    void evalComplexExpression() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals(23, e.eval("x = 10; y = 13"));
    }

    @Test
    void evalWithoutAssignmentsForConstant() {
        assertEquals(5, new Number(5).eval(""));
    }

    @Test
    void evalThrowsWhenVariableUndefined() {
        Expression e = new Variable("x");
        assertThrows(IllegalArgumentException.class, () -> e.eval("y = 5"));
    }

    @Test
    void derivativeOfNumberIsZero() {
        assertEquals("0", new Number(5).derivative("x").toString());
    }

    @Test
    void derivativeOfSameVariableIsOne() {
        assertEquals("1", new Variable("x").derivative("x").toString());
    }

    @Test
    void derivativeOfOtherVariableIsZero() {
        assertEquals("0", new Variable("y").derivative("x").toString());
    }

    @Test
    void derivativeOfAdd() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        assertEquals("(1+0)", e.derivative("x").toString());
    }

    @Test
    void derivativeOfSub() {
        Expression e = new Sub(new Variable("x"), new Variable("y"));
        assertEquals("(1-0)", e.derivative("x").toString());
    }

    @Test
    void derivativeOfMul() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals("((1*y)+(x*0))", e.derivative("x").toString());
    }

    @Test
    void derivativeOfDiv() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals("(((1*y)-(x*0))*(y*y))".replace("*(y*y)", "/(y*y)"),
                e.derivative("x").toString());
    }

    @Test
    void fullExampleFromTask() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(0+((0*x)+(2*1)))", e.derivative("x").toString());
    }

    @Test
    void parserBasic() {
        assertEquals("(3+(2*x))", ExpressionParser.parse("(3+(2*x))").toString());
    }

    @Test
    void parserNumber() {
        assertEquals("42", ExpressionParser.parse("42").toString());
    }

    @Test
    void parserVariable() {
        assertEquals("x", ExpressionParser.parse("x").toString());
    }

    @Test
    void parserMultiLetterVariable() {
        assertEquals("alpha", ExpressionParser.parse("alpha").toString());
    }

    @Test
    void parserWithSpaces() {
        assertEquals("(3+(2*x))", ExpressionParser.parse("( 3 + ( 2 * x ) )").toString());
    }

    @Test
    void parserRoundTrip() {
        Expression e = ExpressionParser.parse("((a+b)*(c-d))");
        assertEquals("((a+b)*(c-d))", e.toString());
    }

    @Test
    void simplifyAddZero() {
        assertEquals("x", new Add(new Number(0), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyMulZero() {
        assertEquals("0", new Mul(new Number(0), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyMulOne() {
        assertEquals("x", new Mul(new Number(1), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifySubSame() {
        assertEquals("0", new Sub(new Variable("x"), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyConstants() {
        assertEquals("5", new Add(new Number(2), new Number(3)).simplify().toString());
    }

    @Test
    void simplifyDerivativeOfTask() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("2", e.derivative("x").simplify().toString());
    }

    @Test
    void evalDivByZero() {
        Expression e = new Div(new Number(1), new Number(0));
        assertThrows(ArithmeticException.class, () -> e.eval(""));
    }

    @Test
    void subWorks() {
        assertEquals(7, new Sub(new Number(10), new Number(3)).eval(""));
    }

    @Test
    void divWorks() {
        assertEquals(3, new Div(new Number(10), new Number(3)).eval(""));
    }

    @Test
    void parserThrowsOnMissingParen() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("(3+2"));
    }
}