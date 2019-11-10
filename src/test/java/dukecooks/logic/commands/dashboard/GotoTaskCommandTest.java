package dukecooks.logic.commands.dashboard;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.TypicalIndexes.INDEX_FIRST_DASHBOARD;
import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GotoTaskCommand.
 */
public class GotoTaskCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalDashboardRecords(), new UserPrefs());
        expectedModel = new ModelManager(model.getDashboardRecords(), new UserPrefs());
    }

    @Test
    public void execute_dashboardListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new GotoTaskCommand(), model, GotoTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_dashboardListIsFiltered_showsEverything() {
        CommandTestUtil.showDashboardAtIndex(model, INDEX_FIRST_DASHBOARD);
        assertCommandSuccess(new GotoTaskCommand(), model, GotoTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
