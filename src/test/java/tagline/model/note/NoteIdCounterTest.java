package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteIdCounterTest {

    //@Test
    //public void constructor_null_throwsNullPointerException() {
    //    assertThrows(NullPointerException.class, () -> new Name(null));
    //}

    @Test
    public void isValidNote() {
        // null phone number
        assertThrows(NullPointerException.class, () -> NoteIdCounter.isValidNoteIdCount(null));

        // invalid noteId numbers
        assertFalse(NoteIdCounter.isValidNoteIdCount("")); // empty string
        assertFalse(NoteIdCounter.isValidNoteIdCount(" ")); // spaces only
        //assertFalse(NoteIdCounter.isValidNoteIdCount("91")); // less than 3 numbers
        assertFalse(NoteIdCounter.isValidNoteIdCount("phone")); // non-numeric
        assertFalse(NoteIdCounter.isValidNoteIdCount("9011p041")); // alphabets within digits
        assertFalse(NoteIdCounter.isValidNoteIdCount("9312 1534")); // spaces within digits

        // valid noteId numbers
        assertTrue(NoteIdCounter.isValidNoteIdCount("911")); // exactly 3 numbers
        assertTrue(NoteIdCounter.isValidNoteIdCount("93121534"));
        assertTrue(NoteIdCounter.isValidNoteIdCount("124293842033123")); // long phone numbers
    }


    @Test
    public void setCountFromStorage_throwsIllegalArgumentException() {
        //doesnt work now, create a separate class
        long currCount = NoteIdCounter.getCount();
        NoteIdCounter.setZero();

        String invalidNoteIdCounter1 = "";
        assertThrows(IllegalArgumentException.class, () -> NoteIdCounter.setCountFromStorage(invalidNoteIdCounter1));

        String invalidNoteIdCounter2 = "12asd";
        assertThrows(IllegalArgumentException.class, () -> NoteIdCounter.setCountFromStorage(invalidNoteIdCounter2));
        //assertThrows(IllegalArgumentException.class, () -> new NoteId(invalidNoteIdCounter2));

        String invalidNoteIdCounter3 = "one";
        assertThrows(IllegalArgumentException.class, () -> NoteIdCounter.setCountFromStorage(invalidNoteIdCounter3));
        //assertThrows(IllegalArgumentException.class, () -> new NoteId(invalidNoteIdCounter3));

        // Reset Counter to original value to prevent disruption of other test cases
        NoteIdCounter.setCount(currCount);
    }

    @Test
    public void setZero_test() {
        long currCount = NoteIdCounter.getCount();
        NoteIdCounter.setZero();

        NoteIdCounter.setZero();
        assertEquals(0, NoteIdCounter.getCount());

        NoteIdCounter.setCount(50);
        assertEquals(50, NoteIdCounter.getCount());
        NoteIdCounter.setZero();
        assertEquals(0, NoteIdCounter.getCount());

        assertEquals(1, NoteIdCounter.incrementThenGetValue());
        assertEquals(1, NoteIdCounter.getCount());
        NoteIdCounter.setZero();
        assertEquals(0, NoteIdCounter.getCount());

        // Reset Counter to original value to prevent disruption of other test cases
        NoteIdCounter.setCount(currCount);
    }

    @Test
    public void getThenIncrement_test() {
        //doesnt work now, create a separate class
        long currCount = NoteIdCounter.getCount();
        NoteIdCounter.setZero();

        assertEquals(0, NoteIdCounter.getCount());

        assertEquals(1, NoteIdCounter.incrementThenGetValue());
        assertEquals(1, NoteIdCounter.getCount());

        assertEquals(2, NoteIdCounter.incrementThenGetValue());
        assertEquals(2, NoteIdCounter.getCount());

        assertEquals(3, NoteIdCounter.incrementThenGetValue());
        assertEquals(3, NoteIdCounter.getCount());

        // Reset Counter to original value to prevent disruption of other test cases
        NoteIdCounter.setCount(currCount);
    }

    @Test
    public void toString_test() {
        // Saved the currentCount in NoteIdCounter to be reapplied later
        // zeroes out the current count to ensure it starts at zero
        long currCount = NoteIdCounter.getCount();
        NoteIdCounter.setZero();

        NoteId one = new NoteId();
        assertEquals("00001", one.toString());

        NoteId two = new NoteId();
        assertEquals("00002", two.toString());

        NoteId fifty = new NoteId(50);
        assertEquals("00050", fifty.toString());

        NoteId hundred = new NoteId(100);
        assertEquals("00100", hundred.toString());

        NoteId twoThousand = new NoteId(2000);
        assertEquals("02000", twoThousand.toString());

        NoteId fortyTwoThousand = new NoteId(42000);
        assertEquals("42000", fortyTwoThousand.toString());

        NoteId fiveNines = new NoteId(99999);
        assertEquals("99999", fiveNines.toString());

        NoteId countdown = new NoteId(54321);
        assertEquals("54321", countdown.toString());

        // Reset Counter to original value to prevent disruption of other test cases
        NoteIdCounter.setCount(currCount);
    }

    @Test
    public void equals_test() {

        // Saved the currentCount in NoteIdCounter to be reapplied later
        // zeroes out the current count to ensure it starts at zero
        long currCount = NoteIdCounter.getCount();
        NoteIdCounter.setZero();
        NoteId one = new NoteId();


        assertTrue(one.equals(new NoteId(1)));

        NoteId two = new NoteId();
        assertTrue(two.equals(new NoteId(2)));

        NoteId fifty = new NoteId(50);
        assertTrue(fifty.equals(new NoteId(50)));

        // Reset Counter to original value to prevent disruption of other test cases
        NoteIdCounter.setCount(currCount);
    }

}
