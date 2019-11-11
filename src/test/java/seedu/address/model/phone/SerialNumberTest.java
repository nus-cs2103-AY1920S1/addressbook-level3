package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class SerialNumberTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SerialNumber(null));
    }

    @Test
    void constructor_invalidSerialNumber_throwsIllegalArgumentException() {
        String invalidSerialNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new SerialNumber(invalidSerialNumber));
    }

    @Test
    void isValidSerialNumber() {
        // null serial number
        assertThrows(NullPointerException.class, () -> SerialNumber.isValidSerialNumber(null));

        // invalid serial number
        assertFalse(SerialNumber.isValidSerialNumber("")); // empty string
        assertFalse(SerialNumber.isValidSerialNumber(" ")); // spaces only
        assertFalse(SerialNumber.isValidSerialNumber("^")); // only non-alphanumeric characters
        assertFalse(SerialNumber.isValidSerialNumber("^asad")); // contains non-alphanumeric characters

        // valid serial number
        assertTrue(SerialNumber.isValidSerialNumber("abc def")); // alphabets only
        assertTrue(SerialNumber.isValidSerialNumber("12345")); // numbers only
        assertTrue(SerialNumber.isValidSerialNumber("athe23rdphone")); // alphanumeric characters
        assertTrue(SerialNumber.isValidSerialNumber("A 213 DFG")); // with capital letters
        assertTrue(SerialNumber.isValidSerialNumber("A0234234JURNB320420DSALGFDSIBN2131")); // long names
    }

}
