package ru.nsu.asemenychev.task121;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Топологическая сортировка на основе поиска в глубину (DFS).
 *
 * @param <T> тип вершин
 */
public class DfsSorter<T> implements TopologicalSorter<T> {

    @Override
    public List<T> sort(Graph<T> graph) {
        Set<T> visited = new HashSet<>();
        Set<T> visiting = new HashSet<>();
        Deque<T> stack = new ArrayDeque<>();

        for (T v : graph.getVertices()) {
            if (!visited.contains(v)) {
                dfs(graph, v, visited, visiting, stack);
            }
        }

        List<T> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private void dfs(Graph<T> graph, T v, Set<T> visited,
                     Set<T> visiting, Deque<T> stack) {
        if (visiting.contains(v)) {
            throw new IllegalStateException("Граф содержит цикл");
        }
        if (visited.contains(v)) {
            return;
        }

        visiting.add(v);
        for (T u : graph.getNeighbors(v)) {
            dfs(graph, u, visited, visiting, stack);
        }
        visiting.remove(v);
        visited.add(v);
        stack.push(v);
    }
}