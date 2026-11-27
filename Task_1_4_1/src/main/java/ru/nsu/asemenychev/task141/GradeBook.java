package ru.nsu.asemenychev.task141;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import ru.nsu.asemenychev.task141.Grade.ControlType;
import ru.nsu.asemenychev.task141.Grade.GradeValue;

/**
 * Электронная зачетная книжка студента.
 */
public class GradeBook {

    private final List<Semester> semesters = new ArrayList<>();
    // Форма обучения: платная/бесплатная
    private boolean isPaidTuition = true;  // По умолчанию платное
    // Оценка за ВКР
    private GradeValue qualificationWorkGrade = null;

    /**
     * Добавляет информацию о завершенном семестре.
     *
     * @param semester добавляемый семестр
     */
    public void addSemester(Semester semester) {
        semesters.add(semester);
    }

    /**
     * Устанавливает текущую форму обучения.
     *
     * @param paid {@code true}, если обучение платное
     */
    public void setPaidTuition(boolean paid) {
        this.isPaidTuition = paid;
    }

    /**
     * Устанавливает оценку за выпускную квалификационную работу (ВКР).
     *
     * @param grade оценка за ВКР
     */
    public void setQualificationWorkGrade(GradeValue grade) {
        this.qualificationWorkGrade = grade;
    }

    /**
     * Вычисляет текущий средний балл за все время обучения.
     *
     * @return средний балл
     */
    public double getCurrentAverage() {
        return semesters.stream()
                .flatMap(s -> s.getGrades().stream())
                .mapToInt(g -> g.getValue().getVal())
                .average()
                .orElse(0.0);
    }

    /**
     * Проверяет возможность перевода с платной формы на бюджетную.
     * Условие: отсутствие троек на экзаменах за последние две сессии.
     *
     * @return {@code true}, если перевод возможен
     */
    public boolean canTransferToBudget() {
        if (!isPaidTuition) {
            return false;  // Уже на бюджете
        }
        if (semesters.isEmpty()) {
            return true;  // Нет оценок -> нет троек
        }

        // Последние 2 семестра
        List<Semester> lastTwo = semesters.stream()
                .skip(Math.max(0, semesters.size() - 2))
                .collect(Collectors.toList());

        // Проверяем, что нет ни одной тройки на экзамене
        return lastTwo.stream()
                .flatMap(s -> s.getGrades().stream())
                .noneMatch(g -> g.getType() == ControlType.EXAM
                        && g.getValue() == GradeValue.SATISFACTORY);
    }

    /**
     * Проверяет возможность получения красного диплома.
     * Условия: 75% отличных оценок, отсутствие троек, ВКР на отлично.
     *
     * @return {@code true}, если красный диплом возможен
     */
    public boolean canGetRedDiploma() {
        if (semesters.isEmpty()) {
            return false;
        }

        if (qualificationWorkGrade != null && qualificationWorkGrade != GradeValue.EXCELLENT) {
            return false;
        }

        List<Grade> allGrades = semesters.stream()
                .flatMap(s -> s.getGrades().stream())
                .collect(Collectors.toList());

        // Если есть хоть одна тройка (или хуже) -> красный диплом невозможен
        if (allGrades.stream().anyMatch(g -> g.getValue().getVal() <= 3)) {
            return false;
        }

        // Считаем проценты (учитываем только экзамены и диф. зачеты)
        List<Grade> gradedSubjects = allGrades.stream()
                .filter(g -> g.getType() == ControlType.EXAM
                        || g.getType() == ControlType.DIFF_PASS)
                .collect(Collectors.toList());

        if (gradedSubjects.isEmpty()) {
            return false;
        }

        long excellentCount = gradedSubjects.stream()
                .filter(g -> g.getValue() == GradeValue.EXCELLENT)
                .count();

        return (double) excellentCount / gradedSubjects.size() >= 0.75;
    }

    /**
     * Проверяет возможность получения повышенной стипендии в текущем семестре.
     *
     * @return {@code true}, если стипендия положена
     */
    public boolean canGetIncreasedScholarship() {
        if (semesters.isEmpty()) {
            return false;
        }

        Semester currentSemester = semesters.get(semesters.size() - 1);
        List<Grade> currentGrades = currentSemester.getGrades();

        if (currentGrades.isEmpty()) {
            return false;
        }

        // Есть тройки (или хуже) -> стипендии нет
        if (currentGrades.stream().anyMatch(g -> g.getValue().getVal() <= 3)) {
            return false;
        }

        // Средний балл текущего семестра
        double average = currentGrades.stream()
                .mapToInt(g -> g.getValue().getVal())
                .average()
                .orElse(0.0);

        return average >= 4.5;  // Стандартное требование для ПГАС
    }
}
