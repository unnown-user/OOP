package ru.nsu.asemenychev.task121;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Топологическая сортировка алгоритмом Кана.
 *
 * @param <T> тип вершин
 */
public class KahnSorter<T> implements TopologicalSorter<T> {

    @Override
    public List<T> sort(Graph<T> graph) {
        Map<T, Integer> indegree = new HashMap<>();
        for (T v : graph.getVertices()) {
            indegree.put(v, 0);
        }
        for (T v : graph.getVertices()) {
            for (T u : graph.getNeighbors(v)) {
                indegree.merge(u, 1, Integer::sum);
            }
        }

        Deque<T> queue = new ArrayDeque<>();
        for (Map.Entry<T, Integer> e : indegree.entrySet()) {
            if (e.getValue() == 0) {
                queue.add(e.getKey());
            }
        }

        List<T> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            T v = queue.poll();
            result.add(v);
            for (T u : graph.getNeighbors(v)) {
                int left = indegree.merge(u, -1, Integer::sum);
                if (left == 0) {
                    queue.add(u);
                }
            }
        }

        if (result.size() != graph.vertexCount()) {
            throw new IllegalStateException("Граф содержит цикл");
        }
        return result;
    }
}