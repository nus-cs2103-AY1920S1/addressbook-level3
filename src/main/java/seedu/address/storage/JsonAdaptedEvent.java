package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Tag;
import seedu.address.model.events.DateTime;
import seedu.address.model.events.Event;
import seedu.address.model.events.Status;
import seedu.address.model.events.Timing;
import seedu.address.model.person.Person;
import seedu.address.model.person.parameters.Address;
import seedu.address.model.person.parameters.Email;
import seedu.address.model.person.parameters.Name;
import seedu.address.model.person.parameters.PatientReferenceId;
import seedu.address.model.person.parameters.PersonReferenceId;
import seedu.address.model.person.parameters.Phone;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String patientId;
    private final String startTime;
    private final String endTime;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("patientId") String patientId, @JsonProperty("start") String start,
                            @JsonProperty("end") String end, @JsonProperty("status") String status) {
        this.patientId = patientId;
        this.startTime = start;
        this.endTime = end;
        this.status = status;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {

        patientId = source.getPersonId().getReferenceIdentifier();
        startTime = source.getEventTiming().getStartTime().toString();
        endTime = source.getEventTiming().getEndTime().toString();
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {

        if (patientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PatientReferenceId.class.getSimpleName()));
        }
        if (!PersonReferenceId.isValidId(patientId)) {
            throw new ParseException(PersonReferenceId.MESSAGE_CONSTRAINTS);
        } else if (!PatientReferenceId.isValidPatientId(patientId)) {
            throw new ParseException(PatientReferenceId.MESSAGE_CONSTRAINTS);
        }
        final PatientReferenceId patientReferenceId = new PatientReferenceId(patientId);

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

        //TODO: Implement Status
//        final Status eventStatus = new Status();

        final Status eventStatus =Status.APPROVED;
        return new Event(patientReferenceId, eventTiming, eventStatus);
    }

}
