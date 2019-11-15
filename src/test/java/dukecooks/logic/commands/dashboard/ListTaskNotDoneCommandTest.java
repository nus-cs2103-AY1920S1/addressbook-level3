package dukecooks.logic.commands.dashboard;

import static dukecooks.logic.commands.CommandTestUtil.assertCommandSuccess;
import static dukecooks.testutil.dashboard.TypicalDashboard.getTypicalDashboardRecords;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dukecooks.model.Model;
import dukecooks.model.ModelManager;
import dukecooks.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTaskNotDoneCommand.
 */
public class ListTaskNotDoneCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalDashboardRecords(), new UserPrefs());
        expectedModel = new ModelManager(model.getDashboardRecords(), new UserPrefs());
    }

    @Test
    public void execute_dashboardListIsFiltered_showsCompleteSuccess() {

        expectedModel.updateFilteredDashboardList(Model.PREDICATE_SHOW_NOT_DONE_DASHBOARD);
        assertCommandSuccess(new ListTaskNotDoneCommand(), model,
                ListTaskNotDoneCommand.MESSAGE_SHOW_COMPLETE_SUCCESS, expectedModel);
    }

}
