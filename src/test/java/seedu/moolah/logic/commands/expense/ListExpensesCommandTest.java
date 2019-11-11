package seedu.moolah.logic.commands.expense;

import static seedu.moolah.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moolah.logic.commands.CommandTestUtil.showExpenseAtIndex;
import static seedu.moolah.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.moolah.testutil.TypicalMooLah.getTypicalMooLah;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moolah.model.Model;
import seedu.moolah.model.ModelManager;
import seedu.moolah.model.UserPrefs;
import seedu.moolah.model.modelhistory.ModelHistory;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */

public class ListExpensesCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
        expectedModel = new ModelManager(getTypicalMooLah(), new UserPrefs(), new ModelHistory());
    }

    @Test
    public void run_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListExpensesCommand(), model, ListExpensesCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void run_listIsFiltered_showsEverything() {
        showExpenseAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListExpensesCommand(), model, ListExpensesCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
