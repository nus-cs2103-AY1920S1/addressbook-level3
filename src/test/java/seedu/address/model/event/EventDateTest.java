package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class EventDateTest {

    @Test
    void eventDateToString() {
        assertEquals(new EventDate(LocalDate.of(2019, 10, 20)).toString(),
                "20/10/2019");
    }

    @Test
    void eventStartDateEquals() {
        assertEquals(new EventDate(LocalDate.of(2019, 10, 20)),
                new EventDate(LocalDate.of(2019, 10, 20)));

        assertNotEquals(new EventDate(LocalDate.of(2019, 10, 20)),
                new EventDate(LocalDate.of(2019, 10, 19))); //diff day

        assertNotEquals(new EventDate(LocalDate.of(2019, 10, 20)),
                new EventDate(LocalDate.of(2019, 9, 19))); //diff month

        assertNotEquals(new EventDate(LocalDate.of(2019, 10, 20)),
                new EventDate(LocalDate.of(2020, 10, 19))); //diff year
    }

    @Test
    void isValidDate() {
        //null event date
        assertThrows(NullPointerException.class, () -> EventDate.isValidDate(null));

        // invalid date format
        assertFalse(EventDate.isValidDate("")); // empty string
        assertFalse(EventDate.isValidDate(" ")); // spaces only
        assertFalse(EventDate.isValidDate("2019/12/21")); // invalid date format
        assertFalse(EventDate.isValidDate("12-02-2019")); // invalid date format
        assertFalse(EventDate.isValidDate("12 Aug 2019")); // invalid date format


        // valid date format
        assertTrue(EventDate.isValidDate("02/12/2019")); // dd/MM/yyyy format
        assertTrue(EventDate.isValidDate("02/01/2018")); // dd/MM/yyyy format
    }

}
