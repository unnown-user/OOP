package ru.nsu.asemenychev.task121;

import java.util.function.Supplier;

/**
 * Фабрика реализаций графа. Позволяет выбрать тип графа
 * по строковому имени — полезно при чтении из файла
 * или при добавлении новых реализаций.
 */
public final class GraphFactory {

    private GraphFactory() {
    }

    /**
     * @param type имя типа: {@code "matrix"}, {@code "list"}, {@code "incidence"}
     * @return функция-конструктор графа
     */
    public static Supplier<Graph> byType(String type) {
        switch (type.toLowerCase()) {
            case "matrix":
                return AdjacencyMatrixGraph::new;
            case "list":
                return AdjacencyListGraph::new;
            case "incidence":
                return IncidenceMatrixGraph::new;
            default:
                throw new IllegalArgumentException("Неизвестный тип графа: " + type);
        }
    }
}