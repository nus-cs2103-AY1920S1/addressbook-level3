package seedu.address.calendar.model.event;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommitmentTest {
    @Test
    public void to_string() {
        Commitment multiCommitment = new Commitment(new Name("2103 Project"), new Date(new Day(DayOfWeek.FRI, 6,
                MonthOfYear.SEPTEMBER, new Year(2019)), MonthOfYear.SEPTEMBER, new Year(2019)),
                new Date(new Day(DayOfWeek.SAT, 9, MonthOfYear.NOVEMBER, new Year(2019)), MonthOfYear.NOVEMBER,
                        new Year(2019)));
        Commitment oneCommitment = new Commitment(new Name("Volunteer"), new Date(new Day(DayOfWeek.TUE, 29,
                MonthOfYear.FEBRUARY, new Year(2028)), MonthOfYear.FEBRUARY, new Year(2028)),
                new Date(new Day(DayOfWeek.TUE, 29, MonthOfYear.FEBRUARY, new Year(2028)),
                        MonthOfYear.FEBRUARY, new Year(2028)));

        assertEquals("'2103 Project' commitment from Fri, 6 September 2019 to Sat, 9 November 2019",
                multiCommitment.toString());
        assertEquals("'Volunteer' commitment on Tue, 29 February 2028", oneCommitment.toString());
    }
}
