package seedu.address.calendar.model.event;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripTest {

    @Test
    public void to_string() {
        Trip trip = new Trip(new Name("Japan"), new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER,
                new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)), new Date(new Day(DayOfWeek.WED, 1,
                MonthOfYear.JANUARY, new Year(2020)), MonthOfYear.JANUARY, new Year(2020)));
        Trip shortTrip = new Trip(new Name("Malaysia"), new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER,
                new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)), new Date(new Day(DayOfWeek.SUN, 1,
                MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)));

        assertEquals("'Japan' trip from Sun, 1 December 2019 to Wed, 1 January 2020", trip.toString());
        assertEquals("'Malaysia' trip on Sun, 1 December 2019", shortTrip.toString());
    }
}
