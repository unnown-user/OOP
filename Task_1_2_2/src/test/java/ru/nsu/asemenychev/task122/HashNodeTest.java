package ru.nsu.asemenychev.task122;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты для узла {@link HashNode}.
 */
class HashNodeTest {

    @Test
    void testConstructorAndGetters() {
        HashNode<String, Integer> node = new HashNode<>("key", 100, 42, null);

        assertEquals("key", node.getKey());
        assertEquals(100, node.getValue());
        assertEquals(42, node.hash);
        assertEquals(null, node.next);
    }

    @Test
    void testToString() {
        HashNode<String, Double> node = new HashNode<>("pi", 3.14, 0, null);
        assertEquals("pi=3.14", node.toString());
    }
}