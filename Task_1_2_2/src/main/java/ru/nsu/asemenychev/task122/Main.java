package ru.nsu.asemenychev.task122;

/**
 * Демонстрация работы HashTable.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        HashTable<String, Number> hashTable = new HashTable<>();

        hashTable.put("one", 1);
        hashTable.update("one", 1.0);
        System.out.println(hashTable.get("one")); // Ожидается 1.0

        // Демонстрация коллизий
        hashTable.put("two", 2);
        hashTable.put("three", 3);
        System.out.println("Таблица: " + hashTable);

        // Демонстрация ConcurrentModificationException
        try {
            for (HashNode<String, Number> node : hashTable) {
                System.out.println("Итерация: " + node);
                hashTable.put("four", 4);
            }
        } catch (java.util.ConcurrentModificationException e) {
            System.out.println("Поймано исключение: " + e.getMessage());
        }
    }
}
