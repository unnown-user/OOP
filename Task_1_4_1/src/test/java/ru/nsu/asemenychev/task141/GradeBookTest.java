package ru.nsu.asemenychev.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.asemenychev.task141.Grade.ControlType;
import ru.nsu.asemenychev.task141.Grade.GradeValue;

class GradeBookTest {

    // ==========================================
    // Средний балл
    // ==========================================

    @Test
    void testAverageEmptySemesters() {
        GradeBook book = new GradeBook();
        assertEquals(0.0, book.getCurrentAverage());  // semesters.isEmpty()
    }

    @Test
    void testAverageEmptyGrades() {
        GradeBook book = new GradeBook();
        book.addSemester(new Semester(1));  // Семестр есть, но оценок нет
        assertEquals(0.0, book.getCurrentAverage());  // count == 0
    }

    @Test
    void testAverageCalculation() {
        GradeBook book = new GradeBook();
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.GOOD, ControlType.EXAM));
        book.addSemester(s1);

        assertEquals(4.5, book.getCurrentAverage());
    }

    // ==========================================
    // Перевод на бюджет
    // ==========================================

    @Test
    void testTransferAlreadyBudget() {
        GradeBook book = new GradeBook();
        book.setPaidTuition(false);
        assertFalse(book.canTransferToBudget());  // !isPaidTuition
    }

    @Test
    void testTransferEmptyBook() {
        GradeBook book = new GradeBook();  // По умолчанию платное
        assertTrue(book.canTransferToBudget());  // semesters.isEmpty()
    }

    @Test
    void testTransferWithBadGradeOnExamInLastTwoSemesters() {
        GradeBook book = new GradeBook();
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        Semester s2 = new Semester(2);
        s2.addGrade(new Grade("B",
                GradeValue.SATISFACTORY, ControlType.EXAM));  // Тройка на экзамене

        book.addSemester(s1);
        book.addSemester(s2);
        assertFalse(book.canTransferToBudget());  // Ветка нашли тройку
    }

    @Test
    void testTransferWithBadGradeOnCreditIsAllowed() {
        GradeBook book = new GradeBook();
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A",
                GradeValue.SATISFACTORY, ControlType.CREDIT));  // Тройка на зачете
        book.addSemester(s1);
        assertTrue(book.canTransferToBudget());  // Тройка на зачете не мешает
    }

    @Test
    void testTransferBadGradeWasThreeSemestersAgo() {
        GradeBook book = new GradeBook();
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A",
                GradeValue.SATISFACTORY, ControlType.EXAM));  // Тройка в 1 семестре
        book.addSemester(s1);
        book.addSemester(new Semester(2));
        book.addSemester(new Semester(3));  // Текущий 3-й, 1-й уже не считается

        assertTrue(book.canTransferToBudget());  // Тройка вне последних 2 сессий
    }

    @Test
    void testTransferOnlyOneSemesterWithBadGrade() {
        GradeBook book = new GradeBook();
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.SATISFACTORY, ControlType.EXAM));
        book.addSemester(s1);

        assertFalse(book.canTransferToBudget());
    }

    // ==========================================
    // Красный диплом
    // ==========================================

    @Test
    void testRedDiplomaEmptyBook() {
        GradeBook book = new GradeBook();
        assertFalse(book.canGetRedDiploma());  // semesters.isEmpty()
    }

    @Test
    void testRedDiplomaBadVkr() {
        GradeBook book = new GradeBook();
        book.addSemester(new Semester(1));
        book.setQualificationWorkGrade(GradeValue.GOOD);
        assertFalse(book.canGetRedDiploma());  // ВКР != отлично
    }

    @Test
    void testRedDiplomaHasSatisfactory() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.SATISFACTORY, ControlType.DIFF_PASS));

        final GradeBook book = new GradeBook();
        book.addSemester(s1);
        book.setQualificationWorkGrade(GradeValue.EXCELLENT);

        assertFalse(book.canGetRedDiploma());  // Нашли тройку (getVal <= 3)
    }

    @Test
    void testRedDiplomaNoExamsOrDiffPasses() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.CREDIT));

        final GradeBook book = new GradeBook();
        book.addSemester(s1);
        book.setQualificationWorkGrade(GradeValue.EXCELLENT);

        assertFalse(book.canGetRedDiploma());  // totalGrades == 0
    }

    @Test
    void testRedDiplomaLessThan75Percent() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.GOOD, ControlType.EXAM));  // 1 из 2 -> 50%

        final GradeBook book = new GradeBook();
        book.addSemester(s1);
        book.setQualificationWorkGrade(GradeValue.EXCELLENT);

        assertFalse(book.canGetRedDiploma());  // < 0.75
    }

    @Test
    void testRedDiplomaExactly75Percent() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("C", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("D", GradeValue.GOOD, ControlType.EXAM));  // 3 из 4 -> 75%

        final GradeBook book = new GradeBook();
        book.addSemester(s1);
        book.setQualificationWorkGrade(GradeValue.EXCELLENT);

        assertTrue(book.canGetRedDiploma());  // >= 0.75
    }

    // ==========================================
    // Стипендия
    // ==========================================

    @Test
    void testScholarshipEmptyBook() {
        GradeBook book = new GradeBook();
        assertFalse(book.canGetIncreasedScholarship());  // semesters.isEmpty()
    }

    @Test
    void testScholarshipLastSemesterEmpty() {
        GradeBook book = new GradeBook();
        book.addSemester(new Semester(1));  // Последний семестр пуст
        assertFalse(book.canGetIncreasedScholarship());  // currentSemester.getGrades().isEmpty()
    }

    @Test
    void testScholarshipHasBadGrade() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.GOOD, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.SATISFACTORY, ControlType.EXAM));  // Тройка

        final GradeBook book = new GradeBook();
        book.addSemester(s1);

        assertFalse(book.canGetIncreasedScholarship());  // getVal <= 3
    }

    @Test
    void testScholarshipLowAverage() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.GOOD, ControlType.EXAM));
        s1.addGrade(new Grade("C", GradeValue.GOOD, ControlType.EXAM));  // 5+4+4 -> 4.33

        final GradeBook book = new GradeBook();
        book.addSemester(s1);

        assertFalse(book.canGetIncreasedScholarship());  // < 4.5
    }

    @Test
    void testScholarshipHighAverage() {
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("C", GradeValue.GOOD, ControlType.EXAM));  // 5+5+4 -> 4.66

        final GradeBook book = new GradeBook();
        book.addSemester(s1);

        assertTrue(book.canGetIncreasedScholarship());  // >= 4.5
    }

    @Test
    void testRedDiplomaForecastWithoutVkr() {
        // Студент учится на 2 курсе, ВКР еще не защищал
        Semester s1 = new Semester(1);
        s1.addGrade(new Grade("A", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("B", GradeValue.EXCELLENT, ControlType.EXAM));
        s1.addGrade(new Grade("C", GradeValue.GOOD, ControlType.EXAM));  // 2 из 3 -> 66%

        Semester s2 = new Semester(2);
        s2.addGrade(new Grade("D", GradeValue.EXCELLENT, ControlType.EXAM)); // Стало 3 из 4 -> 75%

        GradeBook book = new GradeBook();
        book.addSemester(s1);
        book.addSemester(s2);

        assertTrue(book.canGetRedDiploma());
    }
}
