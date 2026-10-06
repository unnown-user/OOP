package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса {@link Variable}.
 */
class VariableTest {

    @Test
    void printVariable() {
        assertEquals("x", new Variable("x").toString());
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
    void evalThrowsWhenVariableUndefined() {
        Expression e = new Variable("x");
        assertThrows(IllegalArgumentException.class, () -> e.eval("y = 5"));
    }
}