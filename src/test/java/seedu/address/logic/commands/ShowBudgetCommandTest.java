package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.SpendingBook;
import seedu.address.model.UserPrefs;

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
