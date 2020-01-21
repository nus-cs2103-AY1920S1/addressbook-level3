package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.TypicalAlgoBase.getTypicalAlgoBase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.task.DeleteTaskCommand;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.task.Task;

class DeleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteTaskCommand(null));
    }

    @Test
    public void execute_invalidTaskIndex_deletesFailure() {
        Index planIndex = Index.fromOneBased(model.getFilteredPlanList().size());
        Index outOfBoundTaskIndex = Index.fromOneBased(100);
        DeleteTaskCommand.DeleteTaskDescriptor descriptor =
            new DeleteTaskCommand.DeleteTaskDescriptor(planIndex, outOfBoundTaskIndex);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(descriptor);
        assertCommandFailure(deleteTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPlanIndex_deletesFailure() {
        Index outOfBoundPlanIndex = Index.fromOneBased(model.getFilteredPlanList().size() + 1);
        Index taskIndex = Index.fromOneBased(1);
        DeleteTaskCommand.DeleteTaskDescriptor descriptor =
            new DeleteTaskCommand.DeleteTaskDescriptor(outOfBoundPlanIndex, taskIndex);
        DeleteTaskCommand deleteTaskCommand = new DeleteTaskCommand(descriptor);
        assertCommandFailure(deleteTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validTaskAndPlanIndex_deletesSuccessful() {
        Index planIndex = Index.fromZeroBased(0);
        Plan plan = model.getFilteredPlanList().get(planIndex.getZeroBased());
        Index taskIndex = Index.fromZeroBased(0);
        List<Task> taskList = plan.getTaskList();
        Task task = taskList.get(taskIndex.getZeroBased());

        DeleteTaskCommand.DeleteTaskDescriptor descriptor =
            new DeleteTaskCommand.DeleteTaskDescriptor(planIndex, taskIndex);
        DeleteTaskCommand command = new DeleteTaskCommand(descriptor);

        String expectedMessage =
            String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, task.getName(), plan.getPlanName());

        taskList.remove(taskIndex.getZeroBased());
        Set<Task> taskSet = new HashSet<>(taskList);
        Model expectedModel = new ModelManager(new AlgoBase(model.getAlgoBase()), new UserPrefs());
        expectedModel.updateTasks(taskSet, plan);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

}
