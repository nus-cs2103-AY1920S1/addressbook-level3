package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ModuleCodeTest {
    @Test
    void testEquals() {
        ModuleCode moduleCode = new ModuleCode("CS2030");

        // same values -> return true
        assertTrue(moduleCode.equals(new ModuleCode("CS2030")));

        // same object -> return true
        assertTrue(moduleCode.equals(moduleCode));

        // null -> returns false
        assertFalse(moduleCode.equals(null));

        // different moduleCode -> returns false
        assertFalse(moduleCode.equals(new ModuleCode("CS2040")));
    }

    @Test
    void testHashCode() {
        ModuleCode m1 = new ModuleCode("CS2030");
        ModuleCode m2 = new ModuleCode("CS2030");
        ModuleCode m3 = new ModuleCode("CS2040");
        assertEquals(m1.hashCode(), m1.hashCode());
        assertEquals(m1.hashCode(), m2.hashCode());
        assertNotEquals(m1.hashCode(), m3.hashCode());
    }
}
