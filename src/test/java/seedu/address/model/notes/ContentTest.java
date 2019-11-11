package seedu.address.model.notes;

import org.junit.jupiter.api.Test;
import seedu.address.model.note.Content;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class ContentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Content(null));
    }

    @Test
    public void constructor_invalidContent_throwsIllegalArgumentException() {
        String invalidContent = "";
        assertThrows(IllegalArgumentException.class, () -> new Content(invalidContent));
    }

    @Test
    public void isValidContent() {
        // null name
        assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // invalid name
        assertFalse(Content.isValidContent("")); // empty string
        assertFalse(Content.isValidContent(" ")); // spaces only

        // valid name
        assertTrue(Content.isValidContent("Check report progress")); // alphabets only
        assertTrue(Content.isValidContent("x = 2^3")); // numbers only
    }
}
