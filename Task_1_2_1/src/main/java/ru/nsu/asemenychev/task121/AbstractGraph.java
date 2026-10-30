package ru.nsu.asemenychev.task121;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Базовая реализация графа.
 * Хранит вершины и делегирует топологическую сортировку выбранной стратегии.
 *
 * @param <T> тип вершин
 */
public abstract class AbstractGraph<T> implements Graph<T> {

    /** Вершины в порядке добавления. */
    protected final Set<T> vertices = new LinkedHashSet<>();

    /** Стратегия сортировки. По умолчанию — алгоритм Кана. */
    private TopologicalSorter<T> sorter = new KahnSorter<>();

    /**
     * Устанавливает новую стратегию топологической сортировки.
     *
     * @param sorter новая стратегия
     */
    public void setSorter(TopologicalSorter<T> sorter) {
        this.sorter = sorter;
    }

    /**
     * Вызывается при изменении структуры графа (добавлении/удалении вершин).
     */
    protected abstract void onStructureChanged();

    @Override
    public void addVertex(T name) {
        if (name == null) {
            throw new IllegalArgumentException("Имя вершины не может быть null");
        }
        if (name instanceof String && ((String) name).isBlank()) {
            throw new IllegalArgumentException("Имя вершины не может быть пустым");
        }
        if (vertices.add(name)) {
            onStructureChanged();
        }
    }

    @Override
    public boolean removeVertex(T name) {
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
    protected abstract void removeAllEdgesOf(T name);

    @Override
    public boolean hasVertex(T name) {
        return vertices.contains(name);
    }

    @Override
    public Set<T> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public int vertexCount() {
        return vertices.size();
    }

    protected void checkVerticesExist(T from, T to) {
        if (!hasVertex(from)) {
            throw new IllegalArgumentException("Нет вершины: " + from);
        }
        if (!hasVertex(to)) {
            throw new IllegalArgumentException("Нет вершины: " + to);
        }
    }

    @Override
    public List<T> topologicalSort() {
        return sorter.sort(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Graph<?>)) {
            return false;
        }
        Graph<?> g = (Graph<?>) o;

        // Сначала проверяем множества вершин. Если типы разные (String vs Integer),
        // этот метод вернет false, и мы сюда не пройдем.
        if (!vertices.equals(g.getVertices()) || edgeCount() != g.edgeCount()) {
            return false;
        }

        // Теперь точно уверены, что g имеет тот же тип T, что и наш граф.
        // Приводим g к Graph<T>, чтобы компилятор разрешил вызвать getNeighbors(v).
        Graph<T> typedG = (Graph<T>) g;

        for (T v : vertices) {
            Set<T> mine = new HashSet<>(getNeighbors(v));
            Set<T> theirs = new HashSet<>(typedG.getNeighbors(v));
            if (!mine.equals(theirs)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        List<T> vs = new ArrayList<>(vertices);
        List<String> edges = new ArrayList<>();
        for (T v : vs) {
            for (T u : getNeighbors(v)) {
                edges.add(v.toString() + "->" + u.toString());
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
        for (T v : vertices) {
            for (T u : getNeighbors(v)) {
                sb.append("    ").append(v).append(" -> ").append(u).append('\n');
            }
        }
        sb.append('}');
        return sb.toString();
    }
}