package ru.nsu.asemenychev.task121;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Топологическая сортировка алгоритмом Кана.
 */
public class KahnSorter implements TopologicalSorter {

    @Override
    public List<String> sort(Graph graph) {
        Map<String, Integer> indegree = new HashMap<>();
        for (String v : graph.getVertices()) {
            indegree.put(v, 0);
        }
        for (String v : graph.getVertices()) {
            for (String u : graph.getNeighbors(v)) {
                indegree.merge(u, 1, Integer::sum);
            }
        }

        Deque<String> queue = new ArrayDeque<>();
        for (Map.Entry<String, Integer> e : indegree.entrySet()) {
            if (e.getValue() == 0) {
                queue.add(e.getKey());
            }
        }

        List<String> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            String v = queue.poll();
            result.add(v);
            for (String u : graph.getNeighbors(v)) {
                int left = indegree.merge(u, -1, Integer::sum);
                if (left == 0) {
                    queue.add(u);
                }
            }
        }

        if (result.size() != graph.vertexCount()) {
            throw new IllegalStateException("Граф содержит цикл — сортировка невозможна");
        }
        return result;
    }
}
