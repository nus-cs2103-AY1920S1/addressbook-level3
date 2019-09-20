package seedu.algobase.model.Problem;

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
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new WebLink(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> WebLink.isValidEmail(null));

        // blank email
        assertFalse(WebLink.isValidEmail("")); // empty string
        assertFalse(WebLink.isValidEmail(" ")); // spaces only

        // missing parts
        assertFalse(WebLink.isValidEmail("@example.com")); // missing local part
        assertFalse(WebLink.isValidEmail("peterjackexample.com")); // missing '@' symbol
        assertFalse(WebLink.isValidEmail("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(WebLink.isValidEmail("peterjack@-")); // invalid domain name
        assertFalse(WebLink.isValidEmail("peterjack@exam_ple.com")); // underscore in domain name
        assertFalse(WebLink.isValidEmail("peter jack@example.com")); // spaces in local part
        assertFalse(WebLink.isValidEmail("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(WebLink.isValidEmail(" peterjack@example.com")); // leading space
        assertFalse(WebLink.isValidEmail("peterjack@example.com ")); // trailing space
        assertFalse(WebLink.isValidEmail("peterjack@@example.com")); // double '@' symbol
        assertFalse(WebLink.isValidEmail("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(WebLink.isValidEmail("peterjack@example@com")); // '@' symbol in domain name
        assertFalse(WebLink.isValidEmail("peterjack@.example.com")); // domain name starts with a period
        assertFalse(WebLink.isValidEmail("peterjack@example.com.")); // domain name ends with a period
        assertFalse(WebLink.isValidEmail("peterjack@-example.com")); // domain name starts with a hyphen
        assertFalse(WebLink.isValidEmail("peterjack@example.com-")); // domain name ends with a hyphen

        // valid email
        assertTrue(WebLink.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(WebLink.isValidEmail("a@bc")); // minimal
        assertTrue(WebLink.isValidEmail("test@localhost")); // alphabets only
        assertTrue(WebLink.isValidEmail("!#$%&'*+/=?`{|}~^.-@example.org")); // special characters local part
        assertTrue(WebLink.isValidEmail("123@145")); // numeric local part and domain name
        assertTrue(WebLink.isValidEmail("a1+be!@example1.com")); // mixture of alphanumeric and special characters
        assertTrue(WebLink.isValidEmail("peter_jack@very-very-very-long-example.com")); // long domain name
        assertTrue(WebLink.isValidEmail("if.you.dream.it_you.can.do.it@example.com")); // long local part
    }
}
