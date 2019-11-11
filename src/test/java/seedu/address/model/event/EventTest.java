package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.event.TypicalEvents.EVENT1;
import static seedu.address.testutil.event.TypicalEvents.EVENT2;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.event.EventBuilder;

public class EventTest {

    private static final String EMPTY_EVENT_NAME = "";

    private static final LocalDateTime INVALID_START_DATE_TIME = LocalDateTime.parse("2019-01-01T04:00");
    private static final LocalDateTime INVALID_END_DATE_TIME = LocalDateTime.parse("2019-01-01T03:00");

    private static final String INVALID_COLOR_CATEGORY = "group99";

    private static final String VALID_EVENT_NAME = "my event";
    private static final LocalDateTime VALID_START_DATE_TIME = LocalDateTime.parse("2019-01-01T03:00");
    private static final LocalDateTime VALID_END_DATE_TIME = LocalDateTime.parse("2019-01-01T04:00");
    private static final String VALID_COLOR_CATEGORY = "group01";
    private static final String VALID_UNIQUE_ID = "validUniqueId";

    @Test
    public void constructor_nullEventName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, VALID_COLOR_CATEGORY, VALID_UNIQUE_ID, RecurrenceType.NONE));
    }

    @Test
    public void constructor_nullStartDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(VALID_EVENT_NAME, null,
                VALID_END_DATE_TIME, VALID_COLOR_CATEGORY, VALID_UNIQUE_ID, RecurrenceType.NONE));
    }

    @Test
    public void constructor_nullEndDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(VALID_EVENT_NAME, VALID_START_DATE_TIME,
                null, VALID_COLOR_CATEGORY, VALID_UNIQUE_ID, RecurrenceType.NONE));
    }

    @Test
    public void constructor_nullColorCategory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(VALID_EVENT_NAME, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, null, VALID_UNIQUE_ID, RecurrenceType.NONE));
    }

    @Test
    public void constructor_nullUniqueIdentifier_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(VALID_EVENT_NAME, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, VALID_COLOR_CATEGORY, null, RecurrenceType.NONE));
    }

    @Test
    public void constructor_nullRecurrenceType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(VALID_EVENT_NAME, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, VALID_COLOR_CATEGORY, VALID_UNIQUE_ID, null));
    }

    @Test
    public void constructor_emptyEventName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event(EMPTY_EVENT_NAME, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, VALID_COLOR_CATEGORY, VALID_UNIQUE_ID, RecurrenceType.NONE));
    }

    @Test
    public void constructor_invalidDateTimeRange_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event(VALID_EVENT_NAME, INVALID_START_DATE_TIME,
                INVALID_END_DATE_TIME, VALID_COLOR_CATEGORY, VALID_UNIQUE_ID, RecurrenceType.NONE));
    }

    @Test
    public void constructor_invalidColorCategory_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Event(VALID_EVENT_NAME, VALID_START_DATE_TIME,
                VALID_END_DATE_TIME, INVALID_COLOR_CATEGORY, VALID_UNIQUE_ID, RecurrenceType.NONE));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event event1Copy = new EventBuilder(EVENT1).build();
        assertTrue(event1Copy.equals(EVENT1));

        // same object -> returns true
        assertTrue(EVENT1.equals(EVENT1));

        // null -> returns false
        assertFalse(EVENT1.equals(null));

        // different type -> returns false
        assertFalse(EVENT1.equals(5));

        // different event -> returns false
        assertFalse(EVENT1.equals(EVENT2));

        // different event name -> returns false
        Event editedEvent1 = new EventBuilder(EVENT1).withEventName(VALID_EVENT_NAME).build();
        assertFalse(editedEvent1.equals(EVENT1));

        // different start date time -> returns false
        editedEvent1 = new EventBuilder(EVENT1).withStartDateTime(VALID_START_DATE_TIME).build();
        assertFalse(editedEvent1.equals(EVENT1));

        // different end date time -> returns false
        editedEvent1 = new EventBuilder(EVENT1).withEndDateTime(LocalDateTime.now()).build();
        assertFalse(editedEvent1.equals(EVENT1));

        // different color category -> returns false
        editedEvent1 = new EventBuilder(EVENT1).withColorCategory(EVENT2.getColorCategory()).build();
        assertFalse(editedEvent1.equals(EVENT1));

        // different unique identifier -> returns false
        editedEvent1 = new EventBuilder(EVENT1).withUniqueIdentifier(EVENT2.getUniqueIdentifier()).build();
        assertFalse(editedEvent1.equals(EVENT1));
    }
}
