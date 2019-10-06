package seedu.address.testutil;

import java.util.Calendar;

import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.TypicalOrders.ORDERONE;
import seedu.address.testutil.TypicalOrders.ORDERTWO;

public class TypicalSchedules {

    //might move into commandtestutil later on
    private static final Calendar DEFAULT_CALENDAR_1 = new Calendar.Builder()
            .setDate(2019, 12, 17).setTimeOfDay(13, 0, 0).build();
    private static final Calendar DEFAULT_CALENDAR_2 = new Calendar.Builder()
            .setDate(2020, 7, 1).setTimeOfDay(19, 30, 0).build();
    private static final String DEFAULT_VENUE_1 = "Orchard MRT";
    private static final String DEFAULT_VENUE_2 = "Buona Vista KOI";
    private static final String DEFAULT_TAG_1 = "Bring carrier";
    private static final String DEFAULT_TAG_2 = "Bring freebie";

    public static final Schedule SCHEDULEONE = new ScheduleBuilder().withOrder(ORDERONE)
            .withCalendar(DEFAULT_CALENDAR_1).withVenue(DEFAULT_VENUE_1).withTags(DEFAULT_TAG_1).build();

    public static final Schedule SCHEDULETWO = new ScheduleBuilder().withOrder(ORDERTWO)
            .withCalendar(DEFAULT_CALENDAR_2).withVenue(DEFAULT_VENUE_2).withTags(DEFAULT_TAG_2).build();
}
