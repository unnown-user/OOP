package ru.nsu.asemenychev.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты для класса {@link Main}.
 * Проверяют способность программы обрабатывать файлы,
 * размер которых превышает доступную оперативную память.
 */
class MainTest {

    @TempDir
    Path tempDir;

    /**
     * Тест проверяет, что программа может обработать файл размером 150 МБ.
     */
    @Test
    void testMainProcessesLargeFileWithinLimitedMemory() throws Exception {
        Path hugeFile = tempDir.resolve("huge_test.txt");
        long targetIndex = 75_000_000L;
        String target = "СЕКРЕТ";

        try (BufferedWriter writer = Files.newBufferedWriter(hugeFile, StandardCharsets.UTF_8)) {
            String block = "а".repeat(1000);
            int blocksCount = 150_000;

            for (int i = 0; i < blocksCount; i++) {
                if (i == 75_000) {
                    writer.write(target);
                } else {
                    writer.write(block);
                }
            }
        }

        String javaHome = System.getProperty("java.home");
        String javaBin = Path.of(javaHome, "bin", "java").toString();
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(
                javaBin,
                "-Xmx64m",
                "-cp", classpath,
                "ru.nsu.asemenychev.task131.Main",
                hugeFile.toString(),
                target
        );

        Process process = pb.start();
        boolean finished = process.waitFor(60, TimeUnit.SECONDS);

        assertTrue(finished, "Процесс не завершился за 60 секунд");
        assertEquals(0, process.exitValue(), "Процесс завершился с ошибкой (возможно, OutOfMemoryError)");

        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        assertTrue(output.contains("Найдено вхождений: 1"), "Не найдено вхождений в выводе: " + output);
        assertTrue(output.contains(String.valueOf(targetIndex)), "Неверный индекс в выводе: " + output);
    }
}