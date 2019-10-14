package seedu.jarvis.model.cca;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jarvis.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CcaName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new CcaName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> CcaName.isValidName(null));

        // invalid name
        assertFalse(CcaName.isValidName("")); // empty string
        assertFalse(CcaName.isValidName(" ")); // spaces only
        assertFalse(CcaName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(CcaName.isValidName("canoeing*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(CcaName.isValidName("modern dance")); // alphabets only
        assertTrue(CcaName.isValidName("12345")); // numbers only
        assertTrue(CcaName.isValidName("2nd cca")); // alphanumeric characters
        assertTrue(CcaName.isValidName("Modern Dance")); // with capital letters
        assertTrue(CcaName.isValidName("Tamil language society production")); // long names
    }
}
