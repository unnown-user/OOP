package ru.nsu.asemenychev.task141;

import ru.nsu.asemenychev.task141.Grade.ControlType;
import ru.nsu.asemenychev.task141.Grade.GradeValue;

/**
 * Демонстрация работы зачетной книжки.
 */
public class Main {

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        GradeBook book = new GradeBook();

        // 1 семестр
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("ТВиМС", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("Английский язык", GradeValue.GOOD, ControlType.DIFF_PASS));
        book.addSemester(s1);

        // 2 семестр
        Semester s2 = new Semester(2);
        s2.addGrade(new Grade("Программирование на C++", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("ДУиТФКП", GradeValue.GOOD, ControlType.EXAM));
        s2.addGrade(new Grade("Хаскель",
                GradeValue.SATISFACTORY, ControlType.CREDIT)); // Зачет -> тройка допустима
        book.addSemester(s2);

        // 3 семестр (текущий)
        Semester s3 = new Semester(3);
        s3.addGrade(new Grade("Операционные системы", GradeValue.EXCELLENT, ControlType.EXAM));
        s3.addGrade(new Grade("ООП", GradeValue.EXCELLENT, ControlType.DIFF_PASS));
        book.addSemester(s3);

        // ВКР
        book.setQualificationWorkGrade(GradeValue.EXCELLENT);

        System.out.println("Средний балл: " + book.getCurrentAverage());
        System.out.println("Может перевестись на бюджет: " + book.canTransferToBudget());
        System.out.println("Может получить красный диплом: " + book.canGetRedDiploma());
        System.out.println("Может получать повышенную стипендию: "
                + book.canGetIncreasedScholarship());
    }
}
