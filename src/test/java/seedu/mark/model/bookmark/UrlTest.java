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
        assertThrows(NullPointerException.class, () -> Url.isValidEmail(null));

        // blank url
        assertFalse(Url.isValidEmail("")); // empty string
        assertFalse(Url.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(Url.isValidEmail("@example.com")); // missing local part
        assertFalse(Url.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(Url.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(Url.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(Url.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(Url.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(Url.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(Url.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(Url.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(Url.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(Url.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(Url.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(Url.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(Url.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(Url.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(Url.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid url
        assertTrue(Url.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Url.isValidEmail("a@bc")); // minimal
        assertTrue(Url.isValidEmail("test@localhost")); // alphabets only
        assertTrue(Url.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(Url.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(Url.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(Url.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(Url.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
