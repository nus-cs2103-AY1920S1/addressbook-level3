package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class ContentTest {
    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Content(null));
    }

    @Test
    void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Content(invalidAddress));
    }

    @Test
    void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // invalid addresses
        assertFalse(Content.isValidContent("")); // empty string
        assertFalse(Content.isValidContent(" ")); // spaces only

        // valid addresses
        assertTrue(Content.isValidContent("Blk 456, Den Road, #01-355"));
        assertTrue(Content.isValidContent("-")); // one character
        assertTrue(Content.isValidContent("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
