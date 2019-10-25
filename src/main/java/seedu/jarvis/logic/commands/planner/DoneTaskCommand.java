package seedu.jarvis.logic.commands.planner;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.tasks.Task;

import static java.util.Objects.requireNonNull;

/**
 * Updates a {@code Task} from incomplete to complete
 */
//TODO tests
public class DoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "done-task";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a task as complete."
            + "Parameters: "
            +  "INDEX"
            + "\nExample: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "%1$s marked as done.";
    public static final String MESSAGE_TASK_ALREADY_DONE = "This task has already been marked as completed.";

    public static final String MESSAGE_INVERSE_SUCCESS_UNDONE = "%1$s marked as undone.";
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

        model.markTaskAsDone(targetIndex);

        return new CommandResult(String.format(MESSAGE_SUCCESS, doneTask));
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }
}
