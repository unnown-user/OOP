package ru.nsu.asemenychev.task131;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилитарный класс для поиска подстроки в файле.
 * Читает файл потоково, что позволяет обрабатывать файлы,
 * размер которых превышает доступный объем оперативной памяти.
 */
public class FileSubstringSearcher {

    /**
     * Находит все вхождения подстроки в файле.
     *
     * @param filePath путь к файлу
     * @param substring искомая подстрока
     * @return список индексов начал вхождений
     * @throws IOException при ошибке чтения файла
     * @throws IllegalArgumentException если подстрока пуста
     */
    public List<Long> find(String filePath, String substring) throws IOException {
        KmpMatcher matcher = new KmpMatcher(substring);
        List<Long> results = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {
            char[] buffer = new char[8192];
            long globalIndex = 0;
            int read;

            while ((read = reader.read(buffer)) != -1) {
                for (int i = 0; i < read; i++) {
                    long matchIndex = matcher.feed(buffer[i], globalIndex);

                    if (matchIndex != -1) {
                        results.add(matchIndex);
                    }

                    globalIndex++;
                }
            }
        }

        return results;
    }
}
