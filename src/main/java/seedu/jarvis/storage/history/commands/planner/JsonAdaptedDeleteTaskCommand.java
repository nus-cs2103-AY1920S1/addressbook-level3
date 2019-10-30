package seedu.jarvis.storage.history.commands.planner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.planner.DeleteTaskCommand;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.planner.JsonAdaptedTask;

/**
 * Jackson-friendly version of {@link DeleteTaskCommand}.
 */
public class JsonAdaptedDeleteTaskCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_INDEX = "Invalid index.";

    private final JsonAdaptedIndex index;
    private final JsonAdaptedTask task;

    /**
     * Constructs a {@code JsonAdaptedDeleteTaskCommand} with the given {@code Index} of the task to delete, and
     * {@code Task} that was deleted.
     *
     * @param index {@code Index} of the {@code Task} to be deleted.
     * @param task {@code Task} that was deleted, which may be null.
     */
    @JsonCreator
    public JsonAdaptedDeleteTaskCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                        @JsonProperty("task") JsonAdaptedTask task) {
        this.index = index;
        this.task = task;
    }

    /**
     * Converts a given {@code DeleteTaskCommand} into this class for Jackson use.
     *
     * @param deleteTaskCommand {@code DeleteTaskCommand} to be used to construct the
     * {@code JsonAdaptedDeleteTaskCommand}.
     */
    public JsonAdaptedDeleteTaskCommand(DeleteTaskCommand deleteTaskCommand) {
        index = new JsonAdaptedIndex(deleteTaskCommand.getTargetIndex());
        task = deleteTaskCommand.getDeletedTask().map(Task::adaptToJsonAdaptedTask).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedDeleteTaskCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (index == null) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return new DeleteTaskCommand(
                index.toModelType(),
                task != null ? task.toModelType() : null);
    }
}
