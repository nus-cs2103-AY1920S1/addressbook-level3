package mams.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import mams.model.student.Name;


class ModuleTest {

    @Test
    void isValidModuleCode() {
        //invalid code
        assertFalse(Module.isValidModuleCode("")); // empty string
        assertFalse(Module.isValidModuleCode("CS")); // CS letters only
        assertFalse(Module.isValidModuleCode("CS123")); // less than 4 numbers
        assertFalse(Module.isValidModuleCode("CS12314")); // more than 4 numbers

        //valid code
        assertTrue(Module.isValidModuleCode("CS1020"));
        assertTrue(Module.isValidModuleCode("CS9999"));
        assertTrue(Module.isValidModuleCode("CS0000"));
    }

    @Test
    void isValidModuleName() {
        //@@adapted from author MistyRainforest
        assertFalse(Module.isValidModuleName("")); // empty string
        assertFalse(Module.isValidModuleName(" ")); // spaces only
        assertFalse(Module.isValidModuleName("^")); // only non-alphanumeric characters
        assertFalse(Module.isValidModuleName("Programming* Methology"));
        // contains non-alphanumeric characters

        assertTrue(Name.isValidName("programming methology")); // alphabets only
        assertTrue(Name.isValidName("cs research methodology 2")); // alphanumeric characters
        assertTrue(Name.isValidName("Intro to Psych Class")); // with capital letters
        assertTrue(Name.isValidName("Module with Long Nameeeeeeeeeeeeeeeeeeeee"));
        // long names
        //@@author
    }

    @Test
    void isValidQuota() {
        //invalid code
        assertFalse(Module.isValidQuota("-5")); // negative numbers

        //valid code
        assertTrue(Module.isValidQuota("100"));
    }

    @Test
    void isValidTimeSlot() {
        //invalid code
        assertFalse(Module.isValidTimeSlot(null)); // null input
        assertFalse(Module.isValidTimeSlot("")); // empty time slot
        assertFalse(Module.isValidTimeSlot("32,74")); // invalid time slot
        assertFalse(Module.isValidTimeSlot("-5,20")); // invalid time slot

        //valid code
        assertTrue(Module.isValidTimeSlot("23,24,55,54"));
    }

    @Test
    void isSameModule() {
        // same object -> returns true
    }

    @Test
    void testEquals() {
    }
}
