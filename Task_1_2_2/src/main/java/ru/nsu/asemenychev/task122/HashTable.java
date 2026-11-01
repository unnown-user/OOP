package ru.nsu.asemenychev.task122;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Хеш-таблица с разрешением коллизий методом цепочек.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public class HashTable<K, V> implements CustomMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    // Массив для хранения узлов
    HashNode<K, V>[] table;
    // Текущее количество элементов в таблице
    private int size;
    // Текущая емкость массива (длина table)
    int capacity;
    // Счетчик изменений (для итератора)
    int modCount;

    /**
     * Создает пустую хеш-таблицу.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.modCount = 0;
        this.table = (HashNode<K, V>[]) new HashNode[capacity];
    }

    /**
     * Вычисляет хеш ключа (безопасно для null).
     */
    private int hash(K key) {
        return (key == null) ? 0 : key.hashCode();
    }

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null");
        }

        int hash = hash(key);
        int index = HashUtility.getBucketIndex(hash, capacity);

        // Ищем, есть ли уже такой ключ в цепочке
        HashNode<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                // Обновляем значение
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }

            node = node.next;
        }

        // Если ключа нет, добавляем новый узел в начало цепочки
        HashNode<K, V> newFirstNode = new HashNode<K, V>(key, value, hash, table[index]);
        table[index] = newFirstNode;

        size++;
        modCount++;

        if (size > capacity * LOAD_FACTOR) {
            resize();
        }

        return null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Ключ не может быть null");
        }

        int hash = hash(key);
        int index = HashUtility.getBucketIndex(hash, capacity);

        // Ищем, есть ли уже такой ключ в цепочке
        HashNode<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                return node.value;
            }

            node = node.next;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        HashNode<K, V>[] oldTable = table;

        capacity = HashUtility.nextCapacity(capacity);
        table = (HashNode<K, V>[]) new HashNode[capacity];

        // Обходим старый массив
        for (HashNode<K, V> head : oldTable) {
            HashNode<K, V> node = head;
            while (node != null) {
                HashNode<K, V> next = node.next;
                int newIndex = HashUtility.getBucketIndex(node.hash, capacity);

                node.next = table[newIndex];
                table[newIndex] = node;

                node = next;
            }
        }
        modCount++;
    }

    @Override
    public void update(K key, V value) {
        if (!containsKey(key)) {
            throw new NoSuchElementException("Ключ не найден: " + key);
        }
        put(key, value);
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        int hash = hash(key);
        int index = HashUtility.getBucketIndex(hash, capacity);

        HashNode<K, V> node = table[index];
        while (node != null) {
            if (node.hash == hash && Objects.equals(node.key, key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(K key, V value) {
        int hash = hash(key);
        int index = HashUtility.getBucketIndex(hash, capacity);

        HashNode<K, V> node = table[index];
        HashNode<K, V> prev = null;
        while (node != null) {
            if (node.hash == hash
                    && Objects.equals(key, node.key)
                    && Objects.equals(value, node.value)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }

                size--;
                modCount++;

                return true;
            }

            prev = node;
            node = node.next;
        }

        return false;
    }

    @Override
    public Iterator<HashNode<K, V>> iterator() {
        return new HashTableIterator<>(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashTable<?, ?>)) {
            return false;
        }

        HashTable<K, V> other = (HashTable<K, V>) o;

        if (this.size != other.size) {
            return false;
        }

        for (HashNode<K, V> head : table) {
            HashNode<K, V> node = head;
            while (node != null) {
                Object otherValue = other.get(node.key);

                if (!Objects.equals(node.value, otherValue)) {
                    return false;
                }
                node = node.next;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (HashNode<K, V> head : table) {
            HashNode<K, V> node = head;
            while (node != null) {
                h += Objects.hashCode(node.key) ^ Objects.hashCode(node.value);
                node = node.next;
            }
        }
        return h;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        Iterator<HashNode<K, V>> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
