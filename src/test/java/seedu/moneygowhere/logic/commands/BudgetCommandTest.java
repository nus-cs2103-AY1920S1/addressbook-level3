package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.core.Messages;
import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.budget.Budget;

class BudgetCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void execute_setNewBudget_success() {
        Budget budget = new Budget(10000);
        BudgetCommand budgetCommand = new BudgetCommand(budget);

        ModelManager expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
        expectedModel.setBudget(budget);

        String expectedMessage = BudgetCommand.MESSAGE_SUCCESS + budget.toString();

        try {
            CommandResult result = budgetCommand.execute(model);
            assertEquals(new CommandResult(expectedMessage), result);
            assertEquals(expectedModel.getBudget().getValue(), model.getBudget().getValue());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);

        }
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
