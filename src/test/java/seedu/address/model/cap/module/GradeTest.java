package seedu.address.model.cap.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.person.Grade;

public class GradeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Grade(null));
    }

    @Test
    public void constructor_invalidGrade_throwsIllegalArgumentException() {
        String invalidGrade = "";
        assertThrows(IllegalArgumentException.class, () -> new Grade(invalidGrade));
    }


    @Test
    public void isValidGrade() {
        // null Grade
        assertThrows(NullPointerException.class, () -> Grade.isValidGrade(null));

        // blank Grade
        assertFalse(Grade.isValidGrade("")); // empty string
        assertFalse(Grade.isValidGrade(" ")); // spaces only

        // invalid parts
        assertFalse(Grade.isValidGrade("asfafef"));
        assertFalse(Grade.isValidGrade("fass"));
        assertFalse(Grade.isValidGrade(" a"));

        // missing parts
        assertFalse(Grade.isValidGrade("f")); // missing s
        assertFalse(Grade.isValidGrade("-")); // missing spaces
        assertFalse(Grade.isValidGrade("+")); // missing spaces

        // valid Grade
        assertTrue(Grade.isValidGrade("A"));
        assertTrue(Grade.isValidGrade("A-"));
        assertTrue(Grade.isValidGrade("B"));
        assertTrue(Grade.isValidGrade("B+"));
        assertTrue(Grade.isValidGrade("B-"));
        assertTrue(Grade.isValidGrade("C+"));
        assertTrue(Grade.isValidGrade("C"));
        assertTrue(Grade.isValidGrade("D"));
    }
}
