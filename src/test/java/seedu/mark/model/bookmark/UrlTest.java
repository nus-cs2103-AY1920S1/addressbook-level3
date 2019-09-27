package seedu.mark.model.bookmark;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.mark.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UrlTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Url(null));
    }

    @Test
    public void constructor_invalidUrl_throwsIllegalArgumentException() {
        String invalidUrl = "";
        assertThrows(IllegalArgumentException.class, () -> new Url(invalidUrl));
    }

    @Test
    public void isValidEmail() {
        // null url
        assertThrows(NullPointerException.class, () -> Url.isValidUrl(null));

        // blank url
        assertFalse(Url.isValidUrl("")); // empty string
        assertFalse(Url.isValidUrl(" ")); // spaces only

        // missing parts
        assertFalse(Url.isValidUrl("@example.com")); // missing local part
        assertFalse(Url.isValidUrl("peterjackexample.com")); // missing '@' symbol
        assertFalse(Url.isValidUrl("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Url.isValidUrl("peterjack@-")); // invalid domain name
        assertFalse(Url.isValidUrl("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Url.isValidUrl("peter jack@example.com")); // spaces in local part
        assertFalse(Url.isValidUrl("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Url.isValidUrl(" peterjack@example.com")); // leading space
        assertFalse(Url.isValidUrl("peterjack@example.com ")); // trailing space
        assertFalse(Url.isValidUrl("peterjack@@example.com")); // double '@' symbol
        assertFalse(Url.isValidUrl("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Url.isValidUrl("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Url.isValidUrl("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Url.isValidUrl("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Url.isValidUrl("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Url.isValidUrl("peterjack@example.com-")); // domain name ends with a hyphen

        // valid url
        assertTrue(Url.isValidUrl("PeterJack_1190@example.com"));
        assertTrue(Url.isValidUrl("a@bc")); // minimal
        assertTrue(Url.isValidUrl("test@localhost")); // alphabets only
        assertTrue(Url.isValidUrl("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Url.isValidUrl("123@145")); // numeric local part and domain name
        assertTrue(Url.isValidUrl("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Url.isValidUrl("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Url.isValidUrl("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
