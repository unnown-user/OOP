package ru.nsu.asemenychev.task121;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

/**
 * Утилитарный класс для чтения графа из файла фиксированного формата.
 * Создание экземпляров запрещено.
 */
public final class GraphIo {

    /**
     * Приватный конструктор, чтобы запретить создание экземпляров.
     */
    private GraphIo() {
    }

    /**
     * Читает граф из файла.
     *
     * @param path         путь к файлу
     * @param graphFactory функция-конструктор нужной реализации графа
     * @return построенный граф
     * @throws IOException при ошибке ввода-вывода
     */
    public static Graph readFromFile(String path, Supplier<Graph> graphFactory)
            throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(path), StandardCharsets.UTF_8)) {

            Graph graph = graphFactory.get();

            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Файл пуст");
            }
            int vertexCount = Integer.parseInt(line.trim());

            for (int i = 0; i < vertexCount; i++) {
                graph.addVertex(reader.readLine().trim());
            }

            String edgeCountLine = reader.readLine();
            if (edgeCountLine == null) {
                throw new IOException("Ожидалось количество рёбер");
            }
            int edgeCount = Integer.parseInt(edgeCountLine.trim());

            for (int i = 0; i < edgeCount; i++) {
                String[] parts = reader.readLine().trim().split("\\s+");
                if (parts.length != 2) {
                    throw new IOException("Ожидалось 'откуда куда', получено: "
                            + String.join(" ", parts));
                }
                graph.addEdge(parts[0], parts[1]);
            }
            return graph;
        }
    }
}