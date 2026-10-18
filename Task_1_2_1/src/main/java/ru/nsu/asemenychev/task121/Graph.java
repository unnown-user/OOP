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
     * Проверяет, существует ли вершина в графе.
     *
     * @param name имя вершины
     * @return {@code true}, если вершина есть в графе
     */
    boolean hasVertex(String name);

    /**
     * Возвращает множество всех вершин графа.
     * Множество доступно только для чтения: попытка изменить его
     * вызовет {@link UnsupportedOperationException}.
     *
     * @return неизменяемое множество всех вершин
     */
    Set<String> getVertices();

    /**
     * Возвращает текущее количество вершин в графе.
     *
     * @return количество вершин
     */
    int vertexCount();

    /**
     * Добавляет ориентированное ребро между двумя существующими вершинами.
     * Если ребро уже существует, граф не меняется.
     * Если хотя бы одна из вершин не существует, выбрасывается исключение.
     *
     * @param from начало ребра
     * @param to   конец ребра
     * @throws IllegalArgumentException если вершины {@code from} или {@code to} нет в графе
     */
    void addEdge(String from, String to);

    /**
     * Удаляет ориентированное ребро.
     *
     * @param from начало ребра
     * @param to   конец ребра
     * @return {@code true}, если ребро существовало и было удалено,
     *         {@code false}, если такого ребра не было
     */
    boolean removeEdge(String from, String to);

    /**
     * Проверяет, существует ли ориентированное ребро между двумя вершинами.
     *
     * @param from начало ребра
     * @param to   конец ребра
     * @return {@code true}, если ребро есть
     */
    boolean hasEdge(String from, String to);

    /**
     * Возвращает текущее количество ориентированных рёбер в графе.
     *
     * @return количество рёбер
     */
    int edgeCount();

    /**
     * Возвращает список всех вершин, в которые ведут рёбра из указанной вершины.
     * Если вершины не существует, возвращается пустой список.
     *
     * @param vertex имя вершины
     * @return список соседей (вершин, в которые есть ребро из {@code vertex})
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