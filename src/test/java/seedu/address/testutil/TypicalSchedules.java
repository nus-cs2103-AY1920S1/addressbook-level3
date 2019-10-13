package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import seedu.address.model.ScheduleBook;
import seedu.address.model.schedule.Schedule;

/**
 * A utility class containing a list of {@code Schedule} objects to be used in tests.
 */
public class TypicalSchedules {

    private static final Calendar DEFAULT_CALENDAR_1 = new Calendar.Builder()
            .setDate(2019, 12, 17).setTimeOfDay(13, 0, 0).build();
    private static final Calendar DEFAULT_CALENDAR_2 = new Calendar.Builder()
            .setDate(2020, 7, 1).setTimeOfDay(19, 30, 0).build();
    private static final Calendar CBD_CALENDAR = new Calendar.Builder()
            .setDate(2020, 11, 11).setTimeOfDay(11, 11, 0).build();

    private static final String DEFAULT_VENUE_1 = "Orchard MRT";
    private static final String DEFAULT_VENUE_2 = "Buona Vista KOI";
    private static final String CBD_VENUE = "Tanjong Pagar MRT";

    private static final String DEFAULT_TAG_1 = "Carrier";
    private static final String DEFAULT_TAG_2 = "Freebie";

    public static final Schedule SCHEDULEONE = new ScheduleBuilder()
            .withCalendar(DEFAULT_CALENDAR_1).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULETWO = new ScheduleBuilder()
            .withCalendar(DEFAULT_CALENDAR_2).withVenue(DEFAULT_VENUE_2).withTags(DEFAULT_TAG_2).build();

    public static final Schedule CBD_SCHEDULE = new ScheduleBuilder()
            .withCalendar(CBD_CALENDAR).withVenue(CBD_VENUE).withTags(DEFAULT_TAG_1).build();

    /**
     * Returns an {@code Book} with all the typical schedules.
     */
    public static ScheduleBook getTypicalScheduleBook() {
        ScheduleBook sb = new ScheduleBook();
        for (Schedule s: getTypicalSchedules()) {
            sb.addSchedule(s);
        }
        return sb;
    }

    public static List<Schedule> getTypicalSchedules() {
        return new ArrayList<>(Arrays.asList(SCHEDULEONE, SCHEDULETWO, CBD_SCHEDULE));
    }
}
