package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExamTest {
    private LocalDateTime examDate1 = LocalDateTime.parse("2019-12-04T09:00:00.000");
    private LocalDateTime examDate2 = LocalDateTime.parse("2019-12-10T11:00:00.000");
    private Exam exam1;
    private Exam exam2;
    private Exam exam3;

    @BeforeEach
    void setup() {
        exam1 = new Exam(examDate1, 120);
        exam2 = new Exam(examDate1, 120);
        exam3 = new Exam(examDate2, 180);
    }

    @Test
    void testEquals() {
        // same values -> return true
        assertTrue(exam1.equals(exam2));

        // same object -> return true
        assertTrue(exam1.equals(exam1));

        // null -> returns false
        assertFalse(exam1.equals(null));

        // different exam -> returns false
        assertFalse(exam1.equals(exam3));
    }

    @Test
    void testGetExamDate() {
        assertEquals(examDate1, exam1.getExamDate());
    }

    @Test
    void testGetExamDuration() {
        assertEquals(120, exam1.getExamDuration());
    }

    @Test
    void testToString() {
        assertEquals("Exam Date: 2019-12-04T09:00 120", exam1.toString());
    }

    @Test
    void testHashCode() {
        assertEquals(exam1.hashCode(), exam1.hashCode());
        assertEquals(exam1.hashCode(), exam2.hashCode());
        assertNotEquals(exam1.hashCode(), exam3.hashCode());
    }
}
