package ru.nsu.asemenychev.Task_1_1_3;

import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактное математическое выражение.
 * Базовый класс иерархии: константы, переменные,
 * арифметические операции над выражениями.
 */
public abstract class Expression {

    /**
     * Печатает выражение в стандартный вывод.
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * Создаёт новое выражение — производную по указанной переменной.
     * Исходное выражение не изменяется.
     *
     * @param variable имя переменной, по которой берётся производная
     * @return новое выражение, представляющее производную
     */
    public abstract Expression derivative(String variable);

    /**
     * Вычисляет значение выражения по означиванию переменных.
     * Строка вида "x = 10; y = 13".
     *
     * @param assignments строка с означиванием переменных
     * @return целочисленный результат
     */
    public int eval(String assignments) {
        return eval(parseAssignments(assignments));
    }

    /**
     * Вычисляет значение выражения по готовой карте значений переменных.
     *
     * @param variables карта "имя переменной → значение"
     * @return целочисленный результат
     */
    public abstract int eval(Map<String, Integer> variables);

    /**
     * Создаёт упрощённое выражение по правилам:
     * константные подвыражения сворачиваются, x*0 → 0, x*1 → x, x−x → 0.
     * Исходное выражение не изменяется.
     *
     * @return новое упрощённое выражение
     */
    public Expression simplify() {
        return this;
    }

    /**
     * Разбирает строку означивания в карту переменных.
     *
     * @param assignments строка вида "x = 10; y = 13"
     * @return карта имя → значение
     */
    protected static Map<String, Integer> parseAssignments(String assignments) {
        Map<String, Integer> variables = new HashMap<>();
        if (assignments == null || assignments.isBlank()) {
            return variables;
        }
        for (String pair : assignments.split(";")) {
            String[] parts = pair.split("=");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Некорректное означивание: " + pair);
            }
            variables.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
        return variables;
    }

    /**
     * Проверяет, является ли выражение числом с указанным значением.
     *
     * @param e     проверяемое выражение
     * @param value ожидаемое значение
     * @return {@code true}, если это число с нужным значением
     */
    protected static boolean isNumber(Expression e, int value) {
        return e instanceof Number && ((Number) e).getValue() == value;
    }

    /**
     * Возвращает числовое значение выражения, если оно является числом.
     *
     * @param e проверяемое выражение
     * @return значение или {@code null}, если выражение не является числом
     */
    protected static Integer asNumber(Expression e) {
        return e instanceof Number ? ((Number) e).getValue() : null;
    }
}
