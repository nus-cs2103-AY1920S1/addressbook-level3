package seedu.jarvis.logic.commands.planner;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.PlannerModel;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;
import seedu.jarvis.storage.history.commands.planner.JsonAdaptedDeleteTaskCommand;

/**
 * Deletes a task from JARVIS
 */
public class DeleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "delete-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a task from the planner. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task:\n%1$s";

    public static final String MESSAGE_INVERSE_SUCCESS_ADD = "New task added task: %1$s";
    public static final String MESSAGE_INVERSE_TASK_TO_ADD_ALREADY_EXIST = "Task already added: %1$s";

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;
    private Task deletedTask;

    /**
     * Creates a {@code DeleteTaskCommand} and sets TargetIndex to the {@code Index} and {@code Task} that was
     * deleted, which is null if the task has not been deleted.
     *
     * @param targetIndex {@code Index} of the {@code Task} to be deleted
     * @param deletedTask {@code Task} that was deleted, which is null if the task has not been deleted
     */
    public DeleteTaskCommand(Index targetIndex, Task deletedTask) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.deletedTask = deletedTask;
    }

    /**
     * Creates a {@code DeleteTaskCommand} and sets targetIndex to the {@code Index}
     * of the {@code Task} to be deleted.
     * @param targetIndex {@code Index} of the {@code Task} to be deleted
     */
    public DeleteTaskCommand(Index targetIndex) {
        this(targetIndex, null);
    }

    /**
     * Gets the command word of the command
     * @return {@code String} representation of the command word.
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Gets the {@code Index} of the {@code Task} to be deleted.
     * @return {@code Index} of the {@code Task} to be deleted
     */
    public Index getTargetIndex() {
        return targetIndex;
    }

    /**
     * Gets the {@code Task} that was deleted wrapped in an {@code Optional}.
     * @return {@code Task} that was deleted wrapped in an {@code Optional}.
     */
    public Optional<Task> getDeletedTask() {
        return Optional.ofNullable(deletedTask);
    }

    /**
     * Returns whether the command has an inverse execution.
     * If the command has no inverse execution, then calling {@code executeInverse}
     * will be guaranteed to always throw a {@code CommandException}
     * @return true if it does, false if it does not
     */
    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    /**
     * Deletes {@code Task} from the planner
     *
     * @param model {@code Model} which the command should operate on.
     * @return {@code CommandResult} of a successful delete
     * @throws CommandException If targetIndex is >= the number of tasks in a planner
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        TaskList tasks = model.getTasks();

        if (targetIndex.getZeroBased() >= tasks.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        deletedTask = tasks.getTask(targetIndex);
        model.deleteTask(targetIndex);
        model.updateFilteredTaskList(PlannerModel.PREDICATE_SHOW_ALL_TASKS);
        model.setViewStatus(ViewType.LIST_PLANNER);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask), true);

    }

    /**
     * Adds back the {@code Task} that was deleted
     *
     * @param model {@code Model} which the command should inversely operate on.
     * @return {@code CommandResult} of a successful restore of the deleted {@code Task}
     * if the {@code Task} is not already in the planner
     * @throws CommandException If the task to be added will be in conflict with an existing task
     * in the planner
     */
    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTask(deletedTask)) {
            throw new CommandException(String.format(MESSAGE_INVERSE_TASK_TO_ADD_ALREADY_EXIST, deletedTask));
        }

        model.addTask(targetIndex.getZeroBased(), deletedTask);

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_ADD, deletedTask));
    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedDeleteTaskCommand(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            // short circuit if same object
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof DeleteTaskCommand)) {
            return false;
        }

        return targetIndex.equals(((DeleteTaskCommand) other).targetIndex)
                && Objects.equals(deletedTask, ((DeleteTaskCommand) other).deletedTask);
    }
}
