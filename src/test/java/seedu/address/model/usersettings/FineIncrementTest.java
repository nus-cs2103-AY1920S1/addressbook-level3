package seedu.address.model.usersettings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FineIncrementTest {
    private static final int TEST_FINE_INCREMENT = 500;

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FineIncrement(null));
    }

    @Test
    public void constructor_invalidFineIncrement_throwsIllegalArgumentException() {
        String invalidFineIncrement = "";
        assertThrows(IllegalArgumentException.class, () -> new FineIncrement(invalidFineIncrement));
    }

    @Test
    public void isValidFineIncrement() {
        // null fineIncrement
        assertThrows(NullPointerException.class, () -> FineIncrement.isValidFineIncrement(null));

        // invalid fineIncrement
        assertFalse(FineIncrement.isValidFineIncrement("")); // empty string
        assertFalse(FineIncrement.isValidFineIncrement(" ")); // spaces only
        assertFalse(FineIncrement.isValidFineIncrement("^")); // only non-alphanumeric characters
        assertFalse(FineIncrement.isValidFineIncrement("hello*")); // contains non-alphanumeric characters
        assertFalse(FineIncrement.isValidFineIncrement("hello world")); // alphabets only
        assertFalse(FineIncrement.isValidFineIncrement("-1")); // negative integer
        assertFalse(FineIncrement.isValidFineIncrement("100001")); // exceed max fine increment

        // valid fineIncrement
        assertTrue(FineIncrement.isValidFineIncrement("12345")); // numbers only
        assertTrue(FineIncrement.isValidFineIncrement("0")); // min value
        assertTrue(FineIncrement.isValidFineIncrement("100000")); // max value
    }

    @Test
    public void toString_success() {
        FineIncrement fi = new FineIncrement(TEST_FINE_INCREMENT);
        assertEquals(fi.toString(), String.format("%d", TEST_FINE_INCREMENT));
    }

    @Test
    public void equals() {
        FineIncrement fi1 = new FineIncrement(TEST_FINE_INCREMENT);
        FineIncrement fi2 = new FineIncrement(TEST_FINE_INCREMENT);
        FineIncrement fi3 = new FineIncrement(TEST_FINE_INCREMENT + 1);
        assertEquals(fi1, fi2);
        assertNotEquals(fi1, fi3);
    }

    @Test
    public void hashcode() {
        FineIncrement fi1 = new FineIncrement(TEST_FINE_INCREMENT);
        FineIncrement fi2 = new FineIncrement(TEST_FINE_INCREMENT);
        assertEquals(fi1.hashCode(), fi2.hashCode());
    }
}
