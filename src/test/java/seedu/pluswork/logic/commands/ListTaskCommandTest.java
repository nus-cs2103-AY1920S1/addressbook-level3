package seedu.pluswork.logic.commands;

import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.pluswork.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.commands.task.ListTaskCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTaskCommand.
 */
public class ListTaskCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
        expectedModel = new ModelManager(model.getProjectDashboard(), new UserPrefs(), new UserSettings());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListTaskCommand(), model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);
        assertCommandSuccess(new ListTaskCommand(), model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
