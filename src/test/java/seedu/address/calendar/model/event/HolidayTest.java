package seedu.address.calendar.model.event;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HolidayTest {

    @Test
    public void to_string() {
        Holiday oneHoliday = new Holiday(new Name("Christmas"), new Date(new Day(DayOfWeek.WED, 25,
                MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)),
                new Date(new Day(DayOfWeek.WED, 25, MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER,
                        new Year(2019)));
        Holiday multiHoliday = new Holiday(new Name("Long weekend"), new Date(new Day(DayOfWeek.FRI, 9,
                MonthOfYear.AUGUST, new Year(2019)), MonthOfYear.AUGUST, new Year(2019)),
                new Date(new Day(DayOfWeek.MON, 12, MonthOfYear.AUGUST, new Year(2019)),
                        MonthOfYear.AUGUST, new Year(2019)));
        assertEquals("'Christmas' holiday on Wed, 25 December 2019", oneHoliday.toString());
        assertEquals("'Long weekend' holiday from Fri, 9 August 2019 to Mon, 12 August 2019",
                multiHoliday.toString());
    }
}
