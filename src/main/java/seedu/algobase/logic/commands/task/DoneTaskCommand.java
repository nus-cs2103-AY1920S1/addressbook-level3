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
 * Marks a Task identified using its index in the Plan and the Plan index as done.
 */
public class DoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "donetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the Task identified by the index as done in the plan.\n"
            + "Parameters:\n"
            + PREFIX_PLAN + "PLAN_INDEX "
            + PREFIX_TASK + "TASK_INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_PLAN + "1 "
            + PREFIX_TASK + "10";

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Task [%1$s] marked as done in Plan [%2$s].";
    public static final String MESSAGE_TASK_ALREADY_DONE = "Task [%1$s] already marked as done.";

    private final DoneTaskDescriptor doneTaskDescriptor;

    /**
     * Creates a DoneTaskCommand to mark a {@code Task} as done in the specified {@code Plan}
     *
     * @param doneTaskDescriptor details of the plan and problem involved
     */
    public DoneTaskCommand(DoneTaskDescriptor doneTaskDescriptor) {
        requireNonNull(doneTaskDescriptor);

        this.doneTaskDescriptor = doneTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        String successMessage =
            TaskCommandUtil.updateStatus(
                model,
                doneTaskDescriptor.planIndex,
                doneTaskDescriptor.taskIndex,
                true,
                MESSAGE_DONE_TASK_SUCCESS,
                MESSAGE_TASK_ALREADY_DONE
            );

        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneTaskCommand // instanceof handles nulls
                && doneTaskDescriptor.equals(((DoneTaskCommand) other).doneTaskDescriptor)); // state check
    }

    /**
     * Stores the details of the plan and problem involved.
     */
    public static class DoneTaskDescriptor {
        private Index planIndex;
        private Index taskIndex;

        public DoneTaskDescriptor(Index planIndex, Index taskIndex) {
            this.planIndex = planIndex;
            this.taskIndex = taskIndex;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof DoneTaskDescriptor // instanceof handles nulls
                    && planIndex.equals(((DoneTaskDescriptor) other).planIndex)
                    && taskIndex.equals(((DoneTaskDescriptor) other).taskIndex));
        }
    }
}
