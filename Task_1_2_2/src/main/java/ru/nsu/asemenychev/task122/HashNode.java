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

    public HashNode(K key, V value, int hash, HashNode<K, V> next) {
        this.key = key;
        this.value = value;
        this.hash = hash;
        this.next = next;
    }

    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}
