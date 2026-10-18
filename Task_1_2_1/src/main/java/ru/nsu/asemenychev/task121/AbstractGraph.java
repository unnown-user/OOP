package ru.nsu.asemenychev.task121;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Общая часть всех реализаций графа: хранение вершин,
 * делегирование топологической сортировки, равенство и вывод.
 * Конкретные реализации переопределяют работу с рёбрами
 * и обновляют свою структуру через метод {@link #onStructureChanged()}.
 */
public abstract class AbstractGraph implements Graph {

    /** Вершины в порядке добавления. */
    protected final Set<String> vertices = new LinkedHashSet<>();

    /** Стратегия топологической сортировки. По умолчанию — алгоритм Кана. */
    private TopologicalSorter sorter = new KahnSorter();

    /**
     * Устанавливает новую стратегию топологической сортировки.
     *
     * @param sorter новая стратегия
     */
    public void setSorter(TopologicalSorter sorter) {
        this.sorter = sorter;
    }

    /**
     * Хук, вызываемый при изменении структуры графа.
     * Реализации могут переопределить его, чтобы перестроить свои матрицы/списки.
     */
    protected void onStructureChanged() {
    }

    @Override
    public void addVertex(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Имя вершины не может быть пустым");
        }
        if (vertices.add(name)) {
            onStructureChanged();
        }
    }

    @Override
    public boolean removeVertex(String name) {
        if (!vertices.contains(name)) {
            return false;
        }
        removeAllEdgesOf(name);
        vertices.remove(name);
        onStructureChanged();
        return true;
    }

    /**
     * Удаляет все рёбра, инцидентные указанной вершине.
     *
     * @param name имя удаляемой вершины
     */
    protected abstract void removeAllEdgesOf(String name);

    @Override
    public boolean hasVertex(String name) {
        return vertices.contains(name);
    }

    @Override
    public Set<String> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    /**
     * Проверяет, что обе вершины существуют. Бросает исключение, если нет.
     *
     * @param from начало
     * @param to   конец
     */
    protected void checkVerticesExist(String from, String to) {
        if (!hasVertex(from)) {
            throw new IllegalArgumentException("Нет вершины: " + from);
        }
        if (!hasVertex(to)) {
            throw new IllegalArgumentException("Нет вершины: " + to);
        }
    }

    /**
     * Выполняет топологическую сортировку текущей выбранной стратегией.
     *
     * @return отсортированный список вершин
     * @throws IllegalStateException если граф содержит цикл
     */
    @Override
    public List<String> topologicalSort() {
        return sorter.sort(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph g = (Graph) o;
        if (!vertices.equals(g.getVertices())) {
            return false;
        }
        if (edgeCount() != g.edgeCount()) {
            return false;
        }
        for (String v : vertices) {
            Set<String> mine = new HashSet<>(getNeighbors(v));
            Set<String> theirs = new HashSet<>(g.getNeighbors(v));
            if (!mine.equals(theirs)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        List<String> vs = new ArrayList<>(vertices);
        Collections.sort(vs);
        List<String> edges = new ArrayList<>();
        for (String v : vs) {
            for (String u : getNeighbors(v)) {
                edges.add(v + "->" + u);
            }
        }
        Collections.sort(edges);
        return Objects.hash(vs, edges);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Graph {\n");
        sb.append("  vertices: ").append(vertices).append('\n');
        sb.append("  edges:\n");
        for (String v : vertices) {
            for (String u : getNeighbors(v)) {
                sb.append("    ").append(v).append(" -> ").append(u).append('\n');
            }
        }
        sb.append('}');
        return sb.toString();
    }
}