package ru.nsu.asemenychev.task121;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Топологическая сортировка на основе поиска в глубину (DFS).
 */
public class DfsSorter implements TopologicalSorter {

    @Override
    public List<String> sort(Graph graph) {
        Set<String> visited = new HashSet<>();
        Set<String> visiting = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();

        for (String v : graph.getVertices()) {
            if (!visited.contains(v)) {
                dfs(graph, v, visited, visiting, stack);
            }
        }

        List<String> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private void dfs(Graph graph, String v, Set<String> visited,
                     Set<String> visiting, Deque<String> stack) {
        if (visiting.contains(v)) {
            throw new IllegalStateException("Граф содержит цикл — сортировка невозможна");
        }
        if (visited.contains(v)) {
            return;
        }

        visiting.add(v);
        for (String u : graph.getNeighbors(v)) {
            dfs(graph, u, visited, visiting, stack);
        }
        visiting.remove(v);
        visited.add(v);
        stack.push(v);
    }
}
