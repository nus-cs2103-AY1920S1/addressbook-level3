package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MatricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Matric(null));
    }

    @Test
    public void constructor_invalidMatric_throwsIllegalArgumentException() {
        String invalidMatric = "";
        assertThrows(IllegalArgumentException.class, () -> new Matric(invalidMatric));
    }

    @Test
    public void isValidMatric() {
        // null Matric
        assertThrows(NullPointerException.class, () -> Matric.isValidMatric(null));

        // blank Matric
        assertFalse(Matric.isValidMatric("")); // empty string
        assertFalse(Matric.isValidMatric(" ")); // spaces only

        // missing parts
        assertFalse(Matric.isValidMatric("0191094A")); // missing starting letter
        assertFalse(Matric.isValidMatric("A019109A")); // missing 7 numbers
        assertFalse(Matric.isValidMatric("A0191094")); // missing ending letter

        // invalid parts
        assertFalse(Matric.isValidMatric("B0191094A")); // invalid starting letter
        assertFalse(Matric.isValidMatric("A019_1094A")); // underscore in matric
        assertFalse(Matric.isValidMatric("A0191 094A")); // spaces in matric
        assertFalse(Matric.isValidMatric(" A0191094A")); // leading space
        assertFalse(Matric.isValidMatric("A0191094A ")); // trailing space
        assertFalse(Matric.isValidMatric("AA0191094A")); // double starting letter
        assertFalse(Matric.isValidMatric(".A0191094A")); // matric starts with a period
        assertFalse(Matric.isValidMatric("A0191094A.")); // matric ends with a period
        assertFalse(Matric.isValidMatric("-A0191094A")); // matric starts with a hyphen
        assertFalse(Matric.isValidMatric("A0191094A-")); // matric ends with a hyphen

        // valid Matric
        assertTrue(Matric.isValidMatric("A0191094A"));
    }
}
