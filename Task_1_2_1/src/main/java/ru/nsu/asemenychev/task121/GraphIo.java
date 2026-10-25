package ru.nsu.asemenychev.task121;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Утилитарный класс для чтения графа из файла фиксированного формата.
 */
public final class GraphIo {

    private GraphIo() {
    }

    /**
     * Читает граф из файла.
     *
     * @param path         путь к файлу
     * @param graphFactory функция, создающая пустой граф нужного типа
     * @param vertexParser функция преобразования строки из файла в тип вершины T
     * @param <T>          тип вершин
     * @return построенный граф
     * @throws IOException при ошибке ввода-вывода
     */
    public static <T> Graph<T> readFromFile(
            String path,
            Supplier<Graph<T>> graphFactory,
            Function<String, T> vertexParser) throws IOException {

        try (BufferedReader reader = Files.newBufferedReader(
                Path.of(path), StandardCharsets.UTF_8)) {

            Graph<T> graph = graphFactory.get();

            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Файл пуст");
            }
            int vertexCount = Integer.parseInt(line.trim());

            for (int i = 0; i < vertexCount; i++) {
                graph.addVertex(vertexParser.apply(reader.readLine().trim()));
            }

            String edgeCountLine = reader.readLine();
            if (edgeCountLine == null) {
                throw new IOException("Ожидалось количество рёбер");
            }
            int edgeCount = Integer.parseInt(edgeCountLine.trim());

            for (int i = 0; i < edgeCount; i++) {
                String[] parts = reader.readLine().trim().split("\\s+");
                if (parts.length != 2) {
                    throw new IOException("Ожидалось 'откуда куда'");
                }
                graph.addEdge(vertexParser.apply(parts[0]), vertexParser.apply(parts[1]));
            }
            return graph;
        }
    }
}