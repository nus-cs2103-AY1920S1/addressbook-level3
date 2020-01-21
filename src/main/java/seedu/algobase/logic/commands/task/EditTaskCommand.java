package seedu.algobase.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_DUE_DATE;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_PLAN;
import static seedu.algobase.logic.parser.CliSyntax.PREFIX_TASK;

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
import seedu.algobase.model.task.Task;

/**
 * Marks a Task identified using its index in the Plan and the Plan index as done.
 */
public class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "edittask";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the due date of the Task identified by the index in the plan.\n"
            + "Parameters:\n"
            + PREFIX_PLAN + "PLAN_INDEX "
            + PREFIX_TASK + "TASK_INDEX "
            + PREFIX_DUE_DATE + "DUE_DATE\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_PLAN + "1 "
            + PREFIX_TASK + "10 "
            + PREFIX_DUE_DATE + "2019-12-12";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Task [%1$s] set to be due at [%2$s] in Plan [%3$s].";

    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * Creates a EditTaskCommand to change due date of a {@code Task} in the specified {@code Plan}
     *
     * @param editTaskDescriptor details of the plan and problem involved
     */
    public EditTaskCommand(EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(editTaskDescriptor);

        this.editTaskDescriptor = editTaskDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Plan> lastShownPlanList = model.getFilteredPlanList();
        if (editTaskDescriptor.planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }
        Plan planToUpdate = lastShownPlanList.get(editTaskDescriptor.planIndex.getZeroBased());

        List<Task> taskList = planToUpdate.getTaskList();
        int taskIndex = editTaskDescriptor.taskIndex.getZeroBased();
        if (taskIndex >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToUpdate = taskList.get(taskIndex);
        LocalDate newDate = editTaskDescriptor.targetDate;
        if (!planToUpdate.checkIsWithinDateRange(newDate)) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DATE);
        }
        taskList.remove(taskIndex);
        Set<Task> taskSet = new HashSet<>(taskList);
        taskSet.add(taskToUpdate.updateDueDate(newDate));

        model.updateTasks(taskSet, planToUpdate);

        return new CommandResult(
            String.format(MESSAGE_EDIT_TASK_SUCCESS,
                taskToUpdate.getName(),
                editTaskDescriptor.targetDate,
                planToUpdate.getPlanName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditTaskCommand // instanceof handles nulls
                && editTaskDescriptor.equals(((EditTaskCommand) other).editTaskDescriptor)); // state check
    }

    /**
     * Stores the details of the plan and problem involved.
     */
    public static class EditTaskDescriptor {
        private Index planIndex;
        private Index taskIndex;
        private LocalDate targetDate;

        public EditTaskDescriptor(Index planIndex, Index problemIndex, LocalDate targetDate) {
            this.planIndex = planIndex;
            this.taskIndex = problemIndex;
            this.targetDate = targetDate;
        }

        @Override
        public boolean equals(Object other) {
            return other == this // short circuit if same object
                || (other instanceof EditTaskDescriptor // instanceof handles nulls
                && planIndex.equals(((EditTaskDescriptor) other).planIndex)
                && taskIndex.equals(((EditTaskDescriptor) other).taskIndex)
                && targetDate.equals(((EditTaskDescriptor) other).targetDate));
        }
    }
}
