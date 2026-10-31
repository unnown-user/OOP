package ru.nsu.asemenychev.task121;

import java.util.List;
import java.util.Set;

/**
 * Интерфейс ориентированного графа с вершинами произвольного типа {@code T}.
 * Обеспечивает полиморфизм: клиентский код работает с интерфейсом,
 * не завися от конкретной реализации (матрица, список и т.д.).
 *
 * @param <T> тип данных, хранящихся в вершинах
 */
public interface Graph<T> {

    /**
     * Добавляет вершину в граф, если её ещё нет.
     *
     * @param name имя (идентификатор) вершины
     */
    void addVertex(T name);

    /**
     * Удаляет вершину и все инцидентные ей рёбра.
     *
     * @param name имя вершины
     * @return {@code true}, если вершина существовала и была удалена
     */
    boolean removeVertex(T name);

    /**
     * Проверяет наличие вершины в графе.
     *
     * @param name имя вершины
     * @return {@code true}, если вершина есть
     */
    boolean hasVertex(T name);

    /**
     * Возвращает множество всех вершин.
     *
     * @return неизменяемое множество вершин
     */
    Set<T> getVertices();

    /**
     * Возвращает текущее количество вершин в графе.
     *
     * @return текущее количество вершин в графе
     */
    int vertexCount();

    /**
     * Добавляет ориентированное ребро между существующими вершинами.
     *
     * @param from начало ребра
     * @param to   конец ребра
     * @throws IllegalArgumentException если одной из вершин не существует
     */
    void addEdge(T from, T to);

    /**
     * Удаляет ребро.
     *
     * @param from начало ребра
     * @param to   конец ребра
     * @return {@code true}, если ребро существовало и было удалено
     */
    boolean removeEdge(T from, T to);

    /**
     * Проверяет наличие ребра.
     *
     * @param from начало ребра
     * @param to   конец ребра
     * @return {@code true}, если ребро есть
     */
    boolean hasEdge(T from, T to);

    /**
     * Возвращает текущее количество рёбер в графе.
     *
     * @return текущее количество рёбер в графе
     */
    int edgeCount();

    /**
     * Возвращает список соседей вершины (куда идут рёбра).
     *
     * @param vertex имя вершины
     * @return список соседей, либо пустой список, если вершины нет
     */
    List<T> getNeighbors(T vertex);

    /**
     * Выполняет топологическую сортировку вершин.
     *
     * @return упорядоченный список вершин
     * @throws IllegalStateException если в графе есть цикл
     */
    List<T> topologicalSort();
}