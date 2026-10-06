package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса {@link Add}.
 */
class AddTest {

    @Test
    void derivativeOfAdd() {
        Expression e = new Add(new Variable("x"), new Variable("y"));
        assertEquals("(1+0)", e.derivative("x").toString());
    }

    @Test
    void simplifyAddIfFirstSummandIsZero() {
        assertEquals("x", new Add(new Number(0), new Variable("x")).simplify().toString());
    }

    @Test
    void simplifyAddIfSecondSummandIsZero() {
        assertEquals("x", new Add(new Variable("x"), new Number(0)).simplify().toString());
    }

    @Test
    void addWorks() {
        assertEquals(9, new Add(new Number(2), new Number(7)).eval(""));
    }
}