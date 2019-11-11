package seedu.address.model.password;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WebsiteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Website(null));
    }


    @Test
    public void isValidWebsite() {
        // null website
        assertThrows(NullPointerException.class, () -> Website.isValidWebsite(null));

        // valid website
        assertTrue(Website.isValidWebsite("NIL"));
        assertTrue(Website.isValidWebsite(
                "https://stackoverflow.com/questions/3697202/including-a-hyphen-in-a-regex-character-bracket"));
        assertTrue(Website.isValidWebsite("https://www.youtube.com/"));
    }
}
