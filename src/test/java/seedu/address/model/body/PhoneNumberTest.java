package seedu.address.model.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author ambervoong
class PhoneNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PhoneNumber(null));
    }

    @Test
    public void constructor_invalidNumber_throwsIllegalArgumentException() {
        String invalidPhoneNumber = "";

        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber(invalidPhoneNumber));
    }

    @Test
    void getPhoneNumber() {
        PhoneNumber testPhone = new PhoneNumber("87871234");
        assertEquals("87871234", testPhone.getPhoneNumber());
    }

    @Test
    void testEquals_self_true() {
        PhoneNumber testPhone = new PhoneNumber("87871234");
        PhoneNumber otherPhone = new PhoneNumber("87871234");
        assertEquals(testPhone, testPhone);
        assertEquals(testPhone, otherPhone);
        assertEquals(testPhone.hashCode(), otherPhone.hashCode());
    }

    @Test
    void testEquals_differentAndNull_notEqual() {
        PhoneNumber testPhone = new PhoneNumber("87871234");
        assertNotEquals(testPhone, new PhoneNumber("98765432"));
        assertNotEquals(testPhone, null);
    }

    @Test
    void isValidPhoneNumber() {
        // null PhoneNumber`
        assertThrows(NullPointerException.class, () -> PhoneNumber.isValidPhoneNumber(null));

        // invalid PhoneNumbers
        assertFalse(PhoneNumber.isValidPhoneNumber("")); // empty string
        assertFalse(PhoneNumber.isValidPhoneNumber(" ")); // spaces only
        assertFalse(PhoneNumber.isValidPhoneNumber("8686")); // Not enough digits
        assertFalse(PhoneNumber.isValidPhoneNumber("4848ab122")); // Alphabets in number.

        // valid PhoneNumbers
        assertTrue(PhoneNumber.isValidPhoneNumber("82371039"));
        assertTrue(PhoneNumber.isValidPhoneNumber("98984444"));
    }

}
