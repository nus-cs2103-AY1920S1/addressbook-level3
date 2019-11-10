package seedu.scheduler.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.scheduler.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class SlotTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Slot.fromString(null));
    }

    @Test
    public void constructor_invalidSlot_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> Slot.fromString(" "));
        assertThrows(IllegalArgumentException.class, () -> Slot.fromString("1234"));
        assertThrows(IllegalArgumentException.class, () -> Slot.fromString("12/34/2019 12:34-12:34"));
    }

    @Test
    public void constructorThreeArgs_validInput_noExceptionThrows() {
        new Slot("16/10/2019", "00:00", "23:59");
    }

    @Test
    public void isValidSlot() {
        // null slot
        assertThrows(NullPointerException.class, () -> Slot.fromString(null));

        // invalid slot
        assertFalse(Slot.isValidSlot(""));
        assertFalse(Slot.isValidSlot("16-10-2019 00:00-00:01")); // incorrect date separator
        assertFalse(Slot.isValidSlot("16/10/2019 0000-0001")); // incorrect time format
        assertFalse(Slot.isValidSlot("16/10/2019 00:00 - 00:01")); // incorrect spacing
        assertFalse(Slot.isValidSlot("00/10/2019 00:00-00:01")); // incorrect date format
        assertFalse(Slot.isValidSlot("29/02/2019 00:00-00:01")); // incorrect date format
        assertFalse(Slot.isValidSlot("30/02/2019 00:00-00:01")); // incorrect date format
        assertFalse(Slot.isValidSlot("31/02/2019 00:00-00:01")); // incorrect date format
        assertFalse(Slot.isValidSlot("01/10/2019 23:59-24:00")); // incorrect time format

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
        assertTrue(Slot.isValidSlot("29/02/2020 10:00-11:00"));
    }

    @Test
    public void compareTo_equalDate_returnZero() {
        Slot subjectSlot = new Slot("28/10/2019", "10:00", "10:30");
        Slot testSlot = new Slot("28/10/2019", "10:00", "10:30");

        String errMessage = "T%d: %d\n";

        int comp = subjectSlot.compareTo(testSlot);
        assert comp == 0 : fail(String.format(errMessage, 1, comp));
    }

    @Test
    public void compareTo_laterDate_returnLesserThanZero() {
        Slot subjectSlot = new Slot("28/10/2019", "12:00", "13:00");
        Slot testSlot1 = new Slot("01/11/2019", "12:00", "13:00");
        Slot testSlot2 = new Slot("01/11/2020", "09:00", "10:00");
        Slot testSlot3 = new Slot("01/11/2019", "18:00", "19:00");
        Slot testSlot4 = new Slot("28/10/2019", "12:30", "13:00");
        Slot testSlot5 = new Slot("28/10/2019", "12:01", "13:00");
        Slot testSlot6 = new Slot("28/10/2019", "12:00", "13:01");

        assertTrue(subjectSlot.compareTo(testSlot1) < 0);
        assertTrue(subjectSlot.compareTo(testSlot2) < 0);
        assertTrue(subjectSlot.compareTo(testSlot3) < 0);
        assertTrue(subjectSlot.compareTo(testSlot4) < 0);
        assertTrue(subjectSlot.compareTo(testSlot5) < 0);
        assertTrue(subjectSlot.compareTo(testSlot6) < 0);
    }

    @Test
    public void compareTo_earlierDate_returnGreaterThanZero() {
        Slot subjectSlot = new Slot("09/08/2019", "08:00", "10:00");
        Slot testSlot1 = new Slot("01/01/2019", "08:00", "10:00");
        Slot testSlot2 = new Slot("01/01/2010", "10:00", "12:00");
        Slot testSlot3 = new Slot("01/01/2019", "07:00", "08:00");
        Slot testSlot4 = new Slot("09/08/2019", "07:00", "08:00");
        Slot testSlot5 = new Slot("09/08/2019", "07:59", "08:01");
        Slot testSlot6 = new Slot("09/08/2019", "08:00", "08:30");

        assertTrue(subjectSlot.compareTo(testSlot1) > 0);
        assertTrue(subjectSlot.compareTo(testSlot2) > 0);
        assertTrue(subjectSlot.compareTo(testSlot3) > 0);
        assertTrue(subjectSlot.compareTo(testSlot4) > 0);
        assertTrue(subjectSlot.compareTo(testSlot5) > 0);
        assertTrue(subjectSlot.compareTo(testSlot6) > 0);
    }
}
