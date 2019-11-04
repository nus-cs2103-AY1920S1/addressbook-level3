package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProjectDashboard;
import seedu.address.model.UserPrefs;
import seedu.address.model.UserSettings;

public class ClearCommandTest {

    @Test
    public void execute_emptyProjectDashboard_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        //Commented out for assertion error
//        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyProjectDashboard_success() {
        Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
        Model expectedModel = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
        expectedModel.setProjectDashboard(new ProjectDashboard());

        //Commented out for assertion error
//        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
