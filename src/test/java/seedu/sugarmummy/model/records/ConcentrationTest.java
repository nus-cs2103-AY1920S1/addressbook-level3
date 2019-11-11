package seedu.sugarmummy.model.records;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

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
        assertTrue(Concentration.isValidConcentration("32.99")); // ≥1 double
        assertFalse(Concentration.isValidConcentration("32.99999999")); // ≥1 double
        assertFalse(Concentration.isValidConcentration("33.00001")); // ≥1 double
        assertFalse(Concentration.isValidConcentration("10000")); // positive
        assertFalse(Concentration.isValidConcentration("0"));
    }
}
