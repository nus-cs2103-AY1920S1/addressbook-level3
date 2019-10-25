package seedu.jarvis.storage.planner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

/**
 * Jackson-friendly version of {@link Todo}.
 */
public class JsonAdaptedTodo extends JsonAdaptedTask {

    private final String description;

    /**
     * Constructs a {@code JsonAdaptedTodo} with the given todo details.
     *
     * @param description Description of the todo.
     */
    @JsonCreator
    public JsonAdaptedTodo(@JsonProperty("description") String description) {
        this.description = description;
    }

    /**
     * Converts a given {@code Todo} into this class for Jackson use.
     *
     * @param todo {@code Todo} to be used to construct the {@code JsonAdaptedTodo}.
     */
    public JsonAdaptedTodo(Todo todo) {
        this.description = todo.getTaskDescription();
    }

    /**
     * Converts this Jackson-friendly adapted {@code Todo} into the model's {@code Task} object.
     *
     * @return {@code Task} of the Jackson-friendly adapted {@code Todo}.
     * @throws IllegalValueException If there were any data constraints violated in the adapted {@code Todo}.
     */
    @Override
    public Task toModelType() throws IllegalValueException {
        return new Todo(description);
    }
}
