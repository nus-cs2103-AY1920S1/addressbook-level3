package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalOrders.ORDERTHREE;
import static seedu.address.testutil.TypicalSchedules.SCHEDULEONE;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ScheduleBuilder;

public class ScheduleTest {

    private static final String VALID_VENUE = "Changi Airport T3";
    private static final String VALID_TAG = "Freebie";
    private static final Calendar VALID_CALENDAR = new Calendar.Builder()
            .setDate(2030, 3, 19).setTimeOfDay(15, 30, 0).build();

    @Test
    public void isSameSchedule() {
        // same object -> returns true
        assertTrue(SCHEDULEONE.isSameSchedule(SCHEDULEONE));

        // null -> returns false
        assertFalse(SCHEDULEONE.isSameSchedule(null));

        // clone -> returns true
        assertTrue(SCHEDULEONE.isSameSchedule((Schedule) SCHEDULEONE.clone()));
    }

    @Test
    public void testEquals() {
        // same object -> returns true
        assertTrue(SCHEDULEONE.equals(SCHEDULEONE));

        // clone -> returns true
        assertTrue(SCHEDULEONE.equals((Schedule) SCHEDULEONE.clone()));

        // null -> returns false
        assertFalse(SCHEDULEONE.equals(null));

        // same data fields -> returns true
        assertTrue(SCHEDULEONE.equals(new ScheduleBuilder(SCHEDULEONE).build()));

        // different order -> returns false
        assertFalse(SCHEDULEONE.equals(new ScheduleBuilder(SCHEDULEONE).withOrder(ORDERTHREE).build()));

        // different calendar -> returns false
        assertFalse(SCHEDULEONE.equals(new ScheduleBuilder(SCHEDULEONE).withCalendar(VALID_CALENDAR).build()));

        // different venue -> returns false
        assertFalse(SCHEDULEONE.equals(new ScheduleBuilder(SCHEDULEONE).withVenue(VALID_VENUE).build()));

        // different tags -> returns false
        assertFalse(SCHEDULEONE.equals(new ScheduleBuilder(SCHEDULEONE).withTags(VALID_TAG).build()));
    }
}
