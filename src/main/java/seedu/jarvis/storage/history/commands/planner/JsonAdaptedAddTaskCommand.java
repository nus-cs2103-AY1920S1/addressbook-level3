package seedu.jarvis.storage.history.commands.planner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.planner.AddTaskCommand;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.planner.JsonAdaptedTask;

/**
 * Jackson-friendly version of {@link AddTaskCommand}.
 */
public class JsonAdaptedAddTaskCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_TASK = "Invalid task.";

    private final JsonAdaptedTask task;

    /**
     * Constructs a {@code JsonAdaptedAddTaskCommand} with the given {@code Task} to be added.
     *
     * @param task {@code Task} object in Json format.
     */
    @JsonCreator
    public JsonAdaptedAddTaskCommand(@JsonProperty("task") JsonAdaptedTask task) {
        this.task = task;
    }

    /**
     * Converts a given {@code AddTaskCommand} into this class for Jackson use.
     *
     * @param addTaskCommand {@code AddTaskCommand} to be used to construct the {@code JsonAdaptedAddTaskCommand}.
     */
    public JsonAdaptedAddTaskCommand(AddTaskCommand addTaskCommand) {
        this.task = addTaskCommand.getAddedTask().adaptToJsonAdaptedTask();
    }

    /**
     * Converts this Jackson-friendly adapted {@code AddTaskCommand} object into the model's {@code AddTaskCommand}
     * object.
     *
     * @return {@code Command} of the Jackson-friendly adapted {@code AddTaskCommand}.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code AddTaskCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (task == null) {
            throw new IllegalValueException(MESSAGE_INVALID_TASK);
        }
        return new AddTaskCommand(task.toModelType());
    }
}
