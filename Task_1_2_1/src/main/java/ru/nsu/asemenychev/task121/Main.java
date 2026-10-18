package ru.nsu.asemenychev.task121;

/**
 * Демонстрация работы с графами и топологической сортировкой.
 */
public class Main {

    /**
     * Запускает примеры: строит один и тот же граф тремя способами,
     * сравнивает их, сортирует разными алгоритмами и обрабатывает циклы.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        // --- 1. Создание трех видов графов ---
        Graph matrix = new AdjacencyMatrixGraph();
        Graph list = new AdjacencyListGraph();
        Graph incidence = new IncidenceMatrixGraph();

        Graph[] graphs = {matrix, list, incidence};

        for (Graph g : graphs) {
            g.addVertex("A");
            g.addVertex("B");
            g.addVertex("C");
            g.addVertex("D");
            g.addEdge("A", "B");
            g.addEdge("A", "C");
            g.addEdge("B", "D");
            g.addEdge("C", "D");
        }

        // --- 2. Вывод в консоль ---
        System.out.println("--- Матрица смежности ---");
        System.out.println(matrix);

        System.out.println("--- Список смежности ---");
        System.out.println(list);

        System.out.println("--- Матрица инцидентности ---");
        System.out.println(incidence);

        // --- 3. Сравнение (equals) ---
        System.out.println("--- Сравнение на равенство ---");
        System.out.println("matrix.equals(list):      " + matrix.equals(list));
        System.out.println("list.equals(incidence):   " + list.equals(incidence));
        System.out.println("Хэш-коды одинаковы:       "
                + (matrix.hashCode() == list.hashCode()
                && list.hashCode() == incidence.hashCode()));

        // --- 4. Топологическая сортировка (по умолчанию Кан) ---
        System.out.println("\n--- Топологическая сортировка (по умолчанию — Кан) ---");
        System.out.println("matrix:    " + matrix.topologicalSort());
        System.out.println("list:      " + list.topologicalSort());
        System.out.println("incidence: " + incidence.topologicalSort());

        // --- 5. Смена алгоритма сортировки на DFS ---
        System.out.println("\n--- Смена алгоритма на DFS (через Strategy) ---");
        // Приводим тип к AbstractGraph, так как setSorter объявлен там
        ((AbstractGraph) list).setSorter(new DfsSorter());
        System.out.println("list (DFS): " + list.topologicalSort());

        // --- 6. Обработка цикла ---
        System.out.println("\n--- Демонстрация обнаружения цикла ---");
        Graph cyclic = new AdjacencyListGraph();
        cyclic.addVertex("X");
        cyclic.addVertex("Y");
        cyclic.addVertex("Z");
        cyclic.addEdge("X", "Y");
        cyclic.addEdge("Y", "Z");
        cyclic.addEdge("Z", "X"); // Создаем цикл

        try {
            cyclic.topologicalSort();
            System.out.println("ОШИБКА: Цикл не был обнаружен!");
        } catch (IllegalStateException e) {
            System.out.println("Ожидаемая ошибка: " + e.getMessage());
        }

        // --- 7. Чтение из файла (пример) ---
        System.out.println("\n--- Чтение из файла ---");
        // Для примера создадим временный файл
        try {
            java.nio.file.Path tmp = java.nio.file.Files.createTempFile("test-graph", ".txt");
            java.nio.file.Files.writeString(tmp, "3\nA\nB\nC\n2\nA B\nB C\n");

            Graph fromFile = GraphIo.readFromFile(tmp.toString(), AdjacencyListGraph::new);
            System.out.println("Успешно прочитан граф из файла:");
            System.out.println(fromFile);

            java.nio.file.Files.deleteIfExists(tmp);
        } catch (Exception e) {
            System.out.println("Ошибка при чтении из файла: " + e.getMessage());
        }
    }
}