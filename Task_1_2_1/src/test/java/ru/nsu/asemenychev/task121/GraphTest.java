package ru.nsu.asemenychev.task121;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Тесты для всех реализаций {@link Graph}.
 * Один и тот же сценарий прогоняется для всех трёх типов графа.
 */
class GraphTest {

    static Stream<Supplier<Graph>> graphFactories() {
        return Stream.of(
                AdjacencyMatrixGraph::new,
                AdjacencyListGraph::new,
                IncidenceMatrixGraph::new
        );
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void addAndRemoveVertex(Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        assertTrue(g.hasVertex("A"));
        assertEquals(1, g.vertexCount());

        assertTrue(g.removeVertex("A"));
        assertFalse(g.hasVertex("A"));
        assertEquals(0, g.vertexCount());
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void addAndRemoveEdge(Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        assertTrue(g.hasEdge("A", "B"));
        assertEquals(1, g.edgeCount());

        assertTrue(g.removeEdge("A", "B"));
        assertFalse(g.hasEdge("A", "B"));
        assertEquals(0, g.edgeCount());
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void neighborsAreCorrect(Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("A", "C");

        List<String> neighbors = g.getNeighbors("A");
        assertTrue(neighbors.contains("B"));
        assertTrue(neighbors.contains("C"));
        assertEquals(2, neighbors.size());
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void removingVertexDropsItsEdges(Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "A");

        g.removeVertex("B");
        assertEquals(0, g.edgeCount() - 1);
        assertFalse(g.hasEdge("A", "B"));
        assertFalse(g.hasEdge("B", "C"));
        assertTrue(g.hasEdge("C", "A"));
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void topologicalSortLinearGraph(Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");

        List<String> order = g.topologicalSort();
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("B") < order.indexOf("C"));
    }

    @ParameterizedTest
    @MethodSource("graphFactories")
    void topologicalSortThrowsOnCycle(Supplier<Graph> factory) {
        Graph g = factory.get();
        g.addVertex("A");
        g.addVertex("B");
        g.addEdge("A", "B");
        g.addEdge("B", "A");
        assertThrows(IllegalStateException.class, g::topologicalSort);
    }

    @Test
    void differentRepresentationsAreEqual() {
        Graph matrix = new AdjacencyMatrixGraph();
        Graph list = new AdjacencyListGraph();
        Graph incidence = new IncidenceMatrixGraph();

        for (Graph g : new Graph[]{matrix, list, incidence}) {
            g.addVertex("A");
            g.addVertex("B");
            g.addEdge("A", "B");
        }

        assertEquals(matrix, list);
        assertEquals(list, incidence);
        assertEquals(matrix.hashCode(), list.hashCode());
    }

    @Test
    void graphsWithDifferentEdgesAreNotEqual() {
        Graph g1 = new AdjacencyListGraph();
        Graph g2 = new AdjacencyMatrixGraph();
        g1.addVertex("A");
        g1.addVertex("B");
        g1.addEdge("A", "B");

        g2.addVertex("A");
        g2.addVertex("B");

        assertFalse(g1.equals(g2));
    }

    @Test
    void readFromFileWorks() throws Exception {
        // создаём временный файл
        java.nio.file.Path tmp = java.nio.file.Files.createTempFile("graph", ".txt");
        java.nio.file.Files.writeString(tmp,
                "3\nA\nB\nC\n2\nA B\nB C\n");

        Graph g = GraphIo.readFromFile(tmp.toString(), AdjacencyListGraph::new);
        assertEquals(3, g.vertexCount());
        assertEquals(2, g.edgeCount());
        assertTrue(g.hasEdge("A", "B"));
        assertTrue(g.hasEdge("B", "C"));

        java.nio.file.Files.deleteIfExists(tmp);
    }


    @Test
    void dfsSortsLinearGraphCorrectly() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");

        TopologicalSorter sorter = new DfsSorter();
        List<String> order = sorter.sort(g);

        assertEquals(3, order.size());
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("B") < order.indexOf("C"));
    }

    @Test
    void dfsSortsBranchingGraphCorrectly() {
        Graph g = new AdjacencyMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("C", "D");

        TopologicalSorter sorter = new DfsSorter();
        List<String> order = sorter.sort(g);

        assertEquals(4, order.size());
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("A") < order.indexOf("C"));
        assertTrue(order.indexOf("B") < order.indexOf("D"));
        assertTrue(order.indexOf("C") < order.indexOf("D"));
    }

    @Test
    void dfsSortsDisconnectedGraphCorrectly() {
        Graph g = new IncidenceMatrixGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addEdge("A", "B");
        g.addEdge("C", "D");

        TopologicalSorter sorter = new DfsSorter();
        List<String> order = sorter.sort(g);

        assertEquals(4, order.size());
        assertTrue(order.indexOf("A") < order.indexOf("B"));
        assertTrue(order.indexOf("C") < order.indexOf("D"));
    }

    @Test
    void dfsSortThrowsExceptionOnCycle() {
        Graph g = new AdjacencyListGraph();
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("C", "A"); // Цикл!

        TopologicalSorter sorter = new DfsSorter();

        assertThrows(IllegalStateException.class, () -> sorter.sort(g));
    }

    @Test
    void dfsSortEmptyGraphReturnsEmptyList() {
        Graph g = new AdjacencyListGraph();
        TopologicalSorter sorter = new DfsSorter();

        List<String> order = sorter.sort(g);

        assertTrue(order.isEmpty());
    }
}