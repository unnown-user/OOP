package ru.nsu.asemenychev.task122;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Тесты для утилитарного класса HashUtility.
 */
class HashUtilityTest {

    @Test
    void testGetBucketIndexPositiveHash() {
        assertEquals(15, HashUtility.getBucketIndex(15, 16));
        assertEquals(0, HashUtility.getBucketIndex(16, 16));
    }

    @Test
    void testGetBucketIndexNegativeHash() {
        // В Java -15 % 16 = -15, но благодаря & 0x7FFFFFFF мы получаем положительный индекс.
        // -15 в бинарном виде превращается в большое положительное число.
        // Проверим, что метод всегда возвращает валидный индекс от 0 до capacity-1.
        int index = HashUtility.getBucketIndex(-15, 16);
        assertTrue(index >= 0 && index < 16);
    }

    @Test
    void testNextCapacityNormal() {
        assertEquals(32, HashUtility.nextCapacity(16));
        assertEquals(64, HashUtility.nextCapacity(32));
    }

    @Test
    void testNextCapacityOverflow() {
        // Если емкость станет больше максимально возможного int,
        // должно выброситься исключение (защита от переполнения)
        assertThrows(IllegalStateException.class, () -> {
            HashUtility.nextCapacity(Integer.MAX_VALUE / 2 + 1);
        });
    }

    // Вспомогательный метод для проверки
    private static void assertTrue(boolean condition) {
        org.junit.jupiter.api.Assertions.assertTrue(condition);
    }
}