package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.scheduler.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class FacultyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Faculty(null));
    }

    @Test
    public void constructor_invalidFaculty_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, Faculty.MESSAGE_CONSTRAINTS, () -> new Faculty(""));
    }

    @Test
    public void isValidFaculty() {
        // null string
        assertThrows(NullPointerException.class, () -> Faculty.isValidFaculty(null));

        // invalid string -> return false
        assertFalse(Faculty.isValidFaculty(""));
        assertFalse(Faculty.isValidFaculty(" "));
        assertFalse(Faculty.isValidFaculty("\r"));
        assertFalse(Faculty.isValidFaculty("\t"));
        assertFalse(Faculty.isValidFaculty("\n"));

        // valid string -> return true
        assertTrue(Faculty.isValidFaculty("Computing"));
        assertTrue(Faculty.isValidFaculty("Computing "));
        assertTrue(Faculty.isValidFaculty("School of Computing"));
        assertTrue(Faculty.isValidFaculty("S c h o o l   o f   C o m p u t i n g "));
        assertTrue(Faculty.isValidFaculty("LoGiStIcs"));
        assertTrue(Faculty.isValidFaculty("Log1i5t1cs!@!"));
    }

    @Test
    public void hashCode_validFaculty_success() {
        String faculty = "School of Computing";
        assertEquals(faculty.hashCode(), new Faculty(faculty).hashCode());
    }
}
