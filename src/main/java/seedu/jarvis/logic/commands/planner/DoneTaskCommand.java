package seedu.jarvis.logic.commands.planner;

import static java.util.Objects.requireNonNull;

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

/**
 * Updates a {@code Task} from incomplete to complete
 */
public class DoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "done-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a task as complete."
            + "Parameters: "
            + "INDEX"
            + "\nExample: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "%1$s marked as done.";
    public static final String MESSAGE_TASK_ALREADY_DONE = "This task has already been marked as completed.";

    public static final String MESSAGE_INVERSE_SUCCESS_UNDONE = "%1$s marked as undone.";
    public static final String MESSAGE_INVERSE_TASK_ALREADY_UNDONE = "Task has already been marked as undone";

    public static final boolean HAS_INVERSE = false;

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

        Task undone = model.getTask(targetIndex);
        if (undone.getStatus() == Status.NOT_DONE) {
            throw new CommandException(MESSAGE_INVERSE_TASK_ALREADY_UNDONE);
        }

        undone.markAsNotDone();

        return new CommandResult(String.format(MESSAGE_INVERSE_SUCCESS_UNDONE, undone));

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
