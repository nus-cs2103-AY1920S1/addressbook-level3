package seedu.moneygowhere.logic.commands;

import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.SpendingBook;
import seedu.moneygowhere.model.UserPrefs;

class ShowBudgetCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void execute_displayBudget_success() {
        ShowBudgetCommand showBudgetCommand = new ShowBudgetCommand();

        String expectedMessage = showBudgetCommand.MESSAGE_SUCCESS + model.getBudget().toString();
        Model expectedModel = new ModelManager(new SpendingBook(model.getSpendingBook()), new UserPrefs());

        assertCommandSuccess(showBudgetCommand, model , expectedMessage, expectedModel);
    }
}
