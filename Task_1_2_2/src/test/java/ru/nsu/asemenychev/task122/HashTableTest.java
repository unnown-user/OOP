package ru.nsu.asemenychev.task122;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.Test;

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
        assertEquals(1000, map.get(100 - 1));
    }
}