package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class EventStartDateTest {

    @Test
    void eventStartDateToString() {
        assertEquals(new EventStartDate(LocalDate.of(2019, 10, 20)).toString(),
                "20/10/2019");
    }

    @Test
    void eventStartDateEquals() {
        assertEquals(new EventStartDate(LocalDate.of(2019, 10, 20)),
                new EventStartDate(LocalDate.of(2019, 10, 20)));

        assertNotEquals(new EventStartDate(LocalDate.of(2019, 10, 20)),
                new EventStartDate(LocalDate.of(2019, 10, 19))); //diff day

        assertNotEquals(new EventStartDate(LocalDate.of(2019, 10, 20)),
                new EventStartDate(LocalDate.of(2019, 9, 19))); //diff month

        assertNotEquals(new EventStartDate(LocalDate.of(2019, 10, 20)),
                new EventStartDate(LocalDate.of(2020, 10, 19))); //diff year
    }

    @Test
    void isValidStartDate() {
        //null event venue
        assertThrows(NullPointerException.class, () -> EventStartDate.isValidStartDate(null));

        // invalid end date format
        assertFalse(EventStartDate.isValidStartDate("")); // empty string
        assertFalse(EventStartDate.isValidStartDate(" ")); // spaces only
        assertFalse(EventStartDate.isValidStartDate("2019/12/21")); // invalid date format
        assertFalse(EventStartDate.isValidStartDate("12-02-2019")); // invalid date format
        assertFalse(EventStartDate.isValidStartDate("12 Aug 2019")); // invalid date format


        // valid end date format
        assertTrue(EventStartDate.isValidStartDate("02/12/2019")); // dd/MM/yyyy format
        assertTrue(EventStartDate.isValidStartDate("02/01/2018")); // dd/MM/yyyy format
    }
}
