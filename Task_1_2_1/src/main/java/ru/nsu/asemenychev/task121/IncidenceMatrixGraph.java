package ru.nsu.asemenychev.task121;

import java.util.ArrayList;
import java.util.List;

/**
 * Граф в виде матрицы инцидентности.
 *
 * @param <T> тип вершин
 */
public class IncidenceMatrixGraph<T> extends AbstractGraph<T> {

    private final List<T[]> edges = new ArrayList<>();
    private final List<T> order = new ArrayList<>();
    private int[][] incidence = new int[0][0];

    private void rebuild() {
        order.clear();
        order.addAll(vertices);
        int n = order.size();
        int m = edges.size();
        incidence = new int[n][m];
        for (int e = 0; e < m; e++) {
            T from = edges.get(e)[0];
            T to = edges.get(e)[1];
            incidence[order.indexOf(from)][e] = 1;
            incidence[order.indexOf(to)][e] = -1;
        }
    }

    @Override
    protected void onStructureChanged() {
        edges.removeIf(e -> !hasVertex(e[0]) || !hasVertex(e[1]));
        rebuild();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addEdge(T from, T to) {
        checkVerticesExist(from, to);
        if (hasEdge(from, to)) {
            return;
        }
        edges.add((T[]) new Object[]{from, to});
        rebuild();
    }

    @Override
    public boolean removeEdge(T from, T to) {
        boolean removed = edges.removeIf(e -> e[0].equals(from) && e[1].equals(to));
        if (removed) {
            rebuild();
        }
        return removed;
    }

    @Override
    public boolean hasEdge(T from, T to) {
        for (T[] e : edges) {
            if (e[0].equals(from) && e[1].equals(to)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int edgeCount() {
        return edges.size();
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        List<T> result = new ArrayList<>();
        if (!hasVertex(vertex)) {
            return result;
        }
        for (T[] e : edges) {
            if (e[0].equals(vertex)) {
                result.add(e[1]);
            }
        }
        return result;
    }

    @Override
    protected void removeAllEdgesOf(T name) {
        edges.removeIf(e -> e[0].equals(name) || e[1].equals(name));
    }
}