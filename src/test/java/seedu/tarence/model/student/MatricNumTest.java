package seedu.tarence.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MatricNumTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatricNum(null));
    }

    @Test
    public void constructor_invalidMatricNum_throwsIllegalArgumentException() {
        String invalidMatricNum = "";
        assertThrows(IllegalArgumentException.class, () -> new MatricNum(invalidMatricNum));
    }

    @Test
    public void isValidMatricNum() {
        // null MatricNum
        assertThrows(NullPointerException.class, () -> MatricNum.isValidMatricNum(null));

        // blank MatricNum
        assertFalse(MatricNum.isValidMatricNum("")); // empty string
        assertFalse(MatricNum.isValidMatricNum(" ")); // spaces only

        // missing parts
        assertFalse(MatricNum.isValidMatricNum("0191094A")); // missing starting letter
        assertFalse(MatricNum.isValidMatricNum("A019109A")); // missing 7 numbers
        assertFalse(MatricNum.isValidMatricNum("A0191094")); // missing ending letter

        // invalid parts
        assertFalse(MatricNum.isValidMatricNum("B0191094A")); // invalid starting letter
        assertFalse(MatricNum.isValidMatricNum("A019_1094A")); // underscore in matric num
        assertFalse(MatricNum.isValidMatricNum("A0191 094A")); // spaces in matric num
        assertFalse(MatricNum.isValidMatricNum(" A0191094A")); // leading space
        assertFalse(MatricNum.isValidMatricNum("A0191094A ")); // trailing space
        assertFalse(MatricNum.isValidMatricNum("AA0191094A")); // double starting letter
        assertFalse(MatricNum.isValidMatricNum(".A0191094A")); // matric num starts with a period
        assertFalse(MatricNum.isValidMatricNum("A0191094A.")); // matric num ends with a period
        assertFalse(MatricNum.isValidMatricNum("-A0191094A")); // matric num starts with a hyphen
        assertFalse(MatricNum.isValidMatricNum("A0191094A-")); // matric num ends with a hyphen

        // valid Matric
        assertTrue(MatricNum.isValidMatricNum("A0191094A"));
    }
}
