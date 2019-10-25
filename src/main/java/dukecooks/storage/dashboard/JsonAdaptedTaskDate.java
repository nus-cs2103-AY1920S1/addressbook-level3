package dukecooks.storage.dashboard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.dashboard.components.TaskDate;

/**
 * Jackson-friendly version of {@link TaskDate}.
 */
public class JsonAdaptedTaskDate {

    private final String taskDate;

    /**
     * Constructs a {@code JsonAdaptedTaskDate} with the give {@code taskDate}.
     */
    @JsonCreator
    public JsonAdaptedTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    /**
     * Converts a given {@code TaskDate} into this class for Jackson use.
     */
    public JsonAdaptedTaskDate(TaskDate source) {
        taskDate = source.taskDate;
    }

    /**
     * Returns a String of taskDate.
     */
    @JsonValue
    public String getTaskDate() {
        return taskDate;
    }

    /**
     * Converts this Jackson-friendly adapted TaskDate object into the model's {@code TaskDate} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted TaskDate.
     */
    public TaskDate toModelType() throws IllegalValueException {
        if (!TaskDate.isValidTaskDate(taskDate)) {
            throw new IllegalValueException(TaskDate.MESSAGE_CONSTRAINTS);
        }
        return new TaskDate(taskDate);
    }


}
