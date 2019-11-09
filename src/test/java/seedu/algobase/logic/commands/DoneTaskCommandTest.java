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
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.commands.task.DoneTaskCommand;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.task.Task;

class DoneTaskCommandTest {

    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DoneTaskCommand(null));
    }

    @Test
    public void execute_invalidTaskIndex_doesFailure() {
        Index planIndex = Index.fromOneBased(model.getFilteredPlanList().size());
        Index outOfBoundTaskIndex = Index.fromOneBased(100);
        DoneTaskCommand.DoneTaskDescriptor descriptor =
            new DoneTaskCommand.DoneTaskDescriptor(planIndex, outOfBoundTaskIndex);
        DoneTaskCommand deleteTaskCommand = new DoneTaskCommand(descriptor);
        assertCommandFailure(deleteTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPlanIndex_doesFailure() {
        Index outOfBoundPlanIndex = Index.fromOneBased(model.getFilteredPlanList().size() + 1);
        Index taskIndex = Index.fromOneBased(1);
        DoneTaskCommand.DoneTaskDescriptor descriptor =
            new DoneTaskCommand.DoneTaskDescriptor(outOfBoundPlanIndex, taskIndex);
        DoneTaskCommand deleteTaskCommand = new DoneTaskCommand(descriptor);
        assertCommandFailure(deleteTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskAcceptedByModel_doesSuccessful() {
        Index planIndex = Index.fromZeroBased(0);
        Plan plan = model.getFilteredPlanList().get(planIndex.getZeroBased());
        Index taskIndex = Index.fromZeroBased(0);
        List<Task> taskList = plan.getTaskList();
        Task task = taskList.get(taskIndex.getZeroBased());

        DoneTaskCommand.DoneTaskDescriptor descriptor =
            new DoneTaskCommand.DoneTaskDescriptor(planIndex, taskIndex);
        DoneTaskCommand command = new DoneTaskCommand(descriptor);

        String expectedMessage =
            String.format(
                DoneTaskCommand.MESSAGE_DONE_TASK_SUCCESS,
                task.getName(),
                plan.getPlanName()
            );

        taskList.remove(taskIndex);
        Set<Task> taskSet = new HashSet<>(taskList);
        taskSet.add(task.updateStatus(true));
        Model expectedModel = new ModelManager(new AlgoBase(model.getAlgoBase()), new UserPrefs());
        expectedModel.updateTasks(taskSet, plan);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_taskAlreadyDone_doesFailure() throws CommandException {
        Index planIndex = Index.fromZeroBased(0);
        Plan plan = model.getFilteredPlanList().get(planIndex.getZeroBased());
        Index taskIndex = Index.fromZeroBased(1);
        List<Task> taskList = plan.getTaskList();
        Task task = taskList.get(taskIndex.getZeroBased());

        DoneTaskCommand.DoneTaskDescriptor descriptor =
            new DoneTaskCommand.DoneTaskDescriptor(planIndex, taskIndex);
        DoneTaskCommand command = new DoneTaskCommand(descriptor);

        String expectedMessage =
            String.format(
                DoneTaskCommand.MESSAGE_TASK_ALREADY_DONE,
                task.getName(),
                plan.getPlanName()
            );

        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

}
