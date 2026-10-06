package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для сценариев, где участвуют несколько классов.
 */
class ExpressionTest {

    @Test
    void printComplexExpressionWithMulAndAdd() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(3+(2*x))", e.toString());
    }

    @Test
    void printComplexExpressionWithDivAndSub() {
        Expression e = new Sub(new Number(4), new Div(new Number(5), new Variable("y")));
        assertEquals("(4-(5/y))", e.toString());
    }

    @Test
    void evalComplexExpression() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals(23, e.eval("x = 10; y = 13"));
    }

    @Test
    void derivativeOfAddAndMul() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("(0+((0*x)+(2*1)))", e.derivative("x").toString());
    }

    @Test
    void derivativeOfSubAndDiv() {
        Expression e = new Sub(new Number(4), new Div(new Number(5), new Variable("y")));
        assertEquals("(0-(((0*y)-(5*1))/(y*y)))", e.derivative("y").toString());
    }
}