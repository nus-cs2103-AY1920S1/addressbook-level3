package seedu.address.model.task;

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
    public void isValidMemberName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidMemberName(null));

        // invalid name
        assertFalse(Name.isValidMemberName("")); // empty string
        assertFalse(Name.isValidMemberName(" ")); // spaces only
        assertFalse(Name.isValidMemberName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidMemberName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidMemberName("peter jack")); // alphabets only
        assertTrue(Name.isValidMemberName("12345")); // numbers only
        assertTrue(Name.isValidMemberName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidMemberName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidMemberName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
