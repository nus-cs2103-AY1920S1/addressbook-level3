package seedu.address.model.earnings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Count(null));
    }

    @Test
    public void constructor_invalidCount_throwsIllegalArgumentException() {
        String invalidCount = "2.1";
        assertThrows(IllegalArgumentException.class, () -> new Count(invalidCount));
    }

    @Test
    public void constructor_invalidNegativeCount_throwsIllegalArgumentException() {
        String invalidCount = "-2";
        assertThrows(IllegalArgumentException.class, () -> new Count(invalidCount));
    }

    @Test
    public void isValidCount() {
        // null name
        assertThrows(NullPointerException.class, () -> Count.isValidCount(null));

        // invalid name
        assertFalse(Count.isValidCount("")); // empty string
        assertFalse(Count.isValidCount(" ")); // spaces only
        assertFalse(Count.isValidCount("one")); // letter amount characters
        assertFalse(Count.isValidCount("-1")); // negative count
        assertFalse(Count.isValidCount("123.22")); // contains decimal point characters

        // valid name
        assertTrue(Count.isValidCount("0")); // zero only
        assertTrue(Count.isValidCount("1")); // numbers only
        assertTrue(Count.isValidCount("13")); // max value
    }
}
