package seedu.address.model.usersettings;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class FineIncrementTest {

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

        // valid fineIncrement
        assertTrue(FineIncrement.isValidFineIncrement("12345")); // numbers only
    }
}
