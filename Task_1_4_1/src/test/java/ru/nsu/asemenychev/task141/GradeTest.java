package ru.nsu.asemenychev.task141;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GradeTest {

    @Test
    void testGradeGettersAndEnum() {
        Grade grade = new Grade("Java", Grade.GradeValue.EXCELLENT, Grade.ControlType.EXAM);
        assertEquals("Java", grade.getSubject());
        assertEquals(Grade.GradeValue.EXCELLENT, grade.getValue());
        assertEquals(Grade.ControlType.EXAM, grade.getType());

        // Проверка числового значения Enum
        assertEquals(5, grade.getValue().getVal());
        assertEquals(4, Grade.GradeValue.GOOD.getVal());
        assertEquals(3, Grade.GradeValue.SATISFACTORY.getVal());
        assertEquals(2, Grade.GradeValue.UNSATISFACTORY.getVal());
    }
}
