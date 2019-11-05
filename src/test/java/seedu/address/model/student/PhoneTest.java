package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidParentPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidParentPhone("")); // empty string
        assertFalse(Phone.isValidParentPhone(" ")); // spaces only
        assertFalse(Phone.isValidParentPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidParentPhone("phone")); // non-numeric
        assertFalse(Phone.isValidParentPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidParentPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidParentPhone("91139548")); // exactly 3 numbers
        assertTrue(Phone.isValidParentPhone("93121534"));
        assertTrue(Phone.isValidParentPhone("12429384")); // long phone numbers
    }
}
