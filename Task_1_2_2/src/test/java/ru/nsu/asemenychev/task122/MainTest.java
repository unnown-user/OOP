package ru.nsu.asemenychev.task122;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Тесты для проверки работы демонстрационного класса {@link Main}.
 */
class MainTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Перенаправляем System.out в буфер перед каждым тестом
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        // Возвращаем стандартный поток вывода после теста
        System.setOut(standardOut);
    }

    @Test
    void testMainExecution() {
        // Вызываем метод main напрямую
        Main.main(new String[]{});

        // Получаем весь текст, который программа вывела в консоль
        String output = outputStreamCaptor.toString();

        // Проверяем, что вывод содержит все ожидаемые фрагменты из задания
        assertTrue(output.contains("1.0"), "Должен быть выведен результат get(\"one\")");
        assertTrue(output.contains("Таблица: {"), "Должен быть выведен toString таблицы");
        assertTrue(output.contains("Итерация:"), "Должен быть вывод работы итератора");
        assertTrue(output.contains("Поймано исключение:"),
                "Должно быть поймано ConcurrentModificationException");
    }
}