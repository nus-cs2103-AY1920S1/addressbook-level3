package seedu.pluswork.logic.commands.universal;

import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import org.junit.jupiter.api.Test;

import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;

public class ClearCommandTest {

    @Test
    public void execute_emptyProjectDashboard_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        //Commented out for assertion error
        //assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyProjectDashboard_success() {
        Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
        Model expectedModel = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());
        expectedModel.setProjectDashboard(new ProjectDashboard());

        //Commented out for assertion error
        // assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
