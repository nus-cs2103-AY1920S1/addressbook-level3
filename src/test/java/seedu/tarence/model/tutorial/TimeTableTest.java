package seedu.tarence.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.tarence.testutil.TimeTableBuilder;

public class TimeTableTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TimeTable(null, null, null, null));
    }

    @Test
    public void equals() {
        // same day, startTime, weeks, duration
        TimeTable timetable = new TimeTableBuilder().build();
        TimeTable timetableCopy = new TimeTableBuilder(timetable).build();
        assertTrue(timetable.equals(timetableCopy));

        // different day
        TimeTable timetableDiffDay = new TimeTableBuilder(timetable).withDayOfWeek("FRIDAY").build();
        assertFalse(timetable.equals(timetableDiffDay));

        // different start time
        TimeTable timetableDiffStartTime = new TimeTableBuilder(timetable).withStartTime("00:00:00").build();
        assertFalse(timetable.equals(timetableDiffStartTime));

        // different weeks
        Set<Week> weeks = new TreeSet<>(Arrays.asList(new Week(1), new Week(3), new Week(5)));
        TimeTable timetableDiffWeeks = new TimeTableBuilder(timetable).withWeeks(weeks).build();
        assertFalse(timetable.equals(timetableDiffWeeks));

        // different duration
        TimeTable timetableDiffDuration = new TimeTableBuilder(timetable).withDuration(0).build();
        assertFalse(timetable.equals(timetableDiffDuration));
    }
}
