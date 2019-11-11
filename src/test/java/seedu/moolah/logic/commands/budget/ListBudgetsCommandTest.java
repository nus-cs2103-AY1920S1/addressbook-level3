package seedu.moolah.logic.commands.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.modelhistory.ModelHistory;
import seedu.moolah.ui.budget.BudgetListPanel;

public class ListBudgetsCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_listIsNotFiltered_showsEveryBudget() {
        assertCommandSuccess(new ListBudgetsCommand(), model, ListBudgetsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    void execute_executed_commandResultPanelIsBudgetListPanel() {
        assertEquals(
                new ListBudgetsCommand().execute(model).viewRequest(),
                BudgetListPanel.PANEL_NAME);
    }
}
