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

    public static final String MESSAGE_DONE_TASK_SUCCESS = "Marked Task as done: %1$s";

    private final DoneTaskDescriptor doneTaskDescriptor;

    /**
     * Creates a DoneTaskCommand to mark a {@code Task} as done in the specified {@code Plan}
     *
     * @param doneTaskDescriptor details of the plan and problem involved
     */
    public DoneTaskCommand(DoneTaskDescriptor doneTaskDescriptor) {
        this.doneTaskDescriptor = doneTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Plan> lastShownPlanList = model.getFilteredPlanList();

        if (doneTaskDescriptor.planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Plan planToUpdate = lastShownPlanList.get(doneTaskDescriptor.planIndex.getZeroBased());
        List<Task> taskList = new ArrayList<>(planToUpdate.getTasks());
        Task task = taskList.get(doneTaskDescriptor.taskIndex.getZeroBased());
        taskList.remove(doneTaskDescriptor.taskIndex.getZeroBased());
        Set<Task> taskSet = new HashSet<>(taskList);
        taskSet.add(new Task(task.getProblem(), true));
        Plan updatedPlan = Plan.createUpdatedPlan(planToUpdate, taskSet);
        model.setPlan(planToUpdate, updatedPlan);
        return new CommandResult(String.format(MESSAGE_DONE_TASK_SUCCESS, task));
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

        public DoneTaskDescriptor(Index planIndex, Index problemIndex) {
            this.planIndex = planIndex;
            this.taskIndex = problemIndex;
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
