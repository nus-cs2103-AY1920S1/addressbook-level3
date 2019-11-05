package seedu.algobase.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.Command;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.model.Model;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.task.Task;

/**
 * Deletes a Task identified using its index in the Plan and the Plan index.
 */
public class DeleteTaskCommand extends Command {

    public static final String COMMAND_WORD = "deletetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the Task identified by the index in the plan.\n"
            + "Parameters:\n"
            + PREFIX_PLAN + "PLAN_INDEX "
            + PREFIX_TASK + "TASK_INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_PLAN + "1 "
            + PREFIX_TASK + "10";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Task [%1$s] deleted from Plan [%2$s].";

    private final DeleteTaskDescriptor deleteTaskDescriptor;

    /**
     * Creates a DeleteTaskCommand to delete a {@code Task} in the specified {@code Plan}
     *
     * @param deleteTaskDescriptor details of the plan and problem involved
     */
    public DeleteTaskCommand(DeleteTaskDescriptor deleteTaskDescriptor) {
        this.deleteTaskDescriptor = deleteTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Plan> lastShownPlanList = model.getFilteredPlanList();
        if (deleteTaskDescriptor.planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }
        Plan planToUpdate = lastShownPlanList.get(deleteTaskDescriptor.planIndex.getZeroBased());

        List<Task> taskList = planToUpdate.getTaskList();
        int taskIndex = deleteTaskDescriptor.taskIndex.getZeroBased();
        if (taskIndex >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task task = taskList.get(taskIndex);
        taskList.remove(taskIndex);
        Set<Task> taskSet = new HashSet<>(taskList);

        model.updateTasks(taskSet, planToUpdate);

        return new CommandResult(
                String.format(MESSAGE_DELETE_TASK_SUCCESS, task.getName(), planToUpdate.getPlanName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTaskCommand // instanceof handles nulls
                && deleteTaskDescriptor.equals(((DeleteTaskCommand) other).deleteTaskDescriptor)); // state check
    }

    /**
     * Stores the details of the plan and problem involved.
     */
    public static class DeleteTaskDescriptor {
        private Index planIndex;
        private Index taskIndex;

        public DeleteTaskDescriptor(Index planIndex, Index taskIndex) {
            this.planIndex = planIndex;
            this.taskIndex = taskIndex;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                || (other instanceof DeleteTaskDescriptor // instanceof handles nulls
                && planIndex.equals(((DeleteTaskDescriptor) other).planIndex)
                && taskIndex.equals(((DeleteTaskDescriptor) other).taskIndex));
        }
    }
}
