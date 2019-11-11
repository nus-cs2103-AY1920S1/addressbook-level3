package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void testToString() {
        Name validName = new Name("Capital Tan");

        // same name
        assertEquals(validName.toString(), "Capital Tan");

        // different case
        assertNotEquals(validName.toString(), "capital tan");
    }

    @Test
    public void equals() {
        Name validName = new Name("Capital Tan");

        // same name
        assertTrue(validName.equals(new Name("Capital Tan")));

        // same object
        assertTrue(validName.equals(validName));

        // different name
        assertFalse(validName.equals(new Name("Capital Lee")));
    }

    @Test
    public void testHashCode() {
        Name validName = new Name("Capital Tan");
        // same name -> return true
        assertTrue(validName.hashCode() == new Name("Capital Tan").hashCode());

        // same object -> return true
        assertTrue(validName.hashCode() == validName.hashCode());

        // different name -> return false
        assertFalse(validName.hashCode() == new Name("Capital Lee").hashCode());
    }
}
