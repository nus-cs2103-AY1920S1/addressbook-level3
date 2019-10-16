package seedu.address.model.inventory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class InvNameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InvName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new InvName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> InvName.isValidName(null));

        // invalid name
        assertFalse(InvName.isValidName("")); // empty string
        assertFalse(InvName.isValidName(" ")); // spaces only
        assertFalse(InvName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(InvName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(InvName.isValidName("peter jack")); // alphabets only
        assertTrue(InvName.isValidName("12345")); // numbers only
        assertTrue(InvName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(InvName.isValidName("Capital Tan")); // with capital letters
        assertTrue(InvName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
