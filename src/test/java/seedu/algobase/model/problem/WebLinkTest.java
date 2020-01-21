package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class WebLinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WebLink(null));
    }

    @Test
    public void constructor_invalidWebLink_throwsIllegalArgumentException() {
        String invalidSource = "";
        assertThrows(IllegalArgumentException.class, () -> new Source(invalidSource));
    }

    @Test
    public void isValidWebLink() {
        // null weblink
        assertThrows(NullPointerException.class, () -> WebLink.isValidWebLink(null));

        // invalid weblink
        assertFalse(WebLink.isValidWebLink("")); // empty string
        assertFalse(WebLink.isValidWebLink(" ")); // only spaces
        assertFalse(WebLink.isValidWebLink("Subset Sum")); // does not have protocol

        // valid weblink
        assertTrue(WebLink.isValidWebLink("http://localhost.com")); // http protocol
        assertTrue(WebLink.isValidWebLink("https://github.com/nus-cs2103-AY1920S1/")); // https protocol
    }

}
