package seedu.algobase.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PLANS;

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

    private final UndoneTaskDescriptor undoneTaskDescriptor;

    /**
     * Creates a UndoneTaskCommand to mark a {@code Task} as undone in the specified {@code Plan}
     *
     * @param undoneTaskDescriptor details of the plan and problem involved
     */
    public UndoneTaskCommand(UndoneTaskDescriptor undoneTaskDescriptor) {
        this.undoneTaskDescriptor = undoneTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Plan> lastShownPlanList = model.getFilteredPlanList();
        if (undoneTaskDescriptor.planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Plan planToUpdate = lastShownPlanList.get(undoneTaskDescriptor.planIndex.getZeroBased());

        List<Task> taskList = planToUpdate.getTaskList();
        int taskIndex = undoneTaskDescriptor.taskIndex.getZeroBased();
        if (taskIndex >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToUpdate = taskList.get(taskIndex);
        if (!taskToUpdate.getIsSolved()) {
            throw new CommandException(String.format(Messages.MESSAGE_TASK_NOT_YET_DONE, taskToUpdate.getName()));
        }
        taskList.remove(taskIndex);
        Set<Task> taskSet = new HashSet<>(taskList);
        taskSet.add(Task.updateStatus(taskToUpdate, false));

        Plan updatedPlan = Plan.updateTasks(planToUpdate, taskSet);
        model.setPlan(planToUpdate, updatedPlan);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);

        return new CommandResult(
                String.format(MESSAGE_UNDONE_TASK_SUCCESS, taskToUpdate.getName(),
                        updatedPlan.getPlanName()));
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
