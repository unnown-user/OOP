package ru.nsu.asemenychev.task122;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.Test;

/**
 * Тесты для узла {@link HashTable}.
 */
class HashTableTest {

    @Test
    void testPutAndGet() {
        HashTable<String, Integer> map = new HashTable<>();
        map.put("A", 1);
        assertEquals(1, map.get("A"));
        assertEquals(1, map.size());
    }

    @Test
    void testUpdate() {
        HashTable<String, Double> map = new HashTable<>();
        map.put("A", 1.0);
        map.update("A", 2.0);
        assertEquals(2.0, map.get("A"));
        assertThrows(java.util.NoSuchElementException.class, () -> map.update("B", 3.0));
    }

    @Test
    void testRemove() {
        HashTable<String, Integer> map = new HashTable<>();
        map.put("A", 1);
        assertFalse(map.remove("A", 2)); // неверное значение
        assertTrue(map.remove("A", 1));  // верное
        assertNull(map.get("A"));
        assertEquals(0, map.size());
    }

    @Test
    void testEqualsAndHashCode() {
        HashTable<String, Integer> map1 = new HashTable<>();
        HashTable<String, Integer> map2 = new HashTable<>();
        map1.put("A", 1);
        map2.put("A", 1);
        assertEquals(map1, map2);
        assertEquals(map1.hashCode(), map2.hashCode());

        map2.put("B", 2);
        assertNotEquals(map1, map2);
    }

    @Test
    void testConcurrentModificationException() {
        HashTable<String, Integer> map = new HashTable<>();
        map.put("A", 1);
        map.put("B", 2);

        assertThrows(ConcurrentModificationException.class, () -> {
            for (HashNode<String, Integer> node : map) {
                map.put("C", 3);
            }
        });
    }

    @Test
    void testResize() {
        HashTable<Integer, Integer> map = new HashTable<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i * 10);
        }
        assertEquals(100, map.size());
        assertEquals(990, map.get(100 - 1));

        assertEquals(0, map.get(0));
        assertEquals(500, map.get(50));
    }

    @Test
    void testEqualsWithDifferentGenericTypes() {
        HashTable<String, Integer> map1 = new HashTable<>();
        map1.put("one", 1);

        HashTable<Integer, String> map2 = new HashTable<>();
        map2.put(1, "one");

        assertFalse(map1.equals(map2));
    }

    @Test
    void testEqualsWithNullAndDifferentObjects() {
        HashTable<String, Integer> map = new HashTable<>();
        map.put("A", 1);

        assertNotEquals(null, map);
        assertNotEquals("Just a string", map);
    }

    @Test
    void testCollisionHandling() {
        HashTable<Integer, String> map = new HashTable<>();

        map.put(16, "Sixteen");
        map.put(32, "ThirtyTwo");
        map.put(48, "FortyEight");

        assertEquals("Sixteen", map.get(16));
        assertEquals("ThirtyTwo", map.get(32));
        assertEquals("FortyEight", map.get(48));
        assertEquals(3, map.size());

        assertTrue(map.remove(32, "ThirtyTwo"));
        assertNull(map.get(32));
        assertEquals(2, map.size());
    }

    @Test
    void testRemoveWrongValueOrKey() {
        HashTable<String, Integer> map = new HashTable<>();
        map.put("A", 1);

        assertFalse(map.remove("A", 2));
        assertFalse(map.remove("B", 1));

        assertEquals(1, map.size());
        assertEquals(1, map.get("A"));
    }
}