package seedu.address.itinerary.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(AssertionError.class, () -> new Date(null));
    }

    @Test
    void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string

        assertFalse(Date.isValidDate(" ")); // spaces only

        assertFalse(Date.isValidDate("28-10-2019")); // wrong formatting with -

        assertFalse(Date.isValidDate("28/10/2019")); // wrong formatting with /

        assertFalse(Date.isValidDate("281019")); // wrong formatting for year

        assertFalse(Date.isValidDate("392019")); // wrong formatting for day and month

        assertFalse(Date.isValidDate("12312019")); // wrong order of MMDDYYYY

        assertFalse(Date.isValidDate("29021800")); // 29th February tested

        assertFalse(Date.isValidDate("10070999")); // date is too old. Only year 1000 to 3999

        assertFalse(Date.isValidDate("10074000")); // date is too far into the future. Only year 1000 to 3999

        // valid date
        assertTrue(Date.isValidDate("28102019")); // Valid date of DDMMYYYY

        assertTrue(Date.isValidDate("01011000")); // oldest date accepted

        assertTrue(Date.isValidDate("29022000")); // 29th February tested

        assertTrue(Date.isValidDate("10101010")); // Very old date

        assertTrue(Date.isValidDate("31123999")); // most futuristic date accepted
    }

    @Test
    void testToString() {
        Date date = new Date("10101010");
        Date date1 = new Date("28102019");
        Date date2 = new Date("31123999");

        // Testing different value
        // Unformatted date
        assertNotEquals("10101010", date.toString());

        // Date object will only be created if the input string pass the validation test
        assertEquals("10/10/1010", date.toString());

        assertEquals("28/10/2019", date1.toString());

        assertEquals("31/12/3999", date2.toString());
    }
}
