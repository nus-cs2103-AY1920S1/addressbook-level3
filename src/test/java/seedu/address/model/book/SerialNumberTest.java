package seedu.address.model.book;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SerialNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SerialNumber(null));
    }

    @Test
    public void constructor_invalidSerialNumber_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new SerialNumber(invalidPhone));
    }

    @Test
    public void isValidSerialNumber() {
        // null phone number
        assertThrows(NullPointerException.class, () -> SerialNumber.isValidSerialNumber(null));

        // invalid phone numbers
        assertFalse(SerialNumber.isValidSerialNumber("")); // empty string
        assertFalse(SerialNumber.isValidSerialNumber(" ")); // spaces only
        assertFalse(SerialNumber.isValidSerialNumber("B91")); // less than 4 numbers
        assertFalse(SerialNumber.isValidSerialNumber("C0001")); // different prefix
        assertFalse(SerialNumber.isValidSerialNumber("phone")); // non-numeric
        assertFalse(SerialNumber.isValidSerialNumber("9011p041")); // alphabets within digits
        assertFalse(SerialNumber.isValidSerialNumber("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(SerialNumber.isValidSerialNumber("B0911")); // exactly 4 numbers
        assertTrue(SerialNumber.isValidSerialNumber("B0001")); // smallest serial number
        assertTrue(SerialNumber.isValidSerialNumber("B9999")); // largest serial number
    }
}
