package seedu.address.model.timetable;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimeSlotGeneratorTest {
    @Test
    public void generate_validTimeRanges_generatesCorrectTimeslotForSingleTimetable() throws IllegalValueException {
        LocalTime start = LocalTime.parse("11:00");
        LocalTime end = LocalTime.parse("09:00");
        Timetable timetable = ParserUtil.parseTimetable("MONDAY 1300 MONDAY 1500\nMONDAY 1400 MONDAY 1600\n"
                + "MONDAY 1700 MONDAY 1900\nTUESDAY 0900 TUESDAY 1200\nWEDNESDAY 1000 WEDNESDAY 1500");
        List<Timetable> timetables = new ArrayList<>();
        timetables.add(timetable);
        List<TimeRange> generated = new TimeSlotGenerator(timetables, 2,
                new TimeRange(DayOfWeek.MONDAY, start, DayOfWeek.FRIDAY, end)).generate();
        List<TimeRange> expected = new ArrayList<>();
        expected.add(new TimeRange(DayOfWeek.MONDAY, start, DayOfWeek.MONDAY, LocalTime.parse("13:00")));
        expected.add(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("19:00"), DayOfWeek.TUESDAY, LocalTime.parse("09:00")));
        expected.add(new TimeRange(DayOfWeek.TUESDAY, LocalTime.parse("12:00"), DayOfWeek.WEDNESDAY, LocalTime.parse("10:00")));
        expected.add(new TimeRange(DayOfWeek.WEDNESDAY, LocalTime.parse("15:00"), DayOfWeek.FRIDAY, LocalTime.parse("09:00")));
        assertEquals(generated, expected);
    }

    @Test
    public void generate_validTimeRanges_generatesCorrectTimeslotForMultipleTimetables() throws IllegalValueException {
        LocalTime start = LocalTime.parse("11:00");
        LocalTime end = LocalTime.parse("09:00");
        Timetable t1 = ParserUtil.parseTimetable("MONDAY 1400 MONDAY 1600\nTUESDAY 0900 TUESDAY 1200");
        Timetable t2 = ParserUtil.parseTimetable("MONDAY 1300 MONDAY 1500\nMONDAY 1700 MONDAY 1900");
        Timetable t3 = ParserUtil.parseTimetable("WEDNESDAY 1000 WEDNESDAY 1500");
        List<Timetable> timetables = new ArrayList<>();
        timetables.add(t1);
        timetables.add(t2);
        timetables.add(t3);
        List<TimeRange> generated = new TimeSlotGenerator(timetables, 2,
                new TimeRange(DayOfWeek.MONDAY, start, DayOfWeek.FRIDAY, end)).generate();
        List<TimeRange> expected = new ArrayList<>();
        expected.add(new TimeRange(DayOfWeek.MONDAY, start, DayOfWeek.MONDAY, LocalTime.parse("13:00")));
        expected.add(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("19:00"), DayOfWeek.TUESDAY, LocalTime.parse("09:00")));
        expected.add(new TimeRange(DayOfWeek.TUESDAY, LocalTime.parse("12:00"), DayOfWeek.WEDNESDAY, LocalTime.parse("10:00")));
        expected.add(new TimeRange(DayOfWeek.WEDNESDAY, LocalTime.parse("15:00"), DayOfWeek.FRIDAY, LocalTime.parse("09:00")));
        assertEquals(generated, expected);
    }

    @Test
    public void generate_validTimeRanges_generatesCorrectTimeslotForMultipleTimetablesWithMinutes() throws IllegalValueException {
        LocalTime start = LocalTime.parse("11:35");
        LocalTime end = LocalTime.parse("09:31");
        Timetable t1 = ParserUtil.parseTimetable("MONDAY 1412 MONDAY 1634\nTUESDAY 0923 TUESDAY 1255");
        Timetable t2 = ParserUtil.parseTimetable("MONDAY 1446 MONDAY 1519\nMONDAY 1754 MONDAY 1923");
        Timetable t3 = ParserUtil.parseTimetable("WEDNESDAY 1024 WEDNESDAY 1544");
        List<Timetable> timetables = new ArrayList<>();
        timetables.add(t1);
        timetables.add(t2);
        timetables.add(t3);
        List<TimeRange> generated = new TimeSlotGenerator(timetables, 1,
                new TimeRange(DayOfWeek.MONDAY, start, DayOfWeek.FRIDAY, end)).generate();
        List<TimeRange> expected = new ArrayList<>();
        expected.add(new TimeRange(DayOfWeek.MONDAY, start, DayOfWeek.MONDAY, LocalTime.parse("14:12")));
        expected.add(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("16:34"), DayOfWeek.MONDAY, LocalTime.parse("17:54")));
        expected.add(new TimeRange(DayOfWeek.MONDAY, LocalTime.parse("19:23"), DayOfWeek.TUESDAY, LocalTime.parse("09:23")));
        expected.add(new TimeRange(DayOfWeek.TUESDAY, LocalTime.parse("12:55"), DayOfWeek.WEDNESDAY, LocalTime.parse("10:24")));
        expected.add(new TimeRange(DayOfWeek.WEDNESDAY, LocalTime.parse("15:44"), DayOfWeek.FRIDAY, LocalTime.parse("09:31")));
        assertEquals(generated, expected);
    }

    @Test
    public void generate_timeRangesWithNoPossibleTimeslots_generatesEmptyTimeRangeList() throws IllegalValueException {
        LocalTime start = LocalTime.parse("11:35");
        LocalTime end = LocalTime.parse("09:31");
        Timetable t1 = ParserUtil.parseTimetable("MONDAY 1412 MONDAY 1634\nTUESDAY 0923 TUESDAY 1255\nTHURSDAY 1544 SATURDAY 1924");
        Timetable t2 = ParserUtil.parseTimetable("MONDAY 0800 MONDAY 1519\nMONDAY 1600 TUESDAY 1100");
        Timetable t3 = ParserUtil.parseTimetable("TUESDAY 1024 THURSDAY 1544");
        List<Timetable> timetables = new ArrayList<>();
        timetables.add(t1);
        timetables.add(t2);
        timetables.add(t3);
        List<TimeRange> generated = new TimeSlotGenerator(timetables, 1,
                new TimeRange(DayOfWeek.MONDAY, start, DayOfWeek.FRIDAY, end)).generate();
        List<TimeRange> expected = new ArrayList<>();
        assertEquals(generated, expected);
    }
}
