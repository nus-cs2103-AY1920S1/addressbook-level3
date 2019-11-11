package seedu.algobase.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_FROM;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_TO;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;

/**
 * Copies a Task from one Plan to another.
 */
public class CopyTaskCommand extends Command {

    public static final String COMMAND_WORD = "copytask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies the Task identified by the index from one plan to another.\n"
            + "Parameters:\n"
            + PREFIX_TASK + "TASK_INDEX "
            + PREFIX_PLAN_FROM + "PLAN_FROM_INDEX "
            + PREFIX_PLAN_TO + "PLAN_TO_INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TASK + "10 "
            + PREFIX_PLAN_FROM + "1 "
            + PREFIX_PLAN_TO + "2";

    public static final String MESSAGE_MOVE_TASK_SUCCESS = "Task [%1$s] copied from Plan [%2$s] to Plan [%3$s].";

    private final CopyTaskDescriptor copyTaskDescriptor;

    /**
     * Creates a CopyTaskCommand to copy a {@code Task} from the specified {@code Plan} to another
     *
     * @param copyTaskDescriptor details of the plan and problem involved
     */
    public CopyTaskCommand(CopyTaskDescriptor copyTaskDescriptor) {
        requireNonNull(copyTaskDescriptor);

        this.copyTaskDescriptor = copyTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String successMessage =
            TaskCommandUtil.shiftTask(
                model,
                copyTaskDescriptor.planFromIndex,
                copyTaskDescriptor.planToIndex,
                copyTaskDescriptor.taskIndex,
                false,
                MESSAGE_MOVE_TASK_SUCCESS
            );

        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyTaskCommand // instanceof handles nulls
                && copyTaskDescriptor.equals(((CopyTaskCommand) other).copyTaskDescriptor)); // state check
    }

    /**
     * Stores the details of the plan and problem involved.
     */
    public static class CopyTaskDescriptor {
        private Index taskIndex;
        private Index planFromIndex;
        private Index planToIndex;

        public CopyTaskDescriptor(Index taskIndex, Index planFromIndex, Index planToIndex) {
            this.taskIndex = taskIndex;
            this.planFromIndex = planFromIndex;
            this.planToIndex = planToIndex;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                || (other instanceof CopyTaskDescriptor // instanceof handles nulls
                && taskIndex.equals(((CopyTaskDescriptor) other).taskIndex)
                && planFromIndex.equals(((CopyTaskDescriptor) other).planFromIndex)
                && planToIndex.equals(((CopyTaskDescriptor) other).planToIndex));
        }
    }
}
