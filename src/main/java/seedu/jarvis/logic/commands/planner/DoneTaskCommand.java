package seedu.jarvis.logic.commands.planner;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.PlannerModel;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;
import seedu.jarvis.storage.history.commands.planner.JsonAdaptedDoneTaskCommand;

/**
 * Updates a {@code Task} from incomplete to complete
 */
public class DoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "done-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a task as complete."
            + "Parameters: "
            + "INDEX"
            + "\nExample: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "%1$s\nmarked as done.";
    public static final String MESSAGE_TASK_ALREADY_DONE = "This task has already been marked as completed.";

    public static final String MESSAGE_INVERSE_SUCCESS_UNDONE = "%1$s\nmarked as undone.";
    public static final String MESSAGE_INVERSE_TASK_ALREADY_UNDONE = "Task has already been marked as undone";

    public static final boolean HAS_INVERSE = true;

    private final Index targetIndex;
    private Task doneTask;

    /**
     * Creates a {@code DoneTaskCommand} and sets TargetIndex to the {@code Index}
     *
     * @param targetIndex {@code Index} of the {@code Task} to be marked as done
     */
    public DoneTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Creates a {@code DoneTaskCommand} and sets TargetIndex to the {@code Index} and
     * {@code Task} that was marked as done, which is null if the {@code Task} has not been
     * marked as done yet.
     *
     * @param targetIndex {@code Index} of the {@code Task} to be marked as done
     * @param doneTask {@code Task} that was marked as done, which is null if the {@code Task} has
     *                             not been marked as done yet.
     */
    public DoneTaskCommand(Index targetIndex, Task doneTask) {
        this.targetIndex = targetIndex;
        this.doneTask = doneTask;
    }

    /**
     * Gets the command word of the command
     * @return {@code String} "done-task", i.e. the command word of DoneTaskCommand
     */
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    /**
     * Gets the {@code Index} of the task that is done.
     *
     * @return {@code Index} of the task that is done.
     */
    public Index getTargetIndex() {
        return targetIndex;
    }

    /**
     * Gets the {@code Task} that was done wrapped in an {@code Optional}.
     *
     * @return {@code Task} that was done wrapped in an {@code Optional}.
     */
    public Optional<Task> getDoneTask() {
        return Optional.ofNullable(doneTask);
    }

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        TaskList tasks = model.getTasks();

        if (targetIndex.getZeroBased() >= tasks.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task task = model.getTask(targetIndex);

        if (task.getStatus() == Status.DONE) {
            throw new CommandException(MESSAGE_TASK_ALREADY_DONE);
        }

        model.markTaskAsDone(targetIndex);
        doneTask = model.getTask(targetIndex);
        model.updateFilteredTaskList(PlannerModel.PREDICATE_SHOW_ALL_TASKS);
        model.setViewStatus(ViewType.LIST_PLANNER);
        return new CommandResult(String.format(MESSAGE_SUCCESS, doneTask), true);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        requireNonNull(model);

        TaskList tasks = model.getTasks();

        if (targetIndex.getZeroBased() >= tasks.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (doneTask.getStatus() == Status.NOT_DONE) {
            throw new CommandException(MESSAGE_INVERSE_TASK_ALREADY_UNDONE);
        }

        doneTask.markAsNotDone();

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_UNDONE, doneTask));

    }

    /**
     * Gets a {@code JsonAdaptedCommand} from a {@code Command} for local storage purposes.
     *
     * @return {@code JsonAdaptedCommand}.
     * @throws InvalidCommandToJsonException If command should not be adapted to JSON format.
     */
    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        return new JsonAdaptedDoneTaskCommand(this);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            //short circuit if same object
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof DoneTaskCommand)) {
            return false;
        }

        return targetIndex.equals(((DoneTaskCommand) other).targetIndex);
    }
}
