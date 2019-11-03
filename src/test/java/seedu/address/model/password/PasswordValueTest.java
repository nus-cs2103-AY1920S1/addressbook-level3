package seedu.address.model.password;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PasswordValueTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PasswordValue(null));
    }

    @Test
    public void constructor_invalidPasswordValue_throwsIllegalArgumentException() {
        String invalidPasswordValue = "";
        assertThrows(IllegalArgumentException.class, () -> new PasswordValue(invalidPasswordValue));
    }

    @Test
    public void isValidPassword() {
        // null password
        assertThrows(NullPointerException.class, () -> PasswordValue.isValidPasswordValue(null));

        // invalid password
        assertFalse(PasswordValue.isValidPasswordValue("")); // empty string
        assertFalse(PasswordValue.isValidPasswordValue(" ")); // spaces only
        assertFalse(PasswordValue.isValidPasswordValue("1")); // 1 character
        assertFalse(PasswordValue.isValidPasswordValue("12345678901234567890123456")); // 26 character username
        //assertFalse(PasswordValue.isValidPasswordValue("password 1")); // space character


        // valid addresses
        assertTrue(PasswordValue.isValidPasswordValue("Password1"));
        assertTrue(PasswordValue.isValidPasswordValue("~!@#$%^&*()_+"));
        assertTrue(PasswordValue.isValidPasswordValue("`[]{}:\";'<>,./?'\\()|"));
        assertTrue(PasswordValue.isValidPasswordValue("Password@2"));
        assertTrue(PasswordValue.isValidPasswordValue("ab")); // 2 character
        assertTrue(PasswordValue.isValidPasswordValue("1234567890123456789012345")); // 25 character username
    }
}
