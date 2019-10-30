package seedu.address.model.finance.attributes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Person(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Person(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Person.isValidName(null));

        // invalid name
        assertFalse(Person.isValidName("")); // empty string
        assertFalse(Person.isValidName(" ")); // spaces only
        assertFalse(Person.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Person.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Person.isValidName("peter jack")); // alphabets only
        assertTrue(Person.isValidName("12345")); // numbers only
        assertTrue(Person.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Person.isValidName("Capital Tan")); // with capital letters
        assertTrue(Person.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
