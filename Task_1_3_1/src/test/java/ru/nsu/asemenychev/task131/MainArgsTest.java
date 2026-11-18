package ru.nsu.asemenychev.task131;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты для проверки логики обработки аргументов и ошибок в классе {@link Main}.
 * Для проверки больших файлов класс {@link MainTest}.
 */
class MainArgsTest {

    @TempDir
    Path tempDir;

    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;
    private final ByteArrayOutputStream outCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outCaptor));
        System.setErr(new PrintStream(errCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        System.setErr(standardErr);
    }

    @Test
    void testMainWithoutArguments() {
        Main.main(new String[]{});

        String output = outCaptor.toString();
        assertTrue(output.contains("Использование: java Main"), "Должна выводиться справка");
    }

    @Test
    void testMainWithNonExistentFile() {
        Main.main(new String[]{"non_existent_file_12345.txt", "test"});

        String error = errCaptor.toString();
        assertTrue(error.contains("Ошибка при чтении файла"), "Должна быть ошибка IOException");
    }

    @Test
    void testMainWithEmptySubstring() throws Exception {
        Path file = tempDir.resolve("temp.txt");
        Files.writeString(file, "текст", StandardCharsets.UTF_8);

        Main.main(new String[]{file.toString(), ""});

        String error = errCaptor.toString();
        assertTrue(error.contains("Ошибка: Подстрока не может быть пустой или null"),
                "Должна быть ошибка IllegalArgumentException");
    }

    @Test
    void testMainSuccessPath() throws Exception {
        Path file = tempDir.resolve("success.txt");
        Files.writeString(file, "абракадабра", StandardCharsets.UTF_8);

        Main.main(new String[]{file.toString(), "бра"});

        String output = outCaptor.toString();
        assertTrue(output.contains("[1, 8]"), "Должен быть найден правильный индекс");
        assertTrue(output.contains("Найдено вхождений: 2"), "Должно быть 2 вхождения");
        assertTrue(output.contains("--- Старт поиска ---"), "Должен быть заголовок");
        assertTrue(output.contains("Использовано ОЗУ до начала:"), "Должен быть замер памяти");
        assertTrue(output.contains("Время выполнения:"), "Должен быть замер времени");
    }
}