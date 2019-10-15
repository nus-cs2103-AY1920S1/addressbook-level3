package seedu.address.model.record;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ConcentrationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Concentration(null));
    }

    @Test
    public void constructor_invalidConcentration_throwsIllegalArgumentException() {
        String invalidConcentration = "-1";
        assertThrows(IllegalArgumentException.class, () -> new Concentration(invalidConcentration));
    }

    @Test
    public void isValidConcentration() {
        // null name
        assertThrows(NullPointerException.class, () -> Concentration.isValidConcentration(null));

        // invalid concentration
        assertFalse(Concentration.isValidConcentration("")); // empty string
        assertFalse(Concentration.isValidConcentration(" ")); // spaces only
        assertFalse(Concentration.isValidConcentration("^")); // only non-alphanumeric characters
        assertFalse(Concentration.isValidConcentration("peter")); // contains non-alphanumeric characters
        assertFalse(Concentration.isValidConcentration("-2323")); // negative double

        // valid concentration
        assertTrue(Concentration.isValidConcentration("0.12")); // ≤1 double
        assertTrue(Concentration.isValidConcentration("12.34")); // ≥1 double
        assertTrue(Concentration.isValidConcentration("10000")); // positive
        assertTrue(Concentration.isValidConcentration("0"));
    }
}
