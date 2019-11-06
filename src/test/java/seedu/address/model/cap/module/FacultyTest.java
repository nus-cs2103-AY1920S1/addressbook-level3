package seedu.address.model.cap.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.cap.person.Faculty;

public class FacultyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Faculty(null));
    }

    @Test
    public void constructor_invalidFaculty_throwsIllegalArgumentException() {
        String invalidFaculty = "";
        assertThrows(IllegalArgumentException.class, () -> new Faculty(invalidFaculty));
    }


    @Test
    public void isValidFaculty() {
        // null Faculty
        assertThrows(NullPointerException.class, () -> Faculty.isValidFaculty(null));

        // blank Faculty
        assertFalse(Faculty.isValidFaculty("")); // empty string
        assertFalse(Faculty.isValidFaculty(" ")); // spaces only

        // invalid parts
        assertFalse(Faculty.isValidFaculty("asfafef"));
        assertFalse(Faculty.isValidFaculty("fass"));
        assertFalse(Faculty.isValidFaculty(" law"));

        // missing parts
        assertFalse(Faculty.isValidFaculty("busine")); // missing s
        assertFalse(Faculty.isValidFaculty("LifeLongLearning")); // missing spaces
        assertFalse(Faculty.isValidFaculty("nus")); // missing spaces

        // valid Faculty
        assertTrue(Faculty.isValidFaculty("computing"));
        assertTrue(Faculty.isValidFaculty("nus business school"));
        assertTrue(Faculty.isValidFaculty("business"));
        assertTrue(Faculty.isValidFaculty("engineering"));
        assertTrue(Faculty.isValidFaculty("integrative sciences and engineering"));
        assertTrue(Faculty.isValidFaculty("music"));
        assertTrue(Faculty.isValidFaculty("law"));
        assertTrue(Faculty.isValidFaculty("science"));
    }
}
