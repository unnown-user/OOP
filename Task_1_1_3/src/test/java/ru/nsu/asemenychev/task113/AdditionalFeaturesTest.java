package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

/**
 * Тесты для дополнительного задания.
 */
public class AdditionalFeaturesTest {

    // Пункт 1 (Дополнительный). Парсинг без скобок.

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

    @Test
    void parserMixedOperations() {
        assertEquals("((2+(3*4))-(5/2))", ExpressionParser.parse("2+3*4-5/2").toString());
    }

    @Test
    void parserLeftAssociativeSub() {
        assertEquals("((10-3)-2)", ExpressionParser.parse("10-3-2").toString());
    }

    // Пункт 2a (Дополнительный). Сворачивание констант.

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
    void simplifyDeepConstantExpression() {
        Expression e = new Mul(
                new Add(new Number(2), new Number(3)),
                new Sub(new Number(4), new Number(1)));
        assertEquals("15", e.simplify().toString());
    }

    @Test
    void simplifyComplexConstantExpression() {
        Expression e = new Sub(
                new Mul(new Add(new Number(1), new Number(2)),
                        new Add(new Number(3), new Number(4))),
                new Number(5));
        assertEquals("16", e.simplify().toString());
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

    // Пункт 2b (Дополнительный). Умножение на 0.

    @Test
    void simplifyMulIfFirstMultiplierIsZero() {
        assertEquals("0", new Mul(new Number(0), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyMulIfSecondMultiplierIsZero() {
        assertEquals("0", new Mul(new Variable("x"), new Number(0)).simplify().toString());
    }

    // Пункт 2c (Дополнительный). Умножение на 1.

    @Test
    void simplifyMulIfFirstMultiplierIsOne() {
        assertEquals("x", new Mul(new Number(1), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyMulIfSecondMultiplierIsOne() {
        assertEquals("x", new Mul(new Variable("x"), new Number(1)).simplify().toString());
    }

    // Пункт 2d (Дополнительный). Вычитание одинаковых.

    @Test
    void simplifySubSame() {
        assertEquals("0", new Sub(new Variable("x"), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifySubSameComplexExpression() {
        Expression inner = new Add(new Variable("x"), new Number(1));
        Expression e = new Sub(inner, inner);
        assertEquals("0", e.simplify().toString());
    }

    @Test
    void simplifySubStructurallyEqual() {
        Expression a = new Mul(new Number(2), new Variable("x"));
        Expression b = new Mul(new Number(2), new Variable("x"));
        assertEquals("0", new Sub(a, b).simplify().toString());
    }

    // Проверка условия "Упрощение не меняет исходное выражение,
    // а создает новое (упрощенное) выражение по заданному".

    @Test
    void simplifyDoesNotModifyOriginal() {
        Expression original = new Add(new Number(2), new Number(3));
        Expression simplified = original.simplify();
        assertEquals("(2+3)", original.toString());
        assertEquals("5", simplified.toString());
        assertNotSame(original, simplified);
    }

    @Test
    void simplifyDoesNotModifyComplexOriginal() {
        Expression original = new Mul(new Number(0), new Variable("x"));
        Expression simplified = original.simplify();
        assertEquals("(0*x)", original.toString());
        assertEquals("0", simplified.toString());
    }
}