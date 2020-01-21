package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.TypicalAlgoBase.getTypicalAlgoBase;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.task.EditTaskCommand;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.task.Task;

class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EditTaskCommand(null));
    }

    @Test
    public void execute_invalidTaskIndex_editsFailure() {
        Index planIndex = Index.fromOneBased(model.getFilteredPlanList().size());
        Index outOfBoundTaskIndex = Index.fromOneBased(100);
        EditTaskCommand.EditTaskDescriptor descriptor =
            new EditTaskCommand.EditTaskDescriptor(planIndex, outOfBoundTaskIndex, LocalDate.now());
        EditTaskCommand deleteTaskCommand = new EditTaskCommand(descriptor);
        assertCommandFailure(deleteTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPlanIndex_editsFailure() {
        Index outOfBoundPlanIndex = Index.fromOneBased(model.getFilteredPlanList().size() + 1);
        Index taskIndex = Index.fromOneBased(1);
        EditTaskCommand.EditTaskDescriptor descriptor =
            new EditTaskCommand.EditTaskDescriptor(outOfBoundPlanIndex, taskIndex, LocalDate.now());
        EditTaskCommand deleteTaskCommand = new EditTaskCommand(descriptor);
        assertCommandFailure(deleteTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidDueDate_editsFailure() {
        Index planIndex = Index.fromZeroBased(0);
        Plan plan = model.getFilteredPlanList().get(planIndex.getZeroBased());
        Index taskIndex = Index.fromZeroBased(0);

        EditTaskCommand.EditTaskDescriptor descriptor =
            new EditTaskCommand.EditTaskDescriptor(planIndex, taskIndex, plan.getEndDate().plusMonths(1));
        EditTaskCommand command = new EditTaskCommand(descriptor);

        assertCommandFailure(command, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DATE);
    }

    @Test
    public void execute_taskAcceptedByModel_editsSuccessful() {
        Index planIndex = Index.fromZeroBased(0);
        Plan plan = model.getFilteredPlanList().get(planIndex.getZeroBased());
        Index taskIndex = Index.fromZeroBased(0);
        List<Task> taskList = plan.getTaskList();
        Task task = taskList.get(taskIndex.getZeroBased());

        EditTaskCommand.EditTaskDescriptor descriptor =
            new EditTaskCommand.EditTaskDescriptor(planIndex, taskIndex, plan.getStartDate());
        EditTaskCommand command = new EditTaskCommand(descriptor);

        String expectedMessage =
            String.format(
                EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS,
                task.getName(),
                plan.getStartDate(),
                plan.getPlanName()
            );

        taskList.remove(taskIndex);
        Set<Task> taskSet = new HashSet<>(taskList);
        taskSet.add(task.updateDueDate(plan.getStartDate()));
        Model expectedModel = new ModelManager(new AlgoBase(model.getAlgoBase()), new UserPrefs());
        expectedModel.updateTasks(taskSet, plan);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

}
