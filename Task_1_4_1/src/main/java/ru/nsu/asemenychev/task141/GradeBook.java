package ru.nsu.asemenychev.task141;

import java.util.ArrayList;
import java.util.List;
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
        if (semesters.isEmpty()) {
            return 0.0;
        }

        int sum = 0;
        int count = 0;
        for (Semester s : semesters) {
            for (Grade g : s.getGrades()) {
                sum += g.getValue().getVal();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) sum / count;
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

        int lastIndex = semesters.size() - 1;
        int startIndex = Math.max(0, semesters.size() - 2);  // Последние 2 семестра

        for (int i = startIndex; i <= lastIndex; i++) {
            Semester s = semesters.get(i);
            for (Grade g : s.getGrades()) {
                if (g.getType() == ControlType.EXAM && g.getValue() == GradeValue.SATISFACTORY) {
                    return false;  // Нашли тройку на экзамене
                }
            }
        }
        return true;
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
        if (qualificationWorkGrade != GradeValue.EXCELLENT) {
            return false;
        }

        int totalGrades = 0;
        int excellentGrades = 0;

        for (Semester s : semesters) {
            for (Grade g : s.getGrades()) {
                if (g.getValue().getVal() <= 3) {
                    return false;  // Есть хоть одна тройка (или хуже) -> красный диплом невозможен
                }

                // Считаем проценты (учитываем только экзамены и диф. зачеты)
                if (g.getType() == ControlType.EXAM || g.getType() == ControlType.DIFF_PASS) {
                    totalGrades++;
                    if (g.getValue() == GradeValue.EXCELLENT) {
                        excellentGrades++;
                    }
                }
            }
        }

        if (totalGrades == 0) {
            return false;
        }
        return (double) excellentGrades / totalGrades >= 0.75;
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
        if (currentSemester.getGrades().isEmpty()) {
            return false;
        }

        int sum = 0;
        for (Grade g : currentSemester.getGrades()) {
            if (g.getValue().getVal() <= 3) {
                return false;  // Есть тройки (или хуже) -> стипендии нет
            }
            sum += g.getValue().getVal();
        }

        double semesterAverage = (double) sum / currentSemester.getGrades().size();
        return semesterAverage >= 4.5;  // Стандартное требование
    }
}