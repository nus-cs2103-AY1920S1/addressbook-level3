package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEventDates.OCT_16_2021;
import static seedu.address.testutil.TypicalEventDates.OCT_19_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_20_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_21_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_22_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_23_2019;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class EventDateTest {

    @Test
    void eventIsAfterTest() {
        assertTrue(OCT_20_2019.isAfter(OCT_19_2019));
        assertTrue(OCT_16_2021.isAfter(OCT_23_2019));

        assertFalse(OCT_20_2019.isAfter(OCT_20_2019)); //same date
        assertFalse(OCT_19_2019.isAfter(OCT_20_2019));
    }

    @Test
    void eveventIsBeforeTest() {
        assertTrue(OCT_19_2019.isBefore(OCT_20_2019));
        assertTrue(OCT_23_2019.isBefore(OCT_16_2021));

        assertFalse(OCT_20_2019.isBefore(OCT_20_2019)); //same date
        assertFalse(OCT_20_2019.isBefore(OCT_19_2019));
    }

    @Test
    void eventDatesDifference() {
        assertEquals(OCT_19_2019.dateDifference(OCT_23_2019), 4);
        assertEquals(OCT_20_2019.dateDifference(OCT_16_2021), 727);
        assertEquals(OCT_16_2021.dateDifference(OCT_20_2019), -727);

    }

    @Test
    void eventDatesUntil() {
        List<EventDate> listEventDates = OCT_20_2019.datesUntil(OCT_22_2019).collect(Collectors.toList());
        assertEquals(listEventDates.size(), 3);
        assertFalse(listEventDates.contains(OCT_19_2019));
        assertTrue(listEventDates.contains(OCT_20_2019));
        assertTrue(listEventDates.contains(OCT_21_2019));
        assertTrue(listEventDates.contains(OCT_22_2019));
        assertFalse(listEventDates.contains(OCT_23_2019));
    }

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
        assertFalse(EventDate.isValidDate("1950/12/21")); // Too long ago
        assertFalse(EventDate.isValidDate("2019/02/31")); // Invalid Calendar Date

        // valid date format
        assertTrue(EventDate.isValidDate("02/12/2019")); // dd/MM/yyyy format
        assertTrue(EventDate.isValidDate("02/01/2018")); // dd/MM/yyyy format
    }

    @Test
    void isValidYearMonth() {
        //null event date
        assertThrows(NullPointerException.class, () -> EventDate.isValidYearMonth(null));

        // invalid Year/Month format
        assertFalse(EventDate.isValidYearMonth("")); // empty string
        assertFalse(EventDate.isValidYearMonth(" ")); // spaces only
        assertFalse(EventDate.isValidYearMonth("2019/12/21")); // no need for day
        assertFalse(EventDate.isValidYearMonth("Aug 2019")); // not words
        assertFalse(EventDate.isValidYearMonth("02/19")); // Year not 4 digits
        assertFalse(EventDate.isValidYearMonth("2019/02")); // Wrong ordering

        assertTrue(EventDate.isValidYearMonth("12/2009")); // dd/MM/yyyy format
        assertTrue(EventDate.isValidYearMonth("02/2019")); // dd/MM/yyyy format
        assertTrue(EventDate.isValidYearMonth("05/2019")); // dd/MM/yyyy format
    }

}
