package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.Budget;

class BudgetCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void execute_setNewBudget_success() {
        Budget budget = new Budget(10000);
        BudgetCommand budgetCommand = new BudgetCommand(budget);

        ModelManager expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
        expectedModel.setBudget(budget);

        String expectedMessage = BudgetCommand.MESSAGE_SUCCESS + budget.toString();

        assertCommandSuccess(budgetCommand, model , expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidBudgetAmount_fail() {
        Budget budget = new Budget(0);
        budget.setValue(-1);
        BudgetCommand budgetCommand = new BudgetCommand(budget);

        String expectedMessage = Messages.MESSAGE_INVALID_BUDGET_AMOUNT;

        assertCommandFailure(budgetCommand, model , expectedMessage);
    }
}
