package mams.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import mams.model.student.Name;
import org.junit.jupiter.api.Test;

import mams.testutil.Assert;

class ModuleTest {

    @Test
    void isValidModuleCode() {
        assertFalse(Module.isValidModuleCode("")); // empty string
        assertFalse(Module.isValidModuleCode("CS")); // CS letters only
        assertFalse(Module.isValidModuleCode("CS123")); // less than 4 numbers
        assertFalse(Module.isValidModuleCode("CS12314")); // more than 4 numbers
    }

    @Test
    void isValidModuleName() {
    }

    @Test
    void isValidModuleDescription() {
    }

    @Test
    void isValidLecturerName() {
    }

    @Test
    void isValidQuota() {
    }

    @Test
    void isValidTimeSlot() {
    }

    @Test
    void isSameModule() {
    }

    @Test
    void testEquals() {
    }
}
