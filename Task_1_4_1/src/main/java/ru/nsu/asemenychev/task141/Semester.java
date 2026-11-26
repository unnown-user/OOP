package ru.nsu.asemenychev.task141;

import java.util.ArrayList;
import java.util.List;

/**
 * Семестр, содержащий список полученных оценок.
 */
public class Semester {

    private final int number;
    private final List<Grade> grades = new ArrayList<>();

    /**
     * Создает новый семестр.
     *
     * @param number номер семестра
     */
    public Semester(int number) {
        this.number = number;
    }

    /**
     * Добавляет оценку в семестр.
     *
     * @param grade добавляемая оценка
     */
    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    /**
     * Возвращает номер семестра.
     *
     * @return номер семестра
     */
    public int getNumber() {
        return number;
    }

    /**
     * Возвращает список оценок за семестр.
     *
     * @return список оценок
     */
    public List<Grade> getGrades() {
        return grades;
    }
}