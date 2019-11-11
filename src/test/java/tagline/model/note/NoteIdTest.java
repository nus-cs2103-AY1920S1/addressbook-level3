package tagline.model.note;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NoteIdTest {

    //@Test
    //public void constructor_null_throwsNullPointerException() {
    //    assertThrows(NullPointerException.class, () -> new Name(null));
    //}

    @Test
    public void constructor_invalidNoteId_throwsIllegalArgumentException() {
        String invalidNoteId1 = "";
        assertThrows(IllegalArgumentException.class, () -> new NoteId(invalidNoteId1));

        String invalidNoteId2 = "12asd";
        assertThrows(IllegalArgumentException.class, () -> new NoteId(invalidNoteId2));

        String invalidNoteId3 = "one";
        assertThrows(IllegalArgumentException.class, () -> new NoteId(invalidNoteId3));
    }

    @Test
    public void isValidNote() {
        // null phone number
        assertThrows(NullPointerException.class, () -> NoteId.isValidNoteId(null));

        // invalid noteId numbers
        assertFalse(NoteId.isValidNoteId("")); // empty string
        assertFalse(NoteId.isValidNoteId(" ")); // spaces only
        //assertFalse(NoteId.isValidNoteId("91")); // less than 3 numbers
        assertFalse(NoteId.isValidNoteId("phone")); // non-numeric
        assertFalse(NoteId.isValidNoteId("9011p041")); // alphabets within digits
        assertFalse(NoteId.isValidNoteId("9312 1534")); // spaces within digits

        // valid noteId numbers
        assertTrue(NoteId.isValidNoteId("911")); // exactly 3 numbers
        assertTrue(NoteId.isValidNoteId("93121534"));
        assertTrue(NoteId.isValidNoteId("124293842033123")); // long phone numbers
    }

    @Test
    public void constructor_sequentialId() {
        //doesnt work now, create a separate class // has this been resolved??
        // Saved the currentCount in NoteIdCounter to be reapplied later
        // zeroes out the current count to ensure it starts at zero
        long currCount = NoteIdCounter.getCount();
        NoteIdCounter.setZero();

        assertEquals(0, NoteIdCounter.getCount());

        NoteId one = new NoteId();
        assertEquals(1, one.value);

        NoteId two = new NoteId();
        assertEquals(2, two.value);

        NoteId fifty = new NoteId(50);
        assertEquals(50, fifty.value);

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
        assertTrue(one.toString().equals("00001"));
        //assertEquals("00001", one.toString());

        NoteId two = new NoteId();
        assertTrue(two.toString().equals("00002"));
        //assertEquals("00002", two.toString());

        NoteId fifty = new NoteId(50);
        assertTrue(fifty.toString().equals("00050"));
        //assertEquals("00050", fifty.toString());

        NoteId hundred = new NoteId(100);
        assertTrue(hundred.toString().equals("00100"));
        //assertEquals("00100", hundred.toString());

        NoteId twoThousand = new NoteId(2000);
        assertTrue(twoThousand.toString().equals("02000"));
        //assertEquals("02000", twoThousand.toString());

        NoteId fortyTwoThousand = new NoteId(42000);
        assertTrue(fortyTwoThousand.toString().equals("42000"));
        //assertEquals("42000", fortyTwoThousand.toString());

        NoteId fiveNines = new NoteId(99999);
        assertTrue(fiveNines.toString().equals("99999"));
        //assertEquals("99999", fiveNines.toString());

        NoteId countdown = new NoteId(54321);
        assertTrue(countdown.toString().equals("54321"));
        //assertEquals("54321", countdown.toString());

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
