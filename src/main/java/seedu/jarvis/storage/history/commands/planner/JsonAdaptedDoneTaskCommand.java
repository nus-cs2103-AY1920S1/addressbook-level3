package seedu.jarvis.storage.history.commands.planner;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.planner.DoneTaskCommand;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.storage.JsonAdapter;
import seedu.jarvis.storage.commons.core.JsonAdaptedIndex;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.planner.JsonAdaptedTask;

/**
 * Jackson-friendly version of {@link DoneTaskCommand}.
 */
public class JsonAdaptedDoneTaskCommand extends JsonAdaptedCommand implements JsonAdapter<Command> {

    public static final String MESSAGE_INVALID_INDEX = "Invalid index.";

    private final JsonAdaptedIndex index;
    private final JsonAdaptedTask task;

    /**
     * Constructs a {@code JsonAdaptedDoneTaskCommand} with the given {@code Task} to be done.
     *
     * @param index {@code Index} of the task that was done.
     * @param task {@code Task} that was done.
     */
    @JsonCreator
    public JsonAdaptedDoneTaskCommand(@JsonProperty("index") JsonAdaptedIndex index,
                                      @JsonProperty("task") JsonAdaptedTask task) {
        this.index = index;
        this.task = task;
    }

    /**
     * Converts a given {@code DoneTaskCommand} into this class for Jackson use.
     *
     * @param doneTaskCommand {@code DoneTaskCommand} to be used to construct the {@code JsonAdaptedDoneTaskCommand}.
     */
    public JsonAdaptedDoneTaskCommand(DoneTaskCommand doneTaskCommand) {
        index = new JsonAdaptedIndex(doneTaskCommand.getTargetIndex());
        task = doneTaskCommand.getDoneTask().map(Task::adaptToJsonAdaptedTask).orElse(null);
    }

    /**
     * Converts this Jackson-friendly adapted command into the model's {@code Command} object.
     *
     * @return {@code Command} of the Jackson-friendly adapted command.
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     * {@code JsonAdaptedDoneTaskCommand}.
     */
    @Override
    public Command toModelType() throws IllegalValueException {
        if (index == null) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return new DoneTaskCommand(
                index.toModelType(),
                task != null ? task.toModelType() : null);
    }
}
