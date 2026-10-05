package ru.nsu.asemenychev.task113;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для класса {@link Mul}.
 */
class MulTest {

    @Test
    void derivativeOfMul() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals("((1*y)+(x*0))", e.derivative("x").toString());
    }

    @Test
    void mulWorks() {
        assertEquals(15, new Mul(new Number(5), new Number(3)).eval(""));
    }
}