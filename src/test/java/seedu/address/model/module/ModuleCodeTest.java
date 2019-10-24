package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ModuleCodeTest {
    @Test
    void isValidCode() {
        assertThrows(NullPointerException.class, () -> ModuleCode.isValidCode(null));

        // invalid module codes
        assertFalse(ModuleCode.isValidCode("")); // empty string
        assertFalse(ModuleCode.isValidCode(" ")); // spaces only

        // valid module codes
        assertTrue(ModuleCode.isValidCode("CS3245"));
        assertTrue(ModuleCode.isValidCode("ST2334"));
        assertTrue(ModuleCode.isValidCode("CS1101S"));
    }

    @Test
    public void constructor_invalidModuleCode_throwsIllegalArgumentException() {
        String moduleCode = "";
        assertThrows(IllegalArgumentException.class, () -> new ModuleCode(moduleCode));
    }


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModuleCode(null));
    }

    @Test
    public void equals_success() {
        assertEquals(new ModuleCode("CS1101S"), new ModuleCode("CS1101S"));
        assertNotSame(new ModuleCode("ST2334"), new ModuleCode("ST2334"));
        assertEquals(new ModuleCode("CS1101S").hashCode(), new ModuleCode("CS1101S").hashCode());
        assertNotSame(new ModuleCode("CS1101S").hashCode(), new ModuleCode("CS3245").hashCode());
    }
}
