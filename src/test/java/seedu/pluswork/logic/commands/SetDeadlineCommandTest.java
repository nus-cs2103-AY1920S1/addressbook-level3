package seedu.pluswork.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FOURTH_TASK;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.task.SetDeadlineCommand;
import seedu.pluswork.logic.commands.universal.ClearCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for SetDeadlineCommand.
 */
class SetDeadlineCommandTest {
    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    @Test
    void execute_validDeadline_success() {
        LocalDateTime updatedDeadline = LocalDateTime.now().plusWeeks(1);
        Task taskToAddDeadline = model.getFilteredTasksList().get(INDEX_THIRD_TASK.getZeroBased());
        SetDeadlineCommand setDeadlineCommand = new SetDeadlineCommand(INDEX_THIRD_TASK, updatedDeadline);
        Task taskUpdatedDeadline = new TaskBuilder(taskToAddDeadline)
                .withDeadline(updatedDeadline).build();
        // change the expected model to set a deadline to the above task
        ModelManager expectedModel = new ModelManager(model.getProjectDashboard(), new UserPrefs(), new UserSettings());
        expectedModel.setTask(taskToAddDeadline, taskUpdatedDeadline);
        String expectedMessage = String
                .format(SetDeadlineCommand.MESSAGE_SET_DEADLINE_TASK_SUCCESS, taskUpdatedDeadline);
        assertCommandSuccess(setDeadlineCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_taskWithDeadline_commandFailure() {
        SetDeadlineCommand setDeadlineCommand = new SetDeadlineCommand(INDEX_FIRST_TASK, LocalDateTime.now());
        assertCommandFailure(setDeadlineCommand, model, SetDeadlineCommand.MESSAGE_TASK_ALREADY_HAS_DEADLINE);
    }

    @Test
    void testEquals() {
        final SetDeadlineCommand standardCommand = new SetDeadlineCommand(INDEX_SECOND_TASK, LocalDateTime.now());

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new SetDeadlineCommand(INDEX_FOURTH_TASK, LocalDateTime.now())));

        // different deadline -> return false
        assertFalse(standardCommand.equals(new SetDeadlineCommand(INDEX_FIRST_TASK,
                LocalDateTime.now().plusMonths(1))));
    }
}
