package ru.nsu.asemenychev.task121;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Граф в виде списка смежности: для каждой вершины — множество её соседей.
 */
public class AdjacencyListGraph extends AbstractGraph {

    private final Map<String, Set<String>> adjacency = new LinkedHashMap<>();

    @Override
    protected void onStructureChanged() {
        adjacency.keySet().retainAll(vertices);
        for (String v : vertices) {
            adjacency.computeIfAbsent(v, k -> new LinkedHashSet<>());
        }
        for (Set<String> neighbors : adjacency.values()) {
            neighbors.retainAll(vertices);
        }
    }

    @Override
    public void addEdge(String from, String to) {
        checkVerticesExist(from, to);
        adjacency.get(from).add(to);
    }

    @Override
    public boolean removeEdge(String from, String to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }
        return adjacency.get(from).remove(to);
    }

    @Override
    public boolean hasEdge(String from, String to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }
        return adjacency.get(from).contains(to);
    }

    @Override
    public int edgeCount() {
        int count = 0;
        for (Set<String> s : adjacency.values()) {
            count += s.size();
        }
        return count;
    }

    @Override
    public List<String> getNeighbors(String vertex) {
        if (!hasVertex(vertex)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(adjacency.get(vertex));
    }

    @Override
    protected void removeAllEdgesOf(String name) {
        adjacency.remove(name);
        for (Set<String> neighbors : adjacency.values()) {
            neighbors.remove(name);
        }
    }
}