package seedu.address.model.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactNumber(null));
    }

    @Test
    public void constructor_invalidContactNumber_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new ContactNumber(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null contact number
        assertThrows(NullPointerException.class, () -> ContactNumber.isValidContactNumber(null));

        // invalid contact numbers
        assertFalse(ContactNumber.isValidContactNumber("")); // empty string
        assertFalse(ContactNumber.isValidContactNumber(" ")); // spaces only
        assertFalse(ContactNumber.isValidContactNumber("91")); // less than 3 numbers
        assertFalse(ContactNumber.isValidContactNumber("phone")); // non-numeric
        assertFalse(ContactNumber.isValidContactNumber("9011p041")); // alphabets within digits
        assertFalse(ContactNumber.isValidContactNumber("9312 1534")); // spaces within digits

        // valid contact numbers
        assertTrue(ContactNumber.isValidContactNumber("93121534")); // exactly 8 numbers
        assertTrue(ContactNumber.isValidContactNumber("124293842033123")); // long phone numbers
    }
}
