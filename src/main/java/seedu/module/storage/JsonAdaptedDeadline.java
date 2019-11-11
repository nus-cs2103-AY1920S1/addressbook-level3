package seedu.module.storage;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.model.module.Deadline;
import seedu.module.model.module.exceptions.DeadlineParseException;

/**
 * Jackson-friendly version of {@Deadline Deadline}
 */
public class JsonAdaptedDeadline {
    private final String description;
    private final String time;
    private final String tag;

    /**
     * Constructs a {@code JsonAdaptedDeadline} with the given parameters.
     */
    public JsonAdaptedDeadline(@JsonProperty("deadlineDescription") String description, @JsonProperty("deadlineTime")
                               String time, @JsonProperty("deadlineTag") String tag) {
        this.description = description;
        this.time = time;
        this.tag = tag;
    }

    /**
     * Converts a given {@code Deadline} into this class for Jackson use.
     */
    public JsonAdaptedDeadline(Deadline source) {
        this.description = source.getDescription();
        this.time = source.getTime();
        this.tag = source.getTag();
    }

    /**
     * models deadline in storage
     * @return Deadline object
     * @throws IllegalValueException
     */
    public Deadline toModelType() throws IllegalValueException {
        try {
            return new Deadline(description, time, tag);
        } catch (DeadlineParseException e) {
            throw new IllegalValueException(e.getMessage());
        }
        //return new Deadline(description, time, tag);
    }
}
