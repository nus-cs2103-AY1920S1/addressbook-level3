package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.budget.Budget;
import seedu.moneygowhere.model.currency.Currency;

public class BudgetCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
    }

    @Test
    public void execute_setNewBudget_success() {
        Budget budget = new Budget(10000);

        ModelManager expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
        expectedModel.setBudget(budget);

        String expectedMessage = BudgetCommand.MESSAGE_SUCCESS + model.getCurrencyInUse().symbol + budget.toString();

        assertCommandSuccess(new BudgetCommand(budget), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_setNewBudgetWithChangedCurrency_success() {
        Budget budget = new Budget(10000);

        ModelManager expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
        expectedModel.setBudget(budget);

        Currency usdCurrency = null;
        for (Currency currency : model.getSpendingBook().getCurrencies()) {
            if (currency.name.equalsIgnoreCase("USD")) {
                usdCurrency = currency;
            }
        }

        assertNotNull(usdCurrency);

        double updatedAmount = budget.getAmount() / usdCurrency.rate;
        Budget newBudget = new Budget(updatedAmount);

        expectedModel.setCurrencyInUse(usdCurrency);
        expectedModel.setBudget(newBudget);

        model.setCurrencyInUse(usdCurrency);

        String expectedMessage = BudgetCommand.MESSAGE_SUCCESS + model.getCurrencyInUse().symbol + budget.toString();

        assertCommandSuccess(new BudgetCommand(budget), model, expectedMessage, expectedModel);
    }
}
