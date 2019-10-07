package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PhoneName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new PhoneName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> PhoneName.isValidName(null));

        // invalid name
        assertFalse(PhoneName.isValidName("")); // empty string
        assertFalse(PhoneName.isValidName(" ")); // spaces only
        assertFalse(PhoneName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(PhoneName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(PhoneName.isValidName("peter jack")); // alphabets only
        assertTrue(PhoneName.isValidName("12345")); // numbers only
        assertTrue(PhoneName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(PhoneName.isValidName("Capital Tan")); // with capital letters
        assertTrue(PhoneName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

}
