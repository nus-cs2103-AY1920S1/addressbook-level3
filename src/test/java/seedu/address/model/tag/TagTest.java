package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test for the Tag Model.
 */
public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid name
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // contains spaces
        assertFalse(Tag.isValidTagName("@!")); // only special characters
        assertFalse(Tag.isValidTagName("A@@!")); // contains letters and special characters
        assertFalse(Tag.isValidTagName("wina aifa")); // contains space
        assertFalse(Tag.isValidTagName("222 111")); // numbers and spaces

        // valid name
        assertTrue(Tag.isValidTagName("222111")); // numbers only
        assertTrue(Tag.isValidTagName("chemistry")); // alphabet only
        assertTrue(Tag.isValidTagName("ahfh222")); // numbers and alphabets

    }
}
