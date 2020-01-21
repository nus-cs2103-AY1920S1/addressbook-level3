package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.TypicalAlgoBase.getTypicalAlgoBase;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.task.AddTaskCommand;
import seedu.algobase.model.AlgoBase;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.task.Task;

class AddTaskCommandTest {

    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddTaskCommand(null));
    }

    @Test
    public void execute_invalidProblemIndex_addsFailure() {
        Index planIndex = Index.fromOneBased(model.getFilteredPlanList().size());
        Index outOfBoundProblemIndex = Index.fromOneBased(model.getFilteredProblemList().size() + 1);
        AddTaskCommand.AddTaskDescriptor descriptor =
            new AddTaskCommand.AddTaskDescriptor(planIndex, outOfBoundProblemIndex, LocalDate.now());
        AddTaskCommand addTaskCommand = new AddTaskCommand(descriptor);
        assertCommandFailure(addTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_PROBLEM_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPlanIndex_addsFailure() {
        Index outOfBoundPlanIndex = Index.fromOneBased(model.getFilteredPlanList().size() + 1);
        Index problemIndex = Index.fromOneBased(model.getFilteredProblemList().size());
        AddTaskCommand.AddTaskDescriptor descriptor =
            new AddTaskCommand.AddTaskDescriptor(outOfBoundPlanIndex, problemIndex, LocalDate.now());
        AddTaskCommand addTaskCommand = new AddTaskCommand(descriptor);
        assertCommandFailure(addTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidDueDate_addsFailure() {
        Index planIndex = Index.fromZeroBased(0);
        Plan plan = model.getFilteredPlanList().get(planIndex.getZeroBased());
        Index problemIndex = Index.fromZeroBased(0);

        AddTaskCommand.AddTaskDescriptor descriptor =
            new AddTaskCommand.AddTaskDescriptor(planIndex, problemIndex, plan.getEndDate().plusMonths(1));
        AddTaskCommand command = new AddTaskCommand(descriptor);

        assertCommandFailure(command, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DATE);
    }

    @Test
    public void execute_duplicateTasks_addsFailure() {
        Index planIndex = Index.fromZeroBased(0);
        Plan plan = model.getFilteredPlanList().get(planIndex.getZeroBased());
        Index problemIndex = Index.fromZeroBased(1);
        Problem problem = model.getFilteredProblemList().get(problemIndex.getZeroBased());

        AddTaskCommand.AddTaskDescriptor descriptor =
            new AddTaskCommand.AddTaskDescriptor(planIndex, problemIndex, plan.getStartDate());
        AddTaskCommand command = new AddTaskCommand(descriptor);

        String expectedMessage =
            String.format(Messages.MESSAGE_DUPLICATE_TASK, problem.getName(), plan.getPlanName());
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_taskAcceptedByModel_addsSuccessful() {
        Index planIndex = Index.fromZeroBased(0);
        Plan plan = model.getFilteredPlanList().get(planIndex.getZeroBased());
        Index problemIndex = Index.fromZeroBased(0);
        Problem problem = model.getFilteredProblemList().get(problemIndex.getZeroBased());

        AddTaskCommand.AddTaskDescriptor descriptor =
            new AddTaskCommand.AddTaskDescriptor(planIndex, problemIndex, plan.getStartDate());
        AddTaskCommand command = new AddTaskCommand(descriptor);

        String expectedMessage =
            String.format(AddTaskCommand.MESSAGE_ADD_TASK_SUCCESS, problem.getName(), plan.getPlanName());

        Task task = new Task(problem, plan.getStartDate(), false);
        Set<Task> taskSet = new HashSet<>(plan.getTasks());
        taskSet.add(task);
        Model expectedModel = new ModelManager(new AlgoBase(model.getAlgoBase()), new UserPrefs());
        expectedModel.updateTasks(taskSet, plan);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

}
