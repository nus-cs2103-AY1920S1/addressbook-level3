package seedu.address.model.itinerary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("12345678901234567890123456789012345678912345678"
                + "9012345678901234567890123456789")); // more than 30 characters

        // valid name
        assertTrue(Name.isValidName("africa trip")); // alphabets only
        assertTrue(Name.isValidName("My Trip*")); // contains non-alphanumeric characters
        assertTrue(Name.isValidName("^")); // only non-alphanumeric characters
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("2nd africa arip")); // alphanumeric characters
        assertTrue(Name.isValidName("Bali Trip")); // with capital letters
        assertTrue(Name.isValidName("My Africa Trip with a long name")); // long names
    }

}
