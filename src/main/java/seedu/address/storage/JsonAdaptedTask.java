package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.project.Description;
import seedu.address.model.project.Task;
import seedu.address.model.project.Time;

import java.text.ParseException;

/**
 * Jackson-friendly version of {@link Task}.
 */
class JsonAdaptedTask {

    private final String description;
    private final String time;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedTask} with the given {@code description, time and isDone}.
     */
    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description")String description, @JsonProperty("time")String time, @JsonProperty("isDone ")boolean isDone) {
        this.description = description;
        this.time = time;
        this.isDone = isDone;
    }

    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task source) {
        description = source.description.description;
        time = source.time.toString();
        isDone = source.isDone;
    }


    /**
     * Converts this Jackson-friendly adapted task object into the model's {@code Task} object.
     */
    public Task toModelType() throws ParseException {
        final Description modelDescription = new Description(description);
        final Time modelTime = new Time(time);
        return new Task(modelDescription, modelTime, isDone);
    }
}
