package seedu.savenus.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.savenus.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
    public void isNullTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isBlankTagName() {
        assertFalse(Tag.isValidTagName("")); // blank
        assertFalse(Tag.isValidTagName(" ")); // one space
        assertFalse(Tag.isValidTagName("              ")); // tons of spaces
    }


    @Test
    public void isValidTagName() {
        assertTrue(Tag.isValidTagName("Western"));
        assertTrue(Tag.isValidTagName("ChickenRice"));
    }

}
