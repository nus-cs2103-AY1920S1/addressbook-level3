package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    void isValidUsername() {
        // null name
        assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid username
        assertFalse(Username.isValidUsername("")); // empty string\
        assertFalse(Username.isValidUsername("^")); // only non-alphanumeric characters
        assertFalse(Username.isValidUsername("agent amazing")); // contains space
        assertFalse(Username.isValidUsername("ag")); // less than 3 characters
        assertFalse(Username.isValidUsername("ab-")); // ends with non alphanumeric
        assertFalse(Username.isValidUsername("-ab")); // starts with non alphanumeric
        assertFalse(Username.isValidUsername("a#2")); // contains non approved characters

        // valid username
        assertTrue(Username.isValidUsername("A-2")); // three characters
        assertTrue(Username.isValidUsername("agentSmith")); // alphabets only
        assertTrue(Username.isValidUsername("12345")); // numbers only
        assertTrue(Username.isValidUsername("Agent10")); // alphanumeric characters
        assertTrue(Username.isValidUsername("Capital-Tan")); // with capital letters and dash
        assertTrue(Username.isValidUsername("David.Roger-Jackson.Ray-Jr.2nd")); // long usernames
    }
}
