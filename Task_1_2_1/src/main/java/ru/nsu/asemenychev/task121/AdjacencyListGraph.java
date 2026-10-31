package ru.nsu.asemenychev.task121;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Граф в виде списка смежности.
 *
 * @param <T> тип вершин
 */
public class AdjacencyListGraph<T> extends AbstractGraph<T> {

    private final Map<T, Set<T>> adjacency = new LinkedHashMap<>();

    @Override
    protected void onStructureChanged() {
        adjacency.keySet().retainAll(vertices);
        for (T v : vertices) {
            adjacency.computeIfAbsent(v, k -> new LinkedHashSet<>());
        }
        for (Set<T> neighbors : adjacency.values()) {
            neighbors.retainAll(vertices);
        }
    }

    @Override
    public void addEdge(T from, T to) {
        checkVerticesExist(from, to);
        adjacency.get(from).add(to);
    }

    @Override
    public boolean removeEdge(T from, T to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }
        return adjacency.get(from).remove(to);
    }

    @Override
    public boolean hasEdge(T from, T to) {
        return hasVertex(from) && hasVertex(to) && adjacency.get(from).contains(to);
    }

    @Override
    public int edgeCount() {
        return adjacency.values().stream().mapToInt(Set::size).sum();
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        if (!hasVertex(vertex)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(adjacency.get(vertex));
    }

    @Override
    protected void removeAllEdgesOf(T name) {
        adjacency.remove(name);
        adjacency.values().forEach(n -> n.remove(name));
    }
}