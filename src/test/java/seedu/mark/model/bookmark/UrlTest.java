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
    public void isValidUrl() {
        // null url
        assertThrows(NullPointerException.class, () -> Url.isValidUrl(null));

        // blank url
        assertFalse(Url.isValidUrl("")); // empty string
        assertFalse(Url.isValidUrl(" ")); // spaces only

        // invalid scheme
        assertFalse(Url.isValidUrl("example.com")); // missing scheme
        assertFalse(Url.isValidUrl("abc://example.com")); // invalid scheme

        // invalid authority
        assertFalse(Url.isValidUrl("https://")); // missing authority
        assertFalse(Url.isValidUrl("https://a")); // authority too short
        assertFalse(Url.isValidUrl("https://.example.com")); // authority starts with a period
        assertFalse(Url.isValidUrl("https://example.com.")); // authority ends with a period
        assertFalse(Url.isValidUrl(" http://peterjack.example.com")); // leading space
        assertFalse(Url.isValidUrl("https://peterjack.example.com ")); // trailing space
        assertFalse(Url.isValidUrl("https://peter jack.example.com")); // spaces in authority

        // invalid optional parts
        assertFalse(Url.isValidUrl("https://example.com//")); // empty path segment
        assertFalse(Url.isValidUrl("https://example.com?")); // empty query
        assertFalse(Url.isValidUrl("https://example.com#")); // empty fragment
        assertFalse(Url.isValidUrl("https://example.com##example")); // double '#' symbol
        assertFalse(Url.isValidUrl("https://example.com#example1#example")); // double fragment

        // valid url scheme
        assertTrue(Url.isValidUrl("http://example.com"));
        assertTrue(Url.isValidUrl("https://example.com"));
        assertTrue(Url.isValidUrl("ftp://example.com"));
        assertTrue(Url.isValidUrl("file://example.com"));

        // valid url - different parts present
        assertTrue(Url.isValidUrl("https://mark.com/")); // ends with slash
        assertTrue(Url.isValidUrl("https://example.com/path?query#index.html")); // all parts present
        assertTrue(Url.isValidUrl("https://example.com/some/path/should/work")); // multiple path segments
        assertTrue(Url.isValidUrl("https://example.com/check.php?my-query")); // path segment and query
        assertTrue(Url.isValidUrl("https://example.com/check.php#fragment-to-show")); // path segment and fragment
        assertTrue(Url.isValidUrl("https://example.com?my-query")); // query alone
        assertTrue(Url.isValidUrl("https://example.com?my-query/")); // query with slash
        assertTrue(Url.isValidUrl("https://example.com#fragment-to-show")); // fragment alone

        // valid url - lengths
        assertTrue(Url.isValidUrl("https://ab")); // minimal authority
        assertTrue(Url.isValidUrl("http://ab/a")); // minimal path
        assertTrue(Url.isValidUrl("http://ab?a")); // minimal query
        assertTrue(Url.isValidUrl("http://ab#a")); // minimal fragment
        assertTrue(Url.isValidUrl("https://peter_jack.very-very-very-long-example.com")); // long authority
        assertTrue(Url.isValidUrl("https://peter_jack.example.com/very-very-very-long-path.php")); // long path
        assertTrue(Url.isValidUrl("https://peter_jack.example.com?very=very-very&query=long")); // long query
        assertTrue(Url.isValidUrl("https://peter_jack.example.com#very-very-very-long-fragment")); // long fragment

        // valid url - special characters
        assertTrue(Url.isValidUrl("http://!$&'*+,;=:()@-_.~")); // special characters in authority
        assertTrue(Url.isValidUrl("https://example.com/!$&'*+,;=:()@-_~.")); // special characters in path
        assertTrue(Url.isValidUrl("https://example.com?!$&'*+,;=:()@-_~./?")); // special characters in query
        assertTrue(Url.isValidUrl("https://example.com#!$&'*+,;=:()@-_~./?")); // special characters in response
    }
}
