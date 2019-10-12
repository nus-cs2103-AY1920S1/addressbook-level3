package seedu.address.testutil;

import java.util.Calendar;

import seedu.address.model.schedule.Schedule;

/**
 * A utility class containing a list of {@code Schedule} objects to be used in tests.
 */
public class TypicalSchedules {

    private static final Calendar DEFAULT_CALENDAR_1 = new Calendar.Builder()
            .setDate(2019, 12, 17).setTimeOfDay(13, 0, 0).build();
    private static final Calendar DEFAULT_CALENDAR_2 = new Calendar.Builder()
            .setDate(2020, 7, 1).setTimeOfDay(19, 30, 0).build();
    private static final String DEFAULT_VENUE_1 = "Orchard MRT";
    private static final String DEFAULT_VENUE_2 = "Buona Vista KOI";
    private static final String DEFAULT_TAG_1 = "Carrier";
    private static final String DEFAULT_TAG_2 = "Freebie";

    public static final Schedule SCHEDULEONE = new ScheduleBuilder()
            .withCalendar(DEFAULT_CALENDAR_1).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULETWO = new ScheduleBuilder()
            .withCalendar(DEFAULT_CALENDAR_2).withVenue(DEFAULT_VENUE_2).withTags(DEFAULT_TAG_2).build();
}
