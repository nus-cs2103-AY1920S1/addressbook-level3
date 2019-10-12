package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalSchedules.SCHEDULEONE;

import java.util.Calendar;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ScheduleBuilder;

class ScheduleTest {

    private static final UUID VALID_ID = UUID.randomUUID();
    private static final String VALID_VENUE = "Changi Airport T3";
    private static final String VALID_TAG = "Freebie";
    private static final Calendar VALID_CALENDAR = new Calendar.Builder()
            .setDate(2030, 3, 19).setTimeOfDay(15, 30, 0).build();

    @Test
    void isSameSchedule() {
        // same object -> returns true
        assertTrue(SCHEDULEONE.isSameSchedule(SCHEDULEONE));

        // null -> returns false
        assertFalse(SCHEDULEONE.isSameSchedule(null));

        // different id -> returns false
        assertFalse(SCHEDULEONE.isSameSchedule(new ScheduleBuilder(SCHEDULEONE).withId(VALID_ID).build()));

        // different calendar -> returns true
        assertTrue(SCHEDULEONE.isSameSchedule(new ScheduleBuilder(SCHEDULEONE).withCalendar(VALID_CALENDAR).build()));

        // different venue -> returns true
        assertTrue(SCHEDULEONE.isSameSchedule(new ScheduleBuilder(SCHEDULEONE).withVenue(VALID_VENUE).build()));

        // different tags -> returns true
        assertTrue(SCHEDULEONE.isSameSchedule(new ScheduleBuilder(SCHEDULEONE).withTags(VALID_TAG).build()));
    }

    @Test
    void testEquals() {
        // same object -> equals
        assertEquals(SCHEDULEONE, SCHEDULEONE);

        // null -> not equals
        assertNotEquals(null, SCHEDULEONE);

        // different id -> not equals
        assertNotEquals(SCHEDULEONE, new ScheduleBuilder(SCHEDULEONE).withId(VALID_ID).build());

        // different calendar -> not equals
        assertNotEquals(SCHEDULEONE, new ScheduleBuilder(SCHEDULEONE).withCalendar(VALID_CALENDAR).build());

        // different venue -> not equals
        assertNotEquals(SCHEDULEONE, new ScheduleBuilder(SCHEDULEONE).withVenue(VALID_VENUE).build());

        // different tags -> not equals
        assertNotEquals(SCHEDULEONE, new ScheduleBuilder(SCHEDULEONE).withTags(VALID_TAG).build());
    }
}
