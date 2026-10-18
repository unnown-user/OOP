package ru.nsu.asemenychev.task121;

import java.util.ArrayList;
import java.util.List;

/**
 * Граф в виде матрицы инцидентности {@code int[V][E]}.
 * Для ориентированного графа: {@code matrix[v][e] == +1}, если
 * вершина {@code v} — начало ребра {@code e}, {@code -1} — если конец,
 * {@code 0} — иначе.
 */
public class IncidenceMatrixGraph extends AbstractGraph {

    private final List<String[]> edges = new ArrayList<>();
    private int[][] incidence = new int[0][0];
    private final List<String> order = new ArrayList<>();

    private void rebuild() {
        order.clear();
        order.addAll(vertices);
        int n = order.size();
        int m = edges.size();
        incidence = new int[n][m];
        for (int e = 0; e < m; e++) {
            String from = edges.get(e)[0];
            String to = edges.get(e)[1];
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
        for (String[] e : edges) {
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
    public List<String> getNeighbors(String vertex) {
        List<String> result = new ArrayList<>();
        if (!hasVertex(vertex)) {
            return result;
        }
        for (String[] e : edges) {
            if (e[0].equals(vertex)) {
                result.add(e[1]);
            }
        }
        return result;
    }

    @Override
    protected void removeAllEdgesOf(String name) {
        edges.removeIf(e -> e[0].equals(name) || e[1].equals(name));
    }
}
