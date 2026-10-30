package ru.nsu.asemenychev.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Тесты для всех реализаций графа, алгоритмов сортировки и утилит.
 */
class GraphTest {

    static Stream<Supplier<Graph<String>>> graphFactories() {
        return Stream.of(
                AdjacencyMatrixGraph::new,
                AdjacencyListGraph::new,
                IncidenceMatrixGraph::new
        );
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void addAndRemoveVertex(Supplier<Graph<String>> factory) {
        Graph<String> g = factory.get();
        assertEquals(0, g.vertexCount());

        g.addVertex("A");
        assertTrue(g.hasVertex("A"));
        assertEquals(1, g.vertexCount());

        assertTrue(g.removeVertex("A"));
        assertFalse(g.hasVertex("A"));
        assertEquals(0, g.vertexCount());

        assertFalse(g.removeVertex("X"));
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void invalidVertexNamesThrowException(Supplier<Graph<String>> factory) {
        Graph<String> g = factory.get();
        assertThrows(IllegalArgumentException.class, () -> g.addVertex(null));
        assertThrows(IllegalArgumentException.class, () -> g.addVertex("   "));
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void addAndRemoveEdge(Supplier<Graph<String>> factory) {
        Graph<String> g = factory.get();
        g.addVertex("A");
        g.addVertex("B");

        g.addEdge("A", "B");
        assertTrue(g.hasEdge("A", "B"));
        assertEquals(1, g.edgeCount());

        assertTrue(g.removeEdge("A", "B"));
        assertFalse(g.hasEdge("A", "B"));
        assertEquals(0, g.edgeCount());

        assertFalse(g.removeEdge("A", "B"));
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void edgeRequiresExistingVertices(Supplier<Graph<String>> factory) {
        Graph<String> g = factory.get();
        g.addVertex("A");
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("A", "X"));
        assertThrows(IllegalArgumentException.class, () -> g.addEdge("X", "A"));
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void removingVertexDropsItsEdges(Supplier<Graph<String>> factory) {
        Graph<String> g = factory.get();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");

        g.removeVertex("B");
        assertEquals(0, g.edgeCount());
        assertFalse(g.hasEdge("A", "B"));
        assertFalse(g.hasEdge("B", "C"));
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void getNeighborsWorks(Supplier<Graph<String>> factory) {
        Graph<String> g = factory.get();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("A", "C");

        List<String> neighbors = g.getNeighbors("A");
        assertEquals(2, neighbors.size());
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));

        assertTrue(g.getNeighbors("X").isEmpty());
    }

    @Test
    void graphsEqualityAndHashCode() {
        Graph<String> matrix = new AdjacencyMatrixGraph<>();
        Graph<String> list = new AdjacencyListGraph<>();
        Graph<String> incidence = new IncidenceMatrixGraph<>();

        for (Graph<String> g : new Graph[]{matrix, list, incidence}) {
            g.addVertex("A");
            g.addVertex("B");
            g.addEdge("A", "B");
        }

        assertEquals(matrix, list);
        assertEquals(list, incidence);
        assertEquals(matrix, matrix); // Рефлексивность
        assertNotEquals(matrix, null);
        assertNotEquals(matrix, "Not a graph"); // Разные типы

        assertEquals(matrix.hashCode(), list.hashCode());

        Graph<String> other = new AdjacencyListGraph<>();
        other.addVertex("A");
        other.addVertex("B");
        assertNotEquals(matrix, other);
    }

    @Test
    void toStringContainsVerticesAndEdges() {
        Graph<String> g = new AdjacencyListGraph<>();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");

        String str = g.toString();
        assertTrue(str.contains("vertices: [A, B]"));
        assertTrue(str.contains("A -> B"));
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void kahnSortsLinearGraph(Supplier<Graph<String>> factory) {
        Graph<String> g = factory.get();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");

        List<String> order = g.topologicalSort();
        assertEquals(3, order.size());
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("B") < order.indexOf("C"));
    }

    @Test
    void kahnSortThrowsOnCycle() {
        Graph<String> g = new AdjacencyListGraph<>();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        g.addEdge("B", "A");

        TopologicalSorter<String> sorter = new KahnSorter<>();
        assertThrows(IllegalStateException.class, () -> sorter.sort(g));
    }

    @Test
    void dfsSortsLinearGraph() {
        Graph<String> g = new AdjacencyListGraph<>();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");

        TopologicalSorter<String> sorter = new DfsSorter<>();
        List<String> order = sorter.sort(g);

        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("B") < order.indexOf("C"));
    }

    @Test
    void dfsSortThrowsOnCycle() {
        Graph<String> g = new AdjacencyListGraph<>();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        g.addEdge("B", "A");

        TopologicalSorter<String> sorter = new DfsSorter<>();
        assertThrows(IllegalStateException.class, () -> sorter.sort(g));
    }

    @Test
    void setSorterReplacesAlgorithm() {
        AbstractGraph<String> g = new AdjacencyListGraph<>();
        g.addVertex("A");

        g.setSorter(new DfsSorter<>());
        List<String> order = g.topologicalSort();

        assertEquals(1, order.size());
        assertEquals("A", order.get(0));
    }

    @Test
    void readFromFileWorks() throws Exception {
        Path tmp = Files.createTempFile("graph", ".txt");
        Files.writeString(tmp, "3\nA\nB\nC\n2\nA B\nB C\n");

        Graph<String> g = GraphIo.readFromFile(tmp.toString(), AdjacencyListGraph::new, s -> s);

        assertEquals(3, g.vertexCount());
        assertEquals(2, g.edgeCount());
        assertTrue(g.hasEdge("A", "B"));
        assertTrue(g.hasEdge("B", "C"));

        Files.deleteIfExists(tmp);
    }
}