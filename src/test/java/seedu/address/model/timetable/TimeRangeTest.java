package seedu.address.model.timetable;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.testutil.Assert.assertThrows;

public class TimeRangeTest {

    @Test
    public void constructor_startBeforeEnd_successful() {
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("11:00");
    }

    @Test
    public void constructor_endBeforeStart_throwsIllegalValueException() {
        LocalTime start = LocalTime.parse("11:00");
        LocalTime end = LocalTime.parse("09:00");
        assertThrows(IllegalValueException.class, () -> new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end));
    }

    @Test
    public void constructor_startEqualEnd_throwsIllegalValueException() {
        LocalTime start = LocalTime.parse("11:00");
        LocalTime end = LocalTime.parse("11:00");
        assertThrows(IllegalValueException.class, () -> new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end));
    }

    @Test
    public void overlap_differentDay_returnsFalse() throws IllegalValueException {
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("11:00");
        TimeRange tr1 = new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end);
        TimeRange tr2 = new TimeRange(DayOfWeek.SATURDAY, start, DayOfWeek.SATURDAY, end);
        assertFalse(tr1.overlap(tr2));
    }

    @Test
    public void overlap_sameDayOverlap_returnsTrue() throws IllegalValueException {
        LocalTime start = LocalTime.parse("09:00");
        LocalTime end = LocalTime.parse("11:00");
        TimeRange tr1 = new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end);
        start = LocalTime.parse("10:00");
        end = LocalTime.parse("12:00");
        TimeRange tr2 = new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end);
        assertTrue(tr1.overlap(tr2));
        start = LocalTime.parse("08:00");
        end = LocalTime.parse("10:00");
        TimeRange tr3 = new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end);
        assertTrue(tr3.overlap(tr1));
    }

    @Test
    public void overlap_sameDayNoOverlap_returnsFalse() throws IllegalValueException {
        LocalTime start = LocalTime.parse("10:00");
        LocalTime end = LocalTime.parse("12:00");
        TimeRange tr1 = new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end);
        start = LocalTime.parse("12:00");
        end = LocalTime.parse("14:00");
        TimeRange tr2 = new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end);
        assertFalse(tr1.overlap(tr2));
        start = LocalTime.parse("08:00");
        end = LocalTime.parse("10:00");
        TimeRange tr3 = new TimeRange(DayOfWeek.FRIDAY, start, DayOfWeek.FRIDAY, end);
        assertFalse(tr1.overlap(tr3));
        assertFalse(tr2.overlap(tr3));
        assertFalse(tr3.overlap(tr2));
    }

    @Test
    public void equals_sameContentDifferentObject_equal() throws IllegalValueException {
        assertEquals(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("10:00"), DayOfWeek.MONDAY, LocalTime.parse("12:00")),
                new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("10:00"), DayOfWeek.MONDAY, LocalTime.parse("12:00")));
    }

    @Test
    public void equals_differentContentDifferentObject_notEqual() throws IllegalValueException {
        assertNotEquals(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("10:00"), DayOfWeek.MONDAY, LocalTime.parse("13:00")),
                new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("10:00"), DayOfWeek.MONDAY, LocalTime.parse("12:00")));
        assertNotEquals(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("10:00"), DayOfWeek.MONDAY, LocalTime.parse("12:00")),
                new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("11:00"), DayOfWeek.MONDAY, LocalTime.parse("12:00")));
        assertNotEquals(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("10:00"), DayOfWeek.TUESDAY, LocalTime.parse("12:00")),
                new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("10:00"), DayOfWeek.MONDAY, LocalTime.parse("12:00")));
        assertNotEquals(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("10:00"), DayOfWeek.WEDNESDAY, LocalTime.parse("12:00")),
                new TimeRange(DayOfWeek.TUESDAY, LocalTime.parse("10:00"), DayOfWeek.WEDNESDAY, LocalTime.parse("12:00")));
    }

}
