package dukecooks.logic.commands.dashboard;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DashboardCommand.
 */
public class DashboardCommandTest {

    private Model modelTask;
    private Model expectedModelTask;

    @BeforeEach
    public void setUp() {
        modelTask = new ModelManager(getTypicalDashboardRecords(), new UserPrefs());
        expectedModelTask = new ModelManager(modelTask.getDashboardRecords(), new UserPrefs());
    }

    @Test
    public void execute_dashboardListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new DashboardCommand(), modelTask, DashboardCommand.MESSAGE_SUCCESS, expectedModelTask);
    }

}
