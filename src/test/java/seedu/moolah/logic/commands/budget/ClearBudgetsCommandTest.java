package seedu.moolah.logic.commands.budget;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelHistory;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;

public class ClearBudgetsCommandTest {

    @Test
    public void run_emptyBudgetList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitModel("");

        assertCommandSuccess(new ClearBudgetsCommand(), model, ClearBudgetsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void run_nonEmptyBudgetList_success() {
        Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        Model expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel.commitModel("");
        expectedModel.clearBudgets();

        assertCommandSuccess(new ClearBudgetsCommand(), model, ClearBudgetsCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
