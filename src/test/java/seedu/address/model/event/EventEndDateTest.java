package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import jdk.jfr.StackTrace;
import org.junit.jupiter.api.Test;

class EventEndDateTest {

    @Test
    void eventEndDateToString() {
        assertEquals(new EventEndDate(LocalDate.of(2019, 10, 20)).toString(),
                "20/10/2019");
    }

    @Test
    void eventEndDateEquals() {
        assertEquals(new EventEndDate(LocalDate.of(2019, 10, 20)),
                new EventEndDate(LocalDate.of(2019, 10, 20)));

        assertNotEquals(new EventEndDate(LocalDate.of(2019, 10, 20)),
                new EventEndDate(LocalDate.of(2019, 10, 19))); //diff day

        assertNotEquals(new EventEndDate(LocalDate.of(2019, 10, 20)),
                new EventEndDate(LocalDate.of(2019, 9, 19))); //diff month

        assertNotEquals(new EventEndDate(LocalDate.of(2019, 10, 20)),
                new EventEndDate(LocalDate.of(2020, 10, 19))); //diff year
    }

    @Test
    void isValidEndDate() {
        //null event venue
        assertThrows(NullPointerException.class, () -> EventEndDate.isValidEndDate(null));

        // invalid end date format
        assertFalse(EventEndDate.isValidEndDate("")); // empty string
        assertFalse(EventEndDate.isValidEndDate(" ")); // spaces only
        assertFalse(EventEndDate.isValidEndDate("2019/12/21")); // invalid date format
        assertFalse(EventEndDate.isValidEndDate("12-02-2019")); // invalid date format
        assertFalse(EventEndDate.isValidEndDate("12 Aug 2019")); // invalid date format


        // valid end date format
        assertTrue(EventEndDate.isValidEndDate("02/12/2019")); // dd/MM/yyyy format
        assertTrue(EventEndDate.isValidEndDate("02/01/2018")); // dd/MM/yyyy format
    }
}
