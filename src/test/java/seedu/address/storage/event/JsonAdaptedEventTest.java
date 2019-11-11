package seedu.address.storage.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.event.JsonAdaptedEvent.INVALID_DATE_FORMAT;
import static seedu.address.storage.event.JsonAdaptedEvent.INVALID_DATE_RANGE;
import static seedu.address.storage.event.JsonAdaptedEvent.INVALID_RECURRENCE_TYPE;
import static seedu.address.storage.event.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.RecurrenceType;
import seedu.address.testutil.event.EventBuilder;


public class JsonAdaptedEventTest {

    private static final String EMPTY_EVENT_NAME = "";
    private static final String INVALID_FORMAT_START_DATE_TIME = "2019-99-99T03:00";
    private static final String INVALID_FORMAT_END_DATE_TIME = "2019-01-0103:00";
    private static final String INVALID_RANGE_START_DATE_TIME = "2019-01-01T03:00";
    private static final String INVALID_RANGE_END_DATE_TIME = "2019-01-01T02:00";
    private static final String EMPTY_COLOR_CATEGORY = "";
    private static final String EMPTY_UNIQUE_IDENTIFIER = "";
    private static final String EMPTY_RECURRENCE_TYPE_STRING = "";
    private static final String INVALID_RECURRENCE_TYPE_STRING = "INVALID RECUR";
    private static final String EMPTY_START_DATE_TIME = "";
    private static final String EMPTY_END_DATE_TIME = "";


    private static final String VALID_EVENT_NAME = "event name";
    private static final String VALID_START_DATE_TIME = "2019-01-01T03:00";
    private static final String VALID_END_DATE_TIME = "2019-01-01T04:00";
    private static final String VALID_UNIQUE_IDENTIFIER = "dummyIdentifier";
    private static final String VALID_COLOR_CATEGORY = "group01";
    private static final String VALID_RECURRENCE_TYPE_STRING = "NONE";

    private static final RecurrenceType VALID_RECURRENCE_TYPE = RecurrenceType.NONE;

    private static final Event EVENT = new EventBuilder()
            .withStartDateTime(LocalDateTime.parse(VALID_START_DATE_TIME))
            .withEndDateTime(LocalDateTime.parse(VALID_END_DATE_TIME))
            .withEventName(VALID_EVENT_NAME)
            .withUniqueIdentifier(VALID_UNIQUE_IDENTIFIER)
            .withColorCategory(VALID_COLOR_CATEGORY)
            .withRecurrenceType(VALID_RECURRENCE_TYPE)
            .build();

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        assertEquals(EVENT, jsonEvent.toModelType());
    }

    @Test
    public void toModelType_emptyEventName_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, EMPTY_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EVENT NAME");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_nullEventName_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EVENT NAME");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_invalidEventRange_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(INVALID_RANGE_START_DATE_TIME, INVALID_RANGE_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = INVALID_DATE_RANGE;
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(INVALID_FORMAT_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = INVALID_DATE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_emptyStartDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(EMPTY_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "START DATE TIME");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(null, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "START DATE TIME");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, null,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "END DATE TIME");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_emptyEndDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, EMPTY_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "END DATE TIME");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, INVALID_FORMAT_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = INVALID_DATE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_emptyColorCategory_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, EMPTY_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "COLOR CATEGORY");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_nullColorCategory_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, null, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "COLOR CATEGORY");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_emptyUniqueIdentifier_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, EMPTY_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "UNIQUE IDENTIFIER");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_nullUniqueIdentifier_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                VALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, null, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "UNIQUE IDENTIFIER");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_emptyRecurrenceType_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                EMPTY_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "RECURRENCE TYPE");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_nullRecurrenceType_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                null, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "RECURRENCE TYPE");
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }

    @Test
    public void toModelType_invalidRecurrenceType_throwsIllegalValueException() {
        JsonAdaptedEvent jsonEvent = new JsonAdaptedEvent(VALID_START_DATE_TIME, VALID_END_DATE_TIME,
                INVALID_RECURRENCE_TYPE_STRING, VALID_COLOR_CATEGORY, VALID_UNIQUE_IDENTIFIER, VALID_EVENT_NAME);
        String expectedMessage = INVALID_RECURRENCE_TYPE;
        assertThrows(IllegalValueException.class, expectedMessage, jsonEvent::toModelType);
    }
}
