package seedu.algobase.logic.commands.task;

import static seedu.algobase.commons.util.CollectionUtil.requireAllNonNull;

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
 * Util methods to improve code reuse.
 */
public class TaskCommandUtil {

    /**
     * Updates the status of a specified task in a specified plan.
     */
    public static String updateStatus(
        Model model,
        Index planIndex,
        Index taskIndex,
        boolean isSolved,
        String successMessage,
        String errorMessage
    ) throws CommandException {
        requireAllNonNull(model, planIndex, taskIndex, isSolved, successMessage, errorMessage);

        List<Plan> lastShownPlanList = model.getFilteredPlanList();
        if (planIndex.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }
        Plan planToUpdate = lastShownPlanList.get(planIndex.getZeroBased());
        int taskIndexInt = taskIndex.getZeroBased();
        List<Task> taskList = planToUpdate.getTaskList();
        if (taskIndexInt >= taskList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToUpdate = taskList.get(taskIndexInt);
        if (taskToUpdate.getIsDone() == isSolved) {
            throw new CommandException(String.format(errorMessage, taskToUpdate.getName()));
        }
        taskList.remove(taskIndexInt);
        Set<Task> taskSet = new HashSet<>(taskList);
        taskSet.add(taskToUpdate.updateStatus(isSolved));
        model.updateTasks(taskSet, planToUpdate);

        return String.format(successMessage, taskToUpdate.getName(), planToUpdate.getPlanName());
    }

    /**
     * Shifts a specified task from one specified plan to another.
     */
    public static String shiftTask(
        Model model,
        Index planIndexToBeShiftedFrom,
        Index planIndexToBeShiftedInto,
        Index taskIndex,
        boolean shouldRemoveFromOrigin,
        String successMessage
    ) throws CommandException {
        requireAllNonNull(
            model,
            planIndexToBeShiftedFrom,
            planIndexToBeShiftedInto,
            taskIndex,
            shouldRemoveFromOrigin,
            successMessage
        );

        List<Plan> lastShownPlanList = model.getFilteredPlanList();
        if (planIndexToBeShiftedFrom.getZeroBased() >= lastShownPlanList.size()
            || planIndexToBeShiftedInto.getZeroBased() >= lastShownPlanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
        }
        Plan planToBeShiftedFrom = lastShownPlanList.get(planIndexToBeShiftedFrom.getZeroBased());
        Plan planToBeShiftedInto = lastShownPlanList.get(planIndexToBeShiftedInto.getZeroBased());
        int taskIndexInt = taskIndex.getZeroBased();
        List<Task> taskListToBeShiftedFrom = planToBeShiftedFrom.getTaskList();
        List<Task> taskListToBeShiftedInto = planToBeShiftedInto.getTaskList();
        if (taskIndexInt >= taskListToBeShiftedFrom.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskToBeShifted = taskListToBeShiftedFrom.get(taskIndexInt);
        if (shouldRemoveFromOrigin) {
            taskListToBeShiftedFrom.remove(taskIndexInt);
        }
        Set<Task> taskSetToBeShiftedFrom = new HashSet<>(taskListToBeShiftedFrom);
        Set<Task> taskSetToBeShiftedInto = new HashSet<>(taskListToBeShiftedInto);
        if (taskSetToBeShiftedInto.contains(taskToBeShifted)) {
            throw new CommandException(
                String.format(Messages.MESSAGE_DUPLICATE_TASK,
                    taskToBeShifted.getName(),
                    planToBeShiftedInto.getPlanName()
                )
            );
        }
        if (!planToBeShiftedInto.checkIsWithinDateRange(taskToBeShifted.getDueDate())) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DATE);
        }
        taskSetToBeShiftedInto.add(taskToBeShifted);
        if (shouldRemoveFromOrigin) {
            model.updateTasks(taskSetToBeShiftedFrom, planToBeShiftedFrom);
        }
        model.updateTasks(taskSetToBeShiftedInto, planToBeShiftedInto);

        return String.format(
            successMessage,
            taskToBeShifted.getName(),
            planToBeShiftedFrom.getPlanName(),
            planToBeShiftedInto.getPlanName()
        );
    }

}
