package ru.nsu.asemenychev.task121;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Демонстрация работы с графами.
 */
public class Main {

    /**
     * Запускает примеры.
     *
     * @param args аргументы командной строки
     * @throws Exception при ошибках ввода-вывода
     */
    public static void main(String[] args) throws Exception {
        Graph<String> list = new AdjacencyListGraph<>();
        Graph<String> matrix = new AdjacencyMatrixGraph<>();
        Graph<String> incidence = new IncidenceMatrixGraph<>();

        for (Graph<String> g : new Graph[]{list, matrix, incidence}) {
            g.addVertex("A");
            g.addVertex("B");
            g.addVertex("C");
            g.addVertex("D");
            g.addEdge("A", "B");
            g.addEdge("A", "C");
            g.addEdge("B", "D");
            g.addEdge("C", "D");
        }

        System.out.println("--- Матрица смежности ---");
        System.out.println(matrix);

        System.out.println("--- Сравнение на равенство ---");
        System.out.println("matrix.equals(list): " + matrix.equals(list));

        System.out.println("--- Сортировка (по умолчанию — Кан) ---");
        System.out.println("list: " + list.topologicalSort());

        System.out.println("--- Смена алгоритма на DFS ---");
        ((AbstractGraph<String>) list).setSorter(new DfsSorter<>());
        System.out.println("list (DFS): " + list.topologicalSort());

        System.out.println("--- Граф с циклом ---");
        Graph<String> cyclic = new AdjacencyListGraph<>();
        cyclic.addVertex("X");
        cyclic.addVertex("Y");
        cyclic.addEdge("X", "Y");
        cyclic.addEdge("Y", "X");
        try {
            cyclic.topologicalSort();
        } catch (IllegalStateException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }

        System.out.println("--- Чтение из файла ---");
        Path tmp = Files.createTempFile("test-graph", ".txt");
        Files.writeString(tmp, "3\nA\nB\nC\n2\nA B\nB C\n");

        Graph<String> fromFile = GraphIo.readFromFile(
                tmp.toString(),
                AdjacencyListGraph::new,
                s -> s
        );
        System.out.println(fromFile);
        Files.deleteIfExists(tmp);
    }
}