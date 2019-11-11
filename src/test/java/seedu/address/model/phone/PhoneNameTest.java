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
        assertThrows(NullPointerException.class, () -> PhoneName.isValidPhoneName(null));

        // invalid name
        assertFalse(PhoneName.isValidPhoneName("")); // empty string
        assertFalse(PhoneName.isValidPhoneName(" ")); // spaces only
        assertFalse(PhoneName.isValidPhoneName("^")); // only non-alphanumeric characters
        assertFalse(PhoneName.isValidPhoneName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(PhoneName.isValidPhoneName("peter jack")); // alphabets only
        assertTrue(PhoneName.isValidPhoneName("12345")); // numbers only
        assertTrue(PhoneName.isValidPhoneName("peter the 2nd")); // alphanumeric characters
        assertTrue(PhoneName.isValidPhoneName("Capital Tan")); // with capital letters
        assertTrue(PhoneName.isValidPhoneName("David Roger Jackson Ray Jr 2nd")); // long names
    }

}
