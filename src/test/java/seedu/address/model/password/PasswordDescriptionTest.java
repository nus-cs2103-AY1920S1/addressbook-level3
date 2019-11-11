package seedu.address.model.password;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PasswordDescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PasswordDescription(null));
    }

    @Test
    public void constructor_invalidPasswordDescription_throwsIllegalArgumentException() {
        String invalidPasswordDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new PasswordDescription(invalidPasswordDescription));
    }

    @Test
    public void isValidPasswordDescription() {
        // null password
        assertThrows(NullPointerException.class, () -> PasswordDescription.isValidDescription(null));

        // invalid password
        assertFalse(PasswordDescription.isValidDescription("")); // empty string
        assertFalse(PasswordDescription.isValidDescription("   ")); // spaces only
        assertFalse(PasswordDescription.isValidDescription("   wq")); // spaces only
        assertFalse(PasswordDescription.isValidDescription("w  q")); // 2 spaces only
        assertFalse(PasswordDescription.isValidDescription("G")); // 1 character
        assertFalse(PasswordDescription.isValidDescription("12345678901234567890123456")); // 26 character username
        assertFalse(PasswordDescription.isValidDescription("password@1")); // address character


        // valid addresses
        assertTrue(PasswordDescription.isValidDescription("GMAIL"));
        assertTrue(PasswordDescription.isValidDescription("GMAIL 1"));
        assertTrue(PasswordDescription.isValidDescription("ab")); // 2 character
        assertTrue(PasswordDescription.isValidDescription("1234567890123456789012345")); // 25 character username
    }
}
