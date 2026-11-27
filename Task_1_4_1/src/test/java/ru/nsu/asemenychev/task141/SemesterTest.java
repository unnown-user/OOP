package ru.nsu.asemenychev.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SemesterTest {

    @Test
    void testSemester() {
        Semester semester = new Semester(1);
        assertEquals(1, semester.getNumber());
        assertTrue(semester.getGrades().isEmpty());

        Grade grade = new Grade("C++", Grade.GradeValue.GOOD, Grade.ControlType.EXAM);
        semester.addGrade(grade);

        assertEquals(1, semester.getGrades().size());
        assertEquals("C++", semester.getGrades().get(0).getSubject());
    }
}
