package seedu.ichifund.logic.commands;

import static seedu.ichifund.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ichifund.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.ichifund.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.ichifund.testutil.TypicalPersons.getTypicalFundBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ichifund.model.Model;
import seedu.ichifund.model.ModelManager;
import seedu.ichifund.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFundBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getFundBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
