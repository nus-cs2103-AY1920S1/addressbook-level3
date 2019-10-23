package seedu.address.calendar.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.calendar.model.Commitment;
import seedu.address.calendar.model.Date;
import seedu.address.calendar.model.Event;
import seedu.address.calendar.model.Info;
import seedu.address.calendar.model.Name;
import seedu.address.commons.exceptions.IllegalValueException;

import java.util.Optional;

/**
 * Jackson-friendly version of {@link Event}
 */
public class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String startDate;
    private final String endDate;
    private final String info;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("startDate") String startDate,
                            @JsonProperty("endDate") String endDate, @JsonProperty("info") String info) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.info = info;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getNameStr();
        startDate = source.getStartDateStr();
        endDate = source.getEndDateStr();
        info = source.getInfoStr();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event}
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name of event"));
        }
        final Name eventName = new Name(name);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date of event"));
        }

        final Date startDate = Date.getInstanceFromString(this.startDate);

        final Optional<Date> endDate;
        if (this.endDate == null) {
            endDate = Optional.empty();
        } else {
            endDate = Optional.of(Date.getInstanceFromString(this.endDate));
        }

        final Optional<Info> info;
        if (this.info == null) {
            info = Optional.empty();
        } else {
            info = Optional.of(new Info(this.info));
        }

        // todo: generalise such that we can have different events, and add end date
        return new Commitment(eventName, startDate, info);
    }
}
