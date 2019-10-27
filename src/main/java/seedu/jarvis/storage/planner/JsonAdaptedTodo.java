package seedu.jarvis.storage.planner;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedTag;

/**
 * Jackson-friendly version of {@link Todo}.
 */
public class JsonAdaptedTodo extends JsonAdaptedTask implements JsonAdapter<Task> {

    /**
     * Constructs a {@code JsonAdaptedTodo} with the given todo details.
     *
     * @param description Description of the todo.
     */
    @JsonCreator
    public JsonAdaptedTodo(@JsonProperty("description") String description, @JsonProperty("priority") String priority,
                           @JsonProperty("frequency") String frequency, @JsonProperty("status") String status,
                           @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        super(description, priority, frequency, status, tags);
    }

    /**
     * Converts a given {@code Todo} into this class for Jackson use.
     *
     * @param todo {@code Todo} to be used to construct the {@code JsonAdaptedTodo}.
     */
    public JsonAdaptedTodo(Todo todo) {
        super(todo);
    }

    /**
     * Converts this Jackson-friendly adapted {@code Todo} into the model's {@code Task} object.
     *
     * @return {@code Task} of the Jackson-friendly adapted {@code Todo}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Todo}.
     */
    @Override
    public Task toModelType() throws IllegalValueException {
        validateAttributes();
        return new Todo(
                description,
                priority != null ? Priority.valueOf(priority) : null,
                frequency != null ? Frequency.valueOf(frequency) : null,
                status != null ? Status.valueOf(status) : null,
                adaptToTags(tags));
    }

}
