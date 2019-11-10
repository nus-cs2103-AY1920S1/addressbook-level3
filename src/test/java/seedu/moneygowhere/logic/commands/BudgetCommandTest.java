package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.logic.commands.exceptions.CommandException;
import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.budget.Budget;

class BudgetCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BudgetCommand(null));
    }
    @Test
    public void execute_setNewBudget_success() {
        Budget budget = new Budget(10000);
        BudgetCommand budgetCommand = new BudgetCommand(budget);

        ModelManager expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
        expectedModel.setBudget(budget);

        String expectedMessage = BudgetCommand.MESSAGE_SUCCESS + model.getCurrencyInUse().symbol + budget.toString();

        try {
            CommandResult result = budgetCommand.execute(model);
            assertEquals(new CommandResult(expectedMessage), result);
            assertEquals(expectedModel.getBudget().getAmount(), model.getBudget().getAmount());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);

        }
    }

}
