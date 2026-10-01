package ru.nsu.asemenychev.task113;

import java.util.Map;

/**
 * Числовая константа.
 */
public class Number extends Expression {

    private final int value;

    /**
     * @param value значение константы
     */
    public Number(int value) {
        this.value = value;
    }

    /**
     * @return значение константы
     */
    public int getValue() {
        return value;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return value;
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
