package seedu.address.model.timetable;

import org.junit.jupiter.api.Test;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateSlotTest {
    @Test
    public void generate_validTimeRanges_generatesCorrectTimeslotForSingleTimetable() throws IllegalValueException {
        LocalTime start = LocalTime.parse("11:00");
        LocalTime end = LocalTime.parse("09:00");
        TimeTable timeTable = ParserUtil.parseTimeTable("MONDAY MONDAY 1300 1500\nMONDAY MONDAY 1400 1600\n"
                + "MONDAY MONDAY 1700 1900\nTUESDAY TUESDAY 0900 1200\nWEDNESDAY WEDNESDAY 1000 1500");
        List<TimeTable> timeTables = new ArrayList<>();
        timeTables.add(timeTable);
        List<TimeRange> generated = GenerateSlot.generate(timeTables, 2,
                new TimeRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, start, end));
        List<TimeRange> expected = new ArrayList<>();
        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.MONDAY, start, LocalTime.parse("13:00")));
        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, LocalTime.parse("19:00"), LocalTime.parse("09:00")));
        expected.add(new TimeRange(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.parse("12:00"), LocalTime.parse("10:00")));
        expected.add(new TimeRange(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, LocalTime.parse("15:00"), LocalTime.parse("09:00")));
        assertEquals(generated, expected);
    }

    @Test
    public void generate_validTimeRanges_generatesCorrectTimeslotForMultipleTimetables() throws IllegalValueException {
        LocalTime start = LocalTime.parse("11:00");
        LocalTime end = LocalTime.parse("09:00");
        TimeTable t1 = ParserUtil.parseTimeTable("MONDAY MONDAY 1400 1600\nTUESDAY TUESDAY 0900 1200");
        TimeTable t2 = ParserUtil.parseTimeTable("MONDAY MONDAY 1300 1500\nMONDAY MONDAY 1700 1900");
        TimeTable t3 = ParserUtil.parseTimeTable("WEDNESDAY WEDNESDAY 1000 1500");
        List<TimeTable> timeTables = new ArrayList<>();
        timeTables.add(t1);
        timeTables.add(t2);
        timeTables.add(t3);
        List<TimeRange> generated = GenerateSlot.generate(timeTables, 2,
                new TimeRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, start, end));
        List<TimeRange> expected = new ArrayList<>();
        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.MONDAY, start, LocalTime.parse("13:00")));
        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, LocalTime.parse("19:00"), LocalTime.parse("09:00")));
        expected.add(new TimeRange(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.parse("12:00"), LocalTime.parse("10:00")));
        expected.add(new TimeRange(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, LocalTime.parse("15:00"), LocalTime.parse("09:00")));
        assertEquals(generated, expected);
    }

    @Test
    public void generate_validTimeRanges_generatesCorrectTimeslotForMultipleTimetablesWithMinutes() throws IllegalValueException {
        LocalTime start = LocalTime.parse("11:35");
        LocalTime end = LocalTime.parse("09:31");
        TimeTable t1 = ParserUtil.parseTimeTable("MONDAY MONDAY 1412 1634\nTUESDAY TUESDAY 0923 1255");
        TimeTable t2 = ParserUtil.parseTimeTable("MONDAY MONDAY 1446 1519\nMONDAY MONDAY 1754 1923");
        TimeTable t3 = ParserUtil.parseTimeTable("WEDNESDAY WEDNESDAY 1024 1544");
        List<TimeTable> timeTables = new ArrayList<>();
        timeTables.add(t1);
        timeTables.add(t2);
        timeTables.add(t3);
        List<TimeRange> generated = GenerateSlot.generate(timeTables, 1,
                new TimeRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, start, end));
        List<TimeRange> expected = new ArrayList<>();
        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.MONDAY, start, LocalTime.parse("14:12")));
        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.MONDAY, LocalTime.parse("16:34"), LocalTime.parse("17:54")));
        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, LocalTime.parse("19:23"), LocalTime.parse("09:23")));
        expected.add(new TimeRange(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.parse("12:55"), LocalTime.parse("10:24")));
        expected.add(new TimeRange(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, LocalTime.parse("15:44"), LocalTime.parse("09:31")));
        assertEquals(generated, expected);
    }

    @Test
    public void generate_timeRangesWithNoPossibleTimeslots_generatesEmptyTimeRangeList() throws IllegalValueException {
        LocalTime start = LocalTime.parse("11:35");
        LocalTime end = LocalTime.parse("09:31");
        TimeTable t1 = ParserUtil.parseTimeTable("MONDAY MONDAY 1412 1634\nTUESDAY TUESDAY 0923 1255\nTHURSDAY SATURDAY 1544 1924");
        TimeTable t2 = ParserUtil.parseTimeTable("MONDAY MONDAY 0800 1519\nMONDAY TUESDAY 1600 1100");
        TimeTable t3 = ParserUtil.parseTimeTable("TUESDAY THURSDAY 1024 1544");
        List<TimeTable> timeTables = new ArrayList<>();
        timeTables.add(t1);
        timeTables.add(t2);
        timeTables.add(t3);
        List<TimeRange> generated = GenerateSlot.generate(timeTables, 1,
                new TimeRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, start, end));
        List<TimeRange> expected = new ArrayList<>();
        assertEquals(generated, expected);
    }
}
