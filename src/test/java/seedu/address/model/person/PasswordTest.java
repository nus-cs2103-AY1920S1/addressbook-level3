package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String invalidPassword = "";
        assertThrows(IllegalArgumentException.class, () -> new Password(invalidPassword));
    }

    @Test
    void isValidPassword() {
        // null name
        assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        //invalid password
        assertFalse(Password.isValidPassword("test word")); // contains space
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword("wh\\atthe")); // contains non approved special character
        assertFalse(Password.isValidPassword("passw")); // five characters

        //valid password
        assertTrue(Password.isValidPassword("password")); // alphabets only
        assertTrue(Password.isValidPassword("1234556")); // numbers only
        assertTrue(Password.isValidPassword("pa$$w0rd")); // contains approved special characters
        assertTrue(Password.isValidPassword("pas$w0")); // six characters
    }
}
