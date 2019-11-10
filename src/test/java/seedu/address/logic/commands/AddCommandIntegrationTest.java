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
import seedu.address.testutil.BudgetBuilder;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddExpenseCommand} and {@code AddBudgetCommand}.
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

        Model expectedModel = new ModelManager(model.getExpenseList(), getTypicalBudgetList(),
            model.getExchangeData(), new UserPrefs());

        expectedModel.addExpense(validExpense);

        assertCommandSuccess(new AddExpenseCommand(validExpense), model,
            String.format(AddExpenseCommand.MESSAGE_SUCCESS, validExpense), expectedModel, commandHistory);
    }

    @Test
    public void execute_duplicateExpense_throwsCommandException() {
        Expense expenseInList = model.getExpenseList().getExpenseList().get(0);
        assertCommandFailure(new AddExpenseCommand(expenseInList), model,
            AddExpenseCommand.MESSAGE_DUPLICATE_EXPENSE, commandHistory);
    }

    @Test
    public void execute_newBudget_success() {
        Budget validBudget = new BudgetBuilder().build();

        Model expectedModel = new ModelManager(getTypicalExpenseList(), model.getBudgetList(),
            model.getExchangeData(), new UserPrefs());
        expectedModel.addBudget(validBudget);

        assertCommandSuccess(new AddBudgetCommand(validBudget), model,
            String.format(AddBudgetCommand.MESSAGE_SUCCESS, validBudget), expectedModel, commandHistory);
    }

    @Test
    public void execute_duplicateBudget_throwsCommandException() {
        Budget budgetInList = model.getBudgetList().getBudgetList().get(0);
        assertCommandFailure(new AddBudgetCommand(budgetInList), model, AddBudgetCommand.MESSAGE_DUPLICATE_BUDGET,
            commandHistory);
    }
}
