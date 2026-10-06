package ru.nsu.asemenychev.task113;

/**
 * Демонстрация работы с выражениями.
 */
public class Main {

    /**
     * Запускает примеры из задания.
     */
    public static void main() {
        // Пример из условия: 3 + (2*x)
        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));

        System.out.print("Выражение:     ");
        e.print();

        Expression de = e.derivative("x");
        System.out.print("Производная:   ");
        de.print();

        System.out.print("Упрощённая:    ");
        de.simplify().print();

        int result = e.eval("x = 10; y = 13");
        System.out.println("При x=10:      " + result);

        // Разбор из строки
        Expression parsed = ExpressionParser.parse("(3+(2*x))");
        System.out.print("Из строки:     ");
        parsed.print();
    }
}