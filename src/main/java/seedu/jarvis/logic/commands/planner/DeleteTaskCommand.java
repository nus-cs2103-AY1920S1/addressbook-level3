package seedu.jarvis.logic.commands.planner;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.commons.core.Messages;
import seedu.jarvis.commons.core.index.Index;
import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.planner.TaskList;
import seedu.jarvis.model.planner.tasks.Task;

/**
 * Deletes a task from JARVIS
 */
public class DeleteTaskCommand extends Command {
    public static final String COMMAND_WORD = "deleteTask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a task from the planner. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task:\n%1$s";

    public static final boolean HAS_INVERSE = false;

    private final Index targetIndex;

    /**
     * Creates a {@code DeleteTaskCommand} and sets targetIndex to the {@code Index}
     * of the {@code Task} to be deleted.
     * @param targetIndex {@code Index} of the {@code Task} to be deleted
     */
    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
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

        Task deletedTask = tasks.getTask(targetIndex);
        model.deleteTask(targetIndex);

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask));

    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTaskCommand) other).targetIndex));
    }
}
