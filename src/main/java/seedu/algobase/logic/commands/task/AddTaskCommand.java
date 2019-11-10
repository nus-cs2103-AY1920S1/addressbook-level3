package seedu.algobase.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DUE_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PROBLEM;

import java.time.LocalDate;
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
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;

/**
 * Adds a Task to a Plan.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a task to a training plan.\n"
            + "Parameters:\n"
            + PREFIX_PLAN + "PLAN_INDEX "
            + PREFIX_PROBLEM + "PROBLEM_INDEX "
            + "[" + PREFIX_DUE_DATE + "DUE_DATE]\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_PLAN + "1 "
            + PREFIX_PROBLEM + "10 "
            + PREFIX_DUE_DATE + "2019-12-12";

    public static final String MESSAGE_ADD_TASK_SUCCESS = "New Task [%1$s] added to Plan [%2$s].";

    private final AddTaskDescriptor addTaskDescriptor;

    /**
     * Creates an AddTaskCommand to add a {@code Task} to the specified {@code Plan}
     *
     * @param addTaskDescriptor details of the plan and problem involved
     */
    public AddTaskCommand(AddTaskDescriptor addTaskDescriptor) {
        requireNonNull(addTaskDescriptor);

        this.addTaskDescriptor = addTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Plan> lastShownPlanList = model.getFilteredPlanList();
        List<Problem> lastShownProblemList = model.getFilteredProblemList();

        if (addTaskDescriptor.planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }

        if (addTaskDescriptor.problemIndex.getZeroBased() >= lastShownProblemList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROBLEM_DISPLAYED_INDEX);
        }

        Plan planToUpdate = lastShownPlanList.get(addTaskDescriptor.planIndex.getZeroBased());
        Problem problem = lastShownProblemList.get(addTaskDescriptor.problemIndex.getZeroBased());

        Task task;
        LocalDate taskDate = addTaskDescriptor.targetDate;
        LocalDate planDate = planToUpdate.getEndDate();
        if (taskDate != null) {
            if (!planToUpdate.checkIsWithinDateRange(taskDate)) {
                throw new CommandException(Messages.MESSAGE_INVALID_TASK_DATE);
            }
            task = new Task(problem, taskDate, false);
        } else {
            task = new Task(problem, planDate, false);
        }

        Set<Task> taskSet = new HashSet<>(planToUpdate.getTasks());
        if (taskSet.contains(task)) {
            throw new CommandException(
                    String.format(Messages.MESSAGE_DUPLICATE_TASK, task.getName(), planToUpdate.getPlanName()));
        }
        taskSet.add(task);

        model.updateTasks(taskSet, planToUpdate);

        return new CommandResult(
                String.format(MESSAGE_ADD_TASK_SUCCESS, task.getName(), planToUpdate.getPlanName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && addTaskDescriptor.equals(((AddTaskCommand) other).addTaskDescriptor));
    }

    /**
     * Stores the details of the plan and problem involved.
     */
    public static class AddTaskDescriptor {
        private Index planIndex;
        private Index problemIndex;
        private LocalDate targetDate;

        public AddTaskDescriptor(Index planIndex, Index problemIndex, LocalDate targetDate) {
            this.planIndex = planIndex;
            this.problemIndex = problemIndex;
            this.targetDate = targetDate;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                    || (other instanceof AddTaskDescriptor // instanceof handles nulls
                    && planIndex.equals(((AddTaskDescriptor) other).planIndex)
                    && problemIndex.equals(((AddTaskDescriptor) other).problemIndex)
                    && targetDate.equals(((AddTaskDescriptor) other).targetDate));
        }
    }
}
