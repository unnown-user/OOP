package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса {@link Number}.
 */
class NumberTest {

    @Test
    void printNumber() {
        assertEquals("42", new Number(42).toString());
    }

    @Test
    void evalWithoutAssignmentsForConstant() {
        assertEquals(5, new Number(5).eval(""));
    }

    @Test
    void derivativeOfNumberIsZero() {
        assertEquals("0", new Number(5).derivative("x").toString());
    }
}