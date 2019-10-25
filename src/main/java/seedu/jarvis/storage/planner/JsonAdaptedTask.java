package seedu.jarvis.storage.planner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.planner.tasks.Task;


/**
 * Abstract class that represents a Jackson-Friendly task.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedTodo.class, name = "JsonAdaptedTodo"),
        @JsonSubTypes.Type(value = JsonAdaptedDeadline.class, name = "JsonAdaptedDeadline"),
        @JsonSubTypes.Type(value = JsonAdaptedEvent.class, name = "JsonAdaptedEvent")
})
public abstract class JsonAdaptedTask {

    protected final String description;

    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description) {
        this.description = description;
    }

    public JsonAdaptedTask(Task task) {
        this.description = task.getTaskDescription();
    }

    /**
     * Converts this Jackson-friendly adapted task into the model's {@code Task} object.
     *
     * @return {@code Task} of the Jackson-friendly adapted task.
     * @throws IllegalValueException If there were any data constraints violated in the adapted task.
     */
    public abstract Task toModelType() throws IllegalValueException;
}
