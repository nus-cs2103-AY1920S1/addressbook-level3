package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("28-10-2019")); // wrong formatting
        assertFalse(Date.isValidDate("28/10/2019")); // wrong formatting
        assertFalse(Date.isValidDate("281019")); // wrong formatting
        assertFalse(Date.isValidDate("12312019")); // wrong order of MMDDYYYY

        // valid date
        assertTrue(Date.isValidDate("28102019")); // Valid date of DDMMYYYY
        assertTrue(Date.isValidDate("10101010")); // Very old date
    }

    @Test
    void testToString() {
        Date date = new Date("10101010");
        assertEquals(date.toString(), "10/10/1010");
    }
}
