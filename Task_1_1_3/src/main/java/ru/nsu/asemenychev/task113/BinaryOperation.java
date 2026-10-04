package ru.nsu.asemenychev.task113;

import java.util.Map;

/**
 * Абстрактная бинарная операция над двумя выражениями.
 * Хранит левый и правый операнды и символ операции.
 */
public abstract class BinaryOperation extends Expression {

    protected final Expression left;
    protected final Expression right;
    private final String symbol;

    /**
     * Создаёт бинарную операцию с двумя операндами и символом операции.
     *
     * @param left   левый операнд
     * @param right  правый операнд
     * @param symbol символ операции для печати
     */
    protected BinaryOperation(Expression left, Expression right, String symbol) {
        this.left = left;
        this.right = right;
        this.symbol = symbol;
    }

    @Override
    public int eval(Map<String, Integer> variables) {
        return apply(left.eval(variables), right.eval(variables));
    }

    /**
     * Применяет операцию к двум числам.
     *
     * @param a левый операнд
     * @param b правый операнд
     * @return результат
     */
    protected abstract int apply(int a, int b);

    @Override
    public String toString() {
        return "(" + left + symbol + right + ")";
    }
}