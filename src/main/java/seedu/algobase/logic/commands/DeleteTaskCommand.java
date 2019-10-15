package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
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

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownPlanList = model.getFilteredPlanList();

        if (deleteTaskDescriptor.planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Plan planToUpdate = lastShownPlanList.get(deleteTaskDescriptor.planIndex.getZeroBased());
        List<Task> taskList = new ArrayList<>(planToUpdate.getTasks());
        Task task = taskList.get(deleteTaskDescriptor.taskIndex.getZeroBased());
        taskList.remove(deleteTaskDescriptor.taskIndex.getZeroBased());
        Set<Task> taskSet = new HashSet<>(taskList);
        Plan updatedPlan = Plan.createUpdatedPlan(planToUpdate, taskSet);
        model.setPlan(planToUpdate, updatedPlan);
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, task));
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

        public DeleteTaskDescriptor(Index planIndex, Index problemIndex) {
            this.planIndex = planIndex;
            this.taskIndex = problemIndex;
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
