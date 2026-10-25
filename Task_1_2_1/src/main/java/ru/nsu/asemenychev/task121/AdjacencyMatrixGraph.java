package ru.nsu.asemenychev.task121;

import java.util.ArrayList;
import java.util.List;

/**
 * Граф в виде матрицы смежности.
 * Матрица перестраивается при изменении набора вершин.
 *
 * @param <T> тип вершин
 */
public class AdjacencyMatrixGraph<T> extends AbstractGraph<T> {

    private final List<T> order = new ArrayList<>();
    private final List<T[]> edges = new ArrayList<>();
    private boolean[][] matrix = new boolean[0][0];

    private void rebuild() {
        order.clear();
        order.addAll(vertices);
        int n = order.size();
        matrix = new boolean[n][n];
        for (T[] e : edges) {
            if (hasVertex(e[0]) && hasVertex(e[1])) {
                matrix[order.indexOf(e[0])][order.indexOf(e[1])] = true;
            }
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
    @SuppressWarnings("unchecked")
    public boolean removeEdge(T from, T to) {
        boolean removed = edges.removeIf(e -> e[0].equals(from) && e[1].equals(to));
        if (removed) {
            rebuild();
        }
        return removed;
    }

    @Override
    public boolean hasEdge(T from, T to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }
        return matrix[order.indexOf(from)][order.indexOf(to)];
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
        int i = order.indexOf(vertex);
        for (int j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j]) {
                result.add(order.get(j));
            }
        }
        return result;
    }

    @Override
    protected void removeAllEdgesOf(T name) {
        edges.removeIf(e -> e[0].equals(name) || e[1].equals(name));
    }
}