package seedu.address.storage.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReferenceId;
import seedu.address.model.events.Event;
import seedu.address.model.events.parameters.DateTime;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String id;
    private final String startTime;
    private final String endTime;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("id") String id, @JsonProperty("start") String start,
                            @JsonProperty("end") String end, @JsonProperty("status") String status) {
        this.id = id;
        this.startTime = start;
        this.endTime = end;
        this.status = status;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {

        id = source.getPersonId().toString();
        startTime = source.getEventTiming().getStartTime().toString();
        endTime = source.getEventTiming().getEndTime().toString();
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType(boolean isStaff) throws IllegalValueException {

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ReferenceId.class.getSimpleName()));
        }
        final ReferenceId modelReferenceId = isStaff
                ? ParserUtil.lookupStaffReferenceId(id, "Id should be registered as a Staff")
                : ParserUtil.lookupPatientReferenceId(id, "Id should be registered as a Patient");

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Start Date"));
        }
        final DateTime startDateTime = DateTime.tryParseSimpleDateFormat(startTime);
        if (startDateTime == null) {
            throw new ParseException("The start " + DateTime.MESSAGE_CONSTRAINTS);
        }

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "End Date"));
        }
        final DateTime endDateTime = DateTime.tryParseSimpleDateFormat(endTime);
        if (endDateTime == null) {
            throw new ParseException("The end " + DateTime.MESSAGE_CONSTRAINTS);
        }

        if (!Timing.isValidTiming(startDateTime, endDateTime)) {
            throw new IllegalValueException(Timing.MESSAGE_CONSTRAINTS);
        }
        final Timing eventTiming = new Timing(startDateTime, endDateTime);


        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Status"));
        }
        if (!Status.isValidStatus(status)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }

        final Status eventStatus = new Status(status);
        return new Event(modelReferenceId, eventTiming, eventStatus);
    }
}
