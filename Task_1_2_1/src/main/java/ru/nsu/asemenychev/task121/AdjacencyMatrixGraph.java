package ru.nsu.asemenychev.task121;

import java.util.ArrayList;
import java.util.List;

/**
 * Граф в виде матрицы смежности {@code boolean[n][n]}.
 * Матрица перестраивается при каждом изменении структуры —
 * просто и надёжно, хотя и не оптимально.
 */
public class AdjacencyMatrixGraph extends AbstractGraph {

    private final List<String> order = new ArrayList<>();
    private final List<String[]> edges = new ArrayList<>();
    private boolean[][] matrix = new boolean[0][0];

    private void rebuild() {
        order.clear();
        order.addAll(vertices);
        int n = order.size();
        matrix = new boolean[n][n];
        for (String[] e : edges) {
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
    public void addEdge(String from, String to) {
        checkVerticesExist(from, to);
        if (hasEdge(from, to)) {
            return;
        }
        edges.add(new String[]{from, to});
        rebuild();
    }

    @Override
    public boolean removeEdge(String from, String to) {
        boolean removed = edges.removeIf(e -> e[0].equals(from) && e[1].equals(to));
        if (removed) {
            rebuild();
        }
        return removed;
    }

    @Override
    public boolean hasEdge(String from, String to) {
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
    public List<String> getNeighbors(String vertex) {
        List<String> result = new ArrayList<>();
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
    protected void removeAllEdgesOf(String name) {
        edges.removeIf(e -> e[0].equals(name) || e[1].equals(name));
    }
}