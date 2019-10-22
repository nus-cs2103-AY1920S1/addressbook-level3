package seedu.module.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.model.module.Deadline;

/**
 * Jackson-friendly version of {@Deadline Deadline}
 */
public class JsonAdaptedDeadline {
    private final String description;
    private final String time;

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given parameters.
     */
    public JsonAdaptedDeadline(@JsonProperty("deadlineDescription") String description, @JsonProperty("deadlineTime")
                               String time) {
        this.description = description;
        this.time = time;
    }

    /**
     * Converts a given {@code Deadline} into this class for Jackson use.
     */
    public JsonAdaptedDeadline(Deadline source) {
        this.description = source.getDescription();
        this.time = source.getTime();
    }

    public Deadline toModelType() throws IllegalValueException {
        return new Deadline(description, time);
    }
}
