package seedu.algobase.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_FROM;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN_TO;
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
 * Copies a Task from one Plan to another.
 */
public class CopyTaskCommand extends Command {

    public static final String COMMAND_WORD = "copytask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies the Task identified by the index from one plan to another.\n"
            + "Parameters:\n"
            + PREFIX_TASK + "TASK_INDEX "
            + PREFIX_PLAN_FROM + "PLAN_INDEX "
            + PREFIX_PLAN_TO + "PLAN_INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TASK + "10 "
            + PREFIX_PLAN_FROM + "1 "
            + PREFIX_PLAN_TO + "2";

    public static final String MESSAGE_MOVE_TASK_SUCCESS = "Task [%1$s] copied from Plan [%2$s] to Plan [%3$s].";
    public static final String MESSAGE_DUPLICATE_TASK = "Task [%1$s] already exists in Plan [%2$s].";

    private final CopyTaskDescriptor copyTaskDescriptor;

    /**
     * Creates a CopyTaskCommand to copy a {@code Task} from the specified {@code Plan} to another
     *
     * @param copyTaskDescriptor details of the plan and problem involved
     */
    public CopyTaskCommand(CopyTaskDescriptor copyTaskDescriptor) {
        this.copyTaskDescriptor = copyTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Plan> lastShownPlanList = model.getFilteredPlanList();
        if (copyTaskDescriptor.planFromIndex.getZeroBased() >= lastShownPlanList.size()
            || copyTaskDescriptor.planToIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Plan planFrom = lastShownPlanList.get(copyTaskDescriptor.planFromIndex.getZeroBased());
        Plan planTo = lastShownPlanList.get(copyTaskDescriptor.planToIndex.getZeroBased());

        List<Task> taskListFrom = planFrom.getTaskList();
        List<Task> taskListTo = planTo.getTaskList();
        int taskIndex = copyTaskDescriptor.taskIndex.getZeroBased();
        if (taskIndex >= taskListFrom.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToCopy = taskListFrom.get(taskIndex);
        Set<Task> taskSetTo = new HashSet<>(taskListTo);
        if (taskSetTo.contains(taskToCopy)) {
            throw new CommandException(
                String.format(MESSAGE_DUPLICATE_TASK, taskToCopy.getProblem().getName(), planTo.getPlanName()));
        }
        if (!planTo.checkWithinDateRange(taskToCopy.getTargetDate())) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DATE);
        }
        taskSetTo.add(taskToCopy);

        Plan updatedPlanTo = Plan.updateTasks(planTo, taskSetTo);
        model.setPlan(planTo, updatedPlanTo);
        model.updateFilteredPlanList(PREDICATE_SHOW_ALL_PLANS);

        return new CommandResult(
            String.format(MESSAGE_MOVE_TASK_SUCCESS,
                taskToCopy.getName(),
                planFrom.getPlanName(),
                updatedPlanTo.getPlanName()
            )
        );
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
