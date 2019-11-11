package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBudgets.getTypicalBudgetList;
import static seedu.address.testutil.TypicalExpenses.getTypicalExchangeData;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.BudgetList;
import seedu.address.model.ExpenseList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ViewState;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyExpenseList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CLEAR_EXPENSES_SUCCESS, expectedModel,
            commandHistory);
    }

    @Test
    public void execute_nonEmptyExpenseList_success() {
        Model model = new ModelManager(getTypicalExpenseList(), new BudgetList(),
            getTypicalExchangeData(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalExpenseList(), new BudgetList(),
            getTypicalExchangeData(), new UserPrefs());
        expectedModel.setExpenseList(new ExpenseList());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CLEAR_EXPENSES_SUCCESS, expectedModel,
            commandHistory);
    }

    @Test
    public void execute_emptyBudgetList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        model.setViewState(ViewState.BUDGETLIST);
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CLEAR_BUDGETS_SUCCESS, expectedModel,
            commandHistory);
        model.setViewState(ViewState.DEFAULT_EXPENSELIST);
    }

    @Test
    public void execute_nonEmptyBudgetList_success() {
        Model model = new ModelManager(new ExpenseList(), getTypicalBudgetList(), getTypicalExchangeData(),
            new UserPrefs());
        Model expectedModel = new ModelManager(new ExpenseList(), getTypicalBudgetList(), getTypicalExchangeData(),
            new UserPrefs());
        expectedModel.setBudgetList(new BudgetList());
        model.setViewState(ViewState.BUDGETLIST);
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_CLEAR_BUDGETS_SUCCESS, expectedModel,
            commandHistory);
        model.setViewState(ViewState.DEFAULT_EXPENSELIST);
    }
}
