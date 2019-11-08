package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBudgets.getTypicalBudgetList;
import static seedu.address.testutil.TypicalExpenses.getTypicalExchangeData;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.Budget;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpenseList(), getTypicalBudgetList(),
            getTypicalExchangeData(), new UserPrefs());
    }

    @Test
    public void execute_newExpense_success() {
        Expense validExpense = new ExpenseBuilder().build();

        Model expectedModel = new ModelManager(model.getExpenseList(), model.getBudgetList(),
            model.getExchangeData(), new UserPrefs());
        expectedModel.addExpense(validExpense);

        assertCommandSuccess(new AddCommand(validExpense), model,
            String.format(AddCommand.MESSAGE_SUCCESS, validExpense), expectedModel, commandHistory);
    }

    @Test
    public void execute_duplicateExpense_throwsCommandException() {
        Expense expenseInList = model.getExpenseList().getExpenseList().get(0);
        assertCommandFailure(new AddCommand(expenseInList), model, AddCommand.MESSAGE_DUPLICATE_EXPENSE,
            commandHistory);
    }

    @Test
    public void execute_clashBudget_throwsCommandException() {
        Budget budgetInList = model.getBudgetList().getBudgetList().get(0);
        assertCommandFailure(new AddBudgetCommand(budgetInList), model, AddBudgetCommand.MESSAGE_BUDGET_CLASH,
            commandHistory);
    }

}
