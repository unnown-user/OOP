package ru.nsu.asemenychev.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
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
     * Тест проверяет, что программа может обработать файл размером 10 ГБ.
     */
    @Test
    void testMainProcessesHugeFileExceedingRam() throws Exception {
        Path hugeFile = tempDir.resolve("huge_test.txt");
        long fileSize = 10L * 1024 * 1024 * 1024; // 10 ГБ
        String target = "СЕКРЕТ";
        byte[] targetBytes = target.getBytes(StandardCharsets.UTF_8);

        // Создаем файл на 10 ГБ. Благодаря RandomAccessFile.setLength().
        // На Linux создается "разреженный файл", который не занимает
        // реального места на диске, но ОС считает его размер 10 ГБ.
        try (RandomAccessFile raf = new RandomAccessFile(hugeFile.toFile(), "rw")) {
            raf.setLength(fileSize);
            // В конце файла пишем наше слово.
            raf.seek(fileSize - targetBytes.length - 100);
            raf.write(targetBytes);
        }

        String javaBin = ProcessHandle.current().info().command().orElse("java");
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(
                javaBin,
                "-Xmx128m", // Ограничиваем память на 128 МБ.
                "-cp", classpath,
                "ru.nsu.asemenychev.task131.Main",
                hugeFile.toString(),
                target
        );

        Process process = pb.start();
        boolean finished = process.waitFor(120, TimeUnit.SECONDS);

        assertTrue(finished, "Процесс не завершился за 120 секунд");

        assertEquals(0, process.exitValue(),
                "Процесс завершился с ошибкой (возможно, OutOfMemoryError)");

        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        assertTrue(output.contains("Найдено вхождений: 1"),
                "Не найдено вхождений в выводе: " + output);
    }
}