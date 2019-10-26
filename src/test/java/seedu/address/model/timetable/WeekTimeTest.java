package seedu.address.model.timetable;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeekTimeTest {
    @Test
    public void minus_otherDuration_givesCorrectResult() {
        WeekTime w1 = new WeekTime(DayOfWeek.MONDAY, LocalTime.parse("12:30"));
        WeekTime w2 = new WeekTime(DayOfWeek.TUESDAY, LocalTime.parse("14:56"));
        assertEquals(w2.minus(w1), new Duration(1, 2, 26));
    }
}
