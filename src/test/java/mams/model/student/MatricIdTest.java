package mams.model.student;

import static mams.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MatricIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatricId(null));
    }

    @Test
    public void constructor_invalidMatricId_throwsIllegalArgumentException() {
        String invalidMatricId = "";
        assertThrows(IllegalArgumentException.class, () -> new MatricId(invalidMatricId));
    }

    @Test
    public void isValidMatricId() {
        // null matricId
        assertThrows(NullPointerException.class, () -> MatricId.isValidMatricId(null));

        // invalid matricId
        assertFalse(MatricId.isValidMatricId("")); // empty string
        assertFalse(MatricId.isValidMatricId(" ")); // spaces only

        // valid matricId
        assertTrue(MatricId.isValidMatricId("A0189999Q"));
        assertTrue(MatricId.isValidMatricId("-")); // one character
        assertTrue(MatricId.isValidMatricId("Matric ID")); // long matricId
    }
}
