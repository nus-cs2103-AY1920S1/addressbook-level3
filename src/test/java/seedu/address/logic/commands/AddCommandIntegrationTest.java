package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;
import static seedu.address.testutil.TypicalBudgets.getTypicalBudgetList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.BudgetList;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.BudgetBuilder;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpenseList(), getTypicalBudgetList(), new UserPrefs());
    }

    @Test
    public void execute_newExpense_success() {
        Expense validExpense = new ExpenseBuilder().build();

        Model expectedModel = new ModelManager(model.getExpenseList(), getTypicalBudgetList(), new UserPrefs());
        expectedModel.addExpense(validExpense);

        assertCommandSuccess(new AddCommand(validExpense), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validExpense), expectedModel);
    }

    @Test
    public void execute_duplicateExpense_throwsCommandException() {
        Expense expenseInList = model.getExpenseList().getExpenseList().get(0);
        assertCommandFailure(new AddCommand(expenseInList), model, AddCommand.MESSAGE_DUPLICATE_EXPENSE);
    }

    @Test
    public void execute_newBudget_success() {
        Budget validBudget = new BudgetBuilder().build();

        Model expectedModel = new ModelManager(getTypicalExpenseList(), model.getBudgetList(), new UserPrefs());
        expectedModel.addBudget(validBudget);

        assertCommandSuccess(new AddBudgetCommand(validBudget), model,
                String.format(AddBudgetCommand.MESSAGE_SUCCESS, validBudget), expectedModel);
    }

    @Test
    public void execute_clashBudget_throwsCommandException() {
        Budget budgetInList = model.getBudgetList().getBudgetList().get(0);
        assertCommandFailure(new AddBudgetCommand(budgetInList), model, AddBudgetCommand.MESSAGE_BUDGET_CLASH);
    }

}
