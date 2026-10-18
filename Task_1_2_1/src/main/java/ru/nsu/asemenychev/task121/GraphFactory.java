package ru.nsu.asemenychev.task121;

import java.util.function.Supplier;

/**
 * Фабрика реализаций графа. Позволяет выбрать
 * тип графа по строковому имени — для чтения
 * из файла или добавлении новых реализаций.
 *
 * <p>Класс инкапсулирует логику выбора и создания конкретного
 * типа графа. Благодаря этому клиентский код не зависит от
 * конкретных классов реализаций ({@code AdjacencyMatrixGraph},
 * {@code AdjacencyListGraph} и т.д.).
 *
 * <p>Это нужно для расширяемости: если в будущем потребуется
 * добавить новое представление графа (например, на основе списка ребер),
 * достаточно будет создать новый класс {@code EdgeListGraph} и добавить
 * одну строку в метод {@link #byType(String)}. Весь остальной код,
 * который работает с графами через этот интерфейс, менять не придется.
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