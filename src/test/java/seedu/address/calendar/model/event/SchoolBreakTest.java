package seedu.address.calendar.model.event;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.date.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SchoolBreakTest {
    @Test
    public void to_string() {
        SchoolBreak schoolBreak = new SchoolBreak(new Name("Winter vacation"), new Date(new Day(DayOfWeek.SUN, 1,
                MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)),
                new Date(new Day(DayOfWeek.WED, 8, MonthOfYear.JANUARY, new Year(2020)), MonthOfYear.JANUARY,
                        new Year(2020)));
        SchoolBreak shortSchoolBreak = new SchoolBreak(new Name("Short vacation"), new Date(new Day(DayOfWeek.SUN, 1,
                MonthOfYear.DECEMBER, new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)),
                new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER, new Year(2019)),
                        MonthOfYear.DECEMBER, new Year(2019)));
        assertEquals("'Winter vacation' school break from Sun, 1 December 2019 to Wed, 8 January 2020",
                schoolBreak.toString());
        assertEquals("'Short vacation' school break on Sun, 1 December 2019", shortSchoolBreak.toString());
    }
}
