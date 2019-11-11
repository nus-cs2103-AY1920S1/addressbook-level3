package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.moduleutil.NusModsShareLinkStrings;

class NusModsShareLinkTest {
    @Test
    void parseLink_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> NusModsShareLink.parseLink(null));
    }

    @Test
    void parseLink_validValues_noParseExceptionThrown() {
        try {
            NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_1);
            NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_2);
            NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_INVALID_MODULE_CODE);
            NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_INVALID_CLASS_NUMBER);
            NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_DUPLICATE_MODULE_LESSONS);
        } catch (ParseException e) {
            assert false;
        }
    }

    @Test
    void parseLink_invalidValues_throwsParseExceptionWithSpecificMessages() {
        try {
            NusModsShareLink.parseLink(NusModsShareLinkStrings.INVALID_LINK_NOT_URL);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(NusModsShareLinkStrings.INVALID_LINK_ANOTHER_URL);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(NusModsShareLinkStrings.INVALID_LINK_TYPO_URL);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(NusModsShareLinkStrings.INVALID_LINK_INVALID_SEMESTER);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_CONSTRAINTS, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(NusModsShareLinkStrings.INVALID_LINK_MISSING_QUERY_STRING);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_MISSING_QUERY_STRING, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(NusModsShareLinkStrings.INVALID_LINK_INVALID_QUERY_STRING);
            assert false;
        } catch (ParseException e) {
            assertEquals(NusModsShareLink.MESSAGE_NOTHING_TO_ADD, e.getMessage());
        }

        try {
            NusModsShareLink.parseLink(NusModsShareLinkStrings.INVALID_LINK_INVALID_CLASS_TYPE);
            assert false;
        } catch (ParseException e) {
            assertTrue(e.getMessage().startsWith("Invalid class type"));
        }
    }

    @Test
    void isValidUrl() {
        assertTrue(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.VALID_LINK_1));
        assertTrue(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.VALID_LINK_2));
        assertTrue(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.VALID_LINK_INVALID_MODULE_CODE));
        assertTrue(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.VALID_LINK_INVALID_CLASS_NUMBER));
        assertTrue(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.VALID_LINK_DUPLICATE_MODULE_LESSONS));
        assertFalse(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.INVALID_LINK_NOT_URL));
        assertFalse(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.INVALID_LINK_ANOTHER_URL));
        assertFalse(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.INVALID_LINK_TYPO_URL));
        assertFalse(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.INVALID_LINK_MISSING_QUERY_STRING));
        assertFalse(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.INVALID_LINK_INVALID_QUERY_STRING));
        assertFalse(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.INVALID_LINK_INVALID_SEMESTER));
        assertFalse(NusModsShareLink.isValidUrl(NusModsShareLinkStrings.INVALID_LINK_INVALID_CLASS_TYPE));
    }

    @Test
    void equals() throws ParseException {
        NusModsShareLink link = NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_1);
        NusModsShareLink link2 = NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_2);
        assertTrue(link.equals(link));
        assertTrue(link.equals(NusModsShareLink.parseLink(NusModsShareLinkStrings.VALID_LINK_1)));
        assertFalse(link.equals(link2));
    }
}
