package seedu.address.model.password;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidWebsite_throwsIllegalArgumentException() {
        String invalidUsername = "";
        assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null username
        assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid username
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("1")); // 1 character
        assertFalse(Username.isValidUsername("12345678901234567890123456")); // 26 character username
        assertFalse(Username.isValidUsername("sa__cas")); // 2 underscore character
        assertFalse(Username.isValidUsername("sa@@cas")); // 2 address sign character
        assertFalse(Username.isValidUsername("sa  cas")); // 2 space character
        assertFalse(Username.isValidUsername("sa--cas")); // 2 hypen character
        assertFalse(Username.isValidUsername("_Randomguy1")); // underscore at start
        assertFalse(Username.isValidUsername("Randomguy1_")); // underscore at end
        assertFalse(Username.isValidUsername("-Randomguy1")); // hypen at start
        assertFalse(Username.isValidUsername("Randomguy1-")); // hypen at end
        assertFalse(Username.isValidUsername("@Randomguy1")); // address sign at start
        assertFalse(Username.isValidUsername("Randomguy1@")); // address sign at end
        assertFalse(Username.isValidUsername("Randomguy@-1")); // two special character in a row
        assertFalse(Username.isValidUsername("Randomguy_-1")); // two special character in a row

        // valid username
        assertTrue(Username.isValidUsername("User 2"));
        assertTrue(Username.isValidUsername("User@2"));
        assertTrue(Username.isValidUsername("User-2"));
        assertTrue(Username.isValidUsername("User_2"));
        assertTrue(Username.isValidUsername("ab")); // 2 character
        assertTrue(Username.isValidUsername("1234567890123456789012345")); // 25 character username
    }
}
