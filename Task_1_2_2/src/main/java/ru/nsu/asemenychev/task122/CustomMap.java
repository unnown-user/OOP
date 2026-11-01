package ru.nsu.asemenychev.task122;

import java.util.Iterator;

/**
 * Интерфейс параметризованного словаря.
 *
 * @param <K> тип ключей
 * @param <V> тип значений
 */
public interface CustomMap<K, V> extends Iterable<HashNode<K, V>> {

    /**
     * Добавляет пару. Если ключ есть, перезаписывает значение.
     *
     * @return предыдущее значение или null
     */
    V put(K key, V value);

    /**
     * Обновляет значение. Если ключа нет — выбрасывает исключение.
     *
     * @throws java.util.NoSuchElementException если ключа нет
     */
    void update(K key, V value);

    /**
     * Удаляет пару, только если ключ И значение совпадают.
     *
     * @return true, если пара была удалена
     */
    boolean remove(K key, V value);

    /**
     * Возвращает значение по ключу или null.
     */
    V get(K key);

    /**
     * Проверяет наличие ключа в таблице.
     *
     * @return true, если ключ есть
     */
    boolean containsKey(K key);

    /**
     * Возвращает количество элементов в таблице.
     *
     * @return размер таблицы
     */
    int size();

    /**
     * @return итератор по узлам таблицы
     */
    @Override
    Iterator<HashNode<K, V>> iterator();
}