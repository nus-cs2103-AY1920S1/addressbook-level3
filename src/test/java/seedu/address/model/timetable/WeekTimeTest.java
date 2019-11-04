package seedu.address.model.timetable;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeekTimeTest {
    @Test
    public void minus_otherDuration_givesCorrectResult() {
        WeekTime w1 = new WeekTime(DayOfWeek.MONDAY, LocalTime.parse("12:30"));
        WeekTime w2 = new WeekTime(DayOfWeek.TUESDAY, LocalTime.parse("14:56"));
        WeekTime w3 = new WeekTime(DayOfWeek.WEDNESDAY, LocalTime.parse("09:31"));
        assertEquals(w2.minus(w1), new Duration(1, 2, 26));
        assertEquals(w3.minus(w1), new Duration(1, 21, 1));
        assertEquals(w3.minus(w2), new Duration(0, 18, 35));
        assertEquals(w2.minus(w3), new Duration(0, -18, -35));
    }

    @Test
    public void isAfter_otherDuration_givesCorrectResult() {
        WeekTime w1 = new WeekTime(DayOfWeek.MONDAY, LocalTime.parse("12:30"));
        WeekTime w2 = new WeekTime(DayOfWeek.TUESDAY, LocalTime.parse("14:56"));
        WeekTime w3 = new WeekTime(DayOfWeek.TUESDAY, LocalTime.parse("14:31"));
        assertTrue(w2.isAfter(w1));
        assertTrue(w3.isAfter(w1));
        assertTrue(w2.isAfter(w3));
    }

    @Test
    public void isBefore_otherDuration_givesCorrectResult() {
        WeekTime w1 = new WeekTime(DayOfWeek.MONDAY, LocalTime.parse("12:30"));
        WeekTime w2 = new WeekTime(DayOfWeek.TUESDAY, LocalTime.parse("14:56"));
        WeekTime w3 = new WeekTime(DayOfWeek.TUESDAY, LocalTime.parse("14:31"));
        assertTrue(w1.isBefore(w2));
        assertTrue(w1.isBefore(w3));
        assertTrue(w3.isBefore(w2));
    }
}
