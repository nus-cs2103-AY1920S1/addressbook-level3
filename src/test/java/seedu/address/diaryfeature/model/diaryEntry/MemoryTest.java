package seedu.address.diaryfeature.model.diaryEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.diaryfeature.logic.parser.Validators;

public class MemoryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Memory test = new Memory (null);
        assertThrows(NullPointerException.class, () -> { test.toString().length();});    }

    @Test
    public void copy_is_different_from_original() {
        Memory test = new Memory("Hello");
        assertFalse(test == test.copy());
    }


    @Test
    public void check_privacy() {
        Memory test = new Memory("Test");
        assertFalse(test.getPrivacy());
        test.setPrivate();
        assertTrue(test.getPrivacy());
        test.unPrivate();
        assertFalse(test.getPrivacy());
    }

    @Test
    public void check_privacy_string_ouput() {
        Memory test = new Memory("Test");
        assertEquals("Test",test.toString());
        test.setPrivate();
        assertEquals("*****",test.toString());
        test.unPrivate();
        assertEquals("Test",test.toString());
    }




}

