package ru.nsu.asemenychev.task122;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Итератор для обхода хеш-таблицы.
 */
public class HashTableIterator<K, V> implements Iterator<HashNode<K, V>> {

    private final HashTable<K, V> table;
    private final int expectedModCount;
    private int currentBucket;
    private HashNode<K, V> nextNode;

    /**
     * Конструктор итератора.
     * Находит первый непустой узел в таблице.
     */
    public HashTableIterator(HashTable<K, V> table) {
        this.table = table;
        this.expectedModCount = table.modCount;
        this.currentBucket = 0;
        this.nextNode = null;

        findNextNode();
    }

    /**
     * Вспомогательный метод: ищет следующий узел.
     * Если текущая очередь закончилась, он идет к следующему дому.
     */
    private void findNextNode() {
        while (currentBucket < table.capacity) {
            if (table.table[currentBucket] != null) {
                nextNode = table.table[currentBucket];
                return;
            }
            currentBucket++;
        }
        nextNode = null;
    }

    /**
     * Наш предохранитель. Если таблицу изменили — падаем с ошибкой.
     */
    private void checkForComodification() {
        if (table.modCount != expectedModCount) {
            throw new ConcurrentModificationException("Таблица была изменена во время итерации");
        }
    }

    @Override
    public boolean hasNext() {
        checkForComodification();
        return nextNode != null;
    }

    @Override
    public HashNode<K, V> next() {
        checkForComodification();
        if (nextNode == null) {
            throw new NoSuchElementException("Элементов больше нет");
        }

        HashNode<K, V> result = nextNode;
        nextNode = nextNode.next;

        if (nextNode == null) {
            currentBucket++;
            findNextNode();
        }

        return result;
    }
}