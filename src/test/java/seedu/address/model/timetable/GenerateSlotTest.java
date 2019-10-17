//TODO: ADD TESTS BACK ONCE WE DONT HAVE TO PARSE TIMETABLE FROM USER INPUT
//package seedu.address.model.timetable;
//
//import org.junit.jupiter.api.Test;
//import seedu.address.commons.exceptions.IllegalValueException;
//
//import java.time.DayOfWeek;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class GenerateSlotTest {
//    @Test
//    public void generate_validTimeRanges_generatesCorrectTimeslotForSingleTimetable() throws IllegalValueException {
//        LocalTime start = LocalTime.parse("11:00");
//        LocalTime end = LocalTime.parse("09:00");
//        TimeTable timeTable = new TimeTable("MONDAY MONDAY 1300 1500\nMONDAY MONDAY 1400 1600\n"
//                + "MONDAY MONDAY 1700 1900\nTUESDAY TUESDAY 0900 1200\nWEDNESDAY WEDNESDAY 1000 1500");
//        List<TimeTable> timeTables = new ArrayList<>();
//        timeTables.add(timeTable);
//        List<TimeRange> generated = GenerateSlot.generate(timeTables, 2,
//                new TimeRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, start, end));
//        List<TimeRange> expected = new ArrayList<>();
//        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.MONDAY, start, LocalTime.parse("13:00")));
//        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, LocalTime.parse("19:00"), LocalTime.parse("09:00")));
//        expected.add(new TimeRange(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.parse("12:00"), LocalTime.parse("10:00")));
//        expected.add(new TimeRange(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, LocalTime.parse("15:00"), LocalTime.parse("09:00")));
//        assertEquals(generated, expected);
//    }
//
//    @Test
//    public void generate_validTimeRanges_generatesCorrectTimeslotForMultipleTimetables() throws IllegalValueException {
//        LocalTime start = LocalTime.parse("11:00");
//        LocalTime end = LocalTime.parse("09:00");
//        TimeTable t1 = new TimeTable("MONDAY MONDAY 1400 1600\nTUESDAY TUESDAY 0900 1200");
//        TimeTable t2 = new TimeTable("MONDAY MONDAY 1300 1500\nMONDAY MONDAY 1700 1900");
//        TimeTable t3 = new TimeTable("WEDNESDAY WEDNESDAY 1000 1500");
//        List<TimeTable> timeTables = new ArrayList<>();
//        timeTables.add(t1);
//        timeTables.add(t2);
//        timeTables.add(t3);
//        List<TimeRange> generated = GenerateSlot.generate(timeTables, 2,
//                new TimeRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, start, end));
//        List<TimeRange> expected = new ArrayList<>();
//        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.MONDAY, start, LocalTime.parse("13:00")));
//        expected.add(new TimeRange(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, LocalTime.parse("19:00"), LocalTime.parse("09:00")));
//        expected.add(new TimeRange(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, LocalTime.parse("12:00"), LocalTime.parse("10:00")));
//        expected.add(new TimeRange(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, LocalTime.parse("15:00"), LocalTime.parse("09:00")));
//        assertEquals(generated, expected);
//    }
//}
