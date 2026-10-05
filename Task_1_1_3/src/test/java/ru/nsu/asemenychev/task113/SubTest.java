package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса {@link Sub}.
 */
class SubTest {

    @Test
    void derivativeOfSub() {
        Expression e = new Sub(new Variable("x"), new Variable("y"));
        assertEquals("(1-0)", e.derivative("x").toString());
    }

    @Test
    void subWorks() {
        assertEquals(8, new Sub(new Number(11), new Number(3)).eval(""));
    }
}