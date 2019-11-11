package seedu.moolah.logic.commands.budget;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.modelhistory.ModelChanges;
import seedu.moolah.model.modelhistory.ModelHistory;
import seedu.moolah.testutil.TypicalMooLah;

public class ClearBudgetsCommandTest {

    @Test
    public void run_emptyBudgetList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        ClearBudgetsCommand command = new ClearBudgetsCommand();

        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()));

        assertCommandSuccess(new ClearBudgetsCommand(), model, ClearBudgetsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void run_nonEmptyBudgetList_success() {
        Model model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        Model expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        ClearBudgetsCommand command = new ClearBudgetsCommand();

        expectedModel.clearBudgets();
        expectedModel.addToPastChanges(new ModelChanges(command.getDescription()).setMooLah(model.getMooLah()));

        assertCommandSuccess(new ClearBudgetsCommand(), model, ClearBudgetsCommand.MESSAGE_SUCCESS, expectedModel);
        TypicalMooLah.reset();
    }

}
