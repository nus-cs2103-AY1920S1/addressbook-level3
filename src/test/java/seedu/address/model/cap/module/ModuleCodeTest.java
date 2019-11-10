package seedu.address.model.cap.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class ModuleCodeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }
    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String invalidInput = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(invalidInput));
    }

    @Test
    public void isValidModuleCode() {
        // null time
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidModuleCode(null));

        // invalid addresses
        assertFalse(ModuleCode.isValidModuleCode("")); // empty string
        assertFalse(ModuleCode.isValidModuleCode(" ")); // spaces only
        assertFalse(ModuleCode.isValidModuleCode("12"));
        assertFalse(ModuleCode.isValidModuleCode("dekjdmff;,a"));
        assertFalse(ModuleCode.isValidModuleCode("a-20-20"));

        // valid addresses
        assertTrue(ModuleCode.isValidModuleCode("cs1010s"));
        assertTrue(ModuleCode.isValidModuleCode("cs2103"));
        assertTrue(ModuleCode.isValidModuleCode("ec4383"));
    }
}
