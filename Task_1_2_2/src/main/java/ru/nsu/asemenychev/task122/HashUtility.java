package ru.nsu.asemenychev.task122;

/**
 * Утилитарные методы для хеш-таблицы.
 */
public final class HashUtility {

    private static final int MAX_INT = 0x7FFFFFFF;

    /**
     * Вычисляет индекс корзины (защита от отрицательного hashCode).
     *
     * @param hash       хеш ключа
     * @param bucketSize размер массива
     * @return индекс от 0 до bucketSize - 1
     */
    public static int getBucketIndex(int hash, int bucketSize) {
        return (hash & MAX_INT) % bucketSize;
    }

    /**
     * Вычисляет следующую емкость для ресайза (обычно x2),
     * проверяет, достигнут ли максимальный размер таблицы.
     */
    public static int nextCapacity(int currentCapacity) {
        int newCapacity = currentCapacity * 2;
        if (newCapacity < 0) {
            throw new IllegalStateException("Достигнут максимальный размер таблицы");
        }

        return newCapacity;
    }
}
