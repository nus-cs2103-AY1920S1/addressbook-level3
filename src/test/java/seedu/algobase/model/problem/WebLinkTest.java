package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WebLinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WebLink(null));
    }

    @Test
    public void constructor_invalidWeblink_throwsIllegalArgumentException() {
        String invalidWeblink = "";
        assertThrows(IllegalArgumentException.class, () -> new WebLink(invalidWeblink));
    }

    @Test
    public void isValidWeblink() {
        // null weblink
        assertThrows(NullPointerException.class, () -> WebLink.isValidWeblink(null));

        // blank weblink
        assertFalse(WebLink.isValidWeblink("")); // empty string
        assertFalse(WebLink.isValidWeblink(" ")); // spaces only

        // missing parts
        assertFalse(WebLink.isValidWeblink("@example.com")); // missing local part
        assertFalse(WebLink.isValidWeblink("peterjackexample.com")); // missing '@' symbol
        assertFalse(WebLink.isValidWeblink("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(WebLink.isValidWeblink("peterjack@-")); // invalid domain name
        assertFalse(WebLink.isValidWeblink("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(WebLink.isValidWeblink("peter jack@example.com")); // spaces in local part
        assertFalse(WebLink.isValidWeblink("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(WebLink.isValidWeblink(" peterjack@example.com")); // leading space
        assertFalse(WebLink.isValidWeblink("peterjack@example.com ")); // trailing space
        assertFalse(WebLink.isValidWeblink("peterjack@@example.com")); // double '@' symbol
        assertFalse(WebLink.isValidWeblink("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(WebLink.isValidWeblink("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(WebLink.isValidWeblink("peterjack@.example.com")); // domain name starts with a period
        assertFalse(WebLink.isValidWeblink("peterjack@example.com.")); // domain name ends with a period
        assertFalse(WebLink.isValidWeblink("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(WebLink.isValidWeblink("peterjack@example.com-")); // domain name ends with a hyphen

        // valid weblink
        assertTrue(WebLink.isValidWeblink("PeterJack_1190@example.com"));
        assertTrue(WebLink.isValidWeblink("a@bc")); // minimal
        assertTrue(WebLink.isValidWeblink("test@localhost")); // alphabets only
        assertTrue(WebLink.isValidWeblink("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(WebLink.isValidWeblink("123@145")); // numeric local part and domain name
        assertTrue(WebLink.isValidWeblink("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(WebLink.isValidWeblink("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(WebLink.isValidWeblink("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
