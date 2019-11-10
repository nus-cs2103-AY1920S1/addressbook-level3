package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.testutil.TypicalExpenses.getTypicalExchangeData;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.budget.BudgetList;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDefaultExpensesCommand.
 */
public class ListDefaultExpensesCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalExpenseList(), new BudgetList(),
            getTypicalExchangeData(), new UserPrefs());
        expectedModel = new ModelManager(model.getExpenseList(), new BudgetList(),
            model.getExchangeData(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListDefaultExpensesCommand(), model, ListDefaultExpensesCommand.MESSAGE_SUCCESS,
            expectedModel, commandHistory);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showExpenseAtIndex(model, INDEX_FIRST_ITEM);
        assertCommandSuccess(new ListDefaultExpensesCommand(), model, ListDefaultExpensesCommand.MESSAGE_SUCCESS,
            expectedModel, commandHistory);
    }
}
