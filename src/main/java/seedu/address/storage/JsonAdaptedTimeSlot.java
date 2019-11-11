package seedu.address.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.schedule.Timeslot;
import seedu.address.model.person.schedule.Venue;

/**
 * Jackson-friendly version of {@link Timeslot}.
 */
public class JsonAdaptedTimeSlot {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "TimeSlot's %s field is missing!";

    private final String startTime;
    private final String endTime;
    private final String venue;

    /**
     * Constructs a {@code JsonAdaptedTimeSlot} with the given Timeslot details.
     */
    @JsonCreator
    public JsonAdaptedTimeSlot(@JsonProperty("startTime") String startTime,
                               @JsonProperty("endTime") String endTime,
                               @JsonProperty("venue") String venue) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    /**
     * Converts a given {@code Timeslot} into this class for Jackson use.
     */
    public JsonAdaptedTimeSlot(Timeslot source) {
        startTime = source.getStartTime().format(Timeslot.DATE_TIME_FORMATTER);
        endTime = source.getEndTime().format(Timeslot.DATE_TIME_FORMATTER);
        venue = source.getVenue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted timeslot object into the model's {@code Timeslot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted timeslot.
     */
    public Timeslot toModelType() throws IllegalValueException {
        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "startTime"));
        }
        final LocalDateTime modelStartTime = LocalDateTime.parse(startTime, Timeslot.DATE_TIME_FORMATTER);

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "endTime"));
        }
        final LocalDateTime modelEndTime = LocalDateTime.parse(endTime, Timeslot.DATE_TIME_FORMATTER);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        final Venue modelVenue = new Venue(venue);

        return new Timeslot(modelStartTime, modelEndTime, modelVenue);

    }


}
