package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class IdentityNumberTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new IdentityNumber(null));
    }

    @Test
    void constructor_invalidIdentityNumber_throwsIllegalArgumentException() {
        String invalidIdentityNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new IdentityNumber(invalidIdentityNumber));
    }

    @Test
    void isValidIdentityNumber() {
        // null identity number
        assertThrows(NullPointerException.class, () -> new IdentityNumber(null));

        // invalid identity number
        assertFalse(IdentityNumber.isValidIdentityNumber("")); // empty string
        assertFalse(IdentityNumber.isValidIdentityNumber(" ")); // spaces only
        assertFalse(IdentityNumber.isValidIdentityNumber("alphanumeric123")); // alphanumeric
        assertFalse(IdentityNumber.isValidIdentityNumber("12345678909876")); // less than 15 digits
        assertFalse(IdentityNumber.isValidIdentityNumber("1234567890987654")); // more than 15 digits

        // valid identity number
        assertTrue(IdentityNumber.isValidIdentityNumber("123456789098765")); // 15 digits
    }

}
