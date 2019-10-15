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
 * Marks a Task identified using its index in the Plan and the Plan index as undone.
 */
public class UndoneTaskCommand extends Command {

    public static final String COMMAND_WORD = "undonetask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the Task identified by the index as undone in the plan.\n"
            + "Parameters: "
            + PREFIX_PLAN + "PLAN"
            + PREFIX_TASK + "TASK\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PLAN + "1 "
            + PREFIX_TASK + "10";

    public static final String MESSAGE_UNDONE_TASK_SUCCESS = "Marked Task as undone: %1$s";

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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownPlanList = model.getFilteredPlanList();

        if (undoneTaskDescriptor.planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Plan planToUpdate = lastShownPlanList.get(undoneTaskDescriptor.planIndex.getZeroBased());
        List<Task> taskList = new ArrayList<>(planToUpdate.getTasks());
        Task task = taskList.get(undoneTaskDescriptor.taskIndex.getZeroBased());
        taskList.remove(undoneTaskDescriptor.taskIndex.getZeroBased());
        Set<Task> taskSet = new HashSet<>(taskList);
        taskSet.add(new Task(task.getProblem(), false));
        Plan updatedPlan = Plan.createUpdatedPlan(planToUpdate, taskSet);
        model.setPlan(planToUpdate, updatedPlan);
        return new CommandResult(String.format(MESSAGE_UNDONE_TASK_SUCCESS, task));
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

        public UndoneTaskDescriptor(Index planIndex, Index problemIndex) {
            this.planIndex = planIndex;
            this.taskIndex = problemIndex;
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
