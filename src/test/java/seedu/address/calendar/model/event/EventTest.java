package seedu.address.calendar.model.event;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {
    private Commitment commitment = new Commitment(new Name("2103 Project"), new Date(new Day(DayOfWeek.FRI, 6,
            MonthOfYear.SEPTEMBER, new Year(2019)), MonthOfYear.SEPTEMBER, new Year(2019)),
            new Date(new Day(DayOfWeek.SAT, 9, MonthOfYear.NOVEMBER, new Year(2019)), MonthOfYear.NOVEMBER,
                    new Year(2019)));

    private Trip trip = new Trip(new Name("Japan trip"), new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER,
            new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)), new Date(new Day(DayOfWeek.WED, 1,
            MonthOfYear.JANUARY, new Year(2020)), MonthOfYear.JANUARY, new Year(2020)));

    private SchoolBreak schoolBreak = new SchoolBreak(new Name("Winter vacation"), new Date(new Day(DayOfWeek.SUN, 1,
            MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)),
            new Date(new Day(DayOfWeek.WED, 8, MonthOfYear.JANUARY, new Year(2020)), MonthOfYear.JANUARY,
                    new Year(2020)));

    private Holiday holiday = new Holiday(new Name("Christmas"), new Date(new Day(DayOfWeek.WED, 25,
            MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)),
            new Date(new Day(DayOfWeek.WED, 25, MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER,
                    new Year(2019)));

    @Test
    public void isBusy() {
        assertTrue(commitment.isBusy());
        assertTrue(trip.isBusy());
        assertFalse(schoolBreak.isBusy());
        assertFalse(holiday.isBusy());
    }

    @Test
    public void getNameStr() {
        assertEquals("2103 Project", commitment.getNameStr());
        assertEquals("Japan trip", trip.getNameStr());
        assertEquals("Winter vacation", schoolBreak.getNameStr());
        assertEquals("Christmas", holiday.getNameStr());
    }

    @Test
    public void getEventType() {
        assertEquals(EventType.COMMITMENT, commitment.getEventType());
        assertEquals(EventType.TRIP, trip.getEventType());
        assertEquals(EventType.SCHOOL_BREAK, schoolBreak.getEventType());
        assertEquals(EventType.HOLIDAY, holiday.getEventType());
    }

    @Test
    public void isOneDay() {
        assertTrue(holiday.isOneDay());
        assertFalse(trip.isOneDay());
        assertFalse(schoolBreak.isOneDay());
        assertFalse(commitment.isOneDay());
    }

    @Test
    public void asEventQuery() {
        EventQuery expectedCommitmentQuery = new EventQuery(new Date(new Day(DayOfWeek.FRI, 6,
                MonthOfYear.SEPTEMBER, new Year(2019)), MonthOfYear.SEPTEMBER, new Year(2019)),
                new Date(new Day(DayOfWeek.SAT, 9, MonthOfYear.NOVEMBER, new Year(2019)), MonthOfYear.NOVEMBER,
                        new Year(2019)));
        EventQuery expectedTripQuery = new EventQuery(new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER,
                new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)), new Date(new Day(DayOfWeek.WED, 1,
                MonthOfYear.JANUARY, new Year(2020)), MonthOfYear.JANUARY, new Year(2020)));
        EventQuery expectedSchoolBreakQuery = new EventQuery(new Date(new Day(DayOfWeek.SUN, 1,
                MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)),
                new Date(new Day(DayOfWeek.WED, 8, MonthOfYear.JANUARY, new Year(2020)), MonthOfYear.JANUARY,
                        new Year(2020)));
        EventQuery expectedHolidayQuery = new EventQuery(new Date(new Day(DayOfWeek.WED, 25,
                MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)),
                new Date(new Day(DayOfWeek.WED, 25, MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER,
                        new Year(2019)));

        assertEquals(expectedCommitmentQuery, commitment.asEventQuery());
        assertEquals(expectedTripQuery, trip.asEventQuery());
        assertEquals(expectedSchoolBreakQuery, schoolBreak.asEventQuery());
        assertEquals(expectedHolidayQuery, holiday.asEventQuery());
    }
}
