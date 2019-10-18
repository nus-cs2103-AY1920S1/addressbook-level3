package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.TaskTime;

/**
 * Jackson-friendly version of {@link TaskTime}.
 */
public class JsonAdaptedTaskTime {
    private final String fullTime;

    /**
     * Constructs a {@code JsonAdaptedTaskTime} with the given {@code fullTime}.
     */
    @JsonCreator
    public JsonAdaptedTaskTime(String fullTime) {
        this.fullTime = fullTime;
    }

    /**
     * Converts a given {@code TaskTime} into this class for Jackson use.
     */
    public JsonAdaptedTaskTime(TaskTime source) {
        fullTime = source.fullTime;
    }

    @JsonValue
    public String getFullTime() {
        return fullTime;
    }

    /**
     * Converts this Jackson-friendly adapted task time object into the model's {@code TaskTime} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted taskTime.
     */
    public TaskTime toModelType() throws IllegalValueException {
        if (!TaskTime.isValidTaskTime(fullTime)) {
            throw new IllegalValueException(TaskTime.MESSAGE_CONSTRAINTS);
        }
        return new TaskTime(fullTime);
    }


    }
