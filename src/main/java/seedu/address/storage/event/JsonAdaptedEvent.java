package seedu.address.storage.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jfxtras.icalendarfx.properties.component.relationship.UniqueIdentifier;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.RecurrenceType;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.OpenEndedQuestion;
import seedu.address.model.question.Question;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "event %s field is missing!";

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
        if (eventName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "EVENT NAME"));
        }
        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "START DATE TIME"));
        }
        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "END DATE TIME"));
        }
        if (uniqueIdentifier == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "UNIQUE IDENTIFIER"));
        }

        RecurrenceType recurrenceTypeToAdd = RecurrenceType.NONE;

        if (recurrenceType.equals("WEEKLY")) {
            recurrenceTypeToAdd = RecurrenceType.WEEKLY;
        } else if (recurrenceType.equals("DAILY")) {
            recurrenceTypeToAdd = RecurrenceType.DAILY;
        }

        return new Event(eventName, LocalDateTime.parse(startDateTime), LocalDateTime.parse(endDateTime), colorCategory,
                uniqueIdentifier, recurrenceTypeToAdd);
    }

}
