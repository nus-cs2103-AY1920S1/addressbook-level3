package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import seedu.address.model.DataBook;
import seedu.address.model.schedule.Schedule;

/**
 * A utility class containing a list of {@code Schedule} objects to be used in tests.
 */
public class TypicalSchedules {

    private static final Calendar DEFAULT_CALENDAR_1 = new Calendar.Builder()
            .setDate(2019, 11, 17).setTimeOfDay(13, 0, 0).build();
    private static final Calendar DEFAULT_CALENDAR_2 = new Calendar.Builder()
            .setDate(2020, 6, 1).setTimeOfDay(19, 30, 0).build();
    private static final Calendar CBD_CALENDAR = new Calendar.Builder()
            .setDate(2020, 11, 11).setTimeOfDay(11, 11, 0).build();

    private static final String DEFAULT_VENUE_1 = "Orchard MRT";
    private static final String DEFAULT_VENUE_2 = "Buona Vista KOI";
    private static final String CBD_VENUE = "Tanjong Pagar MRT";

    private static final String DEFAULT_TAG_1 = "Carrier";
    private static final String DEFAULT_TAG_2 = "Freebie";

    public static final Schedule MONDAY_SCHEDULE = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(DEFAULT_CALENDAR_1).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule FRIDAY_SCHEDULE = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(DEFAULT_CALENDAR_2).withVenue(DEFAULT_VENUE_2).withTags(DEFAULT_TAG_2).build();

    public static final Schedule CBD_SCHEDULE = new ScheduleBuilder()
            .withCalendar(CBD_CALENDAR).withVenue(CBD_VENUE).withTags(DEFAULT_TAG_1).build();

    private static final Calendar CBD_CALENDAR_1 = new Calendar.Builder()
            .setDate(2019, 01, 11).setTimeOfDay(11, 11, 0).build();
    private static final Calendar CBD_CALENDAR_2 = new Calendar.Builder()
            .setDate(2019, 02, 11).setTimeOfDay(11, 11, 0).build();
    private static final Calendar CBD_CALENDAR_3 = new Calendar.Builder()
            .setDate(2019, 04, 11).setTimeOfDay(11, 11, 0).build();
    private static final Calendar CBD_CALENDAR_4 = new Calendar.Builder()
            .setDate(2019, 05, 11).setTimeOfDay(11, 11, 0).build();
    private static final Calendar CBD_CALENDAR_5 = new Calendar.Builder()
            .setDate(2019, 05, 11).setTimeOfDay(11, 11, 0).build();
    private static final Calendar CBD_CALENDAR_6 = new Calendar.Builder()
            .setDate(2019, 05, 11).setTimeOfDay(11, 11, 0).build();
    private static final Calendar CBD_CALENDAR_7 = new Calendar.Builder()
            .setDate(2019, 10, 11).setTimeOfDay(11, 11, 0).build();

    public static final Schedule SCHEDULESTATONE = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(CBD_CALENDAR_1).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULESTATTWO = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(CBD_CALENDAR_2).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULESTATTHREE = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(CBD_CALENDAR_3).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULESTATFOUR = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(CBD_CALENDAR_4).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULESTATFIVE = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(CBD_CALENDAR_5).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULESTATSIX = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(CBD_CALENDAR_6).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULESTATSEVEN = new ScheduleBuilder().withId(UUID.randomUUID())
            .withCalendar(CBD_CALENDAR_7).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    /**
     * Returns a {@code DataBook} with all the typical schedules.
     */
    public static DataBook<Schedule> getTypicalScheduleBook() {
        DataBook<Schedule> sb = new DataBook<>();
        for (Schedule s: getTypicalSchedules()) {
            sb.add(s);
        }
        return sb;
    }

    public static List<Schedule> getTypicalSchedules() {
        return new ArrayList<>(Arrays.asList(MONDAY_SCHEDULE, FRIDAY_SCHEDULE, CBD_SCHEDULE));
    }
}
