package ru.nsu.asemenychev.task121;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс графа. Поддерживает работу с вершинами и рёбрами,
 * запрос соседей и топологическую сортировку.
 *
 * <p>Все три классические реализации ({@link AdjacencyMatrixGraph},
 * {@link AdjacencyListGraph}, {@link IncidenceMatrixGraph}) реализуют
 * этот интерфейс и полностью взаимозаменяемы.
 */
public interface Graph {

    /**
     * Добавляет вершину, если её ещё нет.
     *
     * @param name имя вершины
     */
    void addVertex(String name);

    /**
     * Удаляет вершину и все инцидентные рёбра.
     *
     * @param name имя вершины
     * @return {@code true}, если вершина была
     */
    boolean removeVertex(String name);

    /**
     * @param name имя вершины
     * @return {@code true}, если вершина есть в графе
     */
    boolean hasVertex(String name);

    /**
     * @return неизменяемое множество всех вершин
     */
    Set<String> getVertices();

    /**
     * @return количество вершин
     */
    int vertexCount();

    /**
     * Добавляет ориентированное ребро. Обе вершины уже должны существовать.
     *
     * @param from начало ребра
     * @param to   конец ребра
     */
    void addEdge(String from, String to);

    /**
     * @param from начало ребра
     * @param to   конец ребра
     * @return {@code true}, если ребро было
     */
    boolean removeEdge(String from, String to);

    /**
     * @param from начало ребра
     * @param to   конец ребра
     * @return {@code true}, если ребро есть
     */
    boolean hasEdge(String from, String to);

    /**
     * @return количество рёбер
     */
    int edgeCount();

    /**
     * @param vertex имя вершины
     * @return список всех соседей, в которые идут рёбра из {@code vertex}
     */
    List<String> getNeighbors(String vertex);

    /**
     * Топологическая сортировка (алгоритм Кана).
     *
     * @return упорядоченный список вершин
     * @throws IllegalStateException если в графе есть цикл
     */
    List<String> topologicalSort();
}