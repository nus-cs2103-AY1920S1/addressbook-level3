package seedu.jarvis.storage.planner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.planner.Frequency;
import seedu.jarvis.model.planner.Priority;
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

    public static final String MESSAGE_INVALID_PRIORITY = "Invalid priority.";
    public static final String MESSAGE_INVALID_FREQUENCY = "Invalid frequency.";
    public static final String MESSAGE_INVALID_PRIORITY_AND_FREQUENCY = "Invalid priority and frequency";

    protected final String description;
    protected final String priority;
    protected final String frequency;

    @JsonCreator
    public JsonAdaptedTask(@JsonProperty("description") String description, @JsonProperty("priority") String priority,
                           @JsonProperty("frequency") String frequency) {
        this.description = description;
        this.priority = priority;
        this.frequency = frequency;
    }

    public JsonAdaptedTask(Task task) {
        description = task.getTaskDescription();
        priority = task.getPriority().name();
        frequency = task.getFrequency().name();
    }

    /**
     * Converts this Jackson-friendly adapted task into the model's {@code Task} object.
     *
     * @return {@code Task} of the Jackson-friendly adapted task.
     * @throws IllegalValueException If there were any data constraints violated in the adapted task.
     */
    public abstract Task toModelType() throws IllegalValueException;

    /**
     * Checks if the {@code String} {@code priority} is either null or a valid {@code Priority} constant.
     *
     * @return If {@code String} {@code priority} is either null or a valid {@code Priority} constant.
     */
    protected boolean isValidPriority() {
        if (priority == null) {
            return true;
        }

        try {
            Priority.valueOf(priority);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    /**
     * Checks if the {@code String} {@code frequency} is either null or a valid {@code Frequency} constant.
     *
     * @return If {@code String} {@code frequency} is either null or a valid {@code Frequency} constant.
     */
    protected boolean isValidFrequency() {
        if (frequency == null) {
            return true;
        }

        try {
            Frequency.valueOf(frequency);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    /**
     * Checks for the validity of {@code priority} and {@code frequency} which returns void if both are valid, if not,
     * an {@code IllegalValueException} is thrown with the appropriate message.
     *
     * @throws IllegalValueException If either {@code priority} or {@code frequency} are not valid enum constants.
     */
    protected void checkPriorityAndFrequency() throws IllegalValueException {
        if (!isValidPriority() && !isValidFrequency()) {
            throw new IllegalValueException(MESSAGE_INVALID_PRIORITY_AND_FREQUENCY);
        }

        if (!isValidPriority()) {
            throw new IllegalValueException(MESSAGE_INVALID_PRIORITY);
        }

        if (!isValidFrequency()) {
            throw new IllegalValueException(MESSAGE_INVALID_FREQUENCY);
        }
    }
}
