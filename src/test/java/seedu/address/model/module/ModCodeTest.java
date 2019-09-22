package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModCodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModCode(null));
    }

    @Test
    public void constructor_invalidModCode_throwsIllegalArgumentException() {
        String invalidModCode = "";
        assertThrows(IllegalArgumentException.class, () -> new ModCode(invalidModCode));
    }

    @Test
    public void isValidModCode() {
        // null ModCode
        assertThrows(NullPointerException.class, () -> ModCode.isValidModCode(null));

        // blank ModCode
        assertFalse(ModCode.isValidModCode("")); // empty string
        assertFalse(ModCode.isValidModCode(" ")); // spaces only

        // missing parts
        assertFalse(ModCode.isValidModCode("S2103")); // missing starting letter
        assertFalse(ModCode.isValidModCode("CS")); // missing numbers
        assertFalse(ModCode.isValidModCode("CS210")); // missing ending character

        // invalid parts
        assertFalse(ModCode.isValidModCode("CS_2103")); // underscore in ModCode
        assertFalse(ModCode.isValidModCode("CS 2103")); // spaces in ModCode
        assertFalse(ModCode.isValidModCode(" CS2103")); // leading space
        assertFalse(ModCode.isValidModCode("CS2103 ")); // trailing space
        assertFalse(ModCode.isValidModCode(".CS2103")); // ModCode starts with a period
        assertFalse(ModCode.isValidModCode("CS2103.")); // ModCode ends with a period
        assertFalse(ModCode.isValidModCode("-CS2103")); // ModCode starts with a hyphen
        assertFalse(ModCode.isValidModCode("CS2103-")); // ModCode ends with a hyphen

        // valid ModCode
        assertTrue(ModCode.isValidModCode("CS2103"));
    }
}
