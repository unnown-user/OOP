package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    // ---Печать (toString)---

    @Test
    void printNumber() {
        assertEquals("42", new Number(42).toString());
    }

    @Test
    void printVariable() {
        assertEquals("x", new Variable("x").toString());
    }

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

    // ---Вычисление (eval)---

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

    // ---Вычисление производных---

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
        assertEquals("(((1*y)-(x*0))/(y*y))", e.derivative("x").toString());
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

    // ---Парсинг---

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
    void parserWithoutParensPrecedence() {
        assertEquals("(2+(3*4))", ExpressionParser.parse("2+3*4").toString());
    }

    @Test
    void parserWithoutParensLeftAssociative() {
        assertEquals("((1+2)+3)", ExpressionParser.parse("1+2+3").toString());
    }

    @Test
    void parserWithParensOverrides() {
        assertEquals("((2+3)/4)", ExpressionParser.parse("(2+3)/4").toString());
    }

    // ---Упрощение выражений---

    @Test
    void simplifyMulIfFirstSummandIsZero() {
        assertEquals("x", new Add(new Number(0), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyMulIfSecondSummandIsZero() {
        assertEquals("x", new Add(new Variable("x"), new Number(0)).simplify().toString());
    }

    @Test
    void simplifyMulIfFirstMultiplierIsZero() {
        assertEquals("0", new Mul(new Number(0), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyMulIfSecondMultiplierIsZero() {
        assertEquals("0", new Mul(new Variable("x"), new Number(0)).simplify().toString());
    }

    @Test
    void simplifyMulIfFirstMultiplierIsOne() {
        assertEquals("x", new Mul(new Number(1), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyMulIfSecondMultiplierIsOne() {
        assertEquals("x", new Mul(new Variable("x"), new Number(1)).simplify().toString());
    }

    @Test
    void simplifySubSame() {
        assertEquals("0", new Sub(new Variable("x"), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyAddConstants() {
        assertEquals("5", new Add(new Number(2), new Number(3)).simplify().toString());
    }

    @Test
    void simplifySubConstants() {
        assertEquals("6", new Sub(new Number(10), new Number(4)).simplify().toString());
    }

    @Test
    void simplifyMulConstants() {
        assertEquals("16", new Mul(new Number(4), new Number(4)).simplify().toString());
    }

    @Test
    void simplifyDivConstants() {
        assertEquals("3", new Div(new Number(9), new Number(3)).simplify().toString());
    }

    @Test
    void simplifyDerivativeOfAddAndMul() {
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        assertEquals("2", e.derivative("x").simplify().toString());
    }

    @Test
    void simplifyDerivativeOfSubAndDiv() {
        Expression e = new Sub(new Number(4), new Div(new Number(5), new Variable("y")));
        assertEquals("(0-(-5/(y*y)))", e.derivative("y").simplify().toString());
    }

    // ---Отслеживание деления на ноль---

    @Test
    void evalDivByZero() {
        Expression e = new Div(new Number(1), new Number(0));
        assertThrows(ArithmeticException.class, () -> e.eval(""));
    }

    // ---Проверка работы арифметических выражений---

    @Test
    void addWorks() {
        assertEquals(9, new Add(new Number(2), new Number(7)).eval(""));
    }

    @Test
    void subWorks() {
        assertEquals(8, new Sub(new Number(11), new Number(3)).eval(""));
    }
    @Test
    void mulWorks() {
        assertEquals(15, new Mul(new Number(5), new Number(3)).eval(""));
    }

    @Test
    void divWorks() {
        assertEquals(6, new Div(new Number(20), new Number(3)).eval(""));
    }

    // ---Отслеживание непарности скобок---

    @Test
    void parserThrowsOnMissingClosingParen() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("(3+2*4/6-1"));
    }

    @Test
    void parserThrowsOnMissingOpeningParen() {
        assertThrows(IllegalArgumentException.class, () -> ExpressionParser.parse("3+2*4/6-1)"));
    }
}