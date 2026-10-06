package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса {@link Div}.
 */
class DivTest {

    @Test
    void derivativeOfDiv() {
        Expression e = new Div(new Variable("x"), new Variable("y"));
        assertEquals("(((1*y)-(x*0))/(y*y))", e.derivative("x").toString());
    }

    @Test
    void evalDivByZero() {
        Expression e = new Div(new Number(1), new Number(0));
        assertThrows(ArithmeticException.class, () -> e.eval(""));
    }

    @Test
    void divWorks() {
        assertEquals(6, new Div(new Number(20), new Number(3)).eval(""));
    }
}