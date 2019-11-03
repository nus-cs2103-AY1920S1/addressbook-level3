package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class HeadingTest {

    @Test
    void constructor_null_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Heading(null));
    }

    @Test
    void constructor_invalidHeading_throwsIllegalArgumentException() {
        String invalidHeading = "";
        assertThrows(IllegalArgumentException.class, () -> new Heading(invalidHeading));
    }

    @Test
    void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Heading.isValidHeading(null));

        // invalid addresses
        assertFalse(Heading.isValidHeading("")); // empty string
        assertFalse(Heading.isValidHeading(" ")); // spaces only

        // valid addresses
        assertTrue(Heading.isValidHeading("Blk 456, Den Road, #01-355"));
        assertTrue(Heading.isValidHeading("-")); // one character
        assertTrue(Heading.isValidHeading("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    @Test
    void equals() {
        Heading firstHeading = new Heading("Heading");

        //same object -> return true
        assertEquals(firstHeading, firstHeading);

        //same heading string but different object reference -> return true
        assertEquals(firstHeading, new Heading("Heading"));

        //different object and different string -> return true
        assertNotEquals(firstHeading, new Heading("heading"));
    }
}
