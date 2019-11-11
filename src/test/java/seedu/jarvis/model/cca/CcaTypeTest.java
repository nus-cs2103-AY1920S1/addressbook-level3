package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CcaTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CcaType(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new CcaType(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> CcaType.isValidCcaType(null));

        // invalid name
        assertFalse(CcaType.isValidCcaType("")); // empty string
        assertFalse(CcaType.isValidCcaType(" ")); // spaces only
        assertFalse(CcaType.isValidCcaType("^")); // only non-alphanumeric characters
        assertFalse(CcaType.isValidCcaType("sport*")); // contains non-alphanumeric characters

        // valid name - 1 of the 4 enum types
        assertTrue(CcaType.isValidCcaType("sport")); // sport
        assertTrue(CcaType.isValidCcaType("performingArt")); // performingArt
        assertTrue(CcaType.isValidCcaType("uniformedGroup")); // uniformedGroup
        assertTrue(CcaType.isValidCcaType("club")); // club
    }
}
