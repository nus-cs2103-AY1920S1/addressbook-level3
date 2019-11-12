package seedu.moolah.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_STATS_TREND_DESCRIPTOR;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLahForStatistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.general.ClearCommand;
import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.modelhistory.ModelHistory;
import seedu.moolah.model.statistics.PieChartStatistics;
import seedu.moolah.model.statistics.TrendStatistics;

/**
 * Contains integration tests (interaction with the Model) and unit tests for StatsTrendCommand.
 */
class StatsTrendCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLahForStatistics(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLahForStatistics(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_allFieldsSpecifiedStatistics_success() {
        StatsTrendDescriptor descriptor = new StatsTrendDescriptor();

        Budget primaryBudget = model.getPrimaryBudget();
        descriptor.setStartDate(primaryBudget.getWindowStartDate());
        descriptor.setEndDate(primaryBudget.getWindowEndDate());
        StatsTrendCommand command = new StatsTrendCommand(descriptor);

        TrendStatistics statistics = new TrendStatistics(primaryBudget.getWindowStartDate(),
                primaryBudget.getWindowEndDate(),
                primaryBudget, true);
        statistics.populateData();
        expectedModel.setStatistics(statistics);
        String expectedMessage = StatsTrendCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


    @Test
    public void run_startDateSpecifiedStatistics_success() {
        StatsTrendDescriptor descriptor = new StatsTrendDescriptor();

        Budget primaryBudget = model.getPrimaryBudget();
        descriptor.setStartDate(primaryBudget.getWindowStartDate());
        StatsTrendCommand command = new StatsTrendCommand(descriptor);

        PieChartStatistics statistics = new PieChartStatistics(primaryBudget.getExpenses(),
                primaryBudget.getWindowStartDate(),
                primaryBudget.getWindowEndDate());
        statistics.populateData();
        expectedModel.setStatistics(statistics);
        String expectedMessage = StatsTrendCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void run_endDateSpecifiedStatistics_success() {
        StatsTrendDescriptor descriptor = new StatsTrendDescriptor();

        Budget primaryBudget = model.getPrimaryBudget();
        descriptor.setEndDate(primaryBudget.getWindowEndDate());
        StatsTrendCommand command = new StatsTrendCommand(descriptor);

        TrendStatistics statistics = new TrendStatistics(primaryBudget.getWindowStartDate(),
                primaryBudget.getWindowEndDate(),
                primaryBudget, true);
        statistics.populateData();
        expectedModel.setStatistics(statistics);
        String expectedMessage = StatsTrendCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }


    @Test
    public void run_noFieldSpecifiedStatistics_success() {
        StatsTrendDescriptor descriptor = new StatsTrendDescriptor();

        Budget primaryBudget = model.getPrimaryBudget();
        StatsTrendCommand command = new StatsTrendCommand(descriptor);

        TrendStatistics statistics = new TrendStatistics(primaryBudget.getWindowStartDate(),
                primaryBudget.getWindowEndDate(),
                primaryBudget, true);
        statistics.populateData();
        expectedModel.setStatistics(statistics);
        String expectedMessage = StatsTrendCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final StatsTrendCommand standardCommand = new StatsTrendCommand(VALID_STATS_TREND_DESCRIPTOR);

        // same values -> returns true
        StatsTrendDescriptor copyDescriptor = new StatsTrendDescriptor(VALID_STATS_TREND_DESCRIPTOR);
        StatsTrendCommand commandWithSameValues = new StatsTrendCommand(copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertEquals(standardCommand, standardCommand);

        // null -> returns false
        assertNotEquals(null, standardCommand);

        // different types -> returns false
        assertNotEquals(standardCommand, new ClearCommand());
    }



}
