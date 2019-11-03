package seedu.address.logic.commands.expense;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.address.testutil.TypicalExpenses.getTypicalMooLah;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelHistory;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListExpensesCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(model.getMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListExpensesCommand(), model, ListExpensesCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void run_listIsFiltered_showsEverything() {
        showExpenseAtIndex(model, INDEX_FIRST_EXPENSE);
        assertCommandSuccess(new ListExpensesCommand(), model, ListExpensesCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
