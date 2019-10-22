package seedu.address.storage.dashboard;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import javafx.concurrent.Task;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.dashboard.components.TaskDate;

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

    @JsonValue
    public String getTaskDate() {
        return taskDate;
    }

    public TaskDate toModelType() throws IllegalValueException {
        if(!TaskDate.isValidTaskDate(taskDate)) {
            throw new IllegalValueException(TaskDate.MESSAGE_CONSTRAINTS);
        }
        return new TaskDate(taskDate);
    }


}
