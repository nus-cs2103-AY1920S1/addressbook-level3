package seedu.address.storage.event;

import static seedu.address.commons.util.EventUtil.validateStartEndDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.RecurrenceType;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "event %s field is missing!";
    public static final String INVALID_DATE_FORMAT = "Invalid date format";
    public static final String INVALID_DATE_RANGE = "Invalid date range. StartDateTime must be "
            + "before end date time";
    public static final String INVALID_RECURRENCE_TYPE = "Invalid recurrence type";

    private String eventName;
    private String startDateTime;
    private String endDateTime;
    private String colorCategory;
    private String uniqueIdentifier;
    private String recurrenceType;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given question details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("startDateTime") String startDateTime,
                            @JsonProperty("endDateTime") String endDateTime,
                            @JsonProperty("recurrenceType") String recurrenceType,
                            @JsonProperty("colorCategory") String colorCategory,
                            @JsonProperty("uniqueIdentifier") String uniqueIdentifier,
                            @JsonProperty("eventName") String eventName) {
        this.eventName = eventName;
        this.recurrenceType = recurrenceType;
        this.colorCategory = colorCategory;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.uniqueIdentifier = uniqueIdentifier;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        this.eventName = source.getEventName();
        this.recurrenceType = source.getRecurrenceType().name();
        this.colorCategory = source.getColorCategory();
        this.startDateTime = source.getStartDateTime().toString();
        this.endDateTime = source.getEndDateTime().toString();
        this.uniqueIdentifier = source.getUniqueIdentifier();
    }

    /**
     * Converts this Jackson-friendly adapted question object into the model's {@code Event}
     * object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               question.
     */
    public Event toModelType() throws IllegalValueException {
        if (eventName == null || eventName.isEmpty()) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "EVENT NAME"));
        }
        if (startDateTime == null || startDateTime.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "START DATE TIME"));
        }
        if (endDateTime == null || endDateTime.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "END DATE TIME"));
        }
        if (uniqueIdentifier == null || uniqueIdentifier.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "UNIQUE IDENTIFIER"));
        }

        if (colorCategory == null || colorCategory.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "COLOR CATEGORY"));
        }

        RecurrenceType recurrenceTypeToAdd;
        if (recurrenceType == null || recurrenceType.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "RECURRENCE TYPE"));
        } else if (recurrenceType.equalsIgnoreCase(RecurrenceType.WEEKLY.name())) {
            recurrenceTypeToAdd = RecurrenceType.WEEKLY;
        } else if (recurrenceType.equalsIgnoreCase(RecurrenceType.DAILY.name())) {
            recurrenceTypeToAdd = RecurrenceType.DAILY;
        } else if (recurrenceType.equalsIgnoreCase(RecurrenceType.NONE.name())) {
            recurrenceTypeToAdd = RecurrenceType.NONE;
        } else {
            throw new IllegalValueException(INVALID_RECURRENCE_TYPE);
        }

        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            startDateTime = LocalDateTime.parse(this.startDateTime);
            endDateTime = LocalDateTime.parse(this.endDateTime);
        } catch (DateTimeParseException dtpEx) {
            throw new IllegalValueException(INVALID_DATE_FORMAT);
        }
        if (!validateStartEndDateTime(startDateTime, endDateTime)) {
            throw new IllegalValueException(INVALID_DATE_RANGE);
        }

        return new Event(eventName, startDateTime, endDateTime, colorCategory,
                uniqueIdentifier, recurrenceTypeToAdd);
    }
}
