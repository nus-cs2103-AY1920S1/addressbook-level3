package seedu.sugarmummy.model.records;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sugarmummy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WeightTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void constructor_invalidWeight_throwsIllegalArgumentException() {
        String invalidWeight = "-1";
        assertThrows(IllegalArgumentException.class, () -> new Weight(invalidWeight));
    }

    @Test
    public void isValidWeight() {
        // null name
        assertThrows(NullPointerException.class, () -> Weight.isValidWeight(null));

        // invalid weight
        assertFalse(Weight.isValidWeight("")); // empty string
        assertFalse(Weight.isValidWeight(" ")); // spaces only
        assertFalse(Weight.isValidWeight("^")); // only non-alphanumeric characters
        assertFalse(Weight.isValidWeight("peter")); // contains non-alphanumeric characters
        assertFalse(Weight.isValidWeight("-2323")); // negative double

        // valid weight
        assertTrue(Weight.isValidWeight("0.12")); // ≤1 double
        assertTrue(Weight.isValidWeight("12.34")); // ≥1 double
        assertTrue(Weight.isValidWeight("499.99")); // ≥1 double
        assertTrue(Weight.isValidWeight("499.994")); // ≥1 double
        assertFalse(Weight.isValidWeight("499.995")); // ≥1 double
        assertFalse(Weight.isValidWeight("500")); // ≥1 double
        assertFalse(Weight.isValidWeight("10000")); // positive
        assertFalse(Weight.isValidWeight("0"));
    }
}
