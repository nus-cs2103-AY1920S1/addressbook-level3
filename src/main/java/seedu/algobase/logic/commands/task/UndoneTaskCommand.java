package seedu.algobase.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;

/**
 * Marks a Task identified using its index in the Plan and the Plan index as undone.
 */
public class UndoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "undonetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the Task identified by the index as undone in the plan.\n"
            + "Parameters:\n"
            + PREFIX_PLAN + "PLAN_INDEX"
            + PREFIX_TASK + "TASK_INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_PLAN + "1 "
            + PREFIX_TASK + "10";

    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "Task [%1$s] marked as undone in Plan [%2$s].";
    public static final String MESSAGE_TASK_NOT_YET_DONE = "Task [%1$s] not yet marked as done.";

    private final UndoneTaskDescriptor undoneTaskDescriptor;

    /**
     * Creates a UndoneTaskCommand to mark a {@code Task} as undone in the specified {@code Plan}
     *
     * @param undoneTaskDescriptor details of the plan and problem involved
     */
    public UndoneTaskCommand(UndoneTaskDescriptor undoneTaskDescriptor) {
        requireNonNull(undoneTaskDescriptor);

        this.undoneTaskDescriptor = undoneTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String successMessage =
            TaskCommandUtil.updateStatus(
                model,
                undoneTaskDescriptor.planIndex,
                undoneTaskDescriptor.taskIndex,
                false,
                MESSAGE_UNDONE_TASK_SUCCESS,
                MESSAGE_TASK_NOT_YET_DONE
            );

        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoneTaskCommand // instanceof handles nulls
                && undoneTaskDescriptor.equals(((UndoneTaskCommand) other).undoneTaskDescriptor)); // state check
    }

    /**
     * Stores the details of the plan and problem involved.
     */
    public static class UndoneTaskDescriptor {
        private Index planIndex;
        private Index taskIndex;

        public UndoneTaskDescriptor(Index planIndex, Index taskIndex) {
            this.planIndex = planIndex;
            this.taskIndex = taskIndex;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof UndoneTaskDescriptor // instanceof handles nulls
                    && planIndex.equals(((UndoneTaskDescriptor) other).planIndex)
                    && taskIndex.equals(((UndoneTaskDescriptor) other).taskIndex));
        }
    }
}
