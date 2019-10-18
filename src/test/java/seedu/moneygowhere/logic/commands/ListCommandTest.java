package seedu.moneygowhere.logic.commands;

import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.showSpendingAtIndex;
import static seedu.moneygowhere.testutil.TypicalIndexes.INDEX_FIRST_SPENDING;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getSpendingBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showSpendingAtIndex(model, INDEX_FIRST_SPENDING);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
