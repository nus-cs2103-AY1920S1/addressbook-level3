package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LessonNoTest {
    private LessonNo lessonNo;
    private LessonNo lessonNo2;
    private LessonNo lessonNo3;

    @BeforeEach
    void setUp() {
        lessonNo = new LessonNo("G01");
        lessonNo2 = new LessonNo("G01");
        lessonNo3 = new LessonNo("D17");
    }

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonNo(null));
    }

    @Test
    void constructor_invalidLessonNo_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new LessonNo("!@)!$*@#"));
    }

    @Test
    void testEquals() {
        // same object -> return true
        assertTrue(lessonNo.equals(lessonNo));

        // same values -> return true
        assertTrue(lessonNo.equals(lessonNo2));

        // null -> returns false
        assertFalse(lessonNo.equals(null));

        // different values -> returns false
        assertFalse(lessonNo.equals(lessonNo3));
    }

    @Test
    void testHashCode() {
        assertEquals(lessonNo.hashCode(), lessonNo.hashCode());
        assertEquals(lessonNo.hashCode(), lessonNo2.hashCode());
        assertNotEquals(lessonNo.hashCode(), lessonNo3.hashCode());
    }
}
