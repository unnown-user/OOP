package ru.nsu.asemenychev.task122;

/**
 * Узел хеш-таблицы, представляющий пару ключ-значение.
 *
 * @param <K> тип ключа
 * @param <V> тип значения
 */
public class HashNode<K, V> {
    final K key;
    V value;
    final int hash;
    HashNode<K, V> next;

    /**
     * Конструктор: создает новый узел.
     *
     * @param key   ключ
     * @param value значение
     * @param hash  хеш-код
     * @param next  следующий узел
     */
    public HashNode(K key, V value, int hash, HashNode<K, V> next) {
        this.key = key;
        this.value = value;
        this.hash = hash;
        this.next = next;
    }

    /**
     * Возвращает ключ узла.
     *
     * @return ключ узла
     */
    public K getKey() {
        return key;
    }

    /**
     * Возвращает значение узла.
     *
     * @return значение узла
     */
    public V getValue() {
        return value;
    }
    @Override
    public String toString() {
        return key + "=" + value;
    }
}
