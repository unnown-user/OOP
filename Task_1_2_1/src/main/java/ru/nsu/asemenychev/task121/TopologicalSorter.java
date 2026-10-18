package ru.nsu.asemenychev.task121;

import java.util.List;

/**
 * Стратегия топологической сортировки графа.
 */
public interface TopologicalSorter {
    /**
     * @param graph граф
     * @return отсортированный список вершин
     * @throws IllegalStateException если есть цикл
     */
    List<String> sort(Graph graph);
}