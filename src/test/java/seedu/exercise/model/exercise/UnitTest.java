package seedu.exercise.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UnitTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Unit(null));
    }

    @Test
    public void constructor_invalidUnit_throwsIllegalArgumentException() {
        String invalidUnit = " ";
        assertThrows(IllegalArgumentException.class, () -> new Unit(invalidUnit));
    }

    @Test
    public void isValidUnit() {
        // null unit
        assertThrows(NullPointerException.class, () -> Unit.isValidUnit(null));

        // invalid unit
        assertFalse(Unit.isValidUnit("")); // empty string
        assertFalse(Unit.isValidUnit("^32")); // only non-alphabetic characters
        assertFalse(Unit.isValidUnit("Running*")); // contains non-alphabetic characters
        assertFalse(Unit.isValidUnit("3432")); // only numeric characters


        // valid unit
        assertTrue(Unit.isValidUnit("running")); // alphabets only
        assertTrue(Unit.isValidUnit("RunningFast")); // with capital letters
    }
}
