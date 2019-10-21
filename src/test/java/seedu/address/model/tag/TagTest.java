package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
    public void getTagName() {
        Tag validTag = new Tag("food");

        assertTrue(validTag.getTagName().equals("food"));
        assertFalse(validTag.getTagName().equals("drink"));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals() {
        Tag firstTag = new Tag("club");
        Tag secondTag = new Tag("drink");

        // same object -> returns true
        assertTrue(firstTag.equals(firstTag));

        // same values -> returns true
        assertTrue(firstTag.equals(new Tag("club")));

        // different types -> returns false
        assertFalse(firstTag.equals(1));

        // null -> returns false
        assertFalse(firstTag.equals(null));

        // different Tag -> returns false
        assertFalse(firstTag.equals(secondTag));
    }

    @Test
    public void testToString() {
        Tag validTag = new Tag("food");

        assertTrue(validTag.toString().equals("[food]"));
        assertFalse(validTag.toString().equals("[drink]"));
    }

}
