package ru.nsu.asemenychev.task131;

import java.io.IOException;
import java.util.List;

/**
 * Точка входа в программу поиска подстроки в файле.
 */
public class Main {

    private static final long BYTES_IN_MEGABYTE = 1048576;

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки: путь к файлу и искомая подстрока
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Использование: java Main <файл> <подстрока>");
            return;
        }

        final String filePath = args[0];
        final String substring = args[1];

        // Замер памяти ДО
        Runtime runtime = Runtime.getRuntime();
        // Сборщик мусора
        System.gc();
        long memoryBefore = (runtime.totalMemory() - runtime.freeMemory())
                / BYTES_IN_MEGABYTE;

        System.out.println("--- Старт поиска ---");
        System.out.println("Использовано ОЗУ до начала: " + memoryBefore + " МБ");

        FileSubstringSearcher searcher = new FileSubstringSearcher();

        long startTime = System.currentTimeMillis();

        try {
            List<Long> indices = searcher.find(filePath, substring);

            final long endTime = System.currentTimeMillis();

            // Замер памяти ПОСЛЕ
            System.gc();
            final long memoryAfter = (runtime.totalMemory() - runtime.freeMemory())
                    / BYTES_IN_MEGABYTE;

            System.out.println("Найдено вхождений: " + indices.size());
            if (!indices.isEmpty()) {
                System.out.println("Первые 5 индексов: "
                        + indices.subList(0, Math.min(5, indices.size())));
            }
            System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
            System.out.println("Использовано ОЗУ после: " + memoryAfter + " МБ");

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}