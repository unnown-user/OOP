package ru.nsu.asemenychev.task131;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Тесты для {@link FileSubstringSearcher}.
 */
class FileSubstringSearcherTest {

    @TempDir
    Path tempDir;

    @Test
    void testSimpleMatch() throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "абракадабра", StandardCharsets.UTF_8);

        FileSubstringSearcher searcher = new FileSubstringSearcher();
        List<Long> result = searcher.find(file.toString(), "бра");
        assertEquals(Arrays.asList(1L, 8L), result);
    }

    @Test
    void testNoMatch() throws IOException {
        Path file = tempDir.resolve("no_match.txt");
        Files.writeString(file, "абракадабра", StandardCharsets.UTF_8);

        FileSubstringSearcher searcher = new FileSubstringSearcher();
        List<Long> result = searcher.find(file.toString(), "кот");
        assertTrue(result.isEmpty());
    }

    @Test
    void testOverlappingMatches() throws IOException {
        Path file = tempDir.resolve("overlap.txt");
        Files.writeString(file, "ааааа", StandardCharsets.UTF_8);

        FileSubstringSearcher searcher = new FileSubstringSearcher();
        List<Long> result = searcher.find(file.toString(), "аа");
        assertEquals(Arrays.asList(0L, 1L, 2L, 3L), result);
    }

    @Test
    void testEmptySubstringThrowsException() {
        FileSubstringSearcher searcher = new FileSubstringSearcher();
        assertThrows(IllegalArgumentException.class, () -> searcher.find("fake.txt", ""));
    }

    @Test
    void testLargeFileGeneration() throws IOException {
        Path file = tempDir.resolve("large.txt");
        int totalSize = 10_000_000;
        String target = "таргет";

        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            for (int i = 0; i < totalSize; i++) {
                if (i % 100_000 == 0) {
                    writer.write(target);
                    writer.write("а");
                    i += target.length();
                } else {
                    writer.write('а');
                }
            }
        }

        FileSubstringSearcher searcher = new FileSubstringSearcher();
        List<Long> result = searcher.find(file.toString(), target);

        assertFalse(result.isEmpty());
        assertEquals(0L, result.get(0));
        assertEquals(100_000L, result.get(1));
    }
}