package ru.nsu.asemenychev.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.asemenychev.task141.Grade.ControlType;
import ru.nsu.asemenychev.task141.Grade.GradeValue;

class GradeBookTest {

    private GradeBook book;

    @BeforeEach
    void setUp() {
        book = new GradeBook();
    }

    @Test
    void testAverageCalculation() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("Java", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("C++", GradeValue.GOOD, ControlType.EXAM));
        book.addSemester(s1);

        assertEquals(4.5, book.getCurrentAverage(), 0.01);
    }

    @Test
    void testCannotTransferToBudgetIfBadGradeInLastTwoSemesters() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("Math", GradeValue.SATISFACTORY, ControlType.EXAM)); // Тройка на экзамене
        book.addSemester(s1);

        assertFalse(book.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudgetIfBadGradeWasLongAgo() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("English", GradeValue.SATISFACTORY, ControlType.EXAM)); // Тройка на 1 курсе
        book.addSemester(s1);

        book.addSemester(new Semester(2));
        book.addSemester(new Semester(3)); // Прошло больше 2 сессий

        assertTrue(book.canTransferToBudget());
    }

    @Test
    void testCannotGetRedDiplomaWithLessThan75PercentExcellent() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.GOOD, ControlType.EXAM)); // 1 из 2 -> 50%
        book.addSemester(s1);
        book.setQualificationWorkGrade(GradeValue.EXCELLENT);

        assertFalse(book.canGetRedDiploma());
    }

    @Test
    void testCannotGetRedDiplomaIfThesisIsNotExcellent() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        book.addSemester(s1);
        book.setQualificationWorkGrade(GradeValue.GOOD); // ВКР - хорошо

        assertFalse(book.canGetRedDiploma());
    }

    @Test
    void testCanGetIncreasedScholarship() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.GOOD, ControlType.EXAM));
        book.addSemester(s1);

        // Средний балл 4.5, троек нет
        assertTrue(book.canGetIncreasedScholarship());
    }

    @Test
    void testCannotGetIncreasedScholarshipWithSatisfactoryGrade() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.SATISFACTORY, ControlType.EXAM));
        book.addSemester(s1);

        assertFalse(book.canGetIncreasedScholarship());
    }
}