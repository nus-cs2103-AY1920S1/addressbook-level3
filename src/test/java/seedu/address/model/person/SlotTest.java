package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class SlotTest {

    @Test
    public void constructor_null_throwsNUllPointerException() {
        assertThrows(NullPointerException.class, () -> new Slot(null));
    }

    @Test
    public void constructor_invalidSlot_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Slot(" "));
        assertThrows(IllegalArgumentException.class, () -> new Slot("1234"));
        assertThrows(IllegalArgumentException.class, () -> new Slot("12/34/2019 12:34-12:34"));
    }

    @Test
    public void constructorThreeArgs_validInput_noExceptionThrows() {
        new Slot("16/10/2019", "00:00", "23:59");
    }

    @Test
    public void isValidSlot() {
        // null slot
        assertThrows(NullPointerException.class, () -> new Slot(null));

        // invalid slot
        assertFalse(Slot.isValidSlot(""));
        assertFalse(Slot.isValidSlot("16-10-2019 00:00-00:01")); // incorrect date separator
        assertFalse(Slot.isValidSlot("16/10/2019 0000-0001")); // incorrect time format
        assertFalse(Slot.isValidSlot("16/10/2019 00:00 - 00:01")); // incorrect spacing
        assertFalse(Slot.isValidSlot("00/10/2019 00:00-00:01")); // incorrect date format
        assertFalse(Slot.isValidSlot("01/10/2019 24:59-24:60")); // incorrect time format

        // valid slot
        assertTrue(Slot.isValidSlot(String.format(Slot.STRING_FORMAT, "16/10/2019", "00:00", "23:59")));
        assertTrue(Slot.isValidSlot("01/01/1997 10:00-10:10"));
        assertTrue(Slot.isValidSlot("01/01/0001 00:00-00:01"));
        assertTrue(Slot.isValidSlot("11/01/0001 00:00-00:01"));
        assertTrue(Slot.isValidSlot("01/11/0001 00:00-00:01"));
        assertTrue(Slot.isValidSlot("01/01/1997 00:00-00:01"));
        assertTrue(Slot.isValidSlot("30/12/9999 00:00-23:59"));
        assertTrue(Slot.isValidSlot("16/10/2019 03:01-20:01"));
        assertTrue(Slot.isValidSlot("03/12/1997 10:00-13:00"));
    }
}
