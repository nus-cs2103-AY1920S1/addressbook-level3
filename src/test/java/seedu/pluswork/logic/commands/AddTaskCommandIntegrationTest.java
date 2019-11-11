package seedu.pluswork.logic.commands;

import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddTaskCommand}.
 */
public class AddTaskCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
    }

    // TODO @ambhinav Test fails with "Operation would result in duplicate tasks"
    @Test
    public void execute_newTask_success() {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getProjectDashboard(), new UserPrefs(), new UserSettings());
        expectedModel.addTask(validTask);

        assertCommandSuccess(new AddTaskCommand(validTask), model,
                String.format(AddTaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskInList = model.getProjectDashboard().getTaskList().get(0);
        assertCommandFailure(new AddTaskCommand(taskInList), model, AddTaskCommand.MESSAGE_DUPLICATE_TASK);
    }

}
