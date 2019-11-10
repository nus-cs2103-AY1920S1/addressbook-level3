package seedu.ichifund.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("^")); // only non-alphanumeric characters
        assertFalse(Description.isValidDescription("dinner*")); // contains non-alphanumeric characters
        assertFalse(Description.isValidDescription("Breakfast with Tiffany because Sabrina disappearedd")); // long

        // valid description
        assertTrue(Description.isValidDescription("dinner with myself")); // alphabets only
        assertTrue(Description.isValidDescription("000")); // numbers only
        assertTrue(Description.isValidDescription("2nd lunch")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Breakfast with Tiffany")); // with capital letters
        assertTrue(Description.isValidDescription("Breakfast with Tiffany because Sabrina disappeared")); // 50 char
    }
}
