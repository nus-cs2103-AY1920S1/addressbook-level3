package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalSchedules.MONDAY_SCHEDULE;

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
        assertTrue(MONDAY_SCHEDULE.isSameAs(MONDAY_SCHEDULE));

        // null -> returns false
        assertFalse(MONDAY_SCHEDULE.isSameAs(null));

        // different id -> returns false
        assertFalse(MONDAY_SCHEDULE.isSameAs(new ScheduleBuilder(MONDAY_SCHEDULE).withId(VALID_ID).build()));

        // different calendar -> returns true
        assertTrue(MONDAY_SCHEDULE.isSameAs(new ScheduleBuilder(MONDAY_SCHEDULE).withCalendar(VALID_CALENDAR).build()));

        // different venue -> returns true
        assertTrue(MONDAY_SCHEDULE.isSameAs(new ScheduleBuilder(MONDAY_SCHEDULE).withVenue(VALID_VENUE).build()));

        // different tags -> returns true
        assertTrue(MONDAY_SCHEDULE.isSameAs(new ScheduleBuilder(MONDAY_SCHEDULE).withTags(VALID_TAG).build()));
    }

    @Test
    void testEquals() {
        // same object -> equals
        assertEquals(MONDAY_SCHEDULE, MONDAY_SCHEDULE);

        // null -> not equals
        assertNotEquals(null, MONDAY_SCHEDULE);

        // different id -> not equals
        assertNotEquals(MONDAY_SCHEDULE, new ScheduleBuilder(MONDAY_SCHEDULE).withId(VALID_ID).build());

        // different calendar -> not equals
        assertNotEquals(MONDAY_SCHEDULE, new ScheduleBuilder(MONDAY_SCHEDULE).withCalendar(VALID_CALENDAR).build());

        // different venue -> not equals
        assertNotEquals(MONDAY_SCHEDULE, new ScheduleBuilder(MONDAY_SCHEDULE).withVenue(VALID_VENUE).build());

        // different tags -> not equals
        assertNotEquals(MONDAY_SCHEDULE, new ScheduleBuilder(MONDAY_SCHEDULE).withTags(VALID_TAG).build());
    }
}
