package ru.nsu.asemenychev.Task_1_1_3;

import java.util.Map;

/**
 * Переменная. Имя может быть многобуквенным.
 */
public class Variable extends Expression {

    private final String name;

    /**
     * @param name имя переменной
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * @return имя переменной
     */
    public String getName() {
        return name;
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(name.equals(variable) ? 1 : 0);
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        Integer value = variables.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Переменная не определена: " + name);
        }
        return value;
    }

    @Override
    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
