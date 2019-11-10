package seedu.address.model.note;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ContentTest {

    @Test
    public void isValidContent() {
        // null address
        assertThrows(NullPointerException.class, () -> Content.isValidContent(null));

        // valid addresses
        assertTrue(Content.isValidContent("")); //empty string
        assertTrue(Content.isValidContent(" ")); //whitespace
        assertTrue(Content.isValidContent("Blk 456, Den Road, #01-355"));
        assertTrue(Content.isValidContent("-")); // one character
        assertTrue(Content.isValidContent("Secret key 1@#$#%#$^#%&$%#Q##22s$@")); // special characters
    }
}
