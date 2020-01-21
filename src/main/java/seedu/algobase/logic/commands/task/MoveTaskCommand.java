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
 * Moves a Task from one Plan to another.
 */
public class MoveTaskCommand extends Command {

    public static final String COMMAND_WORD = "movetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Moves the Task identified by the index from one plan to another.\n"
            + "Parameters:\n"
            + PREFIX_TASK + "TASK_INDEX "
            + PREFIX_PLAN_FROM + "PLAN_FROM_INDEX "
            + PREFIX_PLAN_TO + "PLAN_TO_INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TASK + "10 "
            + PREFIX_PLAN_FROM + "1 "
            + PREFIX_PLAN_TO + "2";

    public static final String MESSAGE_MOVE_TASK_SUCCESS = "Task [%1$s] moved from Plan [%2$s] to Plan [%3$s].";

    private final MoveTaskDescriptor moveTaskDescriptor;

    /**
     * Creates a MoveTaskCommand to move a {@code Task} from the specified {@code Plan} to another
     *
     * @param moveTaskDescriptor details of the plan and problem involved
     */
    public MoveTaskCommand(MoveTaskDescriptor moveTaskDescriptor) {
        requireNonNull(moveTaskDescriptor);
        this.moveTaskDescriptor = moveTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String successMessage =
            TaskCommandUtil.shiftTask(
                model,
                moveTaskDescriptor.planFromIndex,
                moveTaskDescriptor.planToIndex,
                moveTaskDescriptor.taskIndex,
                true,
                MESSAGE_MOVE_TASK_SUCCESS
            );

        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoveTaskCommand // instanceof handles nulls
                && moveTaskDescriptor.equals(((MoveTaskCommand) other).moveTaskDescriptor)); // state check
    }

    /**
     * Stores the details of the plan and problem involved.
     */
    public static class MoveTaskDescriptor {
        private Index taskIndex;
        private Index planFromIndex;
        private Index planToIndex;

        public MoveTaskDescriptor(Index taskIndex, Index planFromIndex, Index planToIndex) {
            this.taskIndex = taskIndex;
            this.planFromIndex = planFromIndex;
            this.planToIndex = planToIndex;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                || (other instanceof MoveTaskDescriptor // instanceof handles nulls
                && taskIndex.equals(((MoveTaskDescriptor) other).taskIndex)
                && planFromIndex.equals(((MoveTaskDescriptor) other).planFromIndex)
                && planToIndex.equals(((MoveTaskDescriptor) other).planToIndex));
        }
    }
}
