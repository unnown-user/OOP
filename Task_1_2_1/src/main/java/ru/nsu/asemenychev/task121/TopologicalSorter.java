package ru.nsu.asemenychev.task121;

import java.util.List;

/**
 * Стратегия топологической сортировки.
 *
 * @param <T> тип вершин
 */
public interface TopologicalSorter<T> {
    /**
     * Сортирует вершины так, что для каждого ребра {@code u -> v}
     * вершина {@code u} идёт раньше {@code v}.
     *
     * @param graph граф
     * @return упорядоченный список вершин
     * @throws IllegalStateException если в графе есть цикл
     */
    List<T> sort(Graph<T> graph);
}